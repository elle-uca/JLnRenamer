package org.ln.java.renamer.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.ln.java.renamer.RnFile;

@SuppressWarnings("serial")
public class FileRenamerTableModel extends AbstractTableModel{
	

	protected List<RnFile> data;
	
	private int rowCount;
	 
	
    public FileRenamerTableModel() {
    	this(20);
    	
    }
    public FileRenamerTableModel(int rowCount) {
    	this.rowCount = rowCount;
    	data = new ArrayList<RnFile>();
    }

    
	public void setData(List<RnFile> data) {
		this.data = data;
		fireTableDataChanged();
	} 
 
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
	    if (rowIndex >= data.size()) {
	        switch (columnIndex) {
	            case 4: return Boolean.FALSE; // checkbox spento di default
	            default: return "";           // stringa vuota per le altre colonne
	        }
	    }

	    RnFile file = data.get(rowIndex);

	    switch (columnIndex) {
	        case 0: return file.getFrom().getName();
	        case 1: return (file.getNameDest() == null || file.getNameDest().isEmpty())
	                        ? file.getFrom().getName()
	                        : file.getNameDest();
	        case 2: return file.getFrom().getParent();
	        case 3: return file.getFileStatus();
	        case 4: return file.getSelected();
	        default: return "";
	    }
	}
	/**
	 * @return the data
	 */
	public List<RnFile> getData() {
		return data;
	}
	
	@Override
	public int getRowCount() {
		if(data.size() > 0) {
			rowCount = data.size();
		}
		return rowCount;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 4 && rowIndex < data.size()) {
			return true;
		}  
		return false;
	}
	
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    	if (columnIndex == 4) {
    		data.get(rowIndex).setSelected((Boolean) aValue);
    		fireTableCellUpdated(rowIndex, columnIndex);
    	}
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 4:
                return Boolean.class; // checkbox
            default:
                return String.class;  // tutte le altre colonne come testo
        }
    }


    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Name";       // String
            case 1: return "New Name";   // String
            case 2: return "Path";       // String
            case 3: return "Status";     // String
            case 4: return "Selected";   // Boolean
        }
        return "";
    }
    
    

}
