package org.ln.java.renamer.gui.panel;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class SourceTargetPanel extends JPanel {
	
	private JLabel sourceLabel;
	private JLabel targetLabel;
	private JTextField sourceField;
	private JTextField targetField;
	private JButton fc1;
	private JButton fc2;
	
	
	public SourceTargetPanel() {
		super(new MigLayout("", "[][grow][]", "[][]"));
		sourceField = new JTextField();
		targetField = new JTextField();
		sourceLabel = new JLabel("Source dir");
		targetLabel = new JLabel("Target dir");
		fc1 = new JButton("...");
		fc2 = new JButton("...");
		
		add(sourceLabel, 	"cell 0 0");
		add(sourceField,	"cell 1 0, growx, w :150:");
		add(fc1, 			"cell 2 0");
		
		add(targetLabel, 	"cell 0 1");
		add(targetField, 	"cell 1 1, growx, w :150: ");
		add(fc2, 			"cell 2 1");
	}


	public void setSourceFieldText(String t) {
		sourceField.setText(t);
	}

	public void setTargetFieldText(String t) {
		targetField.setText(t);
	}
	public void addFc1ActionListener(ActionListener l) {
		fc1.addActionListener(l);
	}

	public void addFc2ActionListener(ActionListener l) {
		fc2.addActionListener(l);
	}

}
