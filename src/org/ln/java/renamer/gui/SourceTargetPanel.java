package org.ln.java.renamer.gui;

import java.awt.event.ActionListener;
import java.util.function.Consumer;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 * Pannello riutilizzabile per selezionare cartelle di origine e destinazione.
 * Non apre JFileChooser, ma notifica eventi via listener o Consumer.
 */
@SuppressWarnings("serial")
public class SourceTargetPanel extends JPanel {

    private final JLabel sourceLabel;
    private final JLabel targetLabel;
    private final JTextField sourceField;
    private final JTextField targetField;
    private final JButton fc1;
    private final JButton fc2;

    // callback opzionali (lambda-friendly)
    private Consumer<String> onSourceChosen;
    private Consumer<String> onTargetChosen;

    public SourceTargetPanel() {
        super(new MigLayout("", "[][grow][]", "[][]"));

        sourceField = new JTextField();
        targetField = new JTextField();
        sourceLabel = new JLabel("Source dir");
        targetLabel = new JLabel("Target dir");
        fc1 = new JButton("...");
        fc2 = new JButton("...");

        add(sourceLabel, "cell 0 0");
        add(sourceField, "cell 1 0, growx, w :150:");
        add(fc1, "cell 2 0");

        add(targetLabel, "cell 0 1");
        add(targetField, "cell 1 1, growx, w :150:");
        add(fc2, "cell 2 1");

        // listener base che invoca i consumer se presenti
        fc1.addActionListener(e -> {
            if (onSourceChosen != null)
                onSourceChosen.accept(sourceField.getText());
        });
        fc2.addActionListener(e -> {
            if (onTargetChosen != null)
                onTargetChosen.accept(targetField.getText());
        });
    }

    // --- Getter e Setter campi testo ---
    public String getSourceFieldText() {
        return sourceField.getText();
    }

    public String getTargetFieldText() {
        return targetField.getText();
    }

    public void setSourceFieldText(String t) {
        sourceField.setText(t);
    }

    public void setTargetFieldText(String t) {
        targetField.setText(t);
    }

    /** Imposta lo stesso testo su entrambi i campi */
    public void setText(String t) {
        sourceField.setText(t);
        targetField.setText(t);
    }

    // --- Listener classici ---
    public void addFc1ActionListener(ActionListener l) {
        fc1.addActionListener(l);
    }

    public void addFc2ActionListener(ActionListener l) {
        fc2.addActionListener(l);
    }

    /** Aggiunge lo stesso listener a entrambi i bottoni */
    public void addFcActionListener(ActionListener l) {
        fc1.addActionListener(l);
        fc2.addActionListener(l);
    }

    // --- Listener "moderni" con Consumer ---
    public void onSourceChosen(Consumer<String> consumer) {
        this.onSourceChosen = consumer;
    }

    public void onTargetChosen(Consumer<String> consumer) {
        this.onTargetChosen = consumer;
    }

    // --- Accesso ai bottoni ---
    public JButton getFc1() {
        return fc1;
    }

    public JButton getFc2() {
        return fc2;
    }
}
