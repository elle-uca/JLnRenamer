package org.ln.java.renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;

import org.ln.java.renamer.gui.RenamerView;

public class Controller {

	private RenamerView view;
	
    private static final Preferences prefs = Preferences.userRoot().node("JLnRenamer");
   // private static final String LAST_DIR_KEY = "lastDir";


	/**
	 * @param view
	 */
	public Controller(RenamerView view) {
		super();
		this.view = view;
	}

	
	public static JFileChooser getFileChooser(int mode, boolean multi) {
		String lastPath = prefs.get(Costants.LAST_DIR_KEY, null);
		
		JFileChooser fc = (lastPath != null)
                ? new JFileChooser(new File(lastPath))
                : new JFileChooser();
		
		fc.setFileSelectionMode(mode);
		fc.setMultiSelectionEnabled(multi);
		return fc;
	}
	
	public static void setPrefs(String key, String path) {
		prefs.put(key, path);
	}


	/**
	 *
	 */
	public class AddFileButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
	
    		JFileChooser fc = getFileChooser(JFileChooser.FILES_ONLY, true);
    		int returnVal = fc.showOpenDialog(null);

    		if (returnVal != JFileChooser.APPROVE_OPTION) {
    			return;
    		}
    		File[] files = fc.getSelectedFiles();
    		List<RnFile> rnfilesList = new ArrayList<RnFile>();
    		List<File> fileList = new ArrayList<>(Arrays.asList(files));
    		for (File file : fileList) {
    			RnFile rn = new RnFile(new AdFile(file.getPath()));
    			rnfilesList.add(rn);
    		}
    		//prefs.put(LAST_DIR_KEY, fileList.getFirst().getParent());
    		setPrefs(Costants.LAST_DIR_KEY, fileList.getFirst().getParent());
    		
    		view.getTableModel().setData(rnfilesList);
    		view.setInfoText("file "+rnfilesList.size());
		}
	}
	
	/**
	 *
	 */
	public class AddDirButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			JFileChooser fc = getFileChooser(JFileChooser.DIRECTORIES_ONLY, false);	
			int returnVal = fc.showOpenDialog(null);

			if (returnVal != JFileChooser.APPROVE_OPTION) {
				return;
			}

			File root = fc.getSelectedFile();
			List<File> fileList = displayDirectory(root, new ArrayList<File>()); 

			List<RnFile> rnfilesList = new ArrayList<RnFile>();

			for (File file : fileList) {
				RnFile rn = new RnFile(new AdFile(file.getPath()));
				rnfilesList.add(rn);
			}
			//prefs.put(Costants.LAST_DIR_KEY, fileList.getFirst().getParent());
			setPrefs(Costants.LAST_DIR_KEY, fileList.getFirst().getParent());
			view.getTableModel().setData(rnfilesList);
			view.setInfoText("file "+rnfilesList.size());
		}
	}	
	
	
	private List<File> displayDirectory(File dir, List<File> result) {
		File[] files = dir.listFiles();
		for (File file : files) {
			// Checking of file inside directory
			if (file.isDirectory()) {
				result.add(file);
				displayDirectory(file, result);
			}
		}
		return result;
	}
	
	/**
	 * @param list
	 */
	public void setTableData(List<RnFile> list) {
		view.getTableModel().setData(list);
		Map<RnFile, String> newNames = new HashMap<>();
		for (RnFile rnFile : list) {
			newNames.put(rnFile, rnFile.getNameDest());
		}
		FileRenamer.checkConflicts(list.getFirst().getFrom().getParentFile(), 
				newNames);
	}

	/**
	 *
	 */
	public class RenameButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			List<RnFile> list = view.getTableModel().getData();
			Map<RnFile, String> newNames = new HashMap<>();
			for (RnFile rnFile : list) {
				newNames.put(rnFile, rnFile.getNameDest());
			}
			List<RnFile> renameList = new ArrayList<RnFile>();
			if (!FileRenamer.checkConflicts(list.getFirst().getFrom().getParentFile(), 
					newNames)) {

				for (RnFile rnFile : list) {
					File file = rnFile.safeRename();
					RnFile rn = new RnFile(new AdFile(file.getPath()));
					renameList.add(rn);
				}
			}
			view.getTableModel().setData(renameList);
		}
	}



}
