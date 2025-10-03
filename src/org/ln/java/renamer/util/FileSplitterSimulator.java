package org.ln.java.renamer.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class FileSplitterSimulator {

    /**
     * Simulazione suddivisione per numero file.
     */
    public static Map<String, List<File>> simulateSplitByCount(String sourceDir, int maxFiles, String folderPrefix) {
        Map<String, List<File>> simulation = new LinkedHashMap<>();

        File folder = new File(sourceDir);
        File[] files = folder.listFiles(File::isFile);
        if (files == null || files.length == 0) return simulation;

        int folderIndex = 1;
        int fileCounter = 0;
        String currentFolder = folderPrefix + folderIndex;
        simulation.put(currentFolder, new ArrayList<>());

        for (File file : files) {
            if (fileCounter >= maxFiles) {
                folderIndex++;
                currentFolder = folderPrefix + folderIndex;
                simulation.put(currentFolder, new ArrayList<>());
                fileCounter = 0;
            }
            simulation.get(currentFolder).add(file);
            fileCounter++;
        }
        return simulation;
    }

    /**
     * Simulazione suddivisione per dimensione (MB).
     */
    public static Map<String, List<File>> simulateSplitBySize(String sourceDir, long maxSizeMB, String folderPrefix) {
        Map<String, List<File>> simulation = new LinkedHashMap<>();
        long maxBytes = maxSizeMB * 1024 * 1024;

        File folder = new File(sourceDir);
        File[] files = folder.listFiles(File::isFile);
        if (files == null || files.length == 0) return simulation;

        int folderIndex = 1;
        long currentSize = 0;
        String currentFolder = folderPrefix + folderIndex;
        simulation.put(currentFolder, new ArrayList<>());

        for (File file : files) {
            long fileSize = file.length();

            if (currentSize + fileSize > maxBytes) {
                folderIndex++;
                currentFolder = folderPrefix + folderIndex;
                simulation.put(currentFolder, new ArrayList<>());
                currentSize = 0;
            }
            simulation.get(currentFolder).add(file);
            currentSize += fileSize;
        }
        return simulation;
    }

    /**
     * Applica realmente lo split in base a una simulazione.
     */
    public static void applySplit(String sourceDir, Map<String, List<File>> simulation) throws IOException {
        for (Map.Entry<String, List<File>> entry : simulation.entrySet()) {
            Path targetDir = Paths.get(sourceDir, entry.getKey());
            Files.createDirectories(targetDir);

            for (File file : entry.getValue()) {
                Files.move(file.toPath(), targetDir.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    /**
     * Mostra la simulazione in una JTable.
     * @param path 
     */
    public static void showSimulationTable(String path, Map<String, List<File>> simulation) {
        String[] columns = {"Cartella", "Nome File", "Dimensione (KB)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Map.Entry<String, List<File>> entry : simulation.entrySet()) {
            String folder = entry.getKey();
            for (File file : entry.getValue()) {
                model.addRow(new Object[]{
                        folder,
                        file.getName(),
                        file.length() / 1024
                });
            }
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        int result = JOptionPane.showConfirmDialog(
                null,
                scrollPane,
                "Simulazione suddivisione",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            int confirm = JOptionPane.showConfirmDialog(null, "Vuoi applicare davvero lo split?");
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    applySplit(path, simulation); 
                    JOptionPane.showMessageDialog(null, "Split completato!");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Errore: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        String path = "R:\\02_Resi\\test\\Sapidata\\PM_Firenze\\Da_inviare";

        // Simulazione (scegli tu se per numero o per dimensione)
        Map<String, List<File>> simulation = simulateSplitByCount(path, 10, "parte");
      //  Map<String, List<File>> simulation = simulateSplitBySize(path, 20, "parte");

        SwingUtilities.invokeLater(() -> showSimulationTable(path, simulation));
    }
}
