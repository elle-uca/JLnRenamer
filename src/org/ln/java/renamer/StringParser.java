package org.ln.java.renamer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ln.java.renamer.tag.RnTag;
import org.ln.java.renamer.util.FileUtility;
import org.ln.java.renamer.util.Utility;

public class StringParser {

	
//	public static void main(String[] args) {
////		StringParser.parse("<IncR:1:0>_Hello_<Word:1>", 
////				FileUtility.createRnFiles("/home/luke/test/pippo/", 10, "pippo_pluto(minni) klo.lo", ".txt"));
//		StringParser.parse("<IncR:1:0>_Hello_<Subs:5:8>_<Word:1>", 
//				FileUtility.createRnFiles("C:\\Users\\l.noale\\Downloads\\test\\pippo\\", 10, "pippo_pluto(minni) klo.lo", ".txt"));
//	}



	/**
	 * @param string
	 * @param fileList
	 * @return
	 */
	public static List<RnFile> parse(String string, List<RnFile> fileList) {
		List<Object> objectList = new ArrayList<Object>() ;
		//System.out.println(string);

		Pattern pattern = Pattern.compile("<[^>]+>|[^<]+");
		Matcher matcher = pattern.matcher(string);

		while (matcher.find()) {
			String str = matcher.group();
			if(str.startsWith("<")) {
				RnTag tag = parseTag(str);
				tag.setOldName(Utility.getOldName(fileList));
				tag.setNewName(Utility.getNewName(fileList));
				tag.init();
				objectList.add(tag);

			}else {
				objectList.add(str);
			}
		}

		for (int i = 0; i < fileList.size(); i++) {
			String result = "";
			RnFile rnf = fileList.get(i);
			for (Object object : objectList) {
				if(object instanceof RnTag) {
					result+= ((RnTag) object).getNewName().get(i);
				}else {
					result+= object.toString();
				}
			}
			rnf.setNameDest(result);
			///System.out.println("result  "+rnf.getNameDest());
		}
		return fileList;
	}



	/**
	 * @param str
	 * @return
	 */
	private static RnTag parseTag(String str) {
		String name = "org.ln.java.renamer.tag.";
		Pattern pattern1 = Pattern.compile("(?<=<)[a-zA-Z]+(?=:)");	 // estrae il nome del tag
		Matcher matcher1 = pattern1.matcher(str);
		while (matcher1.find()) {
			name = name + matcher1.group();
		}

		Pattern pattern2 = Pattern.compile("(?<=:)\\d+(?=[>:])");	// estrae i parametri del tag
		Matcher matcher2 = pattern2.matcher(str);

		List<Integer> args = new ArrayList<Integer>();
		while (matcher2.find()) {
			args.add(Integer.parseInt(matcher2.group()));
		}

		Integer[] arr = new Integer[args.size()];
		args.toArray(arr);
		RnTag tag = null;

		try {
			// Reflection
			Class<?> clazz = Class.forName(name);
			Constructor<?> cons = clazz.getDeclaredConstructor(Integer[].class);
			tag = (RnTag) cons.newInstance((Object) arr);
			
		} catch (ClassNotFoundException | IllegalArgumentException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return tag;
	}




	/**
	 * @param str
	 * @param search
	 * @return
	 */
	public static int CountCharsInString(String str, char search) {
		int totalCharacters = 0;
		char temp;
		for (int i = 0; i < str.length(); i++) {
			temp = str.charAt(i);
			if (temp == search) {
				totalCharacters++;
			}
		}
		return totalCharacters;
	}

	/**
	 * @param str
	 * @return
	 */
	public static boolean isParsable(String str) {
		if(str == null || str.equals("") || (CountCharsInString(str, '<') != CountCharsInString(str, '>'))) {
			return false;
		}
		return true;
	}




}
