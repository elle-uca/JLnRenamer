package org.ln.java.renamer.tag;

import java.io.File;
import java.util.List;

/**
 * Classe base per i tag
 *
 *
 *
 * @author  Luca Noale
 */
public abstract class RnTag {




	protected String tagName;
	
	protected int start; 	// arg1
	protected int step;		// arg2
	protected int pos;		// arg3
	
	protected List<String> oldName;
	
	protected List<String> newName;


	protected static final char OPEN_TAG = '<';
	protected static final char CLOSE_TAG = '>';
	protected static final String SEPARATOR_TAG = ":";


	

//	public RnTag() {
//		super();
//	}

	public RnTag(Integer...  arg) {
		int size = arg.length;
		if(arg == null || size == 0 ) {
			System.out.println("ERROR VARARGS  ");
		}
		//System.out.println("VARARGS  "+arg.length);
		 for (int i = 0; i < size; i++) {
			if(arg[i] < 1)
				arg[i] = 1;
		}
		start = size >= 1 ? arg[0] : 1;
		step = size >= 2 ? arg[1] : 1;
		pos = size >= 3 ? arg[2] : 1;
		System.out.println("start    "+start);
		System.out.println("step    "+step);
		System.out.println("pos    "+pos);
	}

	public abstract void init() ;
	

	


	/**
	 * @return
	 */
	public abstract String getDescription();


	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getName() {
		return tagName;
	}

	public String getTagString() {
		return OPEN_TAG+tagName+SEPARATOR_TAG+start+CLOSE_TAG;
	}

	@Override
	public String toString() {
		return getTagString()+" - "+getDescription();
		//return OPEN_TAG+tagName+SEPARATOR_TAG+start+SEPARATOR_TAG+step+CLOSE_TAG;
	}

	/**
	 * @return the newName
	 */
	public List<String> getNewName() {
		return newName;
	}

	/**
	 * @param newName the newName to set
	 */
	public void setNewName(List<String> newName) {
		this.newName = newName;
	}




	/**
	 * @return the oldName
	 */
	public List<String> getOldName() {
		return oldName;
	}


	/**
	 * @param oldName the oldName to set
	 */
	public void setOldName(List<String> oldName) {
		this.oldName = oldName;
	}





	public static void main(String[] args) {
		File f = new File("DecN.java");
		System.out.println(f.exists());
	}




}
