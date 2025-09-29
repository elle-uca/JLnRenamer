package org.ln.java.renamer.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class FileSplitter {

    /**
     * Divide i file in sottocartelle con un numero massimo di file per cartella.
     *
     * @param sourceDir   la cartella sorgente
     * @param maxFiles    numero massimo di file per cartella
     * @param folderPrefix prefisso delle cartelle di output (es. "parte")
     * @throws IOException se ci sono errori di I/O
     */
    public static void splitByCount(String sourceDir, int maxFiles, String folderPrefix) throws IOException {
        File folder = new File(sourceDir);
        File[] files = folder.listFiles(File::isFile);

        if (files == null || files.length == 0) {
            System.out.println("Nessun file trovato.");
            return;
        }

        int folderIndex = 1;
        int fileCounter = 0;
        Path currentTarget = Paths.get(sourceDir, folderPrefix + folderIndex);
        Files.createDirectories(currentTarget);

        for (File file : files) {
            if (fileCounter >= maxFiles) {
                folderIndex++;
                currentTarget = Paths.get(sourceDir, folderPrefix + folderIndex);
                Files.createDirectories(currentTarget);
                fileCounter = 0;
            }

            Files.move(file.toPath(), currentTarget.resolve(file.getName()), 
            		StandardCopyOption.REPLACE_EXISTING);
            fileCounter++;
        }
    }

    /**
     * Divide i file in sottocartelle con una dimensione massima complessiva (in MB).
     *
     * @param sourceDir    la cartella sorgente
     * @param maxSizeMB    dimensione massima per cartella (in MB)
     * @param folderPrefix prefisso delle cartelle di output (es. "parte")
     * @throws IOException se ci sono errori di I/O
     */
    public static void splitBySize(String sourceDir, long maxSizeMB, 
    		String folderPrefix) throws IOException {
        long maxBytes = maxSizeMB * 1024 * 1024;

        File folder = new File(sourceDir);
        File[] files = folder.listFiles(File::isFile);

        if (files == null || files.length == 0) {
            System.out.println("Nessun file trovato.");
            return;
        }

        int folderIndex = 1;
        long currentSize = 0;
        Path currentTarget = Paths.get(sourceDir, folderPrefix + folderIndex);
        Files.createDirectories(currentTarget);

        for (File file : files) {
            long fileSize = file.length();

            if (currentSize + fileSize > maxBytes) {
                folderIndex++;
                currentTarget = Paths.get(sourceDir, folderPrefix + folderIndex);
                Files.createDirectories(currentTarget);
                currentSize = 0;
            }

            Files.move(file.toPath(), currentTarget.resolve(file.getName()), 
            		StandardCopyOption.REPLACE_EXISTING);
            currentSize += fileSize;
        }
    }

    // ESEMPIO DI UTILIZZO
    public static void main(String[] args) {
        try {
            String path = "C:/temp/files";

            // Dividi in cartelle da 20 file con prefisso "parte"
            splitByCount(path, 20, "parte");

            // Oppure dividi in cartelle da 20MB con prefisso "parte"
            // splitBySize(path, 20, "parte");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
