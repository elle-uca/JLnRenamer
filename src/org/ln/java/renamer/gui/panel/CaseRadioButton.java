package org.ln.java.renamer.gui.panel;

import javax.swing.JRadioButton;

import org.ln.java.renamer.Costants.ModeCase;

@SuppressWarnings("serial")
public class CaseRadioButton extends JRadioButton {
	
	private ModeCase modeCase;

	public CaseRadioButton(ModeCase modeCase) {
		this(modeCase, false);
	}

	public CaseRadioButton(ModeCase modeCase, boolean selected) {
		super(modeCase.getTitle(), selected);
		this.modeCase = modeCase;
	}

	/**
	 * @return the modeCase
	 */
	public ModeCase getModeCase() {
		return modeCase;
	}
	
	
}
