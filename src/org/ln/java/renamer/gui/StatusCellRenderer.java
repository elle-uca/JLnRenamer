package org.ln.java.renamer.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.ln.java.renamer.Costants.FileStatus;

@SuppressWarnings("serial")
public class StatusCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value instanceof FileStatus) {
        	FileStatus status = (FileStatus) value;
        	System.out.println("====== nuovo status " + status );

            if (!isSelected) { // Mantieni selezione blu di default
                switch (status) {
                    case OK -> c.setBackground(new Color(144, 238, 144)); // Verde chiaro
                  //  case WARNING -> c.setBackground(new Color(255, 255, 150)); // Giallo
                    case KO -> c.setBackground(new Color(255, 100, 100)); // Rosso
                }
                c.setForeground(Color.BLACK);
            }
        }

        return c;
    }
}