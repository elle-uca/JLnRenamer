package org.ln.java.renamer.gui.panel;

import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

import org.ln.java.renamer.Controller;
import org.ln.java.renamer.RnPrefs;
import org.ln.java.renamer.gui.AccordionPanel;
import org.ln.java.renamer.gui.SourceTargetPanel;
import org.ln.java.renamer.util.SplitMergeUtils;
import org.ln.java.renamer.util.SplitMergeUtils.MergeResult;

import net.miginfocom.swing.MigLayout;

/**
 * Pannello per eseguire unione (merge) di cartelle con simulazione.
 * Usa SourceTargetPanel per input directory.
 */
@SuppressWarnings("serial")
public class MergePanel extends AbstractPanelContent {

    private boolean move = true;
    private ButtonGroup group;
    private JRadioButton jrbMove;
    private JRadioButton jrbCopy;
    private JButton go;
    private SourceTargetPanel stp;

    public MergePanel(AccordionPanel accordion) {
        super(accordion);
    }

    @Override
    void initComponents() {
        stp = new SourceTargetPanel();
        jrbMove = new JRadioButton("Sposta", true);
        jrbCopy = new JRadioButton("Copia");

        group = new ButtonGroup();
        group.add(jrbMove);
        group.add(jrbCopy);

        go = new JButton("Go");

        // Listener moderni (Consumer)
        stp.onSourceChosen(t -> chooseDirectory(true));
        stp.onTargetChosen(t -> chooseDirectory(false));

        jrbMove.addActionListener(this);
        jrbCopy.addActionListener(this);

        go.addActionListener(e -> runMergeSimulation());

        setLayout(new MigLayout("", "[][grow][]", "20[][][]20"));
        add(stp, 		"cell 0 0 3 1, growx");
        add(jrbMove, 	"cell 0 1");
        add(jrbCopy, 	"cell 1 1");
        add(go, 		"cell 0 2");
    }

    @Override
    void updateView() {
        move = jrbMove.isSelected();
    }

    /** Mostra JFileChooser e aggiorna il campo corretto */
    private void chooseDirectory(boolean isSource) {
        JFileChooser fc = Controller.getFileChooser(JFileChooser.DIRECTORIES_ONLY, false);
        int returnVal = fc.showOpenDialog(null);
        if (returnVal != JFileChooser.APPROVE_OPTION)
            return;

        String path = fc.getSelectedFile().getAbsolutePath();
        if (isSource) {
            stp.setSourceFieldText(path);
        } else {
            stp.setTargetFieldText(path);
        }

        RnPrefs.saveLastDir(path);
    }

    /** Esegue la simulazione merge */
    private void runMergeSimulation() {
        try {
            MergeResult simulation = SplitMergeUtils.simulateMerge(
                    stp.getSourceFieldText(), stp.getTargetFieldText());
            SwingUtilities.invokeLater(() -> SplitMergeUtils.showSimulation(simulation, move));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore durante la simulazione:\n" + e.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}
