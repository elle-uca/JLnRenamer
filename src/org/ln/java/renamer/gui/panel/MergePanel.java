package org.ln.java.renamer.gui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

import org.ln.java.renamer.gui.AccordionPanel;
import org.ln.java.renamer.gui.JIntegerSpinner;
import org.ln.java.renamer.util.FileSplitterSimulator;

import net.miginfocom.swing.MigLayout;

/**
 *
 * @author luke
 */
@SuppressWarnings("serial")
public class MergePanel extends AbstractPanelContent {

	private JLabel textLabel;
	private JLabel numberLabel;
	private JLabel sizeLabel;
	private JIntegerSpinner numberSpinner;
	private JIntegerSpinner sizeSpinner;
	private ButtonGroup group;
	private JRadioButton jrbNumber;
	private JRadioButton jrbSize;
	private JButton go;

	public MergePanel(AccordionPanel accordion) {
		super(accordion);
	}


	/**
	 *
	 */
	@Override
	void initComponents() {
		renameField.setText("Part_");
		//renameField.getDocument().addDocumentListener(this);
		textLabel = new JLabel("Prefisso dir");
		numberLabel = new JLabel("Numero file");
		sizeLabel = new JLabel("Dimensione in MB");
		numberSpinner = new JIntegerSpinner(1, 1 ,500 ,1);
		sizeSpinner = new JIntegerSpinner(1, 1, 500, 1);
		sizeSpinner.setEnabled(false);
		
		jrbNumber = new JRadioButton("Per numero", true);
		jrbSize = new JRadioButton("Per grandezza");
		group = new ButtonGroup();
		group.add(jrbNumber);
		group.add(jrbSize);

		jrbNumber.addActionListener(this);
		jrbSize.addActionListener(this);
		go = new JButton("GO");

		go.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 String path = accordion.getTableData().getFirst().getFrom().getParent();
				 System.out.println(path);
				 
				 Map<String, List<File>> simulation;
				
				if(jrbNumber.isSelected()) {
					simulation = FileSplitterSimulator.simulateSplitByCount(path, numberSpinner.getIntValue(), renameField.getText());
				}
				else {
					simulation = FileSplitterSimulator.simulateSplitBySize(path, sizeSpinner.getIntValue(), renameField.getText());
				}
				
				SwingUtilities.invokeLater(() -> FileSplitterSimulator.showSimulationTable(simulation));
				
			}
		});

		setLayout(new MigLayout("", "[][grow]", "20[][][][][]20"));
		
		add(jrbNumber, 		"cell 0 0 2 1");
		add(numberLabel,	"cell 0 1");
		add(numberSpinner, 	"cell 1 1, growx, w :150: ");
		
		add(jrbSize, 		"cell 0 2 2 1");
		add(sizeLabel, 		"cell 0 3");
		add(sizeSpinner, 	"cell 1 3, growx, w :150: ");

		add(textLabel, 		"cell 0 4");
		add(renameField, 	"cell 1 4, growx, w :150: ");
		
		add(go, 			"cell 0 5");
		
	}   

	
	/**
	 *
	 */
	@Override
	void updateView() {
		
		if(jrbNumber.isSelected()) {
			sizeSpinner.setEnabled(false);
			numberSpinner.setEnabled(true);
		}
		else {
			sizeSpinner.setEnabled(true);
			numberSpinner.setEnabled(false);
		}
		

		
//        for (Map.Entry<String, List<File>> entry : simulation.entrySet()) {
//            String folder = entry.getKey();
//            for (File file : entry.getValue()) {
//                model.addRow(new Object[]{
//                        folder,
//                        file.getName(),
//                        file.length() / 1024
//                });
//            }
//        }
		
		
		
//		
//		accordion.setTableData(RenamerMethod.addMethod(
//				accordion.getTableData(), getRenameText(), intPos));

	}

}
