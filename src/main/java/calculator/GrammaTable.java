package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrammaTable {
	
	// 0 -> error
	public static Map<String,Map<String,String>> MAIN_TABLE;
	private static String REGULAR_NOT_TERMINALS = "[SETF]'?";

	static{
		
		MAIN_TABLE = new HashMap<String, Map<String, String>>();
		HashMap<String, String> row1 = new HashMap<String, String>();
		row1.put(Constans.DIGIT, "1");
		row1.put(Constans.SUM, "Error: S +");
		row1.put(Constans.DIFF, "Error: S -");
		row1.put(Constans.MULL, "Error: S *");
		row1.put(Constans.DIV, "Error: S /");
		row1.put(Constans.DEGREE, "Error: S ^");
		row1.put(Constans.OPEN_BRACKET, "1");
		row1.put(Constans.CLOSE_BRACKET, "Error: S )");
		row1.put(Constans.AND_OF_INPUT, "2");
		MAIN_TABLE.put("S", row1);
		
		HashMap<String, String> row2 = new HashMap<String, String>();
		row2.put(Constans.DIGIT, "3");
		row2.put(Constans.SUM, "Error: E +");
		row2.put(Constans.DIFF, "Error: E -");
		row2.put(Constans.MULL, "Error: E *");
		row2.put(Constans.DIV, "Error: E /");
		row2.put(Constans.DEGREE, "Error: E ^");
		row2.put(Constans.OPEN_BRACKET, "3");
		row2.put(Constans.CLOSE_BRACKET, "Error: E )");
		row2.put(Constans.AND_OF_INPUT, "Error: E !");
		MAIN_TABLE.put("E", row2);
		
		HashMap<String, String> row3 = new HashMap<String, String>();
		row3.put(Constans.DIGIT, "Error: E' digit");
		row3.put(Constans.SUM, "4");
		row3.put(Constans.DIFF, "5");
		row3.put(Constans.MULL, "Error: E' *");
		row3.put(Constans.DIV, "Error: E' /");
		row3.put(Constans.DEGREE, "Error: E' ^");
		row3.put(Constans.OPEN_BRACKET, "Error: E' (");
		row3.put(Constans.CLOSE_BRACKET, "6");
		row3.put(Constans.AND_OF_INPUT, "6");
		MAIN_TABLE.put("E'", row3);
		
		HashMap<String, String> row4 = new HashMap<String, String>();
		row4.put(Constans.DIGIT,"7");
		row4.put(Constans.SUM, "Error: T +");
		row4.put(Constans.DIFF, "Error: T -");
		row4.put(Constans.MULL,"Error: T *");
		row4.put(Constans.DIV, "Error: T /");
		row4.put(Constans.DEGREE, "Error: T ^");
		row4.put(Constans.OPEN_BRACKET, "7");
		row4.put(Constans.CLOSE_BRACKET, "Error: T )");
		row4.put(Constans.AND_OF_INPUT, "Error: T !");
		MAIN_TABLE.put("T", row4);		
		
		HashMap<String, String> row5 = new HashMap<String, String>();
		row5.put(Constans.DIGIT, "Error: T' digit");
		row5.put(Constans.SUM, "10");
		row5.put(Constans.DIFF, "10");
		row5.put(Constans.MULL, "8");
		row5.put(Constans.DIV, "9");
		row5.put(Constans.DEGREE, "13");
		row5.put(Constans.OPEN_BRACKET, "Error: Missed operator in position ");
		row5.put(Constans.CLOSE_BRACKET, "10");
		row5.put(Constans.AND_OF_INPUT, "10");
		MAIN_TABLE.put("T'", row5);
		
		HashMap<String, String> row6 = new HashMap<String, String>();
		row6.put(Constans.DIGIT, "11");
		row6.put(Constans.SUM, "Error: F +");
		row6.put(Constans.DIFF, "Error: F -");
		row6.put(Constans.MULL, "Error: F *");
		row6.put(Constans.DIV, "Error: F /");
		row6.put(Constans.DEGREE, "Error: F ^");
		row6.put(Constans.OPEN_BRACKET, "12");
		row6.put(Constans.CLOSE_BRACKET, "Error: F )");
		row6.put(Constans.AND_OF_INPUT, "Error: F !");
		MAIN_TABLE.put("F", row6);
	}	
	
	public static String getOperationCode(String notTerminal, String terminal){	
		
		if(notTerminal.equals(terminal)){
			if(notTerminal.equals("!")){
				return "STOP";
			}
			return "100";
		}
		if(isNotTerminal(notTerminal)){
			return MAIN_TABLE.get(notTerminal).get(terminal);
		} else {
			return "Error: syntax error in positon ";
		}
		
	}
	
	private static boolean isNotTerminal(String peekAutomat) {
		Pattern p = Pattern.compile(REGULAR_NOT_TERMINALS);
		Matcher m = p.matcher(peekAutomat);
		if(m.matches()){
			return true;
		}
		return false;
	}
}
