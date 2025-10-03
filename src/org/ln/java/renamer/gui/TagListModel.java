package org.ln.java.renamer.gui;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import org.ln.java.renamer.tag.DecN;
import org.ln.java.renamer.tag.IncH;
import org.ln.java.renamer.tag.IncL;
import org.ln.java.renamer.tag.IncN;
import org.ln.java.renamer.tag.IncR;
import org.ln.java.renamer.tag.RandL;
import org.ln.java.renamer.tag.RandN;
import org.ln.java.renamer.tag.RnTag;
import org.ln.java.renamer.tag.Subs;
import org.ln.java.renamer.tag.Word;
import org.ln.java.renamer.util.FileUtils;

@SuppressWarnings("serial")
public class TagListModel extends AbstractListModel<RnTag> {

	List<RnTag> dataList;



	public TagListModel() {
		dataList = new ArrayList<RnTag>();
		//initReflection();
		initClassic();
	}

	private void initClassic() {
		dataList.add(new IncN(1,1));
		dataList.add(new DecN(1,1));
		dataList.add(new Subs(1,1));
		dataList.add(new Word(1,1));
		dataList.add(new RandN(4,1));
		dataList.add(new RandL(4,1));
		dataList.add(new IncR(1,1));
		dataList.add(new IncH(1,1));
		dataList.add(new IncL(1,1));
	}

	@SuppressWarnings("unused")
	private void initReflection() {
		dataList = new ArrayList<>();
		
        List<Class<?>> classes = null;
		try {
			classes = FileUtils.getClasses("org.ln.java.renamer.tag");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
        RnTag tag = null;
        Integer[] arr = {1,1};
        for (Class<?> clazz : classes) {
        	if(!clazz.getSuperclass().getName().equals("java.lang.Object")) {
        		Constructor<?> cons = null;
				try {
					cons = clazz.getDeclaredConstructor(Integer[].class);
					tag = (RnTag) cons.newInstance((Object) arr);
					dataList.add(tag);
				} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
        	}
         }
	}
	
	
	@Override
	public int getSize() {
		return dataList.size();
	}

	@Override
	public RnTag getElementAt(int index) {
		return dataList.get(index);
	}

}
