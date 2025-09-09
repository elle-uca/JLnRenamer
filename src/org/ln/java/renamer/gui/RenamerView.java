package org.ln.java.renamer.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import org.ln.java.renamer.gui.panel.RemovePanel;
import org.ln.java.renamer.gui.panel.TagPanel;

import com.formdev.flatlaf.FlatLightLaf;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class RenamerView extends JFrame{

	private JScrollPane tableScrollPane;
	private JTable table;
	private Controller controller;
	private JButton renameButton;
	private JButton addFileButton;
	private JButton addDirButton;
	private FileRenamerTableModel tableModel;
	private JLabel infoLabel;


	private JSplitPane splitPane;
	AccordionPanel accordion;


	public RenamerView() {
		controller = new Controller(this);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createMethodPanel(), createListPanel());
		splitPane.setDividerLocation(400);
		getContentPane().add(splitPane);
		
		 // === Barra dei menu ===
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        JMenu settingsMenu = new JMenu("Setting");
        JMenuItem prefItem = new JMenuItem("Preferences");
        settingsMenu.add(prefItem);
        menuBar.add(settingsMenu);

        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem tool1 = new JMenuItem("Tool 1");
        JMenuItem tool2 = new JMenuItem("Tool 2");
        toolsMenu.add(tool1);
        toolsMenu.add(tool2);
        menuBar.add(toolsMenu);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);

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
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton newNameButton = new JButton("Nuovo nome");
		JButton addButton = new JButton("Aggiungi");
		JButton removeButton = new JButton("Rimuovi");
		JButton caseButton = new JButton("Case");
		buttonPanel.add(newNameButton);
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(caseButton);

		accordion = new AccordionPanel(this);
		accordion.setLayout(new BoxLayout(accordion, BoxLayout.Y_AXIS));
		accordion.setAlignmentX(Component.LEFT_ALIGNMENT);
		accordion.setBorder(BorderFactory.createEmptyBorder());


		newNameButton.addActionListener(new AccordionButtonActionListener());
		addButton.addActionListener(new AccordionButtonActionListener());
		removeButton.addActionListener(new AccordionButtonActionListener());
		caseButton.addActionListener(new AccordionButtonActionListener());


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
		tableScrollPane = new JScrollPane();
		table = new JTable();
		tableModel = new FileRenamerTableModel(40);
		table.setModel(tableModel);
		tableScrollPane.setViewportView(table);
		table.setAutoCreateRowSorter(true);
        table.getColumnModel().getColumn(3).setCellRenderer(new StatusCellRenderer());


		addFileButton = new JButton("Add file");
		addDirButton = new JButton("Add directory");
		renameButton = new JButton("Rename");

		addFileButton.addActionListener(controller.new AddButtonActionListener());
		renameButton.addActionListener(controller.new RenameButtonActionListener());
		
		infoLabel = new JLabel("Label");
		
		north.setLayout(new MigLayout("", "[grow]", "[]"));
		north.add(addFileButton);
		north.add(addDirButton);
		north.add(renameButton);

		south.setLayout(new MigLayout("", "[grow]", "[]"));
		south.add(infoLabel);

		container.setLayout(new MigLayout("", "[grow]", "[]"));
		container.add(north, "wrap");
		container.add(tableScrollPane, "grow, wrap");
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
		return tableModel;
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
			}
		}
	}




	public static void main(String args[]) {
		FlatLightLaf.setup();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new RenamerView().setVisible(true);
			}
		});
	}

}


