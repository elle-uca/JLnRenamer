package org.ln.java.renamer;

public class Costants {

	public static final String DOT = ".";

	public static final  String DELIMITERS = ".-_()[]";

	public static final String LAST_DIR_KEY = "lastDir";

	public static final String CONFIG_FILE = "config.properties";

	public static final String TAG_PACKAGE = "org.ln.java.renamer.tag.";

	public static final String ICON_RIGHT = "/icons/arrow_right.png";

	public static final String ICON_DOWN = "/icons/arrow_down.png";
	
	 /**
     * Enum to define the type of replacement to perform.
     */
    public enum ReplacementType {
        FIRST,
        LAST,
        ALL
    }

    /**
     * An enumeration to define the type of padding to apply.
     *
     */
	public enum FillOption {
		NO_FILL("Nessun riempimento"),
		FILL_TO_ZERO("Riempi con zeri"),
		FILL_TO_NUMBER("Riempi fino a");

		private final String displayName;


		private FillOption(String displayName) {
			this.displayName = displayName;
		}

		@Override
		public String toString() {
			return displayName; 
		}

		public  static FillOption getByPref() {
			return fromString(RnPrefs.getInstance().getGlobalProperty("FILL_TYPE"));

		}
		/**
		 * Questo metodo è case-insensitive (non fa distinzione tra maiuscole e minuscole)
		 * e restituisce un valore di default se la stringa non corrisponde a nessuna costante.
		 *
		 * @param text Il nome della costante da cercare (es. "TOTAL_DIGITS").
		 * @param defaultType Il valore da restituire se 'text' non è valido o è nullo.
		 * @return Il PaddingType corrispondente o il valore di default.
		 */
		public static FillOption fromString(String text) {
			if (text == null) {
				return NO_FILL;
			}

			try {
				// valueOf() cerca una corrispondenza esatta (case-sensitive)
				// quindi convertiamo il testo in maiuscolo per renderlo flessibile.
				return FillOption.valueOf(text.trim().toUpperCase());
			} catch (IllegalArgumentException e) {
				// Se la stringa non corrisponde a nessuna costante dell'enum...
				return NO_FILL;
			}
		} 

	}

	public enum RenameMode {
		FULL,        // rinomina tutto (nome + estensione)
		NAME_ONLY,   // rinomina solo il nome, mantiene estensione
		EXT_ONLY     // rinomina solo l'estensione, mantiene il nome
	}

	public enum ModeCase {
		UPPER ("TUTTO MAIUSCOLO"),         
		LOWER ("tutto minuscolo"),         
		TITLE_CASE ("Tutte le iniziali maiuscole"),    
		CAPITALIZE_FIRST ("Iniziale maiuscola prima parola"), 
		TOGGLE_CASE ("iNVERTI mAIUSCOLE") ;

		private String title;
		ModeCase(String string) {
			this.title = string;
		}

		/**
		 * @return the title
		 */
		public String getTitle() {
			return title;
		}
	}

	public enum FileStatus {
		OK ("Ok"),  
		KO1 ("Esiste nella cartella"),
		KO ("Nome duplicato");

		private String title;
		FileStatus(String string) {
			this.title = string;
		}


		@Override
		public String toString() {
			return title;
		}

	}
}
