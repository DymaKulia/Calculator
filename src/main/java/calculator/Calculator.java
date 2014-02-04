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
	}

	private Stack<String> firstStack = new Stack<String>();
	private Stack<String> secondStack = new Stack<String>();

	private String OPEN_BRACKET = "(";
	private String CLOSE_BRACKET = ")";

	public static void main(String[] args) {

		Calculator calc = new Calculator();
		try {
			calc.calculate("((12+(1)+(1+3)+4))");
			//System.out.println("Answer = " + calc.calculate("(12+1+3+4)"));
		} catch (Exception e) {
			e.printStackTrace();
			calc.secondStack.clear();
		}

	}

	public int calculate(String innerExpres) {

		fillFirstStack(innerExpres);
		ArrayList<String> polskForm = getPolskForm();

		for (int i = 0; i < polskForm.size(); i++) {
			
			System.out.print(polskForm.get(i)+" ");
		}

		return 0;
	}

	private void fillFirstStack(String innerExpres) {

		// Do check here about double operators, start with operator or bracket
		// etc. or check "is correct innerExpres"
		for (int i = 0; i < innerExpres.length(); i++) {
			String symbol = innerExpres.substring(i, i + 1);
			if (i == 0) {
				if (isOperator(symbol)) {
					System.out
							.println("Expression cannot starts with operators");
					throw new RuntimeException();
				}
				if (symbol.equals(CLOSE_BRACKET)) {
					System.out
							.println("Expression cannot starts with close bracket");
					throw new RuntimeException();
				}
			}

			if (i == innerExpres.length() - 1) {
				if (isOperator(symbol)) {
					System.out.println("Expression cannot ends with operators");
					throw new RuntimeException();
				}
				if (symbol.equals(OPEN_BRACKET)) {
					System.out
							.println("Expression cannot ends with close bracket");
					throw new RuntimeException();
				}
			}

			if (isBracket(symbol)) {
				if (i != 0) {
					String pastSymbol = innerExpres.substring(i - 1, i);
					if (symbol.equals(CLOSE_BRACKET) && isOperator(pastSymbol)) {
						System.out
								.println("Expression cannot has operators befor close bracket in position "
										+ (i + 1));
						throw new RuntimeException();
					}
				}

				if (symbol.equals(OPEN_BRACKET)) {
					if (i != 0) {
						String pastSymbol = innerExpres.substring(i - 1, i);
						if (pastSymbol.equals(CLOSE_BRACKET)
								|| isAllowableSymbol(pastSymbol)) {
							System.out.println("Missed operator in position "
									+ (i + 1));
							throw new RuntimeException();
						}
					}
				}
			} else if (isOperator(symbol)) {
				String pastSymbol = innerExpres.substring(i - 1, i);
				if (pastSymbol.equals(OPEN_BRACKET)) {
					System.out
							.println("Expression cannot starts with operators after open bracket in position "
									+ (i + 1));
					throw new RuntimeException();
				}
				if (isOperator(pastSymbol)) {
					System.out.println("Duplicate operators in position "
							+ (i + 1));
					throw new RuntimeException();
				}
			} else {
				if (!isAllowableSymbol(symbol)) {
					System.out.println("Undefined symbol " + symbol
							+ " in position " + (i + 1));
					throw new RuntimeException();
				} else {

					if (i != 0) {
						String pastSymbol = innerExpres.substring(i - 1, i);
						if (pastSymbol.equals(CLOSE_BRACKET)) {
							System.out.println("Missed operator in position "
									+ (i + 1));
							throw new RuntimeException();
						}
					}

				}

			}
		}

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

		// to follow symbolPosition
		int symbolPosition = 1;
		// to follow numbers of open/close brackets
		int bracketsBalans = 0;

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
					symbolPosition++;
					break;

				case "(":

					secondStack.push(symbol);
					firstStack.pop();
					symbolPosition++;
					break;

				default:

					polskForm.add(secondStack.pop());

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

					if (symbol.equals(CLOSE_BRACKET)) {
						System.out.println("Bracket in position "
								+ symbolPosition + " do not have open bracket");
						throw new RuntimeException();
					} else {
						secondStack.push(OPEN_BRACKET);
						firstStack.pop();
						symbolPosition++;
						bracketsBalans++;
					}
					break;

				case "(":

					if (symbol.equals(CLOSE_BRACKET)) {
						secondStack.pop();
						firstStack.pop();
						symbolPosition++;
						bracketsBalans--;
					} else {
						secondStack.push(OPEN_BRACKET);
						firstStack.pop();
						symbolPosition++;
						bracketsBalans++;
					}
					break;

				// some operator in second stack
				default:

					if (symbol.equals(CLOSE_BRACKET)) {
						polskForm.add(secondStack.pop());
					} else {
						secondStack.push(symbol);
						firstStack.pop();
						symbolPosition++;
					}

					break;
				}
				// finish !
			} else {

				if (symbol.equals("!")) {

					if (!isEmptyBuilder(builder)) {

						polskForm.add(builder.toString());
						builder.setLength(0);
					}

					if (secondStack.peek().equals("!")) {
						break;
					} else if (secondStack.peek().equals(OPEN_BRACKET)) {
						System.out.println(bracketsBalans + " bracket "
								+ "do not have close bracket");
						throw new RuntimeException();
					} else {
						polskForm.add(secondStack.pop());
					}
				}

				builder.append(symbol);
				firstStack.pop();
				symbolPosition++;
			}

		}

		return polskForm;
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

}
