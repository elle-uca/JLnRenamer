package org.ln.java.renamer;

public class Costants {

	public static final String DOT = ".";

	public static final  String DELIMITERS = ".-_()[]";

	public static final String LAST_DIR_KEY = "lastDir";

	public static final String CONFIG_FILE = "config.properties";

	public static final String TAG_PACKAGE = "org.ln.java.renamer.tag.";

	public static final String ICON_RIGHT = "/icons/arrow_right.png";

	public static final String ICON_DOWN = "/icons/arrow_down.png";
	
	public static final String PROPERTIES_LANG_PATH = "i18n.languages";
	
	
	
	 /**
     * Enum to define the type of replacement to perform.
     */
    public enum ReplacementType {
        FIRST,
        LAST,
        ALL
    }

 



//	/**
//	 * Enum che definisce le diverse modalità di trasformazione del testo.
//	 * Ogni costante implementa la propria logica di trasformazione.
//	 */
//	public enum ModeCase {
//
//	    UPPER {
//	        @Override
//	        public String transform(String input) {
//	            return input.toUpperCase();
//	        }
//	    },
//
//	    LOWER {
//	        @Override
//	        public String transform(String input) {
//	            return input.toLowerCase();
//	        }
//	    },
//
//	    TITLE_CASE {
//	        @Override
//	        public String transform(String input) {
//	            // Questa logica rimane invariata
//	            return Arrays.stream(input.toLowerCase().split("\\s+"))
//	                    .filter(word -> !word.isBlank())
//	                    .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
//	                    .collect(Collectors.joining(" "));
//	        }
//	    },
//
//	    CAPITALIZE_FIRST {
//	        @Override
//	        public String transform(String input) {
//	            // Questa logica rimane invariata
//	            return input.isEmpty()
//	                    ? input
//	                    : Character.toUpperCase(input.charAt(0)) + input.substring(1).toLowerCase();
//	        }
//	    },
//
//	    TOGGLE_CASE {
//	        @Override
//	        public String transform(String input) {
//	            // Ottimizzato: StringBuilder è molto più efficiente 
//	            // per la manipolazione di singoli caratteri rispetto a uno stream.
//	            var sb = new StringBuilder(input.length());
//	            for (char ch : input.toCharArray()) {
//	                if (Character.isUpperCase(ch)) {
//	                    sb.append(Character.toLowerCase(ch));
//	                } else if (Character.isLowerCase(ch)) {
//	                    sb.append(Character.toUpperCase(ch));
//	                } else {
//	                    sb.append(ch);
//	                }
//	            }
//	            return sb.toString();
//	        }
//	    };
//
//	    /**
//	     * Metodo astratto che applica la trasformazione specifica di questo caso.
//	     *
//	     * @param input La stringa da trasformare.
//	     * @return La stringa trasformata.
//	     */
//	    public abstract String transform(String input);
//	}
	

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
