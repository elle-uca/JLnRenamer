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
	private int intPos = 1;
	private ButtonGroup group;
	private JRadioButton jrbNumber;
	private JRadioButton jrbSize;
	//private JRadioButton jrbPos;

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
		numberSpinner = new JIntegerSpinner();
		sizeSpinner = new JIntegerSpinner();
		
		//numberSpinner.setEnabled(false);
		//numberSpinner.addChangeListener(this);
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
		add(numberSpinner, 	"cell 1 1, growx, wrap, w :150: ");
		
		add(jrbSize, 		"cell 0 1 2 1");
		add(sizeLabel, 		"cell 0 2");
		add(sizeSpinner, 	"cell 1 2, growx, wrap, w :150: ");
		

	}   

	/**
	 *
	 */
	@Override
	void updateView() {
//		intPos = 1;
//		numberSpinner.setEnabled(false);
//		if(jrbSize.isSelected()) {
//			intPos = Integer.MAX_VALUE;
//		}
//		if(jrbPos.isSelected()) {
//			numberSpinner.setEnabled(true);
//			intPos = numberSpinner.getIntValue();
//		}
//		
//		accordion.setTableData(RenamerMethod.addMethod(
//				accordion.getTableData(), getRenameText(), intPos));

	}

}
