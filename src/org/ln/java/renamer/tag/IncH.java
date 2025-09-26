package org.ln.java.renamer.tag;

import java.util.ArrayList;
import java.util.List;

public class IncH extends RnTag {

	public IncH(Integer... arg) {
		super(arg);
		this.tagName = "IncH";	
	}

	@Override
	public void init() {
		newName = generateHexNumbers(start, oldName.size());
	}

	@Override
	public String getDescription() {
		return "Numero esadecimale incrementale";
	}

	
	   /**
     * Genera una lista di numeri esadecimali, partendo da un numero decimale dato.
     *
     * @param start Il numero decimale di partenza.
     * @param count Quanti numeri esadecimali generare.
     * @return Una lista di stringhe contenenti numeri esadecimali in formato maiuscolo.
     */
    public static List<String> generateHexNumbers(int start, int count) {
        List<String> hexNumbers = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int currentNumber = start + i;
            String hex = Integer.toHexString(currentNumber).toUpperCase();
            hexNumbers.add(hex);
        }

        return hexNumbers;
    }
}
