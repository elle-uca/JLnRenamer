package org.ln.java.renamer;

import java.io.File;


@SuppressWarnings("serial")
public class AdFile extends File {
	



	public AdFile(String pathname) {
		super(pathname);
	}


	/**
	 * @return	the extension of file without the dot
	 */
	public String getExtension() {
		return getName().substring(getName().toString().lastIndexOf(Costants.DOT) + 1);
	}


	/**
	 * @return	the name of file without the extension
	 */
	public  String getNameExtensionLess() {
		int n = getName().toString().lastIndexOf(Costants.DOT);
		int index = n > -1 ? n:getName().toString().length();
		return getName().substring(0, index);
	}

	/**
     * @param  dest  The new abstract pathname for the named file
     *
     * @return  {@code true} if and only if the renaming succeeded and the file
     * 			dest not exist;
     *          {@code false} otherwise
     *
     *
     * @throws  NullPointerException
     * 			If parameter {@code dest} is {@code null}
     *   
	 */
	public boolean renameTo(AdFile dest) {
		if(dest.exists()) {
			return false;
		}
		return super.renameTo(dest);
	}
	
//	/**
//	 * @param pathname 	A pathname string
//	 * 
//     * @return  {@code true} if and only if the renaming succeeded and the file
//     * 			dest not exist;
//     *          {@code false} otherwise
//     *          
//	 */
//	public  boolean renameTo(String parent, String name, String ext) {
//		return renameTo(new File(parent+separator+name+Costants.DOT+ext));
//	}	
	

//	/**
//	 * @param name
//	 * @return
//	 */
//	public  boolean newName(String name) {
//		return renameTo(getParent(), name, getExtension()) ;
//	}




	@Override
	public String toString() {
		return "AdFile [getParent()=" + getParent() + ", getName()=" + 
				getNameExtensionLess() + ", getExtension()=" + getExtension()
				+ "]";
	}



}