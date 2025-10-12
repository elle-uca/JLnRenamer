package org.ln.java.renamer.gui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

import org.ln.java.renamer.Controller;
import org.ln.java.renamer.RnPrefs;
import org.ln.java.renamer.gui.JIntegerSpinner;
import org.ln.java.renamer.util.SplitMergeUtils;

import net.miginfocom.swing.MigLayout;

/**
 *
 * @author luke
 */
@SuppressWarnings("serial")
public class SplitPanel extends AbstractPanelContent {

	private SourceTargetPanel stp;
	private JLabel textLabel;
	private JLabel numberLabel;
	private JLabel sizeLabel;
	private JIntegerSpinner numberSpinner;
	private JIntegerSpinner sizeSpinner;
	private ButtonGroup group;
	private JRadioButton jrbNumber;
	private JRadioButton jrbSize;
	private JButton go;

	public SplitPanel(AccordionPanel accordion) {
		super(accordion);
	}


	/**
	 *
	 */
	@Override
	void initComponents() {
		renameField.setText("Part_");
		textLabel = new JLabel("Prefisso dir");
		numberLabel = new JLabel("Numero file");
		sizeLabel = new JLabel("Dimensione in MB");
		numberSpinner = new JIntegerSpinner(1, 1 ,500 ,1);
		sizeSpinner = new JIntegerSpinner(1, 1, 500, 1);
		sizeSpinner.setEnabled(false);
		stp = new SourceTargetPanel();
		jrbNumber = new JRadioButton("Per numero", true);
		jrbSize = new JRadioButton("Per grandezza");
		group = new ButtonGroup();
		group.add(jrbNumber);
		group.add(jrbSize);
        stp.onSourceChosen(t -> chooseDirectory(true));
        stp.onTargetChosen(t -> chooseDirectory(false));		
        jrbNumber.addActionListener(this);
		jrbSize.addActionListener(this);
		go = new JButton("GO");

		go.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 String path = stp.getSourceFieldText();// aggiungere logica per cartella destinazione
				 System.out.println(path);
				 
				 Map<String, List<File>> simulation;
				
				if(jrbNumber.isSelected()) {
					simulation = SplitMergeUtils.simulateSplitByCount(path, 
							numberSpinner.getIntValue(), renameField.getText());
				}
				else {
					simulation = SplitMergeUtils.simulateSplitBySize(path, 
							sizeSpinner.getIntValue(), renameField.getText());
				}
				
				SwingUtilities.invokeLater(() -> 
					SplitMergeUtils.showSimulationTable(path, simulation));
				
			}
		});

		setLayout(new MigLayout("", "[][grow]", "20[][][][][][][]20"));
		
		add(stp, 			"cell 0 0 2 1, growx ");
		add(jrbNumber, 		"cell 0 1 2 1");
		add(numberLabel,	"cell 0 2");
		add(numberSpinner, 	"cell 1 2, growx ");
		add(jrbSize, 		"cell 0 3 2 1");
		add(sizeLabel, 		"cell 0 4");
		add(sizeSpinner, 	"cell 1 4, growx ");
		add(textLabel, 		"cell 0 5");
		add(renameField, 	"cell 1 5, growx");
		add(go, 			"cell 0 6");
	}   

	
	/**
	 *
	 */
	@Override
	void updateView() {
		sizeSpinner.setEnabled(true);
		numberSpinner.setEnabled(false);	
		
		if(jrbNumber.isSelected()) {
			sizeSpinner.setEnabled(false);
			numberSpinner.setEnabled(true);
		}
	}
	
    /** 
     * Show JFileChooser and update the correct field 
     */
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
	


}
