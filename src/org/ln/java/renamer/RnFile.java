package org.ln.java.renamer;

import java.io.File;

import org.ln.java.renamer.Costants.FileStatus;

public class RnFile {

	private AdFile from;
	

	private FileStatus fileStatus = FileStatus.OK;
	
	private String nameDest;
	
	private Boolean selected = Boolean.valueOf(false);




	public RnFile(AdFile from) {
		this.from = from;
		//System.out.println(from.exists());
		nameDest = "";
		if(from.exists()) {
			//System.out.println("Status OKKKK");
			fileStatus = FileStatus.OK;
		}else {
			//System.out.println("Status KOOOO");
			fileStatus = FileStatus.KO;
		}
	}


	/**
	 * @return the nameDest
	 */
	public String getNameDest() {
		return nameDest;
	}


	/**
	 * @param nameDest the nameDest to set
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


	public boolean renameTo() {
		String dest = from.getParent()+File.separator+nameDest+"."+from.getExtension();
		//System.out.println(from.getAbsolutePath()+" dest    "+dest);
		return from.renameTo(new File(dest));
	}



	public Boolean getSelected() {
		return selected;
	}


	public void setSelected(Boolean selected) {
		this.selected = selected;
	}


	public void rename(String name) {
		from.newName(name);
	}

	
	
	
	
	@Override
	public String toString() {
		return "RnFiles [from=" + from.toString() + ", to=" + getNameDest() + "]";
	}
	
	
//	public static void main(String[] args) {
//		RnFile f = new RnFile(new AdFile("/home/luke/test/pippo/pippo6.txt"));
//		System.out.println(f.getFrom().getNameExtensionLess());
//		f.getFrom().newName("piripicchio");
//		System.out.println(f);
//	}

}
