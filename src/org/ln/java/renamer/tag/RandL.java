package org.ln.java.renamer.tag;

import java.util.Random;

public class RandL extends RnTag {

	public RandL(Integer... arg) {
		super(arg);
		this.tagName = "RandL";
	}

	@Override
	public void init() {
		newName.clear();
		for (int i = 0; i < oldName.size(); i++) {
			newName.add(generateRandomLetters(start));
		}
	}

	@Override
	public String getDescription() {
		return "Una stringa casuale";
	}

	
    /**
     * @param length
     * @return
     */
    public static String generateRandomLetters(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("La lunghezza deve essere maggiore di 0.");
        }

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char c = (char) ('A' + random.nextInt(26));
            sb.append(c);
        }
        return sb.toString();
    }
	
}
