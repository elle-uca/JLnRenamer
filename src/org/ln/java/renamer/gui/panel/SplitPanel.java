package org.ln.java.renamer.gui.panel;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import org.ln.java.renamer.gui.AccordionPanel;
import org.ln.java.renamer.gui.JIntegerSpinner;

import net.miginfocom.swing.MigLayout;

/**
 *
 * @author luke
 */
@SuppressWarnings("serial")
public class SplitPanel extends AbstractPanelContent {

	private JLabel textLabel;
	private JLabel numberLabel;
	private JLabel sizeLabel;
	private JIntegerSpinner numberSpinner;
	private JIntegerSpinner sizeSpinner;
	private ButtonGroup group;
	private JRadioButton jrbNumber;
	private JRadioButton jrbSize;

	public SplitPanel(AccordionPanel accordion) {
		super(accordion);
	}


	/**
	 *
	 */
	@Override
	void initComponents() {
		renameField.getDocument().addDocumentListener(this);
		textLabel = new JLabel("Prefisso dir");
		numberLabel = new JLabel("Numero file");
		sizeLabel = new JLabel("Dimensione in MB");
		numberSpinner = new JIntegerSpinner(1,1,500,1);
		sizeSpinner = new JIntegerSpinner(1,1,500,1);
		sizeSpinner.setEnabled(false);
		
		jrbNumber = new JRadioButton("Per numero", true);
		jrbSize = new JRadioButton("Per grandezza");
		group = new ButtonGroup();
		group.add(jrbNumber);
		group.add(jrbSize);

		jrbNumber.addActionListener(this);
		jrbSize.addActionListener(this);

		setLayout(new MigLayout("", "[][grow]", "20[][][]20"));
		add(jrbNumber, 		"cell 0 0 2 1");
		add(numberLabel,	"cell 0 1");
		add(numberSpinner, 	"cell 1 1, growx, w :150: ");
		
		add(jrbSize, 		"cell 0 2 2 1");
		add(sizeLabel, 		"cell 0 3");
		add(sizeSpinner, 	"cell 1 3, growx, w :150: ");

		add(textLabel, 		"cell 0 4");
		add(renameField, 	"cell 1 4, growx, w :150: ");


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
//		
//		accordion.setTableData(RenamerMethod.addMethod(
//				accordion.getTableData(), getRenameText(), intPos));

	}

}
