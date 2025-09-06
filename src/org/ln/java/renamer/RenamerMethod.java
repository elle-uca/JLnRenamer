package org.ln.java.renamer;

import java.util.ArrayList;
import java.util.List;

import org.ln.java.renamer.Costants.ModeCase;

public class RenamerMethod {
	
	
	
    /**
     * Inserisce un testo in ogni stringa di una lista, usando un indice a base 1.
     * Se la posizione è maggiore della lunghezza della stringa, il testo viene appeso alla fine.
     *
     * @param list La lista di stringhe da modificare.
     * @param text Il testo da inserire in ogni stringa.
     * @param pos La posizione a base 1 (es. 1 per l'inizio, 2 per il secondo carattere...).
     * @return Una NUOVA lista contenente le stringhe modificate.
     */
    public static List<RnFile> addMethod(List<RnFile> list, String text, int pos) {
        for (RnFile file : list) {
        	
        	String str = file.getFrom().getNameExtensionLess();
            // 1. Converte la posizione da base 1 a base 0 (usata da Java).
            //    Se l'utente inserisce < 1, si considera come se volesse inserire all'inizio (indice 0).
            int indiceBaseZero = Math.max(0, pos - 1);

            // 2. Decide la posizione finale. Se l'indice calcolato supera la lunghezza,
            //    usa la lunghezza stessa, risultando in un'aggiunta alla fine (append).
            int indiceFinale = Math.min(indiceBaseZero, str.length());

            // 3. Esegue l'inserimento nella posizione calcolata.
            String nuovaStringa = new StringBuilder(str)
                .insert(indiceFinale, text)
                .toString();
            
            file.setNameDest(nuovaStringa);
        }
        return list;
    }
    
    /**
     * Rimuove un numero 'n' di caratteri da ogni stringa di una lista,
     * partendo da una posizione 'm' (a base 1).
     *
     * @param list La lista di stringhe da modificare.
     * @param pos La posizione di partenza a base 1 da cui iniziare a rimuovere.
     * @param number Il numero di caratteri da rimuovere.
     * @return Una NUOVA lista contenente le stringhe modificate.
     */
    public static List<RnFile> removeMethod(List<RnFile> list, int pos, int number) {
        // Se il numero di caratteri da rimuovere è nullo o negativo, restituisci una copia
        // della lista originale senza fare nulla.
        if (number <= 0) {
            return list;
        }

        List<String> result = new ArrayList<>();
        for (RnFile file : list) {

        	String str = file.getFrom().getNameExtensionLess();
            // Converte la posizione da base 1 a base 0.
            int startIndex = pos - 1;

            // Se la posizione di partenza non è valida per la stringa corrente,
            // aggiungi la stringa originale e passa alla successiva.
            if (startIndex < 0 || startIndex >= str.length()) {
                result.add(str);
                continue;
            }

            // Calcola l'indice finale della rimozione.
            // Usa Math.min per assicurarsi di non andare oltre la fine della stringa.
            int endIndex = Math.min(startIndex + number, str.length());
            
            // Esegue la rimozione e aggiunge il risultato alla nuova lista.
            String nuovaStringa = new StringBuilder(str)
                .delete(startIndex, endIndex)
                .toString();
            
            file.setNameDest(nuovaStringa);
        }

        return list;
    }
    
    public static List<RnFile> transformCase(List<RnFile> list, ModeCase modeCase) {
        for (RnFile file : list) {
            file.setNameDest(transformCase(file.getFrom().getNameExtensionLess(), modeCase));
        }
        return list;
    }
    
    public static String transformCase(String input, ModeCase modeCase) {
        if (input == null) return null;

        switch (modeCase) {
            case UPPER:
                return input.toUpperCase();

            case LOWER:
                return input.toLowerCase();

            case TITLE_CASE:
                String[] words = input.toLowerCase().split("\\s+");
                StringBuilder sb = new StringBuilder();
                for (String word : words) {
                    if (!word.isEmpty()) {
                        sb.append(Character.toUpperCase(word.charAt(0)))
                          .append(word.substring(1))
                          .append(" ");
                    }
                }
                return sb.toString().trim();

            case CAPITALIZE_FIRST:
                return Character.toUpperCase(input.charAt(0)) 
                        + input.substring(1).toLowerCase();

            case TOGGLE_CASE:
                StringBuilder toggled = new StringBuilder();
                for (char c : input.toCharArray()) {
                    if (Character.isUpperCase(c)) {
                        toggled.append(Character.toLowerCase(c));
                    } else if (Character.isLowerCase(c)) {
                        toggled.append(Character.toUpperCase(c));
                    } else {
                        toggled.append(c);
                    }
                }
                return toggled.toString();

            default:
                throw new IllegalArgumentException("Modalità non valida: " + modeCase);
        }
    }

}
