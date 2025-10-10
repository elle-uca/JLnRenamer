package org.ln.java.renamer.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.ln.java.renamer.Costants.FillOption;
import org.ln.java.renamer.gui.panel.TagPanel;

/**
 * Un pannello che combina una JComboBox e una JIntegerSpinner per gestire opzioni di riempimento.
 * La JIntegerSpinner è editabile solo quando la JComboBox ha selezionato un valore
 * che richiede un input numerico.
 */
@SuppressWarnings("serial")
public class FillOptionsPanel extends JPanel {

    private JComboBox<FillOption> fillComboBox;
    private JIntegerSpinner fillValue;
    private JLabel fillLabel;

    /**
     * 
     */
    public FillOptionsPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        fillComboBox = new JComboBox<>(FillOption.values());
        fillValue = new JIntegerSpinner(1, 0, 100, 1);
        fillLabel = new JLabel("");
        fillComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTextFieldState();
            }
        });

        add(fillComboBox);
        add(fillValue);
        add(fillLabel);
        updateTextFieldState();
    }

    /**
     * Controlla l'elemento selezionato nella ComboBox e aggiorna lo stato
     * della JTextField (abilitata/disabilitata e vuota).
     */
    private void updateTextFieldState() {
        FillOption selectedOption = (FillOption) fillComboBox.getSelectedItem();

        if (selectedOption == null) {
            return; 
        }

        switch (selectedOption) {
            case FILL_TO_ZERO:
            	fillValue.setEnabled(true);
                fillValue.setValue(1);
                fillLabel.setText("zeri");
                break;        	
            	
            case FILL_TO_NUMBER:
            	fillValue.setEnabled(true);
                fillValue.setValue(1);
                fillLabel.setText("cifre");
                break;
            
            case NO_FILL:
            default:
                fillValue.setEnabled(false);
                fillValue.setValue(0);
                fillLabel.setText("");
                break;
        }
     }
    
    
	public void addChangeListener(TagPanel tagPanel) {
		fillValue.addChangeListener(tagPanel);
	}

    // Metodi pubblici per interagire con il pannello dall'esterno (opzionale ma utile)
    public FillOption getSelectedOption() {
        return (FillOption) fillComboBox.getSelectedItem();
    }

    public int getIntValue() {
        return fillValue.getIntValue();
    }
    
    
    public String getStringValue() {
        return  String.valueOf(fillValue.getIntValue());
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