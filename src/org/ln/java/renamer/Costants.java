package org.ln.java.renamer;

public class Costants {

	public static final String DOT = ".";
	
	public static final  String DELIMITERS = ".-_()[]";
	
	public static final String LAST_DIR_KEY = "lastDir";
	
	public static final String CONFIG_FILE = "config.properties";
	
	public static final String TAG_PACKAGE = "org.ln.java.renamer.tag.";
	
	public static final String ICON_RIGHT = "/icons/arrow_right.png";
	
	public static final String ICON_DOWN = "/icons/arrow_down.png";
	
    public enum FillOption {
        NO_FILL("Nessun riempimento", "NO_FILL", 0),
        FILL_TO_ZERO("Riempi con zeri", "FILL_TO_ZERO", 1),
        FILL_TO_NUMBER("Riempi fino a", "FILL_TO_NUMBER", 2);

        private final String displayName;
        private final String keyName;
        private final int keyValue;

        FillOption(String displayName, String keyName, int keyValue) {
            this.displayName = displayName;
			this.keyName = keyName;
			this.keyValue = keyValue;
        }

        @Override
        public String toString() {
            return displayName; 
        }
        
        public String keyString() {
            return keyName; 
        }

		/**
		 * @return the keyValue
		 */
		public int getKeyValue() {
			return keyValue;
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
