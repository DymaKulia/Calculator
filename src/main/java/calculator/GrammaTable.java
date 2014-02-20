package calculator;

import java.util.HashMap;
import java.util.Map;

public class GrammaTable {
	
	// 0 -> error
	public static Map<String,Map<String,Integer>> MAIN_TABLE;	

	static{
		
		MAIN_TABLE = new HashMap<String, Map<String, Integer>>();
		HashMap<String, Integer> row1 = new HashMap<String, Integer>();
		row1.put(Constans.DIGIT, 1);
		row1.put(Constans.SUM, 0);
		row1.put(Constans.DIFF, 0);
		row1.put(Constans.MULL, 0);
		row1.put(Constans.DIV, 0);
		row1.put(Constans.DEGREE, 0);
		row1.put(Constans.OPEN_BRACKET, 1);
		row1.put(Constans.CLOSE_BRACKET, 0);
		row1.put(Constans.AND_OF_INPUT, 2);
		MAIN_TABLE.put("S", row1);
		
		HashMap<String, Integer> row2 = new HashMap<String, Integer>();
		row2.put(Constans.DIGIT, 3);
		row2.put(Constans.SUM, 0);
		row2.put(Constans.DIFF, 0);
		row2.put(Constans.MULL, 0);
		row2.put(Constans.DIV, 0);
		row2.put(Constans.DEGREE, 0);
		row2.put(Constans.OPEN_BRACKET, 3);
		row2.put(Constans.CLOSE_BRACKET, 0);
		row2.put(Constans.AND_OF_INPUT, 0);
		MAIN_TABLE.put("E", row2);
		
		HashMap<String, Integer> row3 = new HashMap<String, Integer>();
		row3.put(Constans.DIGIT, 0);
		row3.put(Constans.SUM, 4);
		row3.put(Constans.DIFF, 5);
		row3.put(Constans.MULL, 0);
		row3.put(Constans.DIV, 0);
		row3.put(Constans.DEGREE, 0);
		row3.put(Constans.OPEN_BRACKET, 0);
		row3.put(Constans.CLOSE_BRACKET, 6);
		row3.put(Constans.AND_OF_INPUT, 0);
		MAIN_TABLE.put("E'", row3);
		
		HashMap<String, Integer> row4 = new HashMap<String, Integer>();
		row4.put(Constans.DIGIT,7);
		row4.put(Constans.SUM, 0);
		row4.put(Constans.DIFF, 0);
		row4.put(Constans.MULL,0);
		row4.put(Constans.DIV, 0);
		row4.put(Constans.DEGREE, 0);
		row4.put(Constans.OPEN_BRACKET, 7);
		row4.put(Constans.CLOSE_BRACKET, 0);
		row4.put(Constans.AND_OF_INPUT, 0);
		MAIN_TABLE.put("T", row4);		
		
		HashMap<String, Integer> row5 = new HashMap<String, Integer>();
		row5.put(Constans.DIGIT, 0);
		row5.put(Constans.SUM, 10);
		row5.put(Constans.DIFF, 10);
		row5.put(Constans.MULL, 8);
		row5.put(Constans.DIV, 9);
		row5.put(Constans.DEGREE, 13);
		row5.put(Constans.OPEN_BRACKET, 0);
		row5.put(Constans.CLOSE_BRACKET, 10);
		row5.put(Constans.AND_OF_INPUT, 0);
		MAIN_TABLE.put("T'", row5);
		
		HashMap<String, Integer> row6 = new HashMap<String, Integer>();
		row6.put(Constans.DIGIT, 11);
		row6.put(Constans.SUM, 0);
		row6.put(Constans.DIFF, 0);
		row6.put(Constans.MULL, 0);
		row6.put(Constans.DIV, 0);
		row6.put(Constans.DEGREE, 0);
		row6.put(Constans.OPEN_BRACKET, 12);
		row6.put(Constans.CLOSE_BRACKET, 0);
		row6.put(Constans.AND_OF_INPUT, 0);
		MAIN_TABLE.put("F", row6);
	}	
	
	public static int getOperationCode(String notTerminal, String terminal){	
		
		return MAIN_TABLE.get(notTerminal).get(terminal);
	}
}
