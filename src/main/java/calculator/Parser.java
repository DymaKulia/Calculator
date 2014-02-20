package calculator;

import java.util.Stack;

public class Parser /*Синтаксический анализатор*/{
	
	//Выходом должна быть польская форма
	//или сообщение об ошибке
	//Для включения поддержки функций нужно будет в синтаксическом анализаторе
	//вычислять эту функцию и результат помещать в польскую форму как лексему
	//для дальнейшего вычисления выражения
	//Таким образом будет поддерживаться синтаксис типа такого: "1+2+sum(1,2,3)"
	
	
	/*****Rules*********
	 * 1.  S -> E ; S
	 * 2.  S -> e 
	 * 3.  E -> T E’ 
	 * 4.  E’ -> + T E’ 
	 * 5.  E’ -> - T E’ 
	 * 6.  E’ -> e 
	 * 7.  T -> F T’ 
	 * 8.  T’ -> * F T’ 
	 * 9.  T’ -> / F T’  
	 * 10. T’ -> e 
	 * 11. F -> N
	 * 12. F -> (E)
	 * 13. T’ -> ^ F T’
	 *****************/
	
	private LexicalAnalyzer lexicalAnalyzer;
	private Stack<String> automat = new Stack<String>();
	public Parser(String analizedInput){
		lexicalAnalyzer = new LexicalAnalyzer(analizedInput);
	}
	
	public boolean startAnalizing(){		
		lexicalAnalyzer.nextInputSymbol();
		automat.push("!");
		automat.push("S");
		
		while (true) {
			
			switch (lexicalAnalyzer.getCurrentTerminalName()) {

			/**** BRACKET *******/
			case Constans.BRACKET:

				if (lexicalAnalyzer.getCurrentTerminalValue().equals(Constans.OPEN_BRACKET)) {

					if (isNotTerminal(automat.peek())) {

					} else {

						if (automat.peek().equals(Constans.OPEN_BRACKET)) {
							automat.pop();
							lexicalAnalyzer.nextInputSymbol();
						} else {
							throw new RuntimeException("Syntax error in position " + lexicalAnalyzer.getCaretPositon());
						}
					}

				}
				if (lexicalAnalyzer.getCurrentTerminalValue().equals(Constans.CLOSE_BRACKET)) {

					if (automat.peek().equals(Constans.CLOSE_BRACKET)) {
						automat.pop();
						lexicalAnalyzer.nextInputSymbol();
					}

				}
				break;

			/**** OPERATOR *******/
			case Constans.OPERATOR:

				switch (lexicalAnalyzer.getCurrentTerminalValue()) {
				case Constans.SUM:

					break;

				case Constans.DIFF:

					break;
				case Constans.MULL:

					break;

				case Constans.DIV:

					break;

				case Constans.DEGREE:

					break;
				}

				break;

			/**** DIGIT *******/
			case Constans.DIGIT:

				break;

			/**** FUNCTION *******/
			case Constans.FUNCTION:

				break;

			/**** AND_OF_INPUT *******/
			case Constans.AND_OF_INPUT:

				break;

			}

			if (true) {
				break;
			}
		}
		
		
		
		
		
		return true;
	}

	private boolean isNotTerminal(String peekAutomat) {
		
		return false;
	}

	private void doRule(int ruleNumber) {

		switch (ruleNumber) {
		case 0:
			throw new RuntimeException("Syntax error in position " + lexicalAnalyzer.getCaretPositon());

		case 1:
			break;

		case 2:
			break;
			
		case 3:
			break;
			
		case 4:
			break;
			
		case 5:
			break;
		case 6:
			break;
			
		case 7:
			break;
			
		case 8:
			break;
			
		case 9:
			break;
			
		case 10:
			break;
			
		case 11:
			break;
			
		case 12:
			break;
			
		case 13:
			break;

		}
	}
	
}
