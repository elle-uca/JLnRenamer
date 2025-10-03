package org.ln.java.renamer.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.ln.java.renamer.AdFile;
import org.ln.java.renamer.Costants.RenameMode;

public class FileUtils {



    /**
     * Rinomina file o directory in base alla modalità scelta.
     *
     * @param fileOrDir file o cartella da rinominare
     * @param newName   nuovo nome o nuova estensione (a seconda della modalità)
     * @param mode      modalità di rinomina (FULL, NAME_ONLY, EXT_ONLY)
     * @return          nuovo File rinominato
     * @throws IOException se ci sono errori di I/O
     */
    public static File safeRename(File fileOrDir, String newName, RenameMode mode) throws IOException {
        Path source = fileOrDir.toPath();
        String finalName;

        if (fileOrDir.isDirectory()) {
            // Per le directory ignoriamo le estensioni
            finalName = newName;
        } else {
            String name = fileOrDir.getName();
            int dot = name.lastIndexOf(".");
            String currentBase = (dot == -1) ? name : name.substring(0, dot);
            String currentExt = (dot == -1) ? "" : name.substring(dot);

            switch (mode) {
                case FULL:
                    finalName = newName; // usa tutto il nuovo nome (con eventuale estensione)
                    break;
                case NAME_ONLY:
                    // cambia solo il nome, mantiene l'estensione attuale
                    finalName = newName + currentExt;
                    break;
                case EXT_ONLY:
                    // cambia solo l'estensione (newName deve iniziare con "." tipo ".bak")
                    if (!newName.startsWith(".")) {
                        newName = "." + newName;
                    }
                    finalName = currentBase + newName;
                    break;
                default:
                    throw new IllegalArgumentException("Modalità non supportata: " + mode);
            }
        }

        Path target = source.resolveSibling(finalName);

        // Gestione conflitti (aggiunge _1, _2, ecc.)
        int counter = 1;
        String base = finalName;
        String ext = "";
        int dot = finalName.lastIndexOf(".");
        if (dot != -1 && fileOrDir.isFile()) {
            base = finalName.substring(0, dot);
            ext = finalName.substring(dot);
        }

        while (Files.exists(target)) {
            target = source.resolveSibling(base + "_" + counter + ext);
            counter++;
        }

        // Rinomina realmente
        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);

        return target.toFile();
    }

    // ESEMPIO DI UTILIZZO
    public static void main(String[] args) {
        try {
            File file = new File("/home/luke/test2/prova.txt");

            // 1. Rinominare tutto
            File f1 = safeRename(file, "documento.data", RenameMode.FULL);

            // 2. Rinominare solo il nome, mantenendo estensione
            File f2 = safeRename(f1, "nuovo_nome", RenameMode.NAME_ONLY);

            // 3. Rinominare solo l’estensione
            File f3 = safeRename(f2, ".bak", RenameMode.EXT_ONLY);

            System.out.println("Nuovo nome finale: " + f3.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
