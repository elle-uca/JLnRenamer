package org.ln.java.renamer.media;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * Modello per JTable che mostra i risultati di analisi media.
 * Colonne: seleziona, originale, nuovo nome (editabile), artista, album, titolo, esito
 */
@SuppressWarnings("serial")
public class MediaTableModel extends AbstractTableModel {
    private final String[] columns = {"Seleziona", "File originale", "Nuovo nome", "Artista", "Album", "Titolo", "Esito"};
    private final List<MediaUtils.SimulationResult> results = new ArrayList<>();
    private final List<Boolean> selected = new ArrayList<>();

    public void loadFromFolder(File folder) {
        results.clear();
        selected.clear();
        if (folder != null && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isFile()) {
                        MediaUtils.SimulationResult r = MediaUtils.analyzeFile(f, true); // simulazione
                        results.add(r);
                        selected.add(Boolean.TRUE);
                    }
                }
            }
        }
        fireTableDataChanged();
    }

    public List<MediaUtils.SimulationResult> getResults() {
        return results;
    }

    public List<MediaUtils.SimulationResult> getSelectedResults() {
        List<MediaUtils.SimulationResult> out = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            if (selected.get(i)) out.add(results.get(i));
        }
        return out;
    }

    @Override
    public int getRowCount() {
        return results.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) return Boolean.class;
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // consentiamo di modificare la selezione e il "Nuovo nome"
        return columnIndex == 0 || columnIndex == 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MediaUtils.SimulationResult r = results.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> selected.get(rowIndex);
            case 1 -> r.file.getName();
            case 2 -> r.newName;
            case 3 -> r.artist;
            case 4 -> r.album;
            case 5 -> r.title;
            case 6 -> (r.error.isEmpty() ? (r.missingTags ? "Simulato (tag aggiunti)" : "OK") : "Errore: " + r.error);
            default -> "";
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        MediaUtils.SimulationResult r = results.get(rowIndex);
        if (columnIndex == 0) {
            selected.set(rowIndex, (Boolean) aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
        } else if (columnIndex == 2) {
            String newName = aValue == null ? r.newName : aValue.toString();
            r.newName = newName;
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }
}
