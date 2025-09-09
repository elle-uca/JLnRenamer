package org.ln.java.renamer.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.ln.java.renamer.gui.AccordionPanel;
import org.ln.java.renamer.gui.RenamerView;

@SuppressWarnings("serial")
public class SlidingPanel extends JPanel {

	private  JPanel headerPanel;
	private  JPanel contentPanel;
	private  AbstractPanelContent contentComponent;

	private boolean isExpanded = false;
	private boolean activePanel = false;
	private Timer animationTimer;
	private int currentHeight = 0;
	private int maxContentHeight = 0;
	private int animationStep = 5;
	private Runnable collapseOthers;

	private JLabel iconLabel;
	private ImageIcon iconRight;
	private ImageIcon iconDown;

	private AccordionPanel accordionPanel;
	private String title;
	private JLabel titleLabel;
	private JLabel countLabel;
	private JCheckBox active;
	private JButton closeButton;



	public SlidingPanel(String title, AbstractPanelContent content, AccordionPanel accordionPanel) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(null);
		this.accordionPanel = accordionPanel;
		//System.out.println(accordionPanel);
		this.contentComponent = content;
		//System.out.println(contentComponent.getClass());
		this.title = title;
		activePanel = true;
		init();
	}
	private void init() {
		contentPanel = new JPanel(new BorderLayout());
		contentPanel.add(contentComponent, BorderLayout.CENTER);
		contentPanel.setPreferredSize(new Dimension(0, 0));
		contentPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 0));
		contentPanel.setMinimumSize(new Dimension(0, 0));
		contentPanel.setVisible(false);
		contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		add(getHeaderPanel() );
		add(contentPanel);
		setAlignmentX(Component.LEFT_ALIGNMENT);
	}

	private void paintHeaderPanel() {
		if(!isActivePanel()) {
			headerPanel.setBackground(Color.PINK);
		}
		if(isExpanded) {
			headerPanel.setBackground(new Color(2,219,246));
		}else {
			headerPanel.setBackground(Color.LIGHT_GRAY);
		}
	}
	
	private JPanel getHeaderPanel() {
		headerPanel = new JPanel(new GridBagLayout());
		paintHeaderPanel();
		headerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		iconRight = new ImageIcon(getClass().getResource("/icons/arrow_right.png"));
		iconDown  = new ImageIcon(getClass().getResource("/icons/arrow_down.png"));
		iconLabel = new JLabel(iconRight);

		countLabel = new JLabel(accordionPanel.getPanelCount() + "");
		titleLabel = new JLabel(title);

		active = new JCheckBox();
		active.setOpaque(false);
		active.setSelected(activePanel);

		closeButton = new JButton("x");
		closeButton.setForeground(Color.RED);
		closeButton.setFocusPainted(false);
		closeButton.setBorderPainted(false);
		closeButton.setContentAreaFilled(false);
		closeButton.setMargin(new Insets(0, 5, 0, 5));

		closeButton.addActionListener(e -> accordionPanel.removePanel(this));

		headerPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!isExpanded && collapseOthers != null) {
					collapseOthers.run();
				}
				togglePanel();
			}
		});

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.CENTER;

		gbc.gridx = 0;
		gbc.weightx = 0;
		headerPanel.add(iconLabel, gbc);

		gbc.gridx = 1;
		headerPanel.add(countLabel, gbc);

		gbc.gridx = 2;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		headerPanel.add(titleLabel, gbc);

		gbc.gridx = 3;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		headerPanel.add(active, gbc);

		gbc.gridx = 4;
		headerPanel.add(closeButton, gbc);

		headerPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		return headerPanel;
	}



	public void collapse() {
		if (isExpanded) togglePanel();///due
	}

	/**
	 * Mostra il contenuto del pannello e aggiorna l'icona del toggle.
	 */
	public void openContent() {
		contentPanel.setVisible(true);
		setActivePanel(true);
		contentComponent.revalidate();
		maxContentHeight = contentComponent.getPreferredSize().height;

	}
	/**
	 * Nasconde il contenuto del pannello e aggiorna l'icona del toggle.
	 */
	public void closeContent() {
		contentPanel.setVisible(false);
		setActivePanel(false);
		revalidate();
	}	



	public void togglePanel() {
		// Timer già attivo
		if (animationTimer != null && animationTimer.isRunning()) return;

		// label
		isExpanded = !isExpanded;
		iconLabel.setIcon(isExpanded ? iconDown : iconRight);
		openContent();
		paintHeaderPanel();
		animationTimer = new Timer(5, e -> {
			int target = isExpanded ? maxContentHeight : 0;

			if (isExpanded) {
				currentHeight = Math.min(currentHeight + animationStep, maxContentHeight);
			} else {
				currentHeight = Math.max(currentHeight - animationStep, 0);
			}

			contentPanel.setPreferredSize(new Dimension(getWidth(), currentHeight));
			contentPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, currentHeight));
			revalidate();
			repaint();

			if (currentHeight == target) {
				if (!isExpanded) closeContent();
				((Timer) e.getSource()).stop();
			}
		});

		animationTimer.start();
	}

	/**
	 * @param collapseOthers the collapseOthers to set
	 */
	public void setCollapseOthers(Runnable collapseOthers) {
		this.collapseOthers = collapseOthers;
	}

	public boolean isExpanded() {
		return isExpanded;
	}


	public void setCountLabel(int i) {
		countLabel.setText(""+i);
	}


	/**
	 * @return the activePanel
	 */
	public boolean isActivePanel() {
		return activePanel;
	}
	/**
	 * @param activePanel the activePanel to set
	 */
	public void setActivePanel(boolean activePanel) {
		this.activePanel = activePanel;
		active.setSelected(activePanel);
	}
	/**
	 * @return the accordionPanel
	 */
	public AccordionPanel getAccordionPanel() {
		return accordionPanel;
	}
	/**
	 * @return
	 * @see org.ln.java.renamer.gui.AccordionPanel#getView()
	 */
	public RenamerView getView() {
		return accordionPanel.getView();
	}
	
	public JTextField getRenameField() {
		return contentComponent.getRenameField();
	}
	
	
}