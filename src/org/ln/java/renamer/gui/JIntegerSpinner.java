package org.ln.java.renamer.gui;


import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Una specializzazione di JSpinner che lavora con valori interi.
 * Fornisce un comodo metodo getIntValue() per evitare cast espliciti.
 */
@SuppressWarnings("serial")
public class JIntegerSpinner extends JSpinner {



    /**
     * Costruttore di default.
     * Crea uno JIntegerSpinner con valori di default (1, 0, max int, 1).
     */
    public JIntegerSpinner() {
    	this(1, 0, Integer.MAX_VALUE, 1);
    }
    
    /**
     * Costruttore di default.
     * Crea uno JIntegerSpinner con valori di default (1, 0, max int, 1).
     */
    public JIntegerSpinner(int value) {
        this(value, 0, Integer.MAX_VALUE, 1);
    }

    /**
     * Costruisce uno JIntegerSpinner con un valore iniziale, un minimo, un massimo
     * e uno step (passo di incremento/decremento).
     *
     * @param value Il valore iniziale e corrente del spinner.
     * @param min   Il valore minimo consentito.
     * @param max   Il valore massimo consentito.
     * @param step  Il valore di cui aumentare o diminuire ad ogni scatto.
     */
    public JIntegerSpinner(int value, int min, int max, int step) {
        super(new SpinnerNumberModel(value, min, max, step));
    }
    
    /**
     * Restituisce il valore corrente dello spinner come un primitivo int.
     *
     * @return il valore intero dello spinner.
     */
    public int getIntValue() {
        // Poiché abbiamo garantito che il modello è un SpinnerNumberModel,
        // possiamo tranquillamente ottenere il valore come Number e poi
        // convertirlo in int. Usare .intValue() è più sicuro che
        // castare direttamente a (Integer).
        return ((Number) super.getValue()).intValue();
    }
}