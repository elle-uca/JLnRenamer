package org.ln.java.renamer.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.ln.java.renamer.Controller;
import org.ln.java.renamer.gui.panel.AddPanel;
import org.ln.java.renamer.gui.panel.CasePanel;
import org.ln.java.renamer.gui.panel.MergePanel;
import org.ln.java.renamer.gui.panel.RemovePanel;
import org.ln.java.renamer.gui.panel.SplitPanel;
import org.ln.java.renamer.gui.panel.TagPanel;
import org.ln.java.renamer.tool.PasswordGeneratorDialog;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class RenamerView extends JFrame{
	
	ResourceBundle bundle = Controller.getBundle();	

	private JScrollPane tableScrollPane1;
	private JTable table1;
	private FileRenamerTableModel tableModel1;

//	private JScrollPane tableScrollPane2;
//	private JTable table2;
//	private FileRenamerTableModel tableModel2;
	
	private Controller controller;
	private JButton renameButton;
	private JButton addFileButton;
	private JButton addDirButton;
	private JLabel infoLabel;


	private JSplitPane splitPane;
	private AccordionPanel accordion;


	public RenamerView() {
		controller = new Controller(this);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				createMethodPanel(), createListPanel());
		splitPane.setDividerLocation(400);
		getContentPane().add(splitPane);
		
		 // === Barra dei menu ===
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem addFileItem = new JMenuItem("Add file");
        addFileItem.addActionListener(controller.new AddFileButtonActionListener());
        JMenuItem addDirItem = new JMenuItem("Add dir");
        addDirItem.addActionListener(controller.new AddDirButtonActionListener());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Termina l'applicazione
            }
        });
        
        fileMenu.add(addFileItem);
        fileMenu.add(addDirItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        JMenu settingsMenu = new JMenu("Setting");
        JMenuItem prefItem = new JMenuItem("Preferences");
        settingsMenu.add(prefItem);
        menuBar.add(settingsMenu);

        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem psswGen = new JMenuItem("Generatore password");
        JMenuItem tool2 = new JMenuItem("Tool 2");
        toolsMenu.add(psswGen);
        toolsMenu.add(tool2);
        menuBar.add(toolsMenu);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);
        
        psswGen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PasswordGeneratorDialog dialog = new PasswordGeneratorDialog(null);
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
				
			}
		});

        setJMenuBar(menuBar);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(600,400);
		setLocationRelativeTo(null);
		pack();	
	}


	/**
	 * @return
	 */
	private JPanel createMethodPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		TextButton newNameButton = new TextButton("Nuovo nome");
		TextButton addButton = new TextButton("Aggiungi");
		TextButton removeButton = new TextButton("Rimuovi");
		TextButton caseButton = new TextButton("Case");
		TextButton splitButton = new TextButton("Split");
		TextButton mergeButton = new TextButton("Merge");
		
		//buttonPanel.setLayout(new MigLayout("", "[][][]", "[]"));
		buttonPanel.setLayout(new GridLayout(0, 4));
		buttonPanel.add(newNameButton);
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(caseButton);
		buttonPanel.add(splitButton);
		buttonPanel.add(mergeButton);

		accordion = new AccordionPanel(this);
		accordion.setLayout(new BoxLayout(accordion, BoxLayout.Y_AXIS));
		accordion.setAlignmentX(Component.LEFT_ALIGNMENT);
		accordion.setBorder(BorderFactory.createEmptyBorder());

		newNameButton.addActionListener(new AccordionButtonActionListener());
		addButton.addActionListener(new AccordionButtonActionListener());
		removeButton.addActionListener(new AccordionButtonActionListener());
		caseButton.addActionListener(new AccordionButtonActionListener());
		splitButton.addActionListener(new AccordionButtonActionListener());
		mergeButton.addActionListener(new AccordionButtonActionListener());

		panel.add(accordion, BorderLayout.NORTH);
		panel.add(buttonPanel, BorderLayout.SOUTH);

		return panel;
	}





	/**
	 * @return
	 */
	private JPanel createListPanel() {
		JPanel container = new JPanel();
		JPanel north = new JPanel();
		JPanel south = new JPanel();
		tableScrollPane1 = new JScrollPane();
		table1 = new JTable();
		tableModel1 = new FileRenamerTableModel(40);
		table1.setModel(tableModel1);
		tableScrollPane1.setViewportView(table1);
		table1.setAutoCreateRowSorter(true);
        table1.getColumnModel().getColumn(3).setCellRenderer(new StatusCellRenderer());

        
//		tableScrollPane2 = new JScrollPane();
//		table2 = new JTable();
//		tableModel2 = new FileRenamerTableModel(40);
//		table2.setModel(tableModel2);
//		tableScrollPane2.setViewportView(table2);
//		table2.setAutoCreateRowSorter(true);
//        table2.getColumnModel().getColumn(3).setCellRenderer(new StatusCellRenderer());
        


		addFileButton = new JButton(bundle.getString("tabPanel.button.addFile"));
		addDirButton = new JButton(bundle.getString("tabPanel.button.addDirectory"));
		renameButton = new JButton(bundle.getString("tabPanel.button.rename"));

		addFileButton.addActionListener(controller.new AddFileButtonActionListener());
		addDirButton.addActionListener(controller.new AddDirButtonActionListener());
		renameButton.addActionListener(controller.new RenameButtonActionListener());
		
		infoLabel = new JLabel("Label");
		
		north.setLayout(new MigLayout("", "[grow]", "[]"));
		north.add(addFileButton);
		north.add(addDirButton);
		north.add(renameButton);

		south.setLayout(new MigLayout("", "[grow]", "[]"));
		south.add(infoLabel);

//		JTabbedPane tabPanel = new JTabbedPane();
//        tabPanel.addTab("Tab 1", tableScrollPane1);
//        tabPanel.addTab("Tab 2", tableScrollPane2);
		
		
		container.setLayout(new MigLayout("", "[grow]", "[]"));
		container.add(north, 			"wrap");
		container.add(tableScrollPane1, "grow, wrap");
		container.add(south);
		return container;
	}


	public Controller getController() {
		return controller;
	}


	public JTextField getRenameField() {
		return accordion.getRenameField();
	}


	public FileRenamerTableModel getTableModel() {
		return tableModel1;
	}


	/**
	 * @param text
	 * @see javax.swing.JLabel#setText(java.lang.String)
	 */
	public void setInfoText(String text) {
		infoLabel.setText(text);
	}


	class AccordionButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//JButton b = (JButton) e.getSource();
			
//			System.out.println("Dimensione preferita: " + b.getPreferredSize());
//			System.out.println("Dimensione effettiva: " + b.getSize());

			
			String name = ((JButton) e.getSource()).getText();

			switch (name) {
			case ("Aggiungi"):
				accordion.addPanel(name, new AddPanel(accordion));
			break;
			case ("Nuovo nome"):
				accordion.addPanel(name, new TagPanel(accordion));
			break;
			case ("Rimuovi"):
				accordion.addPanel(name, new RemovePanel(accordion));
			break;
			case ("Case"):
				accordion.addPanel(name, new CasePanel(accordion));
			break;
			case ("Split"):
				accordion.addPanel(name, new SplitPanel(accordion));
			break;
			case ("Merge"):
				accordion.addPanel(name, new MergePanel(accordion));
			break;			
			}
		}
	}




	public static void main(String args[]) {
		//FlatLightLaf.setup();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new RenamerView().setVisible(true);
			}
		});
	}

}


