package org.ln.java.renamer.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import org.ln.java.renamer.AdFile;
import org.ln.java.renamer.RnFile;
import org.ln.java.renamer.tag.IncN;

public  class FileUtility {


	/**
	 * @param path
	 * @return
	 */
	public static AdFile createAdFile(String path) {
		return createAdFile(new AdFile(path));
	}

	/**
	 * @param AdFile
	 * @return
	 */
	public static AdFile createAdFile(AdFile AdFile) {
		try {
			if(!AdFile.exists()) {
				AdFile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return AdFile;
	}




	public static Vector<AdFile> createFiles(String path, int num) {
		return createFiles(path, num,  "pippo");
	}


	public static Vector<AdFile> createFiles(String path, int num, String name) {
		return createFiles(path, num, name, ".txt");
	}

	public static Vector<AdFile> createFiles(String path, int num, String name, String ext) {
		Vector<AdFile> result = new Vector<AdFile>();
		for (int i = 1; i <= num; i++) {
			result.add(createAdFile(new AdFile(path+name+i+ext)));
		}
		return result;
	}


	public static List<RnFile> createRnFiles(String path, int num, String name, String ext) {
		List<RnFile> result = new ArrayList<RnFile>();
		for (int i = 1; i <= num; i++) {
			AdFile f = new AdFile(path+name+i+ext); 
			result.add((new RnFile(f)));
		}
		return result;
	}


	public static boolean deleteAdFile(String path) {
		return deleteAdFile(new AdFile(path));
	}

	public static boolean deleteAdFile(AdFile AdFile) {
		return AdFile.delete();
	}


	public static boolean deleteAdFiles(String path, int num, String name, String ext) {
		boolean result = true;
		for (int i = 1; i <= num; i++) {
			boolean deleted = deleteAdFile(new AdFile(path+name+i+ext));
			if(!deleted) {
				result = deleted;
			}
		}
		return result;
	}

	public static AdFile createDir(String path) {
		return createDir(new AdFile(path)) ;
	}



	public static AdFile createDir(AdFile AdFile) {
		if(!AdFile.exists()) {
			AdFile.mkdir();
		}
		return AdFile;
	}


//	public static void deleteDir(String path) {
//		deleteDir(new AdFile(path));
//	}


//	public static void deleteDir(AdFile dir) {
//		if (dir.exists()) {
//			AdFile[] AdFiles = dir.listFiles();
//			if (AdFiles != null) { 
//				for (AdFile AdFile : AdFiles) {
//					if (AdFile.isDirectory()) {
//						deleteDir(AdFile); 
//					} else {
//						AdFile.delete(); 
//					}
//				}
//			}
//		}
//		dir.delete(); 
//	}





	
    public static boolean hasExtension(AdFile AdFile) {
    	if(AdFile == null)
    		return false;
    	if(AdFile.isDirectory())
    		return false;   	
        int dotIndex = AdFile.getName().toString().lastIndexOf('.');
		return dotIndex > 0;
    	
    }
    

	
	
	 public static List<Class<?>> getClasses(String packageName)
	            throws ClassNotFoundException, IOException {
	        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	        assert classLoader != null;
	        String path = packageName.replace('.', '/');
	        Enumeration<URL> resources = classLoader.getResources(path);
	        List<File> dirs = new ArrayList<>();
	        while (resources.hasMoreElements()) {
	            URL resource = resources.nextElement();
	            dirs.add(new File(resource.getFile()));
	        }
	        ArrayList<Class<?>> classes = new ArrayList<>();
	        for (File directory : dirs) {
	            classes.addAll(findClasses(directory, packageName));
	        }
	        return classes;
	    }

	    private static List<Class<?>> findClasses(File directory, String packageName)
	            throws ClassNotFoundException {
	        List<Class<?>> classes = new ArrayList<>();
	        if (!directory.exists()) {
	            return classes;
	        }
	        File[] files = directory.listFiles();
	        for (File file : files) {
	            if (file.isDirectory()) {
	                assert !file.getName().contains(".");
	                classes.addAll(findClasses(file, packageName + "." + file.getName()));
	            } else if (file.getName().endsWith(".class")) {
	                try {
	                    String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
	                    classes.add(Class.forName(className));
	                } catch (NoClassDefFoundError e) {
	                    // Ignore this class. It is likely an inner class or a class that cannot be loaded.
	                }
	            }
	        }
	        return classes;
	    }
	
	public static void main(String[] args) {
		IncN tag = new IncN(1,1);
		Class<?> goatClass = tag.getClass();
		Package pkg = goatClass.getPackage();
		System.out.println(pkg);
		
	}


}
