package org.ln.java.renamer.tool;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class DictionarySearchGUI extends JFrame {

	// URL for the real raw text file 
	private static final String DICTIONARY_URL =
			"https://raw.githubusercontent.com/napolux/paroleitaliane/main/paroleitaliane/parole_uniche.txt";


	private static final String DICTIONARY_FILE = "resources/italian_words.txt";

	private final List<String> words;
	private final JTextField inputField;
	private final JCheckBox regexCheck;
	private final JCheckBox fullMatchCheck;
	private final JTextField maxLengthField;
	private final JTextField prefixField;
	private final JTextField suffixField;
	private final JTextArea resultsArea;
	private final JLabel infoLabel;
	private final JButton saveButton;

	private List<String> lastResults = new ArrayList<>();

	// ===== Constructor =====
	public DictionarySearchGUI(List<String> words) {
		super("🔎 Italian Word Search");
		this.words = words;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(750, 600);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));

		// === MAIN INPUT PANEL ===
		JPanel topPanel = new JPanel(new MigLayout("fillx, insets 10", "[right][grow][grow][grow][grow]"));
		inputField = new JTextField(25);
		regexCheck = new JCheckBox("Use regex");
		fullMatchCheck = new JCheckBox("Full match");
		maxLengthField = new JTextField("0", 5);
		JButton searchButton = new JButton("Search");

		topPanel.add(new JLabel("Word or pattern:"));
		topPanel.add(inputField, "span 3, growx");
		topPanel.add(searchButton, "wrap");

		topPanel.add(regexCheck);
		topPanel.add(fullMatchCheck);
		topPanel.add(new JLabel("Max length:"));
		topPanel.add(maxLengthField, "w 60!");
		topPanel.add(new JLabel(), "wrap");

		// === FILTER PANEL ===
		JPanel filterPanel = new JPanel(new MigLayout("insets 0"));
		prefixField = new JTextField(10);
		suffixField = new JTextField(10);
		filterPanel.add(new JLabel("Starts with:"));
		filterPanel.add(prefixField, "gapright 20");
		filterPanel.add(new JLabel("Ends with:"));
		filterPanel.add(suffixField, "wrap");

		// === RESULTS AREA ===
		resultsArea = new JTextArea();
		resultsArea.setEditable(false);
		resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		JScrollPane scroll = new JScrollPane(resultsArea);

		// === BOTTOM STATUS PANEL ===
		JPanel bottomPanel = new JPanel(new MigLayout("fillx, insets 10"));
		infoLabel = new JLabel("Enter a word and press Search");
		saveButton = new JButton("💾 Save results");
		saveButton.setEnabled(false);
		saveButton.addActionListener(e -> saveResults());
		bottomPanel.add(infoLabel, "growx, push");
		bottomPanel.add(saveButton, "right");

		// === Combine top + filters ===
		JPanel northPanel = new JPanel(new BorderLayout());
		northPanel.add(topPanel, BorderLayout.NORTH);
		northPanel.add(filterPanel, BorderLayout.SOUTH);

		add(northPanel, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		// === Actions ===
		searchButton.addActionListener(e -> performSearch());
		inputField.addActionListener(e -> performSearch());
	}

	// ===== Search logic =====
	private void performSearch() {
		String input = inputField.getText().trim().toLowerCase();
		String prefix = prefixField.getText().trim().toLowerCase();
		String suffix = suffixField.getText().trim().toLowerCase();

		if (input.isEmpty() && prefix.isEmpty() && suffix.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter at least one search criterion.",
					"Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}

		boolean useRegex = regexCheck.isSelected();
		boolean fullMatch = fullMatchCheck.isSelected();
		int maxLen = 0;
		try {
			maxLen = Integer.parseInt(maxLengthField.getText().trim());
		} catch (Exception ignored) {}

		resultsArea.setText("Searching...\n");
		long start = System.currentTimeMillis();

		List<String> results = useRegex
				? searchRegex(input, fullMatch)
						: searchContains(input, maxLen);

		if (!prefix.isEmpty()) results.removeIf(w -> !w.startsWith(prefix));
		if (!suffix.isEmpty()) results.removeIf(w -> !w.endsWith(suffix));

		long elapsed = System.currentTimeMillis() - start;

		resultsArea.setText("");
		if (results.isEmpty()) {
			resultsArea.append("❌ No matches found.\n");
			saveButton.setEnabled(false);
		} else {
			results.stream().limit(300).forEach(w -> resultsArea.append(w + "\n"));
			if (results.size() > 300)
				resultsArea.append("... (" + (results.size() - 300) + " more)\n");
			saveButton.setEnabled(true);
		}

		lastResults = results;
		infoLabel.setText("Found " + results.size() + " words in " + elapsed + " ms");
	}

	// ===== Search helpers =====
	private List<String> searchContains(String word, int maxLength) {
		List<String> results = new ArrayList<>();
		if (word.isEmpty()) return new ArrayList<>(words);
		for (String w : words) {
			if (w.contains(word)) {
				if (maxLength == 0 || w.length() <= maxLength)
					results.add(w);
			}
		}
		return results;
	}

	private List<String> searchRegex(String pattern, boolean fullMatch) {
		List<String> results = new ArrayList<>();
		if (pattern.isEmpty()) return new ArrayList<>(words);
		Pattern regex = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		for (String w : words) {
			Matcher m = regex.matcher(w);
			boolean ok = fullMatch ? m.matches() : m.find();
			if (ok) results.add(w);
		}
		return results;
	}

	// ===== Save results to file =====
	private void saveResults() {
		if (lastResults.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No results to save.", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}


		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Scegli dove salvare il file");
		fileChooser.setSelectedFile(new File("results.txt"));


		int userSelection = fileChooser.showSaveDialog(null);

		// 3. Controlliamo se l'utente ha premuto "Salva"
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			// 4. Otteniamo il file selezionato
			File fileDaSalvare = fileChooser.getSelectedFile();

			// Assicuriamoci che il file abbia l'estensione .txt se non è stata specificata
			if (!fileDaSalvare.getAbsolutePath().endsWith(".txt")) {
				fileDaSalvare = new File(fileDaSalvare.getAbsolutePath() + ".txt");
			}

			System.out.println("Salva come file: " + fileDaSalvare.getAbsolutePath());



			Path file = Paths.get(fileDaSalvare.getAbsolutePath());
			try {
				Files.write(file, lastResults);
				JOptionPane.showMessageDialog(this, "Results saved to:\n" + file.toAbsolutePath(),
						"Saved", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Error while saving:\n" + e.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		} 
	}

	// ===== Download dictionary if missing =====
	@SuppressWarnings("deprecation")
	private static List<String> loadDictionary() throws IOException {  
		Path path = Paths.get(DICTIONARY_FILE);

		if (Files.exists(path)) {
			System.out.println("📘 Local dictionary found: " + DICTIONARY_FILE);
			return Files.readAllLines(path);
		}

		System.out.println("🌐 Downloading dictionary from: " + DICTIONARY_URL);
		List<String> lines = new ArrayList<>();
		try (InputStream in = new URL(DICTIONARY_URL).openStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("<html>")) {
					throw new IOException("⚠️ Invalid response: received HTML instead of text.");
				}
				lines.add(line.trim().toLowerCase());
			}
		}

		Files.write(path, lines);
		System.out.println("✅ Dictionary downloaded and saved (" + lines.size() + " words).");
		return lines;
	}

	// ===== Main method =====
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				List<String> words = loadDictionary();
				DictionarySearchGUI gui = new DictionarySearchGUI(words);
				gui.setVisible(true);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"Error loading dictionary:\n" + e.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
