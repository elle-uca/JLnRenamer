package org.ln.java.renamer.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ln.java.renamer.Costants.FileStatus;
import org.ln.java.renamer.RnFile;

public class FileUtils {

	// private final RenameManager manager = new RenameManager();

	public enum RenameMode {
		FULL,        // rinomina tutto (nome + estensione)
		NAME_ONLY,   // rinomina solo il nome, mantiene estensione
		EXT_ONLY     // rinomina solo l'estensione, mantiene il nome
	}
	
	
	 /**
     * Rinomina una lista di file aggiungendo un prefisso.
     * Tutte le operazioni vengono registrate come un singolo "set" annullabile.
     *
     * @param filesToRename la lista di file da rinominare
     * @param prefix il prefisso da aggiungere al nome di ogni file
     */
//    public List<RnFile> addPrefixToAll(List<RnFile> filesToRename, String prefix) {
//        // 1. Inizia un nuovo set di operazioni, pulendo la cronologia precedente.
//        manager.startNewRenameSet();
//        System.out.println("--- Inizio operazione batch: Aggiunta prefisso '" + prefix + "' ---");
//
//        // 2. Itera su tutti i file ed esegui la rinomina
//        for (File currentFile : filesToRename) {
//            try {
//                String newName = prefix + currentFile.getName();
//                
//                // Usa la funzione safeRename, che registrerà l'operazione nel manager
//                safeRename(
//                    currentFile, 
//                    newName, 
//                    RenameMode.NAME_ONLY, 
//                    this.manager
//                );
//
//            } catch (IOException e) {
//                System.err.println("Errore durante la rinomina di " + currentFile.getName() + ": " + e.getMessage());
//                // In un'applicazione reale, potresti decidere di fermarti o continuare
//            }
//        }
//        System.out.println("--- Operazione batch completata ---");
//    }
	
    
    
//	public static List<RnFile> filesRenamer(List<RnFile> list) {
//		//RenameManager renameManager = new RenameManager();
//		List<RnFile> renameList = new ArrayList<RnFile>();
//
//
//			for (RnFile rnFile : list) {
//				File file;
//				try {
//					file = FileUtils.safeRename(rnFile.getFrom(), 
//							rnFile.getNameDest(), RenameMode.NAME_ONLY, renameManager);
//					RnFile rn = new RnFile(new AdFile(file.getPath()));
//					renameList.add(rn);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			return list;
//	}
	
	   /**
     * Rinomina file o directory e registra l'operazione per un eventuale annullamento.
     *
     * @param fileOrDir   file o cartella da rinominare
     * @param newName     nuovo nome o estensione
     * @param mode        modalità di rinomina
     * @param manager     il gestore della cronologia dove registrare l'operazione
     * @return            il nuovo File rinominato
     * @throws IOException se ci sono errori di I/O
     */
    public static File safeRename(File fileOrDir, String newName, RenameMode mode) throws IOException {
        Path sourcePath = fileOrDir.toPath();
        String finalName;

        // ... (logica di calcolo di finalName, identica alla tua) ...
        if (fileOrDir.isDirectory()) {
			finalName = newName;
		} else {
			String name = fileOrDir.getName();
			int dot = name.lastIndexOf(".");
			String currentBase = (dot == -1) ? name : name.substring(0, dot);
			String currentExt = (dot == -1) ? "" : name.substring(dot);

			switch (mode) {
			case FULL:
				finalName = newName;
				break;
			case NAME_ONLY:
				finalName = newName + currentExt;
				break;
			case EXT_ONLY:
				if (!newName.startsWith(".")) newName = "." + newName;
				finalName = currentBase + newName;
				break;
			default:
				throw new IllegalArgumentException("Modalità non supportata: " + mode);
			}
		}

        Path targetPath = sourcePath.resolveSibling(finalName);

        // Gestione conflitti (identica alla tua)
        int counter = 1;
        String base = finalName;
        String ext = "";
        int dot = finalName.lastIndexOf(".");
		if (dot != -1 && fileOrDir.isFile()) {
			base = finalName.substring(0, dot);
			ext = finalName.substring(dot);
		}

        while (Files.exists(targetPath)) {
            targetPath = sourcePath.resolveSibling(base + "_" + counter + ext);
            counter++;
        }

        // Esegue la rinomina
        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        File newFile = targetPath.toFile();

        // **Aggiunta importante: registra l'operazione**
//        if (manager != null) {
//            manager.addOperation(fileOrDir, newFile);
//        }

        return newFile;
    }


	/**
	 * @param directory
	 * @param newNames
	 * @return
	 */
	public static boolean checkConflicts(File directory, Map<RnFile, String> newNames) {

		File[] files = directory.listFiles();


		// Nomi già esistenti
		Set<String> existingNames = new HashSet<>();
		for (File f : files) {
			existingNames.add(f.getName());
		}

		// Controllo conflitti
		Set<String> usedNewNames = new HashSet<>();
		boolean conflictFound = false;

		for (Map.Entry<RnFile, String> entry : newNames.entrySet()) {
			File oldFile = entry.getKey().getFrom();
			String newName = entry.getValue();
			// System.out.println("====== vecchio nome " + oldFile.getName() );
			// System.out.println("====== nuovo nome " + newName );

			// 1. conflitto con file esistenti (ma non se è lo stesso file)
			if (existingNames.contains(newName) && !oldFile.getName().equals(newName)) {
				// System.out.println("❌ Conflitto: " + newName + " esiste già nella cartella.");
				conflictFound = true;
			}

			// 2. duplicati nella lista dei nuovi nomi
			if (!usedNewNames.add(newName)) {
				entry.getKey().setFileStatus(FileStatus.KO);
				// System.out.println("❌ Conflitto: il nuovo nome " + newName + " è duplicato.");
				conflictFound = true;
			}else {
				entry.getKey().setFileStatus(FileStatus.OK);
			}
		}
		return conflictFound;
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
