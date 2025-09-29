package org.ln.java.renamer.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class FileMerger {

    /**
     * Riporta tutti i file da più sottocartelle in una cartella unica.
     *
     * @param parentDir  cartella che contiene le sottocartelle da unire
     * @param targetDir  cartella di destinazione (può essere uguale a parentDir o nuova)
     * @param move       true = sposta i file, false = copia i file
     * @throws IOException se ci sono errori di I/O
     */
    public static void mergeFolders(String parentDir, String targetDir, boolean move) throws IOException {
        File parent = new File(parentDir);
        File[] subDirs = parent.listFiles(File::isDirectory);

        if (subDirs == null || subDirs.length == 0) {
            System.out.println("Nessuna sottocartella trovata in " + parentDir);
            return;
        }

        Path targetPath = Paths.get(targetDir);
        Files.createDirectories(targetPath);

        for (File subDir : subDirs) {
            File[] files = subDir.listFiles(File::isFile);
            if (files == null) continue;

            for (File file : files) {
                Path targetFile = targetPath.resolve(file.getName());

                // Se esiste già un file con lo stesso nome, rinomina aggiungendo un suffisso
                int counter = 1;
                while (Files.exists(targetFile)) {
                    String name = file.getName();
                    int dot = name.lastIndexOf(".");
                    String base = (dot == -1) ? name : name.substring(0, dot);
                    String ext = (dot == -1) ? "" : name.substring(dot);
                    targetFile = targetPath.resolve(base + "_" + counter + ext);
                    counter++;
                }

                if (move) {
                    Files.move(file.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    Files.copy(file.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        System.out.println("Merge completato in: " + targetDir);
    }

    // ESEMPIO DI UTILIZZO
    public static void main(String[] args) {
        try {
            String parentDir = "C:/temp/files";     // contiene parte1, parte2, ...
            String targetDir = "C:/temp/merged";    // cartella di destinazione

            mergeFolders(parentDir, targetDir, true); // true = sposta, false = copia

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
