package org.ln.java.renamer.tag;

import java.util.ArrayList;
import java.util.List;

public class IncL extends RnTag {

	public IncL(Integer... arg) {
		super(arg);
		this.tagName = "IncL";
	}

	@Override
	public void init() {
		this.newName = generateLabelsFrom(toLabel(getStart()), oldName.size());
		
	}

	@Override
	public String getDescription() {
		return "Una stringa incrementale";
	}

	
    // Converte un'etichetta tipo "abj" in indice numerico (es: 0=a, 1=b, ..., 702=aaa)
    public static int fromLabel(String label) {
        int result = 0;

        for (int i = 0; i < label.length(); i++) {
            char c = label.charAt(i);
            result = result * 26 + (c - 'A' + 1);
        }

        return result ; // Per compensare l'offset nel metodo inverso
    }

    // Converte un indice numerico in etichetta tipo "abj"
    public static String toLabel(int index) {
        StringBuilder sb = new StringBuilder();

        while (index > 0) {
            index--;
            char c = (char) ('A' + (index % 26));
            sb.insert(0, c);
            index /= 26;
        }

        return sb.toString();
    }

    // Genera un numero di etichette a partire da una iniziale
    public static List<String>  generateLabelsFrom(String startLabel, int count) {
    	List<String> result = new ArrayList<String>();
        int startIndex = fromLabel(startLabel);
       // System.out.println("startIndex  "+startIndex);

        for (int i = 0; i < count; i++) {
            String label = toLabel(startIndex + i);
           // System.out.println(label);
            result.add(label);
        }
		return result;
    }
    
}
