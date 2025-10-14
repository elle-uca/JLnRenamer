package org.ln.java.renamer.gui.panel;

import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.ln.java.renamer.Costants.ReplacementType;
import org.ln.java.renamer.RenamerMethod;
import org.ln.java.renamer.RnFile;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class ReplacePanel extends AbstractPanelContent {
	
	private JLabel occurrenceLabel;
	private JLabel textLabel;
	private JLabel replaceLabel;
	private JTextField textField;
	private JTextField replaceField;
	private JRadioButton jrbCase;
    private ButtonGroup group;
    private JRadioButton jrbFirst;
    private JRadioButton jrbLast;
    private JRadioButton jrbAll;

	public ReplacePanel(AccordionPanel accordion) {
		super(accordion);
	}

	
	
	@Override
	void initComponents() {
		textLabel = new JLabel(bundle.getString("replacePanel.label.text"));
		replaceLabel = new JLabel(bundle.getString("replacePanel.label.replace"));
		occurrenceLabel = new JLabel(bundle.getString("replacePanel.label.occurence"));
		textField = new JTextField();
		replaceField = new JTextField();
		jrbCase = new JRadioButton(bundle.getString("replacePanel.radioButton.case"));
	    group = new ButtonGroup();
	    jrbFirst = new JRadioButton(bundle.getString("replacePanel.radioButton.first"), true);
	    jrbLast = new JRadioButton(bundle.getString("replacePanel.radioButton.last"));
	    jrbAll = new JRadioButton(bundle.getString("replacePanel.radioButton.all"));
	    group.add(jrbFirst);
	    group.add(jrbLast);
	    group.add(jrbAll);
	    
	    textField.getDocument().addDocumentListener(this);
	    replaceField.getDocument().addDocumentListener(this);
	    jrbCase.addActionListener(this);
	    jrbFirst.addActionListener(this);
	    jrbLast.addActionListener(this);
	    jrbAll.addActionListener(this);
		
	    setLayout(new MigLayout("", "[][grow]", "20[][][][][]20"));
		add(textLabel, 		"cell 0 0");
		add(textField, 		"cell 1 0, grow");
		add(replaceLabel, 	"cell 0 1");
		add(replaceField, 	"cell 1 1, grow");
		add(jrbCase, 		"cell 0 2");
		add(occurrenceLabel,"cell 0 3");
		add(jrbFirst,		"cell 1 3");
		add(jrbLast,		"cell 0 4");
		add(jrbAll,			"cell 1 4");

	}

	@Override
	void updateView() {
		List<RnFile> list = accordion.getTableData();
        for (RnFile file : list) {
            file.setNameDest(RenamerMethod.replaceMethod(file.getFrom().getNameExtensionLess(), 
            		textField.getText(), replaceField.getText(), 
            		ReplacementType.FIRST, jrbCase.isSelected()));
        }
		accordion.setTableData(list);
	}

	
}
