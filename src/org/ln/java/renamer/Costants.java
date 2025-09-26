package org.ln.java.renamer;

public class Costants {

	public static final String DOT = ".";
	
	public static final  String DELIMITERS = ".-_()[]";
	
	
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
