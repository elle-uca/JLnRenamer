package org.ln.java.renamer.tag;

import java.util.Random;

public class RandN extends RnTag {

	public RandN(Integer... arg) {
		super(arg);
		this.tagName = "RandN";
	}

	@Override
	public void init() {
		newName.clear();
		for (int i = 0; i < oldName.size(); i++) {
			newName.add(generateRandomNumber(start)+"");
		}
	}

	@Override
	public String getDescription() {
		return "Un numero casuale";
	}

    /**
     * Genera un numero casuale con un numero fisso di cifre.
     *
     * @param length Numero di cifre desiderato (deve essere >= 1)
     * @return Numero casuale con la lunghezza richiesta
     */
    public static int generateRandomNumber(int length) {
        if (length <= 0 || length > 9) {
            throw new IllegalArgumentException("La lunghezza deve essere maggiore di 0 e minore di 10.");
        }

        Random random = new Random();

        int lowerBound = (int) Math.pow(10, length - 1);
        int upperBound = (int) Math.pow(10, length) - 1;

        // Caso speciale per 1 cifra (da 0 a 9)
        if (length == 1) {
            return random.nextInt(10);
        }

        return random.nextInt(upperBound - lowerBound + 1) + lowerBound;
    }
}
