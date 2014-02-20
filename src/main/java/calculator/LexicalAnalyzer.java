package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {

	private String REGULAR_BRACKETS = "[()]";
	private String REGULAR_OPERATORS = "[+/*-^]";
	private String REGULAR_DIGITS = "[0-9]";
	private String REGULAR_FUNCTIONS = "[sumaxinqrt]";	

	private StringBuilder builder = new StringBuilder();

	private String analizedInput;
	private int caretPositon;

	private String currentTerminalName;
	private String currentTerminalValue;

	/**
	 * Names of terminals: operator, digit, bracket, function
	 * 
	 */

	public LexicalAnalyzer(String analizedInput) {
		this.analizedInput = analizedInput + Constans.AND_OF_INPUT;
	}

	public String getCurrentTerminalName() {
		return currentTerminalName;
	}

	public String getCurrentTerminalValue() {
		return currentTerminalValue;
	}

	public void nextInputSymbol() {
		String symbol = analizedInput.substring(caretPositon, caretPositon + 1);
		caretPositon++;

		if (isBracket(symbol)) {
			currentTerminalName = Constans.BRACKET;
			currentTerminalValue = symbol;

		} else if (isDigit(symbol)) {
			builder.append(symbol);

			if (isNextInputSymbolDigit()) {
				nextInputSymbol();
			} else {
				currentTerminalName = Constans.DIGIT;
				currentTerminalValue = builder.toString();
				builder.setLength(0);
			}			
		} else if (isOperator(symbol)) {
			currentTerminalName = Constans.OPERATOR;
			currentTerminalValue = symbol;

		} else if (symbol.equals(Constans.AND_OF_INPUT)) {
			currentTerminalName = Constans.AND_OF_INPUT;
			currentTerminalValue = symbol;
		} else {

			throw new RuntimeException("Undefined symbol in position " + caretPositon);
		}
	}

	private boolean isBracket(String symbol) {

		Pattern p = Pattern.compile(REGULAR_BRACKETS);
		Matcher m = p.matcher(symbol);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	private boolean isNextInputSymbolDigit() {
		String symbol = analizedInput.substring(caretPositon, caretPositon + 1);
		if (isDigit(symbol)) {
			return true;
		}
		return false;
	}

	private boolean isDigit(String symbol) {

		Pattern p = Pattern.compile(REGULAR_DIGITS);
		Matcher m = p.matcher(symbol);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	private boolean isOperator(String symbol) {

		Pattern p = Pattern.compile(REGULAR_OPERATORS);
		Matcher m = p.matcher(symbol);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	private boolean isEmptyBuilder(StringBuilder builder) {

		if (builder.toString().equals("")) {
			return true;
		}
		return false;
	}

	public int getCaretPositon() {
		return caretPositon;
	}
}
