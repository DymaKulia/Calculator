package calculator;

import java.io.IOException;
import java.io.InputStreamReader;
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
		mathOperations.add("*");
		mathOperations.add("/");
	}

	private ArrayList<String> allowableSimbols;
	{
		// Add available symbols
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
		allowableSimbols.add("s");
		allowableSimbols.add("d");
		allowableSimbols.add(",");

		// allowableSimbols.add(".");
	}

	private Stack<String> firstStack = new Stack<String>();
	private Stack<String> secondStack = new Stack<String>();

	private String OPEN_BRACKET = "(";
	private String CLOSE_BRACKET = ")";

	public static void main(String[] args) {

		Calculator calc = new Calculator();
		while (true) {
			StringBuilder builder = new StringBuilder();
			System.out.println("Enter expression: ");
			try {
				InputStreamReader reader = new InputStreamReader(System.in);
				int b = 0;
				while (b != 13 & b != 10) {
					if (b != 0) {
						char c = (char) b;
						builder.append(c);
					}
					b = reader.read();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.print(builder.toString());

			if (builder.toString().equals("stop")) {
				break;
			}

			System.out.println("Answer = " + calc.calculate(builder.toString()));

		}

		/*LexicalAnalyzer lan = new LexicalAnalyzer("(1+25+3)");

		lan.nextInputSymbol();
		System.out.println(lan.getCurrentTerminalName() + " = " + lan.getCurrentTerminalValue());
		lan.nextInputSymbol();
		System.out.println(lan.getCurrentTerminalName() + " = " + lan.getCurrentTerminalValue());
		lan.nextInputSymbol();
		System.out.println(lan.getCurrentTerminalName() + " = " + lan.getCurrentTerminalValue());
		lan.nextInputSymbol();
		System.out.println(lan.getCurrentTerminalName() + " = " + lan.getCurrentTerminalValue());
		lan.nextInputSymbol();
		System.out.println(lan.getCurrentTerminalName() + " = " + lan.getCurrentTerminalValue());
		lan.nextInputSymbol();
		System.out.println(lan.getCurrentTerminalName() + " = " + lan.getCurrentTerminalValue());
		lan.nextInputSymbol();
		System.out.println(lan.getCurrentTerminalName() + " = " + lan.getCurrentTerminalValue());
		lan.nextInputSymbol();
		System.out.println(lan.getCurrentTerminalName() + " = " + lan.getCurrentTerminalValue());*/
	}

	public int calculate(String innerExpres) {

		int result = 0;
		if (isCorrectForm(innerExpres)) {

			fillFirstStack(innerExpres);
			ArrayList<String> polskForm = getPolskForm();
			result = calculatePolskForm(polskForm);
		}

		return result;
	}

	private boolean isCorrectForm(String innerExpres) {

		Parser parser = new Parser(innerExpres);		
		return parser.startAnalizing();
	}

	private void fillFirstStack(String innerExpres) {

		// "!" - begin and finish symbol of expression
		String adaptedInnerExpres = innerExpres + "!";

		// fill first stack
		for (int i = adaptedInnerExpres.length(); i > 0; i--) {

			String symbol = adaptedInnerExpres.substring(i - 1, i);
			firstStack.push(symbol);
		}
	}

	private ArrayList<String> getPolskForm() {

		ArrayList<String> polskForm = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		secondStack.push("!");

		while (!firstStack.empty()) {

			String symbol = firstStack.peek();

			if (isOperator(symbol)) {

				if (!isEmptyBuilder(builder)) {

					polskForm.add(builder.toString());
					builder.setLength(0);
				}

				switch (secondStack.peek()) {

				case "!":

					secondStack.push(symbol);
					firstStack.pop();
					break;

				case "(":

					secondStack.push(symbol);
					firstStack.pop();
					break;

				default:

					if (isPriorityOperator(secondStack.peek())) {

						polskForm.add(secondStack.pop());

					} else {

						if (isPriorityOperator(symbol)) {

							secondStack.push(symbol);
							firstStack.pop();

						} else {
							polskForm.add(secondStack.pop());

						}
					}
					break;
				}
				// finish !
			} else if (isBracket(symbol)) {

				if (!isEmptyBuilder(builder)) {

					polskForm.add(builder.toString());
					builder.setLength(0);
				}

				switch (secondStack.peek()) {

				case "!":
					secondStack.push(OPEN_BRACKET);
					firstStack.pop();
					break;

				case "(":

					if (symbol.equals(CLOSE_BRACKET)) {
						secondStack.pop();
						firstStack.pop();
					} else {
						secondStack.push(OPEN_BRACKET);
						firstStack.pop();
					}
					break;

				default:

					if (symbol.equals(CLOSE_BRACKET)) {
						polskForm.add(secondStack.pop());
					} else {
						secondStack.push(symbol);
						firstStack.pop();
					}

					break;
				}

			} else {

				if (symbol.equals("!")) {

					if (!isEmptyBuilder(builder)) {

						polskForm.add(builder.toString());
						builder.setLength(0);
					}

					if (secondStack.peek().equals("!")) {
						break;
					} else {
						polskForm.add(secondStack.pop());
					}
				} else {

					builder.append(symbol);
					firstStack.pop();
				}
			}

		}

		return polskForm;
	}

	private Integer calculatePolskForm(ArrayList<String> polskForm) {

		// Prepare stack to work
		Stack integerStack = new Stack();

		for (int i = 0; i < polskForm.size(); i++) {

			String symbol = polskForm.get(i);

			if (!isOperator(symbol)) {
				int number = Integer.parseInt(symbol);
				integerStack.push(number);
			} else {
				if (symbol.equals("+")) {

					Integer rightOperand = (Integer) integerStack.pop();
					Integer leftOperand = (Integer) integerStack.pop();
					Integer result = leftOperand + rightOperand;
					integerStack.push(result);

				} else if (symbol.equals("-")) {

					Integer rightOperand = (Integer) integerStack.pop();
					Integer leftOperand = (Integer) integerStack.pop();
					Integer result = leftOperand - rightOperand;
					integerStack.push(result);

				} else if (symbol.equals("*")) {

					Integer rightOperand = (Integer) integerStack.pop();
					Integer leftOperand = (Integer) integerStack.pop();
					Integer result = leftOperand * rightOperand;
					integerStack.push(result);

				} else if (symbol.equals("/")) {

					Integer rightOperand = (Integer) integerStack.pop();
					Integer leftOperand = (Integer) integerStack.pop();
					Integer result = leftOperand / rightOperand;
					integerStack.push(result);
				}

			}

		}

		return (Integer) integerStack.pop();
	}

	private boolean isPriorityOperator(String operator) {

		if (operator.equals("*") || operator.equals("/")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isEmptyBuilder(StringBuilder builder) {

		if (builder.toString().equals("")) {
			return true;
		}
		return false;
	}

	private boolean isBracket(String symbol) {

		if (symbol.equals(CLOSE_BRACKET) || symbol.equals(OPEN_BRACKET)) {
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

	/*
	 * private boolean isCorrectForm(String innerExpres) { // Do check here
	 * about double operators, start with operator or bracket // etc. or check
	 * "is correct innerExpres"
	 * 
	 * if (innerExpres.equals("")) { System.out.println("Empty expression");
	 * return false; }
	 * 
	 * // to follow numbers of open/close brackets int bracketsBalans = 0;
	 * 
	 * for (int i = 0; i < innerExpres.length(); i++) { String symbol =
	 * innerExpres.substring(i, i + 1); if (i == 0) { if (isOperator(symbol)) {
	 * System.out .println("Expression cannot starts with operators"); return
	 * false; } if (symbol.equals(CLOSE_BRACKET)) { System.out
	 * .println("Expression cannot starts with close bracket"); return false; }
	 * }
	 * 
	 * if (i == innerExpres.length() - 1) { if (isOperator(symbol)) {
	 * System.out.println("Expression cannot ends with operators"); return
	 * false; } if (symbol.equals(OPEN_BRACKET)) { System.out
	 * .println("Expression cannot ends with close bracket"); return false; } }
	 * 
	 * if (isBracket(symbol)) { if (i != 0) { String pastSymbol =
	 * innerExpres.substring(i - 1, i); if (symbol.equals(CLOSE_BRACKET) &&
	 * isOperator(pastSymbol)) { System.out
	 * .println("Expression cannot has operators befor close bracket in position "
	 * + (i + 1)); return false; } }
	 * 
	 * if (symbol.equals(OPEN_BRACKET)) { bracketsBalans++; if (i != 0) { String
	 * pastSymbol = innerExpres.substring(i - 1, i); if
	 * (pastSymbol.equals(CLOSE_BRACKET) || isAllowableSymbol(pastSymbol)) {
	 * System.out.println("Missed operator in position " + (i + 1)); return
	 * false; } } } else {
	 * 
	 * bracketsBalans--; if (bracketsBalans < 0) {
	 * System.out.println("Bracket in position " + i +
	 * " do not have open bracket"); return false; } if (i != 0) { String
	 * pastSymbol = innerExpres.substring(i - 1, i); if
	 * (pastSymbol.equals(OPEN_BRACKET)) {
	 * System.out.println("Missed operand in position " + (i + 1)); return
	 * false; } } } } else if (isOperator(symbol)) { String pastSymbol =
	 * innerExpres.substring(i - 1, i); if (pastSymbol.equals(OPEN_BRACKET)) {
	 * System.out .println(
	 * "Expression cannot starts with operators after open bracket in position "
	 * + (i + 1)); return false; } if (isOperator(pastSymbol)) {
	 * System.out.println("Duplicate operators in position " + (i + 1)); return
	 * false; } } else { if (!isAllowableSymbol(symbol)) {
	 * System.out.println("Undefined symbol " + symbol + " in position " + (i +
	 * 1)); return false; } else {
	 * 
	 * if (i != 0) { String pastSymbol = innerExpres.substring(i - 1, i); if
	 * (pastSymbol.equals(CLOSE_BRACKET)) {
	 * System.out.println("Missed operator in position " + (i + 1)); return
	 * false; } } } } }
	 * 
	 * if (bracketsBalans > 0) { System.out.println(bracketsBalans + " bracket "
	 * + "do not have close bracket"); return false; } return true; }
	 */

}
