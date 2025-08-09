import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ReplaceText {
	public static void main(String[] args) throws IOException {
		if(args.length != 2) {
			System.err.println("Usage: java ReplaceText original.tra translate.tra");
			System.err.println("Example: java ReplaceTest dialog2.tra dialog.tra");
            System.exit(1);
		}
		
		Path origPath = Paths.get(args[0]);
		Path transPath = Paths.get(args[1]);
		
        List<String> origLines = Files.readAllLines(origPath);
        List<String> transLines = Files.readAllLines(transPath);
		
		List<String> output = tlumacz(origLines, transLines);
		for(String out : output) {
			System.out.print(out+"\r\n");
		}
	}
	
	private static List<String> tlumacz(List<String> orig, List<String> trans) {
		Map<Integer, String> origScal = mergeLines(orig);
		Map<Integer, String> transScal = mergeLines(trans);
		return merge(origScal, transScal);
	}
	
	private static Map<Integer, String> mergeLines(List<String> sc) {
		Map<Integer, String> scalled = new TreeMap<>();
		StringBuilder sb = new StringBuilder();
		for(String text : sc) {
			if(text.startsWith("@")) {
				if(sb.length() > 0) {
					//get number
					String number = sb.substring(1, sb.indexOf(" "));
					scalled.put(Integer.valueOf(number), sb.toString());
				}
				sb = new StringBuilder();
				sb.append(text);
			}
			else {
				if(sb.length() > 0) {
					sb.append("\r\n");
				}
				sb.append(text);
			}
		}
		//last add
		String number = sb.substring(1, sb.indexOf(" ="));
		scalled.put(Integer.valueOf(number), sb.toString());
		return scalled;
	}
	
	private static List<String> merge(Map<Integer, String> orig, Map<Integer, String> trans) {
		List<String> merged = new ArrayList<String>();
		for(Map.Entry<Integer, String> origEntry : orig.entrySet()) {
			if(trans.containsKey(origEntry.getKey())) {
				merged.add(translate(origEntry.getValue(), trans.get(origEntry.getKey())));
			} else {
				merged.add(origEntry.getValue());
			}
		}
		return merged;
	}
	
	private static String translate(String orig, String trans) {
		if(orig.contains("STATISTICS:")) {
			return orig.replace(orig.substring(orig.indexOf("~"), orig.indexOf("STATISTICS")), trans.substring(trans.indexOf("~"), trans.indexOf("PARAMETRY")));
		}
		return orig.replace(orig.substring(orig.indexOf("~"), orig.indexOf("~", orig.indexOf("~")+1)), trans.substring(trans.indexOf("~"), trans.indexOf("~", trans.indexOf("~")+1)));
	}
}
