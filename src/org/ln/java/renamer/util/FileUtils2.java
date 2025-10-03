package org.ln.java.renamer.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class FileUtils2 {

    public enum RenameMode {
        BASENAME_ONLY,   // rinomina solo la parte prima del punto
        EXTENSION_ONLY,  // rinomina solo estensione
        FULL_NAME        // rinomina tutto
    }

    /**
     * Rinomina un file o directory in modo sicuro, con modalità personalizzabile.
     *
     * @param fileOrDir  file o directory da rinominare
     * @param newName    nuovo nome o nuova estensione (a seconda della modalità)
     * @param mode       modalità di rinomina
     * @return           nuovo file rinominato
     * @throws IOException se ci sono errori di I/O
     */
    public static File safeRenameAdvanced(File fileOrDir, String newName, RenameMode mode) throws IOException {
        Path source = fileOrDir.toPath();

        String finalName;

        if (fileOrDir.isDirectory()) {
            // Per directory ignoriamo le estensioni
            finalName = (mode == RenameMode.EXTENSION_ONLY)
                    ? fileOrDir.getName() // nessun cambio se EXTENSION_ONLY
                    : newName;
        } else {
            String oldName = fileOrDir.getName();
            int dot = oldName.lastIndexOf(".");
            String base = (dot == -1) ? oldName : oldName.substring(0, dot);
            String ext = (dot == -1) ? "" : oldName.substring(dot); // con punto

            switch (mode) {
                case BASENAME_ONLY -> finalName = newName + ext;
                case EXTENSION_ONLY -> {
                    if (!newName.startsWith(".")) {
                        newName = "." + newName;
                    }
                    finalName = base + newName;
                }
                case FULL_NAME -> finalName = newName;
                default -> throw new IllegalArgumentException("Modalità sconosciuta");
            }
        }

        Path target = source.resolveSibling(finalName);

        // Gestione conflitti
        int counter = 1;
        String tempName = finalName;
        while (Files.exists(target)) {
            int dot = finalName.lastIndexOf(".");
            if (dot != -1 && fileOrDir.isFile()) {
                String b = finalName.substring(0, dot);
                String e = finalName.substring(dot);
                tempName = b + "_" + counter + e;
            } else {
                tempName = finalName + "_" + counter;
            }
            target = source.resolveSibling(tempName);
            counter++;
        }

        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        return target.toFile();
    }

    // ESEMPIO DI UTILIZZO
    public static void main(String[] args) {
        try {
            File file1 = new File("/home/luke/test1/prova.txt");

            // Rinomina solo il nome base
            File r1 = safeRenameAdvanced(file1, "documento", RenameMode.BASENAME_ONLY);
            System.out.println("Rinominato in: " + r1.getName());

            // Rinomina solo l’estensione
            File r2 = safeRenameAdvanced(r1, "pdf", RenameMode.EXTENSION_ONLY);
            System.out.println("Rinominato in: " + r2.getName());

            // Rinomina tutto (anche l’estensione)
            File r3 = safeRenameAdvanced(r2, "finale.docx", RenameMode.FULL_NAME);
            System.out.println("Rinominato in: " + r3.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
