package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolskFormTable {
	
	private static final String REGULAR_DIGIT = "[0-9.]+";
	public static Map<String,Map<String,Integer>> POLSK_FORM_TABLE;
	
	static {
		
	 /******************************************************************** 
	 * 100 -> place digit to PolskFormArray and receive next symbol
	 * 1   -> place input symbol to polsk automate and receive next symbol
	 * 2   -> pop from automate and place to PolskFormArray
	 * 3   -> pop from automate and and receive next symbol
	 * 4   -> stop
	 * 5   -> Error
	 *********************************************************************/

		POLSK_FORM_TABLE = new HashMap<String, Map<String, Integer>>();
		HashMap<String, Integer> row1 = new HashMap<String, Integer>();
		row1.put(Constans.DIGIT, 100);
		row1.put(Constans.SUM, 1);
		row1.put(Constans.DIFF, 1);
		row1.put(Constans.MULL, 1);
		row1.put(Constans.DIV, 1);
		row1.put(Constans.DEGREE, 1);
		row1.put(Constans.OPEN_BRACKET, 1);
		row1.put(Constans.CLOSE_BRACKET, 5);
		row1.put(Constans.AND_OF_INPUT, 4);
		POLSK_FORM_TABLE.put(Constans.AND_OF_INPUT, row1);

		HashMap<String, Integer> row2 = new HashMap<String, Integer>();
		row2.put(Constans.DIGIT, 100);
		row2.put(Constans.SUM, 2);
		row2.put(Constans.DIFF, 2);
		row2.put(Constans.MULL, 1);
		row2.put(Constans.DIV, 1);
		row2.put(Constans.DEGREE, 1);
		row2.put(Constans.OPEN_BRACKET, 1);
		row2.put(Constans.CLOSE_BRACKET, 2);
		row2.put(Constans.AND_OF_INPUT, 2);
		POLSK_FORM_TABLE.put(Constans.SUM, row2);

		HashMap<String, Integer> row3 = new HashMap<String, Integer>();
		row3.put(Constans.DIGIT, 100);
		row3.put(Constans.SUM, 2);
		row3.put(Constans.DIFF, 2);
		row3.put(Constans.MULL, 1);
		row3.put(Constans.DIV, 1);
		row3.put(Constans.DEGREE, 1);
		row3.put(Constans.OPEN_BRACKET, 1);
		row3.put(Constans.CLOSE_BRACKET, 2);
		row3.put(Constans.AND_OF_INPUT, 2);
		POLSK_FORM_TABLE.put(Constans.DIFF, row3);

		HashMap<String, Integer> row4 = new HashMap<String, Integer>();
		row4.put(Constans.DIGIT, 100);
		row4.put(Constans.SUM, 2);
		row4.put(Constans.DIFF, 2);
		row4.put(Constans.MULL, 2);
		row4.put(Constans.DIV, 2);
		row4.put(Constans.DEGREE, 2);
		row4.put(Constans.OPEN_BRACKET, 1);
		row4.put(Constans.CLOSE_BRACKET, 2);
		row4.put(Constans.AND_OF_INPUT, 2);
		POLSK_FORM_TABLE.put(Constans.MULL, row4);

		HashMap<String, Integer> row5 = new HashMap<String, Integer>();
		row5.put(Constans.DIGIT, 100);
		row5.put(Constans.SUM, 2);
		row5.put(Constans.DIFF, 2);
		row5.put(Constans.MULL, 2);
		row5.put(Constans.DIV, 2);
		row5.put(Constans.DEGREE, 2);
		row5.put(Constans.OPEN_BRACKET, 1);
		row5.put(Constans.CLOSE_BRACKET, 2);
		row5.put(Constans.AND_OF_INPUT, 2);
		POLSK_FORM_TABLE.put(Constans.DIV, row5);

		HashMap<String, Integer> row6 = new HashMap<String, Integer>();
		row6.put(Constans.DIGIT, 100);
		row6.put(Constans.SUM, 2);
		row6.put(Constans.DIFF, 2);
		row6.put(Constans.MULL, 2);
		row6.put(Constans.DIV, 2);
		row6.put(Constans.DEGREE, 2);
		row6.put(Constans.OPEN_BRACKET, 1);
		row6.put(Constans.CLOSE_BRACKET, 2);
		row6.put(Constans.AND_OF_INPUT, 2);
		POLSK_FORM_TABLE.put(Constans.DEGREE, row6);

		HashMap<String, Integer> row7 = new HashMap<String, Integer>();
		row7.put(Constans.DIGIT, 100);
		row7.put(Constans.SUM, 1);
		row7.put(Constans.DIFF, 1);
		row7.put(Constans.MULL, 1);
		row7.put(Constans.DIV, 1);
		row7.put(Constans.DEGREE, 1);
		row7.put(Constans.OPEN_BRACKET, 1);
		row7.put(Constans.CLOSE_BRACKET, 3);
		row7.put(Constans.AND_OF_INPUT, 5);
		POLSK_FORM_TABLE.put(Constans.OPEN_BRACKET, row7);
	}

	public static int getOperationCode(String polskAutomateSymbol, String inputSymbol) {

		if (isDigit(inputSymbol)) {
			inputSymbol = Constans.DIGIT;
		}
		
		//System.out.println("polskAutomateSymbol= " + polskAutomateSymbol + " inputSymbol=" + inputSymbol);
		return POLSK_FORM_TABLE.get(polskAutomateSymbol).get(inputSymbol);
	}

	private static boolean isDigit(String inputSymbol) {
		
		Pattern p = Pattern.compile(REGULAR_DIGIT);
		Matcher m = p.matcher(inputSymbol);
		if (m.matches()) {
			return true;
		}
		return false;
	}
}
