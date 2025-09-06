package org.ln.java.renamer.gui.panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.ln.java.renamer.StringParser;
import org.ln.java.renamer.gui.AccordionPanel;
import org.ln.java.renamer.gui.TagListModel;
import org.ln.java.renamer.tag.RnTag;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class TagPanel extends AbstractPanelContent {


	private JList<RnTag> tagList;
	private JScrollPane tagListScrollPane;
	private JLabel tagLabel;


	public TagPanel(AccordionPanel accordion) {
		super(accordion);
	}


	@Override
	void initComponents() {
		tagLabel = new JLabel("Nuovo nome");
		renameField.getDocument().addDocumentListener(this);
		tagListScrollPane = new JScrollPane();
		tagList = new JList<RnTag>();
		tagList.setModel(new TagListModel());
		tagList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tagListScrollPane.setViewportView(tagList);
		tagList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = tagList.locationToIndex(e.getPoint());
					if (index >= 0) {
						String values = tagList.getModel().getElementAt(
								tagList.locationToIndex(e.getPoint())).getTagString();
						renameField.setText(renameField.getText() + values);
					}
				}
			}
		});

		setLayout(new MigLayout("", "[grow]", "[][][]"));
		add(tagLabel, "wrap");
		add(renameField, "growx, wrap, w :150:");
		add(tagListScrollPane, "growx, growy,  h :250:"); 
	}


	@Override
	void updateView() {
		String str  = renameField.getText();
		System.out.println(str);
		if(StringParser.isParsable(str)) {
			accordion.getView().getTableModel().setData(
					StringParser.parse(str, accordion.getView().getTableModel().getData())) ;
		}
	}


}
