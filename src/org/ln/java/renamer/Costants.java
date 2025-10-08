package org.ln.java.renamer;

public class Costants {

	public static final String DOT = ".";
	
	public static final  String DELIMITERS = ".-_()[]";
	
	public static final String LAST_DIR_KEY = "lastDir";
	
	
    public enum FillOption {
        NO_FILL("Nessun riempimento"),
        FILL_TO_ZERO("Riempi con zeri"),
        FILL_TO_NUMBER("Riempi fino a");

        private final String displayName;

        FillOption(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName; 
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
