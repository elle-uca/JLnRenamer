package org.ln.java.renamer.util;

import org.ln.java.renamer.Costants.FillOption;

public class ZeroPadder {

//    /**
//     * Aggiunge un numero specifico di zeri iniziali a un numero intero.
//     *
//     * @param number Il numero da formattare.
//     * @param zeroCount Il numero di zeri da anteporre.
//     * @return Una stringa rappresentante il numero con gli zeri iniziali.
//     */
//    public static String padNumberWithZeros(int number, int zeroCount) {
//        // Converte il numero in stringa per calcolarne la lunghezza attuale
//        String numberStr = String.valueOf(number);
//        
//        // Calcola la lunghezza finale che la stringa dovrà avere
//        int totalLength = numberStr.length() + zeroCount;
//        
//        // Crea la stringa di formato, es. "%04d" per una lunghezza totale di 4
//        String formatSpecifier = "%0" + totalLength + "d";
//        
//        // Applica la formattazione e restituisce il risultato
//        return String.format(formatSpecifier, number);
//    }
//
//    /**
//     * Formatta un numero aggiungendo zeri iniziali fino a raggiungere
//     * una lunghezza totale specificata.
//     *
//     * @param number Il numero da formattare.
//     * @param totalDigits La lunghezza totale desiderata per la stringa finale.
//     * @return Una stringa del numero con riempimento di zeri.
//     */
//    public static String padToTotalDigits(int number, int totalDigits) {
//        // Costruisce la stringa di formato, es. "%05d"
//        String formatSpecifier = "%0" + totalDigits + "d";
//        
//        // Applica la formattazione e restituisce il risultato
//        return String.format(formatSpecifier, number);
//    }
    
    



    /**
     * Formatta un numero intero aggiungendo zeri iniziali o lo restituisce
     * come stringa semplice, in base alla modalità specificata.
     *
     * @param number Il numero da formattare.
     * @param value La lunghezza totale o il numero di zeri da aggiungere.
     * Questo parametro viene ignorato se il tipo è NO_PAD.
     * @param type Il tipo di formattazione da applicare.
     * @return Una stringa del numero con la formattazione richiesta.
     */
    public static String padNumber(int number, int value, FillOption type) {

    	if (type == FillOption.NO_FILL) {
    		return String.valueOf(number);
    	}
    	if (value < 0) {
    		throw new IllegalArgumentException("Il valore per il padding non può essere negativo-The padding value cannot be negative.");
    	}

    	int totalLength;

    	switch (type) {
    	case FILL_TO_NUMBER:
    		// La 'value' rappresenta direttamente la lunghezza totale finale.
    		totalLength = value;
    		break;

    	case FILL_TO_ZERO:
    		// La 'value' rappresenta il numero di zeri da aggiungere.
    		// Calcoliamo la lunghezza totale sommando gli zeri alla lunghezza del numero.
    		String numberStr = String.valueOf(number);
    		totalLength = numberStr.length() + value;
    		break;

    	default:
    		throw new IllegalStateException("Tipo di padding non supportato-Unsupported padding type: " + type);
    	}

    	// La logica di formattazione finale è ora comune a entrambi i casi.
    	String formatSpecifier = "%0" + totalLength + "d";
    	return String.format(formatSpecifier, number);
    }

    

}