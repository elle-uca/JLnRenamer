package org.ln.java.renamer.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ln.java.renamer.Costants.ReplacementType;

public class AdvancedStringReplacer {

//    /**
//     * Enum to define the type of replacement to perform.
//     */
//    public enum ReplacementType {
//        FIRST,
//        LAST,
//        ALL
//    }

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
    public static String replace(String originalText, String searchString, 
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

    // Main method to showcase examples
    public static void main(String[] args) {
        String text = "Lillo loves Lillo and also lillo loves himself.";
        String search = "lillo";
        String replacement = "Pippo";

        System.out.println("--- CASE-SENSITIVE SEARCH ---");
        System.out.println("FIRST: " + replace(text, search, replacement, ReplacementType.FIRST, true));
        System.out.println("LAST:  " + replace(text, search, replacement, ReplacementType.LAST, true));
        System.out.println("ALL:   " + replace(text, search, replacement, ReplacementType.ALL, true));
        // Expected: Replaces only the two lowercase "lillo" instances

        System.out.println("\n--- CASE-INSENSITIVE SEARCH ---");
        System.out.println("FIRST: " + replace(text, search, replacement, ReplacementType.FIRST, false));
        System.out.println("LAST:  " + replace(text, search, replacement, ReplacementType.LAST, false));
        System.out.println("ALL:   " + replace(text, search, replacement, ReplacementType.ALL, false));
        // Expected: Replaces all three instances of "Lillo" and "lillo"
    }
}