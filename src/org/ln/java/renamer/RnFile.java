package org.ln.java.renamer;

import java.io.File;
import java.io.IOException;

import org.ln.java.renamer.Costants.FileStatus;
import org.ln.java.renamer.util.FileUtils;
import org.ln.java.renamer.util.FileUtils.RenameMode;

public class RnFile {

	private AdFile from;
	
	private FileStatus fileStatus = FileStatus.OK;
	
	private String nameDest;
	
	private Boolean selected = Boolean.valueOf(true);




	public RnFile(AdFile from) {
		this.from = from;
		nameDest = "";
		if(from.exists()) {
			fileStatus = FileStatus.OK;
		}else {
			fileStatus = FileStatus.KO;
		}
	}


	/**
	 * @return the name of destination file
	 */
	public String getNameDest() {
		return nameDest;
	}


	/**
	 * @param nameDest the name of destination file to set
	 */
	public void setNameDest(String nameDest) {
		this.nameDest = nameDest;
	}


	/**
	 * @return the extDest
	 */
	public String getExtDest() {
		return from.getExtension();
	}


	/**
	 * @return the parentDest
	 */
	public String getParentDest() {
		return from.getParent();
	}
	

	/**
	 * @return the from
	 */
	public AdFile getFrom() {
		return from;
	}
	
	
	/**
	 * @return the fileStatus
	 */
	public FileStatus getFileStatus() {
		return fileStatus;
	}


	/**
	 * @param fileStatus the fileStatus to set
	 */
	public void setFileStatus(FileStatus fileStatus) {
		this.fileStatus = fileStatus;
	}


	/**
	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	public File safeRename() {
		File result = null;
		try {
			result = FileUtils.safeRename(from, nameDest, RenameMode.NAME_ONLY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @return
	 */
	public Boolean getSelected() {
		return selected;
	}


	/**
	 * @param selected
	 */
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}



	
	@Override
	public String toString() {
		return "RnFiles [from=" + from.toString() + ", to=" + getNameDest() + "]";
	}




}
