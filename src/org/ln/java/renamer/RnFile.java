package org.ln.java.renamer;

import java.io.File;

import org.ln.java.renamer.Costants.FileStatus;

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
	 * @return
	 */
	public boolean renameTo() {
		String dest = from.getParent()+File.separator+nameDest+"."+from.getExtension();
		return from.renameTo(new File(dest));
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


//	public void rename(String name) {
//		from.newName(name);
//	}

	
	@Override
	public String toString() {
		return "RnFiles [from=" + from.toString() + ", to=" + getNameDest() + "]";
	}

}
