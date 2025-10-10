package org.ln.java.renamer.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.ln.java.renamer.Controller;
import org.ln.java.renamer.RenamerMethod;
import org.ln.java.renamer.RnFile;
import org.ln.java.renamer.gui.panel.AbstractPanelContent;
import org.ln.java.renamer.gui.panel.SlidingPanel;


@SuppressWarnings("serial")
public class AccordionPanel extends JPanel {

    private List<SlidingPanel> panels = new ArrayList<SlidingPanel>();
    
    private RenamerView view;
    
    
    public AccordionPanel(RenamerView view) {
    	this.view = view;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }


    public void addPanel(String title, AbstractPanelContent contentComponent) {
    	SlidingPanel panel = new SlidingPanel(title, contentComponent, this);
    	panel.setCollapseOthers( () -> collapseAllExcept(panel));
    	
        // ChiudE tutti gli altri pannelli
        collapseAllExcept(panel);

        // Apre il pannello appena aggiunto con animazione
        if (!panel.isExpanded()) {
            panel.togglePanel();
        }
    	
    	panels.add(panel);
    	add(panel);
    	revalidate();
    	repaint();
    }
    
    
    /**
     * @param expandedPanel
     */
    public void collapseAllExcept(SlidingPanel expandedPanel) {
        for (SlidingPanel panel : panels) {
            if (panel != expandedPanel) {
            	panel.setActivePanel(false);
                panel.collapse(); // chiama toggle se aperto
            }
        }
    }
    
	/**
     * Rimuove un pannello specifico dal menu.
     * @param panel Il pannello da rimuovere.
     */
    public void removePanel(SlidingPanel panel) {
        panels.remove(panel);
        remove(panel);
        for (int i = 0; i < panels.size(); i++) {
			panels.get(i).setCountLabel(i+1);
		}
        revalidate();
        repaint();
    }

    public int getPanelCount() {
        return panels.size()+1;
    }

	/**
	 * @return
	 */
	public JTextField getRenameField() {
		return getActivePanel().getRenameField();
	}


	/**
	 * @return
	 */
	public SlidingPanel getActivePanel() {
		for (SlidingPanel slidingPanel : panels) {
			if(slidingPanel.isActivePanel()) {
				return slidingPanel;
			}
		}
		return null;
	}

	/**
	 * @return the view
	 */
	public RenamerView getView() {
		return view;
	}


	/**
	 * @return
	 */
	public Controller getController() {
		return view.getController();
	}


	/**
	 * @return
	 */
	public FileRenamerTableModel getTableModel() {
		return view.getTableModel();
	}
	
	/**
	 * @param list
	 */
	public void setTableData(List<RnFile> list) {
		if(getTableData().size() > 0) {
			view.getController().setTableData(list);
		}
		
	}
	
	
	/**
	 * @return
	 */
	public List<RnFile> getTableData() {
		return view.getTableModel().getData();
	}
}
