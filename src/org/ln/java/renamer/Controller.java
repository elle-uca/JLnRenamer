package org.ln.java.renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;

import org.ln.java.renamer.gui.RenamerView;

public class Controller {

	private RenamerView view;


	public Controller(RenamerView view) {
		super();
		this.view = view;
	}



	/**
	 *
	 */
	public class AddButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
    		JFileChooser fc = new JFileChooser();
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
    			System.out.println(rn);
    			rnfilesList.add(rn);
    		}
    		view.getTableModel().setData(rnfilesList);
    		view.setInfoText("file "+rnfilesList.size() );
		}
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
			
			List<RnFile> rnfilesList = view.getTableModel().getData();
			
			Map<File, String> newNames = new HashMap<>();
			for (RnFile rnFile : rnfilesList) {
				newNames.put(rnFile.getFrom(), rnFile.getNameDest());
			}
			
			
			System.out.println("Conflitti   "+FileRenamer.checkConflict(rnfilesList.getFirst().getFrom().getParentFile(), newNames));
			
			//view.getTableModel().setData(new ArrayList<RnFile>());
		}
	}



}
