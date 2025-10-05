package org.ln.java.renamer.gui;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class TextButton extends JButton {



	public TextButton(String text) {
		super(text);
		
		// 1. Rimuovi il bordo
        setBorderPainted(false);
        
        // 2. Rimuovi lo sfondo (rendilo trasparente)
        setContentAreaFilled(false);

        // 3. Rimuovi il bordo di focus
       setFocusPainted(false);
        
        // 4. (Opzionale ma consigliato) Cambia il cursore in una manina
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // 5. (Opzionale) Cambia il colore del testo per assomigliare a un link
        setForeground(Color.BLUE);
	}



}
