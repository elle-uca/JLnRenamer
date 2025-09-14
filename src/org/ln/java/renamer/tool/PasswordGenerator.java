package org.ln.java.renamer.tool;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A password generator
 */
public class PasswordGenerator {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+[]{};:,.<>?/";

    public static String generatePassword(int length, boolean useLower, 
    		boolean useUpper, boolean useDigits, boolean useSpecial) {
        StringBuilder charPool = new StringBuilder();
        SecureRandom random = new SecureRandom();
        List<Character> passwordChars = new ArrayList<>();

        if (useLower) {
            charPool.append(LOWERCASE);
            passwordChars.add(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        }
        if (useUpper) {
            charPool.append(UPPERCASE);
            passwordChars.add(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        }
        if (useDigits) {
            charPool.append(DIGITS);
            passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        if (useSpecial) {
            charPool.append(SPECIAL);
            passwordChars.add(SPECIAL.charAt(random.nextInt(SPECIAL.length())));
        }

        if (charPool.length() == 0) {
            throw new IllegalArgumentException("You must select at least one character set!");
        }

        // Adds random characters up to the required length
        while (passwordChars.size() < length) {
            passwordChars.add(charPool.charAt(random.nextInt(charPool.length())));
        }

        // shuffle the list so you don't have all the guaranteed characters in your head.
        Collections.shuffle(passwordChars, random);

        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }

}
