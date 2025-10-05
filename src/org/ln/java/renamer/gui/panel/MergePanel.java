package org.ln.java.renamer.gui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.ln.java.renamer.Controller;
import org.ln.java.renamer.Costants;
import org.ln.java.renamer.gui.AccordionPanel;
import org.ln.java.renamer.util.SplitMergeUtils;
import org.ln.java.renamer.util.SplitMergeUtils.MergeResult;

import net.miginfocom.swing.MigLayout;

/**
 *
 * @author luke
 */
@SuppressWarnings("serial")
public class MergePanel extends AbstractPanelContent {

	private JLabel sourceLabel;
	private JLabel targetLabel;
	private boolean move = true;
	private ButtonGroup group;
	private JRadioButton jrbMove;
	private JRadioButton jrbCopy;
	private JTextField sourceField;
	private JTextField targetField;
	private JButton go;
	private JButton fc1;
	private JButton fc2;

	public MergePanel(AccordionPanel accordion) {
		super(accordion);
	}


	/**
	 *
	 */
	@Override
	void initComponents() {
		sourceField = new JTextField();
		targetField = new JTextField();
		sourceLabel = new JLabel("Source dir");
		targetLabel = new JLabel("Target dir");
		jrbMove = new JRadioButton("Sposta", true);
		jrbCopy = new JRadioButton("Copia");
		group = new ButtonGroup();
		group.add(jrbMove);
		group.add(jrbCopy);
		fc1 = new JButton("...");
		fc2 = new JButton("...");
		
		fc1.addActionListener(new FileChooserActionListener());
		fc2.addActionListener(new FileChooserActionListener());
		

		jrbMove.addActionListener(this);
		jrbCopy.addActionListener(this);
		go = new JButton("Go");

		go.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				MergeResult simulation;
				try {
					simulation = SplitMergeUtils.simulateMerge(sourceField.getText(), targetField.getText());
					SwingUtilities.invokeLater(() -> SplitMergeUtils.showSimulation(simulation, move));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

	            
//				 String path = accordion.getTableData().getFirst().getFrom().getParent();
//				 
//				 Map<String, List<File>> simulation;
//				
//				if(jrbMove.isSelected()) {
//					simulation = FileSplitterSimulator.simulateSplitByCount(path, 
//							numberSpinner.getIntValue(), renameField.getText());
//				}
//				else {
//					simulation = FileSplitterSimulator.simulateSplitBySize(path, 
//							sizeSpinner.getIntValue(), renameField.getText());
//				}
//				
//				SwingUtilities.invokeLater(() -> 
//				FileSplitterSimulator.showSimulationTable(path, simulation));
			}
		});

		setLayout(new MigLayout("", "[][grow][]", "20[][][][][]20"));
		
		add(sourceLabel, 	"cell 0 0");
		add(sourceField,	"cell 1 0, growx, w :150:");
		add(fc1, 			"cell 2 0");
		
		add(targetLabel, 	"cell 0 1");
		add(targetField, 	"cell 1 1, growx, w :150: ");
		add(fc2, 			"cell 2 1");

		add(jrbMove, 		"cell 0 2");
		add(jrbCopy, 		"cell 0 3");
		
		add(go, 			"cell 0 4");
		
	}   

	
	/**
	 *
	 */
	@Override
	void updateView() {
		
		if(jrbMove.isSelected()) {
			move = true;
		}
		else {
			move = false;
		}
	}
	
	
	public class FileChooserActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
	
    		JFileChooser fc = Controller.getFileChooser(JFileChooser.DIRECTORIES_ONLY, false);
    		int returnVal = fc.showOpenDialog(null);

    		if (returnVal != JFileChooser.APPROVE_OPTION) {
    			return;
    		}
    		File file = fc.getSelectedFile();
    		sourceField.setText(file.getAbsolutePath());
    		targetField.setText(file.getAbsolutePath());

    		Controller.setPrefs(Costants.LAST_DIR_KEY, file.getAbsolutePath());
		}
	}

}
