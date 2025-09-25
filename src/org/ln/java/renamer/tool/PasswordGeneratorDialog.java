package org.ln.java.renamer.tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.ln.java.renamer.gui.JIntegerSpinner;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class PasswordGeneratorDialog extends JDialog {

	private JCheckBox jcbLower;
	private JCheckBox jcbUpper;
	private JCheckBox jcbDigit;
	private JCheckBox jcbSpecial;
	private JLabel lenghtLabel;
	private JLabel resultLabel;
	private JLabel copyLabel;
	private JIntegerSpinner lenghtSpinner;
	private JButton goButton;
	private JButton copyButton;

	public PasswordGeneratorDialog(Frame owner) {
		super(owner, "Password Generator", true);

		jcbLower = new JCheckBox("Lettere minuscole", true);
		jcbUpper = new JCheckBox("Lettere maiuscole", true);
		jcbDigit = new JCheckBox("Numeri", true);
		jcbSpecial = new JCheckBox("Caratteri speciali", true);
		lenghtLabel = new JLabel("Lunghezza password");
		resultLabel = new JLabel("password");
		copyLabel = new JLabel("");
		copyLabel.setForeground(Color.red);
		resultLabel.setForeground(Color.blue);
		Font boldFont = new Font(resultLabel.getFont().getFontName(), Font.BOLD, resultLabel.getFont().getSize());
		resultLabel.setFont(boldFont);

		lenghtSpinner = new JIntegerSpinner(8, 6, Integer.MAX_VALUE, 1);
		goButton = new JButton("Genera password");
		copyButton = new JButton("Copia negli appunti");

		JCheckBox[] checkBoxes = {jcbLower, jcbUpper, jcbDigit, jcbSpecial};
		ItemListener enforceOneSelected = e -> {
			if (e.getStateChange() == ItemEvent.DESELECTED && !anySelected(checkBoxes)) {
				((JCheckBox) e.getSource()).setSelected(true);
			}
		};

		for (JCheckBox cb : checkBoxes) {
			cb.addItemListener(enforceOneSelected);
			add(cb);
		}
		goButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String pssw = PasswordGenerator.generatePassword(lenghtSpinner.getIntValue(), 
						jcbLower.isSelected(),
						jcbUpper.isSelected(),
						jcbDigit.isSelected(),
						jcbSpecial.isSelected());
				resultLabel.setText(pssw);
				copyLabel.setText("");
			}
		});
		copyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String pssw = resultLabel.getText();
				Runnable task = () -> {
					StringSelection selection = new StringSelection(pssw);
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(selection, null);
				};

				if (SwingUtilities.isEventDispatchThread()) {
					task.run();
				} else {
					SwingUtilities.invokeLater(task);
				}
				copyLabel.setText("Password copiata negli appunti");
			}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout("", "[grow, align center]", "20[][][][][][]20[]20[]20[]20[]20"));

		panel.add(lenghtLabel, 		"cell 0 0");
		panel.add(lenghtSpinner, 	"cell 0 1, growx, wrap, w :250:");
		panel.add(jcbLower, 		"cell 0 2, wrap, sg jcb");
		panel.add(jcbUpper, 		"cell 0 3, wrap, sg jcb");
		panel.add(jcbDigit, 		"cell 0 4, wrap, sg jcb");
		panel.add(jcbSpecial, 		"cell 0 5, wrap, sg jcb");
		panel.add(goButton, 		"cell 0 6, wrap"); 
		panel.add(resultLabel, 		"cell 0 7, wrap"); 
		panel.add(copyButton, 		"cell 0 8, wrap"); 
		panel.add(copyLabel, 		"cell 0 9, wrap"); 

		add(panel);
		setSize(300, 400);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

	}



	private boolean anySelected(JCheckBox[] boxes) {
		for (JCheckBox cb : boxes) {
			if (cb.isSelected()) return true;
		}
		return false;
	}



}
