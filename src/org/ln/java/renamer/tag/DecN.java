package org.ln.java.renamer.tag;

/**
 * <DecN:1:1>
 * Genera una serie di numeri con un dato decremento a partire da start.
 * Start può essere un numero negativo
 * Il secondo parametro è opzionale e stabilisce il decremento, di default è 1
 *
 *
 * @author Luca Noale
 */
public class DecN extends IncN {

	/**
	 * @param arg
	 */
	public DecN(Integer...arg) {
		super(arg);
		this.tagName = "DecN";
	}

	@Override
	public String getDescription() {
		return "Numero decrescente";
	}

	@Override
	public void init() {
		this.newName = incrementalNumber(this, oldName, false);
	}



}
