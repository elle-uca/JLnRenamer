package org.ln.java.renamer.tag;

/**
 * <Subs:1:1>
 * Generates a substring of the original file name from start.
 * Start cannot be a negative number
 * The second parameter is optional and sets the limit
 *
 *
 * @author  Luca Noale
 */
public class Subs extends RnTag {




	/**
	 * @param arg
	 */
	public Subs(Integer...arg) {
		super(arg);
		this.tagName = "Subs";
	}




	@Override
	public void init() {
		newName.clear();
		for (String string : oldName) {
			String sub = getSafeSubstring(string, start-1, step);
			newName.add(sub);
		}
	}



	@Override
	public String getDescription() {
		return "Parte del nome";
	}



	/**
	 * Returns the substring of the given string, between the start index 
	 * (inclusive) and the end index (exclusive).
	 * Handles index validation to avoid OutOfBoundsException.
	 *
	 * @param str 	The string from which to extract the substring.
	 * @param start The starting index (inclusive) of the substring.
	 * @param end 	The ending index (exclusive) of the substring.
	 * @return 		The requested substring, or an empty string if the indices are not valid.
	 */
    public static String getSafeSubstring(String str, int start, int end) {
        if (str == null || str.isEmpty()) {
            return ""; // Returns an empty string for null or empty input

        }

        int len = str.length();

        // Normalizza gli indici
        if (start < 0) {
            start = 0;
        }
        if (end > len) {
            end = len;
        }

        // Controlla la validità degli indici dopo la normalizzazione
        if (start > end || start >= len || end <= 0) {
            return ""; // Restituisce una stringa vuota se gli indici sono ancora non validi (es. start > end)
        }

        // Assicurati che end non sia minore di start dopo le correzioni
        if (end < start) {
            end = start; // Se end è diventato minore di start, imposta end a start per ottenere una stringa vuota
        }

        return str.substring(start, end);
    }


}
