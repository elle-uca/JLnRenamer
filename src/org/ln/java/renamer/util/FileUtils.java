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
import org.ln.java.renamer.Costants.RenameMode;
import org.ln.java.renamer.RnFile;

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
