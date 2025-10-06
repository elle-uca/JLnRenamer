package org.ln.java.renamer.util;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Un pannello che combina una JComboBox e una JTextField per gestire opzioni di riempimento.
 * La JTextField è editabile solo quando la JComboBox ha selezionato un valore
 * che richiede un input numerico.
 */
@SuppressWarnings("serial")
public class FillOptionsPanel extends JPanel {

    // 1. Definiamo gli stati possibili con un Enum per chiarezza e sicurezza
    public enum FillOption {
        NO_FILL("Nessun riempimento"),
        FILL_TO_ZERO("Riempi con zeri"),
        FILL_TO_NUMBER("Riempi fino a");

        private final String displayName;

        FillOption(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName; // Questo testo apparirà nella JComboBox
        }
    }

    private JComboBox<FillOption> fillComboBox;
    private JTextField valueTextField;

    public FillOptionsPanel() {
        // Usiamo un FlowLayout per disporre i componenti uno accanto all'altro
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        // 2. Inizializziamo i componenti
        fillComboBox = new JComboBox<>(FillOption.values());
        valueTextField = new JTextField(10); // Imposta una larghezza di default (es. 10 colonne)

        // 3. Aggiungiamo il "controllore" (ActionListener) alla ComboBox
        fillComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Questo metodo viene chiamato ogni volta che si cambia la selezione
                updateTextFieldState();
            }
        });

        // 4. Aggiungiamo i componenti al pannello
        this.add(fillComboBox);
        this.add(valueTextField);

        // 5. Impostiamo lo stato iniziale corretto
        updateTextFieldState();
    }

    /**
     * Controlla l'elemento selezionato nella ComboBox e aggiorna lo stato
     * della JTextField (abilitata/disabilitata e vuota).
     */
    private void updateTextFieldState() {
        // Ottieni l'opzione attualmente selezionata
        FillOption selectedOption = (FillOption) fillComboBox.getSelectedItem();

        if (selectedOption == null) {
            return; // Sicurezza, non dovrebbe mai accadere
        }

        // Switch per decidere cosa fare in base alla selezione
        switch (selectedOption) {
            case FILL_TO_ZERO:
            case FILL_TO_NUMBER:
                // Se l'utente deve inserire un valore, abilita il campo di testo
                valueTextField.setEnabled(true);
                break;
            
            case NO_FILL:
            default:
                // Altrimenti, disabilitalo e puliscilo
                valueTextField.setEnabled(false);
                valueTextField.setText(""); // Svuota il campo per evitare confusione
                break;
        }
    }

    // Metodi pubblici per interagire con il pannello dall'esterno (opzionale ma utile)
    public FillOption getSelectedOption() {
        return (FillOption) fillComboBox.getSelectedItem();
    }

    public String getEnteredValue() {
        if (valueTextField.isEnabled()) {
            return valueTextField.getText();
        }
        return null; // O una stringa vuota, a seconda della logica della tua applicazione
    }

    /**
     * Metodo main per testare rapidamente il componente in una finestra.
     */
    public static void main(String[] args) {
        // Eseguiamo l'interfaccia grafica nel thread corretto (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test JComboBox con JTextField");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // Creiamo un'istanza del nostro pannello personalizzato
            FillOptionsPanel optionsPanel = new FillOptionsPanel();
            
            frame.getContentPane().add(optionsPanel, BorderLayout.NORTH);
            frame.pack(); // Dimensiona la finestra per adattarsi al contenuto
            frame.setLocationRelativeTo(null); // Centra la finestra
            frame.setVisible(true);
        });
    }
}