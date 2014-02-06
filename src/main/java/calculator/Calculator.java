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

	private Stack<String> stack = new Stack<String>();

	private char OPEN_BRACKET = '(';
	private char CLOSE_BRACKET = ')';

	public static void main(String[] args) {

		Calculator calc = new Calculator();
		try {
			System.out.println("Answer = " + calc.calculate(""));
		} catch (Exception e) {
			calc.stack.clear();
		}

	}

	public int calculate(String innerExpres) {

		ArrayList<String> polskForm = getPolskForm(innerExpres);

		return 0;
	}

	private ArrayList<String> getPolskForm(String innerExpres) {

		// "!" - begin and finish symbol of expression
		String adaptedInnerExpres = "!" + innerExpres + "!";
		ArrayList<String> polskForm = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		stack.push("!");

		for (int i = 1; i < adaptedInnerExpres.length(); i++) {
			char symbol = adaptedInnerExpres.charAt(i);

			if (isOperator(symbol)) {
				if (i == 1) {
					System.out
							.println("Expression cannot start with operators");
					throw new RuntimeException();
				}

				if (stack.peek().equals(OPEN_BRACKET)) {
					System.out
							.println("Expression cannot start with operators after"
									+ " open bracket in position " + i);
					throw new RuntimeException();
				}

				switch (stack.peek()) {

				case "!":

					break;

				case ")":

					break;

				case "(":

					break;

				case "-":

					break;
				}

			} else if (isBracket(symbol)) {

				switch (stack.peek()) {

				case "!":

					break;

				case ")":

					break;

				case "(":

					break;

				case "-":

					break;
				}

			} else if (!isAllowableSymbol(symbol)) {
				System.out.println("Andefined symbol " + symbol
						+ " in position " + i);
				throw new RuntimeException();
			} else {

				builder.append(symbol);
				
			}

		}

		return null;
	}

	private boolean isBracket(char symbol) {
		if (symbol == CLOSE_BRACKET || symbol == OPEN_BRACKET) {
			return true;
		}
		return false;
	}

	private boolean isAllowableSymbol(char symbol) {

		if (allowableSimbols.contains(symbol)) {
			return true;
		}
		return false;
	}

	private boolean isOperator(char symbol) {

		if (mathOperations.contains(symbol)) {
			return true;
		}
		return false;
	}

}
