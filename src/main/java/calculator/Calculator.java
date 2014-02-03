package calculator;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Calculator {

	private ArrayList<String> mathOperations;
	{
		// Add available math operations
		mathOperations = new ArrayList<String>();
		mathOperations.add("+");
		mathOperations.add("-");
	}

	private ArrayList<String> allowableSimbols;
	{
		// Add available math operations
		allowableSimbols = new ArrayList<String>();
		allowableSimbols.add("0");
		allowableSimbols.add("1");
		allowableSimbols.add("2");
		allowableSimbols.add("3");
		allowableSimbols.add("4");
		allowableSimbols.add("5");
		allowableSimbols.add("6");
		allowableSimbols.add("7");
		allowableSimbols.add("8");
		allowableSimbols.add("9");
	}

	private Stack<String> firstStack = new Stack<String>();
	private Stack<String> secondStack = new Stack<String>();

	private String OPEN_BRACKET = "(";
	private String CLOSE_BRACKET = ")";

	public static void main(String[] args) {

		Calculator calc = new Calculator();
		try {
			System.out.println("Answer = " + calc.calculate(""));
		} catch (Exception e) {
			calc.secondStack.clear();
		}
		/*
		 * Calculator calc = new Calculator(); calc.fillFirstStack( "123456");
		 * 
		 * for (int i = 1; i < 9; i++) {
		 * System.out.println(calc.firstStack.pop()); }
		 */
		// String adaptedInnerExpres="123456";

		/*
		 * for (int i = adaptedInnerExpres.length(); i > 0; i--) {
		 * 
		 * String symbol = adaptedInnerExpres.substring(i-1, i);
		 * System.out.println(symbol); }
		 */

		// String symbol = "123456".substring(5, 6);
		// System.out.println("123456".length());
	}

	public int calculate(String innerExpres) {

		fillFirstStack(innerExpres);
		ArrayList<String> polskForm = getPolskForm();

		return 0;
	}

	private void fillFirstStack(String innerExpres) {

		// Do check here about double operators? start with operator or bracket
		// etc.

		// Kind Errors
		// System.out.println("Expression cannot start with operators");
		// System.out.println("Expression cannot start with close bracket");
		// System.out.println("Undefined symbol " + symbol	+ " in position " + symbolPosition);
		

		// "!" - begin and finish symbol of expression
		String adaptedInnerExpres = innerExpres + "!";

		for (int i = adaptedInnerExpres.length(); i > 0; i--) {

			String symbol = adaptedInnerExpres.substring(i - 1, i);
			firstStack.push(symbol);
		}

	}

	private ArrayList<String> getPolskForm() {

		ArrayList<String> polskForm = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		secondStack.push("!");

		int symbolPosition = 1;

		while (!firstStack.empty()) {

			String symbol = firstStack.peek();

			if (isOperator(symbol)) {

				switch (secondStack.peek()) {

				case "!":

					firstStack.push(symbol);
					secondStack.pop();
					symbolPosition++;

					break;

				case "(":

					firstStack.push(symbol);
					secondStack.pop();
					symbolPosition++;

					break;

				default:

					break;
				}

			} else if (isBracket(symbol)) {

				switch (secondStack.peek()) {

				case "!":

					if (symbol == CLOSE_BRACKET) {
						System.out.println("Bracket in position "
								+ symbolPosition + " do not have open bracket");
						throw new RuntimeException();
					} else {
						secondStack.push(OPEN_BRACKET);
					}
					break;

				case "(":

					if (symbol == CLOSE_BRACKET) {
						secondStack.pop();
					} else {
						secondStack.push(OPEN_BRACKET);
					}
					break;
				}

			} else {

				builder.append(symbol);

			}

		}

		return null;
	}

	private boolean isBracket(String symbol) {
		if (symbol == CLOSE_BRACKET || symbol == OPEN_BRACKET) {
			return true;
		}
		return false;
	}

	private boolean isAllowableSymbol(String symbol) {

		if (allowableSimbols.contains(symbol)) {
			return true;
		}
		return false;
	}

	private boolean isOperator(String symbol) {

		if (mathOperations.contains(symbol)) {
			return true;
		}
		return false;
	}

}
