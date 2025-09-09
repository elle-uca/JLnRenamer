package org.ln.java.renamer;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ln.java.renamer.Costants.FileStatus;

public class FileRenamer {

    /**
     * Rinomina i file specificati nella mappa newNames, se non ci sono conflitti.
     *
     * @param directory cartella in cui si trovano i file
     * @param newNames mappa file -> nuovo nome (solo i file da rinominare)
     */
    public static void renameFiles(File directory, Map<File, String> newNames) {
        if (directory == null || !directory.isDirectory()) {
            System.out.println("❌ La cartella non è valida.");
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("❌ Impossibile leggere i file dalla cartella.");
            return;
        }

        // Nomi già esistenti
        Set<String> existingNames = new HashSet<>();
        for (File f : files) {
            existingNames.add(f.getName());
        }

        // Controllo conflitti
        Set<String> usedNewNames = new HashSet<>();
        boolean conflictFound = false;

        for (Map.Entry<File, String> entry : newNames.entrySet()) {
            File oldFile = entry.getKey();
            String newName = entry.getValue();

            // 1. conflitto con file esistenti (ma non se è lo stesso file)
            if (existingNames.contains(newName) && !oldFile.getName().equals(newName)) {
                System.out.println("❌ Conflitto: " + newName + " esiste già nella cartella.");
                conflictFound = true;
            }

            // 2. duplicati nella lista dei nuovi nomi
            if (!usedNewNames.add(newName)) {
                System.out.println("❌ Conflitto: il nuovo nome " + newName + " è duplicato.");
                conflictFound = true;
            }
        }

        // Se non ci sono conflitti → eseguo la rinomina
        if (!conflictFound) {
            for (Map.Entry<File, String> entry : newNames.entrySet()) {
                File oldFile = entry.getKey();
                File newFile = new File(directory, entry.getValue());

                boolean success = oldFile.renameTo(newFile);
                if (success) {
                    System.out.println("✅ Rinominato: " + oldFile.getName() + " -> " + newFile.getName());
                } else {
                    System.out.println("⚠️ Impossibile rinominare: " + oldFile.getName());
                }
            }
        }
    }

    
    
    public static boolean checkConflict(File directory, Map<File, String> newNames) {
        if (directory == null || !directory.isDirectory()) {
            System.out.println("❌ La cartella non è valida.");
            return true;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("❌ Impossibile leggere i file dalla cartella.");
            return true;
        }
    	
        // Nomi già esistenti
        Set<String> existingNames = new HashSet<>();
        for (File f : files) {
            existingNames.add(f.getName());
        }

        // Controllo conflitti
        Set<String> usedNewNames = new HashSet<>();
        boolean conflictFound = false;
        
        for (Map.Entry<File, String> entry : newNames.entrySet()) {
            File oldFile = entry.getKey();
            String newName = entry.getValue();

            // 1. conflitto con file esistenti (ma non se è lo stesso file)
            if (existingNames.contains(newName) && !oldFile.getName().equals(newName)) {
                System.out.println("❌ Conflitto: " + newName + " esiste già nella cartella.");
                conflictFound = true;
            }

            // 2. duplicati nella lista dei nuovi nomi
            if (!usedNewNames.add(newName)) {
            	
                System.out.println("❌ Conflitto: il nuovo nome " + newName + " è duplicato.");
                conflictFound = true;
            }
        }
		return conflictFound;
    }
    
    
    public static boolean checkConflicts(File directory, Map<RnFile, String> newNames) {


        File[] files = directory.listFiles();

    	
        // Nomi già esistenti
        Set<String> existingNames = new HashSet<>();
        for (File f : files) {
            existingNames.add(f.getName());
        }

        // Controllo conflitti
        Set<String> usedNewNames = new HashSet<>();
        boolean conflictFound = false;
        
        for (Map.Entry<RnFile, String> entry : newNames.entrySet()) {
            File oldFile = entry.getKey().getFrom();
            String newName = entry.getValue();
            System.out.println("====== vecchio nome " + oldFile.getName() );
            System.out.println("====== nuovo nome " + newName );
           
            // 1. conflitto con file esistenti (ma non se è lo stesso file)
            if (existingNames.contains(newName) && !oldFile.getName().equals(newName)) {
                System.out.println("❌ Conflitto: " + newName + " esiste già nella cartella.");
                conflictFound = true;
            }

            // 2. duplicati nella lista dei nuovi nomi
            if (!usedNewNames.add(newName)) {
            	 entry.getKey().setFileStatus(FileStatus.KO);
                System.out.println("❌ Conflitto: il nuovo nome " + newName + " è duplicato.");
                conflictFound = true;
            }
        }
		return conflictFound;
    }
    
    
    // Esempio di utilizzo
    public static void main(String[] args) {
        File directory = new File("C:/mia_cartella");

        // creo la mappa solo con i file da rinominare
        Map<File, String> newNames = new HashMap<>();
        File[] files = directory.listFiles();

        if (files != null && files.length >= 2) {
            newNames.put(files[0], "report.txt");
            newNames.put(files[1], "dati.csv");
        }

        renameFiles(directory, newNames);
    }
}
