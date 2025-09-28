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
    private static final String LAST_DIR_KEY = "lastDir";


	/**
	 * @param view
	 */
	public Controller(RenamerView view) {
		super();
		this.view = view;
	}



	/**
	 *
	 */
	public class AddFileButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String lastPath = prefs.get(LAST_DIR_KEY, null);
			
    		JFileChooser fc = (lastPath != null)
                    ? new JFileChooser(new File(lastPath))
                    : new JFileChooser();
    		
    		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    		fc.setMultiSelectionEnabled(true);
    		int returnVal = fc.showOpenDialog(null);

    		if (returnVal != JFileChooser.APPROVE_OPTION) {
    			return;
    		}
    		File[] files =fc.getSelectedFiles();
    		List<RnFile> rnfilesList = new ArrayList<RnFile>();
    		List<File> fileList = new ArrayList<>(Arrays.asList(files));
    		for (File file : fileList) {
    			RnFile rn = new RnFile(new AdFile(file.getPath()));
    			//System.out.println(rn);
    			rnfilesList.add(rn);
    		}
    		prefs.put(LAST_DIR_KEY, fileList.getFirst().getParent());
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
			String lastPath = prefs.get(LAST_DIR_KEY, null);
			
   		JFileChooser fc = (lastPath != null)
                   ? new JFileChooser(new File(lastPath))
                   : new JFileChooser();
   		
   		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
   		fc.setMultiSelectionEnabled(true);
   		int returnVal = fc.showOpenDialog(null);

   		if (returnVal != JFileChooser.APPROVE_OPTION) {
   			return;
   		}
   		File[] files =fc.getSelectedFiles();
   		List<RnFile> rnfilesList = new ArrayList<RnFile>();
   		List<File> fileList = new ArrayList<>(Arrays.asList(files));
   		for (File file : fileList) {
   			RnFile rn = new RnFile(new AdFile(file.getPath()));
   			//System.out.println(rn);
   			rnfilesList.add(rn);
   		}
   		prefs.put(LAST_DIR_KEY, fileList.getFirst().getParent());
   		view.getTableModel().setData(rnfilesList);
   		view.setInfoText("file "+rnfilesList.size());
		}
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
		
		FileRenamer.checkConflicts(list.getFirst().getFrom().getParentFile(), newNames);
	}

	/**
	 *
	 */
	public class RenameButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
//			List<RnFile> rnfilesList = view.getTableModel().getData();
//			for (RnFile rnFile : rnfilesList) {
//				rnFile.renameTo();
//			}
			
//			List<RnFile> rnfilesList = view.getTableModel().getData();
//			
//			Map<File, String> newNames = new HashMap<>();
//			for (RnFile rnFile : rnfilesList) {
//				newNames.put(rnFile.getFrom(), rnFile.getNameDest());
//			}
//			
//			
//			System.out.println("Conflitti   "+FileRenamer.checkConflict(rnfilesList.getFirst().getFrom().getParentFile(), newNames));
//			
//			view.getTableModel().setData(new ArrayList<RnFile>());
		}
	}



}
