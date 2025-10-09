package org.ln.java.renamer.gui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.ln.java.renamer.gui.AccordionPanel;

@SuppressWarnings("serial")
public abstract class AbstractPanelContent extends JPanel 
implements ChangeListener, DocumentListener, ActionListener{
	
	protected AccordionPanel accordion;
	protected JTextField renameField;

	
	
	/**
	 * @param ap
	 */
	public AbstractPanelContent(AccordionPanel ap) {
			accordion = ap;
			renameField = new JTextField();
			initComponents();
	}

	/**
	 * 
	 */
	abstract void initComponents();

	/**
	 * 
	 */
	abstract void updateView();

	/**
	 * @return
	 */
	public JTextField getRenameField() {
		return renameField;
	}
	
	/**
	 * @return
	 */
	public String getRenameText() {
		return renameField.getText();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		changedUpdate(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changedUpdate(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		updateView();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		updateView();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		updateView();
	}
	

}
