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
	}

	public int calculate(String innerExpres) {

		int result = 0;
		if (isCorrectForm(innerExpres)) {

			/*fillFirstStack(innerExpres);
			ArrayList<String> polskForm = getPolskForm();
			result = calculatePolskForm(polskForm);*/
		}

		return result;
	}

	private boolean isCorrectForm(String innerExpres) {

		Parser parser = new Parser(innerExpres);
		boolean isCorrect = false;
		try {
			isCorrect = parser.startAnalizing();			
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
			return false;
		}
		return isCorrect;
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

}
