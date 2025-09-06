package org.ln.java.renamer.gui.panel;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import org.ln.java.renamer.Costants.ModeCase;
import org.ln.java.renamer.RenamerMethod;
import org.ln.java.renamer.gui.AccordionPanel;

import net.miginfocom.swing.MigLayout;

/**
 *
 * @author luke
 */
@SuppressWarnings("serial")
public class CasePanel extends AbstractPanelContent {

	private ButtonGroup buttonGroup;
	private JRadioButton jrbUpper;
	private JRadioButton jrbLower;
	private JRadioButton jrbCapAll;
	private JRadioButton jrbCapFirst;
	private JRadioButton jrbInvert;


	public CasePanel(AccordionPanel accordion) {
		super(accordion);
	}


	/**
	 *
	 */
	@Override
	void initComponents() {

		jrbUpper = new CaseRadioButton(ModeCase.UPPER, true);
		jrbLower = new CaseRadioButton(ModeCase.LOWER);
		jrbCapAll = new CaseRadioButton(ModeCase.TITLE_CASE);
		jrbInvert = new CaseRadioButton(ModeCase.TOGGLE_CASE);
		jrbCapFirst = new CaseRadioButton(ModeCase.CAPITALIZE_FIRST);
		buttonGroup = new ButtonGroup();
		buttonGroup.add(jrbUpper);
		buttonGroup.add(jrbLower);
		buttonGroup.add(jrbCapAll);
		buttonGroup.add(jrbInvert);
		buttonGroup.add(jrbCapFirst);
		
		//jrbUpper.setSelected(true);

		jrbUpper.addActionListener(this);
		jrbLower.addActionListener(this);
		jrbCapAll.addActionListener(this);
		jrbInvert.addActionListener(this);
		jrbCapFirst.addActionListener(this);
		
		
		setLayout(new MigLayout("", "30[]", "20[][][][][]20"));
		add(jrbUpper, 	"wrap");
		add(jrbLower,	"wrap");
		add(jrbCapAll, 	"wrap");
		add(jrbCapFirst,"wrap");
		add(jrbInvert, 	"wrap");

	}   

	/**
	 *
	 */
	@Override
	void updateView() {
		ModeCase modeCase = ModeCase.UPPER;
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			CaseRadioButton button = (CaseRadioButton) buttons.nextElement();
            if (button.isSelected()) {
            	modeCase = button.getModeCase();
            }
        }

		accordion.getView().getTableModel().setData(RenamerMethod.transformCase(
				accordion.getView().getTableModel().getData(), modeCase));
	}





}
