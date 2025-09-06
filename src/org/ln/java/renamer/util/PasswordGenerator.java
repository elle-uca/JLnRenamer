package org.ln.java.renamer.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PasswordGenerator {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+[]{};:,.<>?/";

    public static String generatePassword(int length, boolean useLower, boolean useUpper, boolean useDigits, boolean useSpecial) {
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
            throw new IllegalArgumentException("Devi selezionare almeno un set di caratteri!");
        }

        // Aggiunge caratteri casuali fino alla lunghezza richiesta
        while (passwordChars.size() < length) {
            passwordChars.add(charPool.charAt(random.nextInt(charPool.length())));
        }

        // Mescola la lista per non avere i caratteri garantiti tutti in testa
        Collections.shuffle(passwordChars, random);

        // Costruisce la password finale
        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }

    @SuppressWarnings("resource")
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Lunghezza password: ");
        int length = scanner.nextInt();

        System.out.print("Includere lettere minuscole? (true/false): ");
        boolean useLower = scanner.nextBoolean();

        System.out.print("Includere lettere maiuscole? (true/false): ");
        boolean useUpper = scanner.nextBoolean();

        System.out.print("Includere numeri? (true/false): ");
        boolean useDigits = scanner.nextBoolean();

        System.out.print("Includere caratteri speciali? (true/false): ");
        boolean useSpecial = scanner.nextBoolean();

        try {
            String password = generatePassword(length, useLower, useUpper, useDigits, useSpecial);
            System.out.println("✅ Password generata: " + password);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Errore: " + e.getMessage());
        }
    }
}
