package org.ln.java.renamer.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.ln.java.renamer.Costants;

/**

 *
 * @author  Luca Noale
 */

public class Word extends RnTag {

	//final static String DELIMITERS = ".-_()[]";


	/**
	 * @param arg
	 */
	public Word(Integer...arg) {
		super(arg);
		this.tagName = "Word";
	}



	@Override
	public void init() {
		newName.clear();
		for (String string : oldName) {
			List<String> sub = extractSubstringsFromChars(string, Costants.DELIMITERS);
			int w = start  <= sub.size() ? start : sub.size() ;
			newName.add(sub.get(w-1));
		}
	}



	@Override
	public String getDescription() {
		return "Un segmento del nome";
	}



	/**
	 * @param inputString
	 * @param delimitersRegex
	 * @return
	 */
	public static List<String> extractSubstringsByDelimiters(String inputString, String delimitersRegex) {
		List<String> substrings = new ArrayList<>();

		if (inputString == null || inputString.isEmpty() || delimitersRegex == null || delimitersRegex.isEmpty()) {
			return substrings; // Restituisce una lista vuota per input non validi
		}

		// Compila l'espressione regolare per i delimitatori
		// Pattern pattern = Pattern.compile(delimitersRegex);

		// Usa Splitter per dividere la stringa in base ai delimitatori
		// Si noti che String.split() è più semplice in questo caso rispetto a Matcher per dividere.
		// String.split() accetta direttamente una regex.

		String[] parts = inputString.split(delimitersRegex);

		// Aggiungi le parti non vuote alla lista
		for (String part : parts) {
			if (!part.isEmpty()) { // Evita di aggiungere stringhe vuote risultanti da delimitatori consecutivi o all'inizio/fine
				substrings.add(part);
			}
		}

		return substrings;
	}  


	/**
	 * Extracts substrings from a string using the specified delimiter characters.
	 * This version is a convenience method that automatically builds the regular expression for the delimiters.
	 *
	 * @param inputString The string from which to extract substrings.
	 * @param delimiterChars A string containing all characters to be treated as delimiters.
	 * For example, "-_()[]" to treat -, _, (, ), [, ] as delimiters.
	 * Escaping special characters is not required here; the method handles it.
	 * @return A list of substrings.
	 */
	public static List<String> extractSubstringsFromChars(String inputString, String delimiterChars) {
		if (delimiterChars == null || delimiterChars.isEmpty()) {
			// Se non ci sono delimitatori, l'intera stringa è la sottostringa
			List<String> result = new ArrayList<>();
			if (inputString != null && !inputString.isEmpty()) {
				result.add(inputString);
			}
			return result;
		}

		// Costruisce una regex che metcha qualsiasi dei caratteri forniti.
		// `Pattern.quote()` fa l'escape dei caratteri speciali della regex.
		// `String.join("|", ...)` potrebbe essere usato, ma una classe di caratteri `[...]` è più efficiente.
		// Per includere caratteri come '-' in una classe di caratteri, è meglio metterlo all'inizio o alla fine.
		// Ad esempio, "[\\Q-\\E_()\\[\\]]"
		StringBuilder regexBuilder = new StringBuilder("[");
		for (char c : delimiterChars.toCharArray()) {
			regexBuilder.append(Pattern.quote(String.valueOf(c))); // Fa l'escape di ogni singolo carattere
		}
		regexBuilder.append("]");
		return extractSubstringsByDelimiters(inputString, regexBuilder.toString());
	}



}
