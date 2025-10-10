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

	


	@Override
	public String toString() {
		return "AdFile [getParent()=" + getParent() + ", getName()=" + 
				getNameExtensionLess() + ", getExtension()=" + getExtension()
				+ "]";
	}



}