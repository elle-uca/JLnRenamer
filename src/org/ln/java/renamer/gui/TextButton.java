package org.ln.java.renamer.gui;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class TextButton extends JButton {



	public TextButton(String text) {
		super(text);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setForeground(Color.BLUE);
	}

}
