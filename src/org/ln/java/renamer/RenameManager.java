//package org.ln.java.renamer;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import org.ln.java.renamer.util.FileUtils;
//
//public class RenameManager {
//
//    private final List<RenameOperation> lastRenameSet = new ArrayList<>();
//
//    public void startNewRenameSet() {
//        lastRenameSet.clear();
//    }
//
//    public void addOperation(File source, File target) {
//        lastRenameSet.add(new RenameOperation(source, target));
//    }
//
//    /**
//     * Annulla le operazioni dell'ultimo set usando safeRename.
//     * Le operazioni vengono annullate in ordine inverso.
//     */
//    public void undoLastRenameSet() {
//        // Itera la lista al contrario per evitare conflitti di nomi durante l'annullamento
//        List<RenameOperation> reversedList = new ArrayList<>(lastRenameSet);
//        Collections.reverse(reversedList);
//
//        System.out.println("Avvio annullamento sicuro...");
//
//        for (RenameOperation op : reversedList) {
//            try {
//                // Il file da rinominare è il 'target' dell'operazione originale
//                File fileToUndo = op.getTarget();
//                // Il nuovo nome deve essere il nome del file 'source' originale
//                String originalName = op.getSource().getName();
//
//                if (fileToUndo.exists()) {
//                    System.out.println("Annullamento: riporto '" + fileToUndo.getName() + "' a '" + originalName + "'");
//                    
//                    // Chiama safeRename per eseguire l'annullamento
//                    // Passiamo 'null' come manager per non registrare l'operazione di undo
//                    FileUtils.safeRename(fileToUndo, originalName, FileUtils.RenameMode.FULL, null);
//                } else {
//                     System.out.println("Attenzione: il file " + fileToUndo.getName() + " non esiste più. Impossibile annullare.");
//                }
//
//            } catch (IOException e) {
//                // Gestisci l'errore come preferisci
//                System.err.println("Errore durante l'annullamento per il file: " + op.getTarget().getName());
//                e.printStackTrace();
//            }
//        }
//        
//        lastRenameSet.clear();
//        System.out.println("Annullamento completato.");
//    }
//}