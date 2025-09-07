package org.ln.java.renamer;

import java.io.File;

import org.ln.java.renamer.Costants.FileStatus;

public class RnFile {

	private AdFile from;
	
	private String status;
	
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the from
	 */
	public AdFile getFrom() {
		return from;
	}


	
	
	public boolean renameTo() {
		String dest = from.getParent()+File.separator+nameDest+"."+from.getExtension();
		System.out.println(from.getAbsolutePath()+" dest    "+dest);
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
	
	
	public static void main(String[] args) {
		RnFile f = new RnFile(new AdFile("/home/luke/test/pippo/pippo6.txt"));
		System.out.println(f.getFrom().getNameExtensionLess());
		f.getFrom().newName("piripicchio");
		System.out.println(f);
	}

}
