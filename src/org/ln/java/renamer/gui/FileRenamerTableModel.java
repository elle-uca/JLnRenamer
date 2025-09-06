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
		if(data.size() < 1) {
			return "";
		}
		switch (columnIndex) {
		case 0:
			return data.get(rowIndex).getFrom().getName();
		case 1:
			if(data.get(rowIndex).getNameDest() == "") {
				return data.get(rowIndex).getFrom().getName();
			}
			return data.get(rowIndex).getNameDest();
		case 2:
			return data.get(rowIndex).getFrom().getParent();
		case 3:
			return data.get(rowIndex).getStatus();
		case 4:
			return data.get(rowIndex).getSelected();

		default:
			return data.get(rowIndex);
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
	
    public boolean isCellEditable(int row, int col) {
        if (col == 4) {
            return true;
        } else {
            return false;
        }
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
//        switch (columnIndex) {
//            case 0: return String.class;
//            case 1: return String.class;
//            case 2: return String.class;
//            case 3: return String.class;
//            case 4: return boolean.class;
//        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Name";
            case 1: return "New Name";
            case 2: return "Path";
            case 3: return "Status";
            case 4: return "Status";
        }
        return "";
    }
}
