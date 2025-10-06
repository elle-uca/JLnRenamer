package org.ln.java.renamer.media;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * Interfaccia Swing per simulare e applicare rinominamenti basati su metadata.
 */
@SuppressWarnings("serial")
public class MediaRenamerUI extends JFrame {
    private final MediaTableModel tableModel = new MediaTableModel();
    private final JTable table = new JTable(tableModel);

    private final JTextField sourceField = new JTextField(30);
    private final JTextField targetField = new JTextField(30);

    private File sourceFolder;
    private File targetFolder;

    public MediaRenamerUI() {
        super("Media Renamer");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(6, 6));

        // Top panel: source / target selection + buttons
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton chooseSourceBtn = new JButton("Scegli cartella sorgente...");
        JButton chooseTargetBtn = new JButton("Scegli cartella destinazione...");
        JButton simulateBtn = new JButton("Simula");
        JButton applyBtn = new JButton("Applica modifiche");

        sourceField.setEditable(false);
        targetField.setEditable(false);

        top.add(chooseSourceBtn);
        top.add(sourceField);
        top.add(chooseTargetBtn);
        top.add(targetField);
        top.add(simulateBtn);
        top.add(applyBtn);

        add(top, BorderLayout.NORTH);

        // Table center
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Actions
        chooseSourceBtn.addActionListener(e -> chooseSource());
        chooseTargetBtn.addActionListener(e -> chooseTarget());
        simulateBtn.addActionListener(e -> simulate());
        applyBtn.addActionListener(e -> applyChanges());

        setVisible(true);
    }

    private void chooseSource() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            sourceFolder = chooser.getSelectedFile();
            sourceField.setText(sourceFolder.getAbsolutePath());
            // default target = source
            if (targetFolder == null) {
                targetFolder = sourceFolder;
                targetField.setText(targetFolder.getAbsolutePath());
            }
        }
    }

    private void chooseTarget() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            targetFolder = chooser.getSelectedFile();
            targetField.setText(targetFolder.getAbsolutePath());
        }
    }

    private void simulate() {
        if (sourceFolder == null) {
            JOptionPane.showMessageDialog(this, "Seleziona prima la cartella sorgente.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        tableModel.loadFromFolder(sourceFolder);
        JOptionPane.showMessageDialog(this, "Simulazione completata. Controlla la tabella (puoi modificare il 'Nuovo nome').");
    }

    private void applyChanges() {
        if (sourceFolder == null) {
            JOptionPane.showMessageDialog(this, "Seleziona prima la cartella sorgente.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (targetFolder == null) {
            targetFolder = sourceFolder;
            targetField.setText(targetFolder.getAbsolutePath());
        }

        List<MediaUtils.SimulationResult> toApply = tableModel.getSelectedResults();
        if (toApply.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessun file selezionato per l'applicazione.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Sei sicuro di applicare i rinominamenti selezionati?\n(Questa operazione sposterà/rinominerà i file.)",
                "Conferma", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        // Execute in background per non bloccare la GUI
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                // Prima: per ogni file, richiami analyzeFile(file, false) per scrivere tag mancanti
                for (MediaUtils.SimulationResult r : toApply) {
                    // scrive tag se mancanti (analisi non-simulazione)
                    MediaUtils.analyzeFile(r.file, false);
                }
                // Poi applica i rinominamenti usando i nuovi nomi eventualmente modificati nella tabella
                for (MediaUtils.SimulationResult r : toApply) {
                    // r.newName potrebbe essere stato editato nella tabella: model tiene il valore aggiornato
                    MediaUtils.applyRename(r, Path.of(targetFolder.getAbsolutePath()));
                }
                return null;
            }

            @Override
            protected void done() {
                JOptionPane.showMessageDialog(MediaRenamerUI.this, "Operazione completata.");
                // ricarica simulazione per mostrare lo stato corrente
                tableModel.loadFromFolder(sourceFolder);
            }
        };
        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MediaRenamerUI::new);
    }
}
