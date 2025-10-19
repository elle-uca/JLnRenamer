package org.ln.java.renamer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class FileRenameManager {

	public enum RenameMode {
		FULL,        // rinomina tutto (nome + estensione)
		NAME_ONLY,   // rinomina solo il nome, mantiene estensione
		EXT_ONLY     // rinomina solo l'estensione, mantiene il nome
	}

	// Classe che rappresenta un'operazione singola
	public static class RenameOperation {
		public final File oldFile;
		public final File newFile;

		public RenameOperation(File oldFile, File newFile) {
			this.oldFile = oldFile;
			this.newFile = newFile;
		}
	}

	// Stack per poter annullare l'ultima operazione batch
	private static final Deque<List<RenameOperation>> history = new ArrayDeque<>();


	
	/**
	 * Renames a list of files or directories as a single operation.
	 * If one rename fails, previously renamed items are rolled back.
	 *
	 * @param files     list of files or directories to rename
	 * @param newNames  list of new names (same size as files)
	 * @param mode      rename mode (FULL, NAME_ONLY, EXT_ONLY)
	 * @throws IOException if an I/O error occurs
	 */
	public static List<RnFile> batchRename(List<RnFile> files, RenameMode mode) throws IOException {
		if (files.size() < 1) {
			throw new IllegalArgumentException("List vuota");
		}

		List<RenameOperation> operations = new ArrayList<>();
		List<RnFile> renameList = new ArrayList<RnFile>();

		try {
			for (int i = 0; i < files.size(); i++) {
				File oldFile = files.get(i).getFrom();
				File newFile = safeRename(oldFile, files.get(i).getNameDest(), mode);
				renameList.add(new RnFile(new AdFile(newFile.getPath())));
				operations.add(new RenameOperation(oldFile, newFile));
			}

			// Tutto ok → registra nel log per eventuale undo
			history.push(operations);

		} catch (IOException e) {
			// In caso di errore → rollback
			System.err.println("Errore durante batchRename: eseguo rollback...");
			for (RenameOperation op : operations) {
				if (op.newFile.exists()) {
					Files.move(op.newFile.toPath(), op.oldFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				}
			}
			throw e;
		}
		return renameList;
	}

	/**
	 * Undoes the last batch rename operation.
	 * If there are no previous operations, does nothing.
	 *
	 * @throws IOException if an I/O error occurs
	 */
	public static void undoLastRename() throws IOException {
		if (history.isEmpty()) {
			System.out.println("Nessuna operazione da annullare.");
			return;
		}

		List<RenameOperation> lastOps = history.pop();

		// Ripristina in ordine inverso
		for (int i = lastOps.size() - 1; i >= 0; i--) {
			RenameOperation op = lastOps.get(i);
			if (op.newFile.exists()) {
				Files.move(op.newFile.toPath(), op.oldFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		}

		System.out.println("Ultima operazione annullata.");
	}

	
	/**
	 * Rename file or directory.
	 * 
	 * @param fileOrDir	file or directory to rename
	 * @param newName	new name or extension
	 * @param mode		rename mode
	 * @return
	 * @throws IOException if an I/O error occurs
	 */
	public static File safeRename(File fileOrDir, String newName, RenameMode mode) throws IOException {
		Path sourcePath = fileOrDir.toPath();
		String finalName;

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

		Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
		return targetPath.toFile();
	}


}
