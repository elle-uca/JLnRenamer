package org.ln.java.renamer.gui.panel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ln.java.renamer.Controller;
import org.ln.java.renamer.Costants.FillOption;
import org.ln.java.renamer.gui.JIntegerSpinner;

/**
 * A panel that combines a JComboBox and a 
 * JIntegerSpinner to manage fill options.
 * The JIntegerSpinner is only editable when 
 * the JComboBox has selected a value that 
 * requires numeric input.
 */
@SuppressWarnings("serial")
public class FillOptionsPanel extends JPanel {

    private JComboBox<FillOption> fillComboBox;
    private JIntegerSpinner fillValue;
    private JLabel fillLabel;
    protected ResourceBundle bundle = Controller.getBundle();

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
            	//updateFillOption((FillOption) fillComboBox.getSelectedItem());
            	updateTextFieldState();
            }
        });

        add(fillComboBox);
        add(fillValue);
        add(fillLabel);
        //updateFillOption((FillOption) fillComboBox.getSelectedItem());
        updateTextFieldState();
    }

//    private void setFillDisabled() {
//        fillValue.setEnabled(false);
//        fillValue.setValue(0);
//        fillLabel.setText("");
//    }
//
//    private void updateFillOption(FillOption selectedOption) {
//        switch (selectedOption) {
//            case FILL_TO_ZERO, FILL_TO_NUMBER -> {
//                fillValue.setEnabled(true);
//                fillValue.setValue(1);
//                fillLabel.setText(selectedOption == FillOption.FILL_TO_ZERO ? "zeri" : "cifre");
//            }
//
//            case NO_FILL -> setFillDisabled();
//            default -> setFillDisabled();
//        }
//    }
    
    
    /**
     * Checks the selected item in the ComboBox 
     * and updates the state of the JTextField 
     * (enabled/disabled and empty)
     */
    private void updateTextFieldState() {
        FillOption selectedOption = (FillOption) fillComboBox.getSelectedItem();

        if (selectedOption == null) {
            return; 
        }
        
        
        switch (selectedOption) {
        case FILL_TO_ZERO, FILL_TO_NUMBER -> {
            fillValue.setEnabled(true);
            fillValue.setValue(1);
            fillLabel.setText(selectedOption == FillOption.FILL_TO_ZERO ? "zeri" : "cifre");
        }

        case NO_FILL -> {
            fillValue.setEnabled(false);
            fillValue.setValue(0);
            fillLabel.setText("");
        }

        default -> {  // default separato, da solo
            fillValue.setEnabled(false);
            fillValue.setValue(0);
            fillLabel.setText("");
        }
    }

        
//        switch (selectedOption) {
//            case FILL_TO_ZERO:
//            	fillValue.setEnabled(true);
//                fillValue.setValue(1);
//                fillLabel.setText("zeri");
//                break;        	
//            	
//            case FILL_TO_NUMBER:
//            	fillValue.setEnabled(true);
//                fillValue.setValue(1);
//                fillLabel.setText("cifre");
//                break;
//            
//            case NO_FILL:
//            default:
//                fillValue.setEnabled(false);
//                fillValue.setValue(0);
//                fillLabel.setText("");
//                break;
//        }
     }
    
    
	public void addChangeListener(TagPanel tagPanel) {
		fillValue.addChangeListener(tagPanel);
	}

    // Public methods
    public FillOption getSelectedOption() {
        return (FillOption) fillComboBox.getSelectedItem();
    }

    public int getIntValue() {
        return fillValue.getIntValue();
    }
    
    
    public String getStringValue() {
        return  String.valueOf(fillValue.getIntValue());
    }

 

}