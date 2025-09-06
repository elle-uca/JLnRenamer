package org.ln.java.renamer.util;

import java.util.ArrayList;
import java.util.List;

import org.ln.java.renamer.RnFile;

public  class Utility {


	public static List<String> getOldName(List<RnFile> fileList) {
		List<String> result = new ArrayList<String>();
		for (RnFile file : fileList) {
			result.add(file.getFrom().getNameExtensionLess());
		}

		return result;
	}
	
	public static List<String> getNewName(List<RnFile> fileList) {
		List<String> result = new ArrayList<String>();
		for (RnFile file : fileList) {
			result.add(file.getNameDest());
		}

		return result;
	}
}
