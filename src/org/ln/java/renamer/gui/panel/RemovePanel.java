package org.ln.java.renamer.gui.panel;

import javax.swing.JLabel;

import org.ln.java.renamer.RenamerMethod;
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
		posLabel = new JLabel(bundle.getString("removePanel.label.position"));
		posSpinner = new JIntegerSpinner();
		numLabel = new JLabel(bundle.getString("removePanel.label.number"));
		numSpinner = new JIntegerSpinner();
		numSpinner.addChangeListener(this);
		posSpinner.addChangeListener(this);

		setLayout(new MigLayout("", "[][grow]", "20[][]20"));

		add(numLabel);
		add(numSpinner, "growx, wrap");
		add(posLabel);
		add(posSpinner, "growx, wrap"); 

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
