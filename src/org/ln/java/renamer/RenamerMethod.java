package org.ln.java.renamer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ln.java.renamer.Costants.ModeCase;
import org.ln.java.renamer.Costants.ReplacementType;

public class RenamerMethod {
	
	
	
	/**
	 * Inserts a text into each string of a list, using a 1-based index.
	 * If the position is greater than the string length, the text is appended at the end.
	 *
	 * @param list The list of strings (files) to modify.
	 * @param text The text to insert into each string.
	 * @param pos  The 1-based position (e.g., 1 for the beginning, 2 for after the first character...).
	 * @return A NEW list containing the modified strings.
	 */
	public static List<RnFile> addMethod(List<RnFile> list, String text, int pos) {
	    for (RnFile file : list) {
	        
	        String str = file.getFrom().getNameExtensionLess();
	        // 1. Convert the position from 1-based to 0-based (used by Java).
	        //    If the user enters < 1, we treat it as inserting at the beginning (index 0).
	        int zeroBasedIndex = Math.max(0, pos - 1);

	        // 2. Determine the final position. If the calculated index exceeds
	        //    the string length, use the length itself (appends to the end).
	        int finalIndex = Math.min(zeroBasedIndex, str.length());

	        // 3. Perform the insertion at the calculated position.
	        String newString = new StringBuilder(str)
	            .insert(finalIndex, text)
	            .toString();
	        
	        file.setNameDest(newString);
	    }
	    return list;
	}

	/**
	 * Removes 'n' characters from each string in a list,
	 * starting from a given 1-based position 'm'.
	 *
	 * @param list   The list of strings (files) to modify.
	 * @param pos    The 1-based starting position from which to begin removing.
	 * @param number The number of characters to remove.
	 * @return A NEW list containing the modified strings.
	 */
	public static List<RnFile> removeMethod(List<RnFile> list, int pos, int number) {
	    // If the number of characters to remove is zero or negative,
	    // return the original list without making any changes.
	    if (number <= 0) {
	        return list;
	    }

	    List<String> result = new ArrayList<>();
	    for (RnFile file : list) {

	        String str = file.getFrom().getNameExtensionLess();
	        // Convert the position from 1-based to 0-based.
	        int startIndex = pos - 1;

	        // If the start position is invalid for the current string,
	        // skip modification and keep the original name.
	        if (startIndex < 0 || startIndex >= str.length()) {
	            result.add(str);
	            continue;
	        }

	        // Calculate the end index of the removal.
	        // Use Math.min to ensure we don’t go beyond the end of the string.
	        int endIndex = Math.min(startIndex + number, str.length());
	        
	        // Perform the deletion and store the result.
	        String newString = new StringBuilder(str)
	            .delete(startIndex, endIndex)
	            .toString();
	        
	        file.setNameDest(newString);
	    }

	    return list;
	}

    
    public static List<RnFile> transformCase(List<RnFile> list, ModeCase modeCase) {
        for (RnFile file : list) {
            file.setNameDest(transformCase(file.getFrom().getNameExtensionLess(), modeCase));
        }
        return list;
    }
    
    public static String transformCase(String input, ModeCase modeCase) {
        if (input == null) return null;

        switch (modeCase) {
            case UPPER:
                return input.toUpperCase();

            case LOWER:
                return input.toLowerCase();

            case TITLE_CASE:
                String[] words = input.toLowerCase().split("\\s+");
                StringBuilder sb = new StringBuilder();
                for (String word : words) {
                    if (!word.isEmpty()) {
                        sb.append(Character.toUpperCase(word.charAt(0)))
                          .append(word.substring(1))
                          .append(" ");
                    }
                }
                return sb.toString().trim();

            case CAPITALIZE_FIRST:
                return Character.toUpperCase(input.charAt(0)) 
                        + input.substring(1).toLowerCase();

            case TOGGLE_CASE:
                StringBuilder toggled = new StringBuilder();
                for (char c : input.toCharArray()) {
                    if (Character.isUpperCase(c)) {
                        toggled.append(Character.toLowerCase(c));
                    } else if (Character.isLowerCase(c)) {
                        toggled.append(Character.toUpperCase(c));
                    } else {
                        toggled.append(c);
                    }
                }
                return toggled.toString();

            default:
                throw new IllegalArgumentException("Modalità non valida: " + modeCase);
        }
    }

    
    public static List<RnFile> replaceMethod(List<RnFile> list, String searchString, 
    		String replacementString, ReplacementType type, boolean isCaseSensitive) {
        for (RnFile file : list) {
            file.setNameDest(replaceMethod(file.getFrom().getNameExtensionLess(), 
            		searchString, replacementString, type, isCaseSensitive));
        }
        return list;
    }
    
    /**
     * Replaces occurrences of a substring, with an option for case-sensitivity.
     *
     * @param originalText    The string to search within.
     * @param searchString    The substring to find.
     * @param replacementString The substring to replace with.
     * @param type            The type of replacement (FIRST, LAST, ALL).
     * @param isCaseSensitive If true, the search is case-sensitive. If false, it is not.
     * @return The modified string.
     */
    public static String replaceMethod(String originalText, String searchString, 
    		String replacementString, ReplacementType type, boolean isCaseSensitive) {
        // Input validation to prevent errors
        if (originalText == null || searchString == null || 
        		replacementString == null || searchString.isEmpty()) {
            return originalText;
        }

        // --- CASE 1: CASE-SENSITIVE SEARCH (distinguishes between upper and lower case) ---
        if (isCaseSensitive) {
            switch (type) {
                case FIRST:
                    // Using Pattern.quote() to treat the search string as a literal
                    return originalText.replaceFirst(Pattern.quote(searchString), 
                    		replacementString);
                case ALL:
                    return originalText.replace(searchString, replacementString);
                case LAST:
                    int lastIndex = originalText.lastIndexOf(searchString);
                    if (lastIndex == -1) {
                        return originalText; // Not found, return original
                    }
                    // Rebuild the string manually
                    return originalText.substring(0, lastIndex) + 
                    		replacementString + originalText.substring(lastIndex + 
                    				searchString.length());
            }
        }

        // --- CASE 2: CASE-INSENSITIVE SEARCH (ignores upper/lower case) ---
        else {
            // Compile the regex pattern with the CASE_INSENSITIVE flag
            // Pattern.quote() is used to treat the searchString as literal text and not as a regex expression
            Pattern pattern = Pattern.compile(Pattern.quote(searchString), 
            		Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(originalText);

            switch (type) {
                case FIRST:
                    return matcher.replaceFirst(replacementString);
                case ALL:
                    return matcher.replaceAll(replacementString);
                case LAST:
                    int lastIndex = -1;
                    // Find the starting index of the last match
                    while (matcher.find()) {
                        lastIndex = matcher.start();
                    }
                    if (lastIndex == -1) {
                        return originalText; // Not found
                    }
                    // Rebuild the string using the found index
                    // We need to find the actual length of the matched string since case can vary
                    String matchedSubstring = originalText.substring(
                    		lastIndex, lastIndex + searchString.length());
                    return originalText.substring(0, lastIndex) + replacementString + 
                    		originalText.substring(lastIndex + matchedSubstring.length());
            }
        }
        return originalText; // Default fallback
    }
}
