//package org.ln.java.renamer;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class Parser {
//
//	
//	
//    /**
//     * Vuoi solo il testo pulito? → usa .replaceAll("<[^>]+>", "")
//
//		Vuoi estrarre singoli pezzi di testo? → usa ">([^<>]+)<" con un Matcher
//     */
//    public static void parse1() {
//        String input = "<html><body>Hello <b>world</b>!</body></html>";
//        Pattern pattern = Pattern.compile("<[^>]+>");
//        Matcher matcher = pattern.matcher(input);
//
//        while (matcher.find()) {
//            //System.out.println("Tag trovato: " + matcher.group());
//        }
//    }
//    
//    
//    public static void parse2() {
//        String input = "<html><body>Hello <b>world</b>!</body></html>";
//        Pattern pattern = Pattern.compile(">([^<>]+)<");  // trova testo tra tag
//        Matcher matcher = pattern.matcher(input);
//
//        while (matcher.find()) {
//           // System.out.print(matcher.group(1	) + " "); // group(1) contiene il testo
//        }
//    }
//    
//    public static void parse3() {
//        String input = "<Word:04>Hello<Inc:12>world_25<Dec:14>_2025<Inc:0:2>PPPP";
//        List<String> result = new ArrayList<String>();
//
//        Pattern pattern = Pattern.compile("<[^>]+>|[^<]+");
//        Matcher matcher = pattern.matcher(input);
//
//        while (matcher.find()) {
//            result.add(matcher.group());
//        }
//
//        // Stampa il risultato
//        for (String s : result) {
//            System.out.println(s);
//        }
//    }
//    
//    public static void main(String[] args) {
//    		parse1();
//    		parse2();
//
//    }
//
//}
