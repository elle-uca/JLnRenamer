package org.ln.java.renamer.tag;

import java.util.ArrayList;
import java.util.List;

/**
 * <IncR:1:1>
 * Genera una serie di numeri romani con un dato incremento a partire da start.
 * Start non può essere un numero negativo
 * Il secondo parametro è opzionale e stabilisce l'incremento, di default è 1
 *
 *
 * @author  Luca Noale
 */
public class IncR extends RnTag {


	/**
	 * @param arg
	 */
	public IncR(Integer...arg) {
		super(arg);
		this.tagName = "IncR";
	}


	@Override
	public void init() {
		this.newName = incrementalRoman(this, oldName);
	}



	@Override
	public String getDescription() {
		return "Numero romano";
	}

	/**
	 * @param start
	 * @param step
	 * @param len
	 * @return
	 */
	public static ArrayList<String> incrementalRoman(RnTag tag, List<String> nameList){
		ArrayList<String> list = new ArrayList<String>();
		int incr = tag.getStart();
		for(int i = 0; i<  nameList.size(); i++) {
			list.add(intToRoman(incr));
			incr =   incr + tag.getStep() ;
		}
		return list;
	}
	/**
	 * @param num
	 * @return
	 */
	public static String intToRoman(int num){
		// storing roman values of digits from 0-9
		// when placed at different places
		String m[] = { "", "M", "MM", "MMM" };
		String c[] = { "",  "C",  "CC",  "CCC",  "CD",
				"D", "DC", "DCC", "DCCC", "CM" };
		String x[] = { "",  "X",  "XX",  "XXX",  "XL",
				"L", "LX", "LXX", "LXXX", "XC" };
		String i[] = { "",  "I",  "II",  "III",  "IV",
				"V", "VI", "VII", "VIII", "IX" };

		// Converting to roman
		String thousands = m[num / 1000];
		String hundreds = c[(num % 1000) / 100];
		String tens = x[(num % 100) / 10];
		String ones = i[num % 10];

		return thousands + hundreds + tens + ones;
	}
}
