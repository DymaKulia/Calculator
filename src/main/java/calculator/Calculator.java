package calculator;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Calculator {
	
	Parser parser;
	
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
				e.printStackTrace();
			}

			if (builder.toString().equals("stop")) {
				break;
			}
			System.out.println("Answer = " + calc.calculate(builder.toString()));
		}		
	}

	public float calculate(String innerExpres) {

		float result = 0;
		try {
			if (isCorrectForm(innerExpres)) {
				ArrayList<String> polskForm = PolskFormParser.getPolskForm(parser.getCorrectInputTerminalSequence());
				result = PolskFormParser.calculatePolskFormExpression(polskForm);
			}
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	private boolean isCorrectForm(String innerExpres) {

		parser = new Parser(innerExpres);
		boolean isCorrect = false;
		try {
			isCorrect = parser.startAnalizing();			
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
		return isCorrect;
	}
}
