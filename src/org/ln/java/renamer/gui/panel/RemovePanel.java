package org.ln.java.renamer.gui.panel;

import javax.swing.JLabel;

import org.ln.java.renamer.RenamerMethod;
import org.ln.java.renamer.gui.AccordionPanel;
import org.ln.java.renamer.gui.JIntegerSpinner;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class RemovePanel extends AbstractPanelContent {

	private JLabel posLabel;
	private JLabel numLabel;
	private JIntegerSpinner posSpinner;
	private JIntegerSpinner numSpinner;



	public RemovePanel(AccordionPanel ap) {
		super(ap);
	}



	@Override
	void initComponents() {
		posLabel = new JLabel("In posizione");
		posSpinner = new JIntegerSpinner();
		numLabel = new JLabel("Rimuovi");
		numSpinner = new JIntegerSpinner();
		numSpinner.addChangeListener(this);
		posSpinner.addChangeListener(this);

		setLayout(new MigLayout("", "[][grow]", "20[][]20"));

		add(numLabel);
		add(numSpinner, "growx, wrap, w :150:");
		add(posLabel);
		add(posSpinner, "growx, wrap, w :150:"); 

	}
	/**
	 *
	 */
	@Override
	void updateView() {
		accordion.setTableData(RenamerMethod.removeMethod(
				accordion.getTableData(), 
				posSpinner.getIntValue(), numSpinner.getIntValue()));
	}

}
