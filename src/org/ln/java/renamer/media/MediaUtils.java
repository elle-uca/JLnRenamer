package org.ln.java.renamer.media;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.*;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.jaudiotagger.tag.mp4.Mp4Tag;
import org.jaudiotagger.tag.vorbiscomment.VorbisCommentTag;

import java.io.File;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * MediaUtils: utilità per leggere metadata, simulare e applicare rinomine e scrittura tag.
 */
public class MediaUtils {

    private static final boolean DEBUG = false;

    // ------------------------------
    // Public API
    // ------------------------------

    /**
     * Analizza un file e ritorna la SimulationResult.
     *
     * @param file     file da analizzare
     * @param simulate true = solo simulazione (non scrive tag)
     * @return SimulationResult con nuovo nome proposto e info
     */
    public static SimulationResult analyzeFile(File file, boolean simulate) {
        String ext = getExtension(file).toLowerCase();
        SimulationResult result = new SimulationResult(file);

        try {
            if (isAudio(ext)) {
                handleAudio(file, ext, simulate, result);
            } else if (isImage(ext)) {
                result.newName = getImageName(file, ext);
            } else if (isVideo(ext)) {
                result.newName = getVideoName(file, ext);
            } else {
                result.newName = file.getName();
            }
        } catch (Exception e) {
            result.error = e.getMessage();
            if (DEBUG) e.printStackTrace();
        }

        return result;
    }

    /**
     * Rinomina (sposta) il file in base al newName di result.
     *
     * @param result    SimulationResult (deve avere result.newName)
     * @param targetDir directory di destinazione (può essere la stessa)
     * @return Path del nuovo file o null se errore
     */
    public static Path applyRename(SimulationResult result, Path targetDir) {
        if (result == null || result.newName == null || result.newName.isEmpty()) {
            System.err.println("Nessun nuovo nome per " + (result == null ? "null" : result.file.getName()));
            return null;
        }
        try {
            Path source = result.file.toPath();
            Files.createDirectories(targetDir);

            Path target = targetDir.resolve(result.newName);
            target = ensureUnique(target);

            // se equals -> niente da fare
            if (Files.exists(target) && Files.isSameFile(source, target)) {
                if (DEBUG) System.out.println(source.getFileName() + " già al posto giusto.");
                return source;
            }

            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            if (DEBUG) System.out.println("Rinominato: " + source.getFileName() + " -> " + target.getFileName());
            return target;
        } catch (Exception e) {
            System.err.println("Errore rinominando " + result.file.getName() + ": " + e.getMessage());
            if (DEBUG) e.printStackTrace();
            return null;
        }
    }

    /**
     * Applica rinominamento a tutti i risultati in lista.
     *
     * @param results   lista dei risultati simulati
     * @param targetDir directory di destinazione
     */
    public static void applyAll(List<SimulationResult> results, Path targetDir) {
        if (results == null || results.isEmpty()) {
            System.out.println("Nessun file da processare.");
            return;
        }
        for (SimulationResult r : results) {
            applyRename(r, targetDir);
        }
        System.out.println("Rinominamento completato.");
    }

    // ------------------------------
    // Internals: audio/image/video handling
    // ------------------------------

    private static void handleAudio(File file, String ext, boolean simulate, SimulationResult result) {
        String artist = "";
        String album = "";
        String title = "";

        try {
            AudioFile audioFile = AudioFileIO.read(file);
            Tag tag = audioFile.getTag();

            if (tag != null) {
                artist = safeGet(tag, FieldKey.ARTIST);
                album = safeGet(tag, FieldKey.ALBUM);
                title = safeGet(tag, FieldKey.TITLE);
            }

            // Se mancano i tag principali, prova dall'filename
            if (isEmpty(artist) && isEmpty(title)) {
                String baseName = removeExtension(file.getName());
                String[] parts = baseName.split(" - ");

                if (parts.length >= 3) {
                    artist = parts[0].trim();
                    album = parts[1].trim();
                    title = parts[2].trim();
                } else if (parts.length == 2) {
                    artist = parts[0].trim();
                    title = parts[1].trim();
                } else {
                    title = baseName.trim();
                }

                result.missingTags = true;
                result.extractedFromFilename = true;

                if (!simulate) {
                    writeAudioTags(file, artist, album, title, ext);
                }
            }

            result.artist = artist;
            result.album = album;
            result.title = title;

            // Compone il nuovo nome
            StringBuilder newName = new StringBuilder();
            if (!isEmpty(artist)) newName.append(artist);
            if (!isEmpty(album)) newName.append(" - ").append(album);
            if (!isEmpty(title)) newName.append(" - ").append(title);
            if (newName.length() == 0) newName.append(removeExtension(file.getName()));

            result.newName = sanitize(newName.toString()) + (ext.isEmpty() ? "" : "." + ext);

        } catch (Exception e) {
            result.error = "Errore: " + e.getMessage();
            if (DEBUG) e.printStackTrace();
        }
    }

    private static void writeAudioTags(File file, String artist, String album, String title, String ext) {
        try {
            AudioFile audioFile = AudioFileIO.read(file);
            Tag tag = audioFile.getTag();

            if (tag == null) {
                // crea un tag adeguato al formato
                if ("mp3".equalsIgnoreCase(ext)) {
                    tag = new ID3v24Tag();
                } else if ("m4a".equalsIgnoreCase(ext) || "aac".equalsIgnoreCase(ext)) {
                    tag = new Mp4Tag();
                } else if ("ogg".equalsIgnoreCase(ext) || "flac".equalsIgnoreCase(ext)) {
                    tag = VorbisCommentTag.createNewTag();
                } else {
                    tag = new ID3v24Tag(); // fallback
                }
                audioFile.setTag(tag);
            }

            if (!isEmpty(artist)) tag.setField(FieldKey.ARTIST, artist);
            if (!isEmpty(album)) tag.setField(FieldKey.ALBUM, album);
            if (!isEmpty(title)) tag.setField(FieldKey.TITLE, title);

            audioFile.commit();

            if (DEBUG) System.out.println("Tag scritti su " + file.getName());
        } catch (Exception e) {
            if (DEBUG) System.err.println("Errore scrittura tag " + file.getName() + ": " + e.getMessage());
        }
    }

    private static String getImageName(File file, String ext) throws Exception {
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        ExifSubIFDDirectory exif = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

        if (exif != null) {
            Date date = exif.getDateOriginal();
            if (date != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                return sdf.format(date) + (ext.isEmpty() ? "" : "." + ext);
            }
        }
        return removeExtension(file.getName()) + (ext.isEmpty() ? "" : "." + ext);
    }

    private static String getVideoName(File file, String ext) throws Exception {
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        for (Directory dir : metadata.getDirectories()) {
            for (com.drew.metadata.Tag tag : dir.getTags()) {
                String tagName = tag.getTagName().toLowerCase();
                String desc = tag.getDescription();
                if (desc != null && (tagName.contains("create") || tagName.contains("date") || tagName.contains("time"))) {
                    return sanitize(desc.replace(":", "-").replace(" ", "_")) + (ext.isEmpty() ? "" : "." + ext);
                }
            }
        }
        return removeExtension(file.getName()) + (ext.isEmpty() ? "" : "." + ext);
    }

    // ------------------------------
    // Utilities
    // ------------------------------

    public static Path ensureUnique(Path target) {
        int counter = 1;
        Path parent = target.getParent();
        String name = target.getFileName().toString();
        String base = removeExtension(name);
        String ext = getExtension(name);
        String extDot = ext.isEmpty() ? "" : "." + ext;

        while (Files.exists(target)) {
            target = parent.resolve(base + "_" + counter + extDot);
            counter++;
        }
        return target;
    }

    public static String getExtension(File file) {
        return getExtension(file.getName());
    }

    public static String getExtension(String name) {
        int dot = name.lastIndexOf('.');
        return (dot == -1) ? "" : name.substring(dot + 1);
    }

    public static String removeExtension(String name) {
        int dot = name.lastIndexOf('.');
        return (dot == -1) ? name : name.substring(0, dot);
    }

    public static String sanitize(String name) {
        return name.replaceAll("[\\\\/:*?\"<>|]", "_").trim();
    }

    private static String safeGet(Tag tag, FieldKey key) {
        try {
            String val = tag.getFirst(key);
            return val == null ? "" : val.trim();
        } catch (Exception e) {
            return "";
        }
    }

    private static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static boolean isImage(String ext) {
        return ext.matches("(?i)jpg|jpeg|png|tif|tiff|heic|heif");
    }

    private static boolean isAudio(String ext) {
        return ext.matches("(?i)mp3|flac|m4a|aac|ogg|wav");
    }

    private static boolean isVideo(String ext) {
        return ext.matches("(?i)mp4|mov|avi|mkv|wmv|3gp");
    }

    // ------------------------------
    // Simulation result
    // ------------------------------
    public static class SimulationResult {
        public File file;
        public String newName;
        public String artist = "";
        public String album = "";
        public String title = "";
        public boolean missingTags = false;
        public boolean extractedFromFilename = false;
        public String error = "";

        public SimulationResult(File file) {
            this.file = file;
        }

        @Override
        public String toString() {
            if (!error.isEmpty()) return "ERRORE " + file.getName() + " -> " + error;
            String base = file.getName() + " -> " + newName;
            if (missingTags) base += " [tag aggiunti]";
            return base;
        }
    }
}
