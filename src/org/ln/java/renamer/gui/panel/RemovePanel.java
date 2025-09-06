package org.ln.java.renamer.gui.panel;

import javax.swing.JLabel;

import org.ln.java.renamer.RenamerMethod;
import org.ln.java.renamer.gui.AccordionPanel;
import org.ln.java.renamer.gui.JIntegerSpinner;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class RemovePanel extends AbstractPanelContent {

	private JLabel posLabel;
	private JIntegerSpinner posSpinner;
	private JLabel numLabel;
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
		System.out.println(getRenameField().getText()+"  "+posSpinner.getValue());
		accordion.getView().getTableModel().setData(RenamerMethod.removeMethod(
				accordion.getView().getTableModel().getData(), 
				posSpinner.getIntValue(), numSpinner.getIntValue()));
	}

}
