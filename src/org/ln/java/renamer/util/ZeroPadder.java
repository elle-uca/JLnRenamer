package org.ln.java.renamer.util;
public class ZeroPadder {

    /**
     * Aggiunge un numero specifico di zeri iniziali a un numero intero.
     *
     * @param number Il numero da formattare.
     * @param zeroCount Il numero di zeri da anteporre.
     * @return Una stringa rappresentante il numero con gli zeri iniziali.
     */
    public static String padNumberWithZeros(int number, int zeroCount) {
        // Converte il numero in stringa per calcolarne la lunghezza attuale
        String numberStr = String.valueOf(number);
        
        // Calcola la lunghezza finale che la stringa dovrà avere
        int totalLength = numberStr.length() + zeroCount;
        
        // Crea la stringa di formato, es. "%04d" per una lunghezza totale di 4
        String formatSpecifier = "%0" + totalLength + "d";
        
        // Applica la formattazione e restituisce il risultato
        return String.format(formatSpecifier, number);
    }

    /**
     * Formatta un numero aggiungendo zeri iniziali fino a raggiungere
     * una lunghezza totale specificata.
     *
     * @param number Il numero da formattare.
     * @param totalDigits La lunghezza totale desiderata per la stringa finale.
     * @return Una stringa del numero con riempimento di zeri.
     */
    public static String padToTotalDigits(int number, int totalDigits) {
        // Costruisce la stringa di formato, es. "%05d"
        String formatSpecifier = "%0" + totalDigits + "d";
        
        // Applica la formattazione e restituisce il risultato
        return String.format(formatSpecifier, number);
    }
    
    public static void main(String[] args) {
        int numero = 11;
        int zeriDaAggiungere = 2;
        String risultato = padNumberWithZeros(numero, zeriDaAggiungere);
        
        System.out.println("Numero originale: " + numero); // Stampa 11
        System.out.println("Risultato con " + zeriDaAggiungere + " zeri: " + risultato); // Stampa 0011

        System.out.println("--- Altro esempio ---");
        
        int numero2 = 123;
        int zeriDaAggiungere2 = 4;
        String risultato2 = padNumberWithZeros(numero2, zeriDaAggiungere2);
        System.out.println("Risultato con " + zeriDaAggiungere2 + " zeri: " + risultato2); // Stampa 0000123
    }
}