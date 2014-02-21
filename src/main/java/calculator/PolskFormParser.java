package calculator;

import java.util.ArrayList;
import java.util.Stack;

public class PolskFormParser {

	public static ArrayList<String> getPolskForm(ArrayList<String> inputTerminalSequence) {

		ArrayList<String> polskForm = new ArrayList<String>();
		Stack<String> polskAutomate = new Stack<String>();
		polskAutomate.push("!");
		
		int item = 0;

		while (item < inputTerminalSequence.size()) {

			int ruleNumber = PolskFormTable.getOperationCode(polskAutomate.peek(), inputTerminalSequence.get(item));
			doRule(ruleNumber, inputTerminalSequence.get(item), polskAutomate, polskForm);
			if (ruleNumber == 100 || ruleNumber == 1 || ruleNumber == 3 || ruleNumber == 4) {
				item++;
			}
		}

		return polskForm;
	}

	public static Float calculatePolskFormExpression(ArrayList<String> polskFormExpression) {

		// Prepare stack to work
		Stack<Float> integerStack = new Stack<Float>();

		for (int i = 0; i < polskFormExpression.size(); i++) {

			String symbol = polskFormExpression.get(i);

			if (symbol.equals(Constans.SUM)) {

				Float rightOperand = (Float) integerStack.pop();
				Float leftOperand = (Float) integerStack.pop();
				Float result = leftOperand + rightOperand;
				integerStack.push(result);

			} else if (symbol.equals(Constans.DIFF)) {

				Float rightOperand = (Float) integerStack.pop();
				Float leftOperand = (Float) integerStack.pop();
				Float result = leftOperand - rightOperand;
				integerStack.push(result);

			} else if (symbol.equals(Constans.MULL)) {

				Float rightOperand = (Float) integerStack.pop();
				Float leftOperand = (Float) integerStack.pop();
				Float result = leftOperand * rightOperand;
				integerStack.push(result);

			} else if (symbol.equals(Constans.DIV)) {

				Float rightOperand = (Float) integerStack.pop();
				Float leftOperand = (Float) integerStack.pop();
				Float result = leftOperand / rightOperand;
				integerStack.push(result);

			} else if (symbol.equals(Constans.DEGREE)) {
				Float rightOperand = (Float) integerStack.pop();
				Float leftOperand = (Float) integerStack.pop();
				Float result = leftOperand;
				for (int k = 1; k < rightOperand; k++) {
					result *= leftOperand;
				}
				integerStack.push(result);

			} else {
				float number = Float.parseFloat(symbol);
				integerStack.push(number);
			}
		}
		return (Float) integerStack.pop();
	}

	private static void doRule(int ruleNumber, String currentSymbol, Stack<String> polskAutomate, ArrayList<String> polskForm) {

		switch (ruleNumber) {

		/*******************************************************
		 * place digit to PolskFormArray and receive next symbol
		 *******************************************************/
		case 100:
			polskForm.add(currentSymbol);
			break;

		/**************************************************************
		 * place input symbol to polsk automate and receive next symbol
		 *************************************************************/
		case 1:			
			polskAutomate.push(currentSymbol);
			break;

		/***********************************************
		 * pop from automate and place to PolskFormArray
		 ***********************************************/	
		case 2:			
			polskForm.add(polskAutomate.pop());
			break;

		/***********************************************
		 * pop from automate and and receive next symbol
		 ***********************************************/	
		case 3:
			polskAutomate.pop();
			break;

		/***********************************************
		 * stop process
		 ***********************************************/
		case 4:
			break;

		/***********************************************
		 * Error
		 ***********************************************/	
		case 5:
			throw new RuntimeException("Error: syntacs error find duaring get polsk form process");
		}
	}
}
