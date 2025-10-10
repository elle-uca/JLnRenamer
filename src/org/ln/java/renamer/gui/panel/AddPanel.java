package org.ln.java.renamer.gui.panel;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import org.ln.java.renamer.RenamerMethod;
import org.ln.java.renamer.gui.AccordionPanel;
import org.ln.java.renamer.gui.JIntegerSpinner;

import net.miginfocom.swing.MigLayout;

/**
 *
 * @author luke
 */
@SuppressWarnings("serial")
public class AddPanel extends AbstractPanelContent {

	private JLabel textLabel;
	private JLabel whereLabel;
	private JIntegerSpinner posSpinner;
	private int intPos = 1;
	private ButtonGroup group;
	private JRadioButton jrbStart;
	private JRadioButton jrbEnd;
	private JRadioButton jrbPos;

	public AddPanel(AccordionPanel accordion) {
		super(accordion);
	}


	/**
	 *
	 */
	@Override
	void initComponents() {
		renameField.getDocument().addDocumentListener(this);
		textLabel = new JLabel(bundle.getString("addPanel.label.text"));
		whereLabel = new JLabel(bundle.getString("addPanel.label.where"));
		posSpinner = new JIntegerSpinner();
		posSpinner.setEnabled(false);
		posSpinner.addChangeListener(this);
		jrbStart = new JRadioButton(bundle.getString("addPanel.radioButton.start"), true);
		jrbEnd = new JRadioButton(bundle.getString("addPanel.radioButton.end"));
		jrbPos = new JRadioButton(bundle.getString("addPanel.radioButton.pos"));
		group = new ButtonGroup();
		group.add(jrbStart);
		group.add(jrbEnd);
		group.add(jrbPos);

		jrbStart.addActionListener(this);
		jrbEnd.addActionListener(this);
		jrbPos.addActionListener(this);

		setLayout(new MigLayout("", "[][][grow]", "20[][][]20"));
		add(textLabel, 	"cell 0 0");
		add(renameField,"cell 1 0 2 1, growx, wrap");
		add(whereLabel, "cell 0 1 1 2");
		add(jrbStart, 	"cell 1 1");
		add(jrbEnd, 	"cell 2 1, wrap");
		add(jrbPos, 	"cell 1 2 ");
		add(posSpinner, "cell 2 2, growx ");
	}   

	/**
	 *
	 */
	@Override
	void updateView() {
		intPos = 1;
		posSpinner.setEnabled(false);
		if(jrbEnd.isSelected()) {
			intPos = Integer.MAX_VALUE;
		}
		if(jrbPos.isSelected()) {
			posSpinner.setEnabled(true);
			intPos = posSpinner.getIntValue();
		}
		
		accordion.setTableData(RenamerMethod.addMethod(
				accordion.getTableData(), getRenameText(), intPos));
	}

}
