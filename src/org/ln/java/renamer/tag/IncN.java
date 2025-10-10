package org.ln.java.renamer.tag;

import java.util.ArrayList;
import java.util.List;

import org.ln.java.renamer.RnPrefs;
import org.ln.java.renamer.Costants.FillOption;
import org.ln.java.renamer.util.ZeroPadder;

/**
 * <IncN:1:1>
 * Genera una serie di numeri con un dato incremento a partire da start.
 * Start può essere un numero negativo
 * Il secondo parametro è opzionale e stabilisce l'incremento, di default è 1
 *
 *
 * @author  Luca Noale
 */
public class IncN extends RnTag {


	/**
	 * @param arg
	 */
	public IncN(Integer...arg) {
		super(arg);
		this.tagName = "IncN";
	}


	@Override
	public void init() {
		this.newName = incrementalNumber(this, oldName, true);
	}


	@Override
	public String getDescription() {
		return "Numero incrementale";
	}

	
	/**
	 * @param tag
	 * @param nameList
	 * @param plus
	 * @return
	 */
	public static List<String> incrementalNumber(RnTag tag, List<String> nameList, boolean plus){
		List<String> result = new ArrayList<String>();
		int incr = tag.getStart();
		for(int i = 0; i < nameList.size(); i++) {
			result.add(ZeroPadder.padNumber(incr, 
					Integer.parseInt(RnPrefs.getInstance().getGlobalProperty("FILL_VALUE")), 
					FillOption.getByPref()));
			incr =  plus  ?  incr + tag.getStep()  :  incr - tag.getStep() ;
		}
		return result;
	}


}
