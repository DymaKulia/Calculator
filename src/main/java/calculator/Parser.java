package calculator;

import java.util.ArrayList;
import java.util.Stack;

public class Parser /*Синтаксический анализатор*/{
	
	//Выходом должна быть польская форма
	//или сообщение об ошибке
	//Для включения поддержки функций нужно будет в синтаксическом анализаторе
	//вычислять эту функцию и результат помещать в польскую форму как лексему
	//для дальнейшего вычисления выражения
	//Таким образом будет поддерживаться синтаксис типа такого: "1+2+sum(1,2,3)"
	
	
	/***** Rules *********
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
	 * 11. F -> Digit
	 * 12. F -> ( E )
	 * 13. T’ -> ^ F T’
	 ***** Rules *******/	
	private ArrayList<String> correctInputTerminalSequence;
	private LexicalAnalyzer lexicalAnalyzer;
	private Stack<String> automat = new Stack<String>();
	public Parser(String analizedInput){
		lexicalAnalyzer = new LexicalAnalyzer(analizedInput);
	}
	
	public boolean startAnalizing() {
		correctInputTerminalSequence = new ArrayList<String>();
		lexicalAnalyzer.nextInputSymbol();
		
		correctInputTerminalSequence.add(lexicalAnalyzer.getCurrentTerminalValue());
		
		automat.push("!");
		automat.push("S");

		while (lexicalAnalyzer.hasNextSymbol()) {
			
			if (lexicalAnalyzer.getCurrentTerminalName().equals(Constans.DIGIT)) {
				doRule(GrammaTable.getOperationCode(automat.peek(), Constans.DIGIT));
			} else {
				doRule(GrammaTable.getOperationCode(automat.peek(), lexicalAnalyzer.getCurrentTerminalValue()));
			}
			
			if (automat.peek().equals("!") && lexicalAnalyzer.getCurrentTerminalValue().equals("!")) {
				break;
			}
		}

		return true;
	}
	
	private void doRule(String ruleNumber) {

		switch (ruleNumber) {
		
		case "100":
			/*System.out.println("pop Dig");
			System.out.println("nextInputSymbol");*/
			automat.pop();			
			lexicalAnalyzer.nextInputSymbol();
			correctInputTerminalSequence.add(lexicalAnalyzer.getCurrentTerminalValue());
			break;
			
		case "1":
			/*System.out.println("pop S");
			System.out.println("push E");*/
			automat.pop();
			automat.push("E");
			break;

		case "2":
			automat.pop();			
			break;

		case "3":
			/*System.out.println("pop E");
			System.out.println("push E'T");*/
			automat.pop();
			automat.push("E'");
			automat.push("T");
			break;

		case "4":
			automat.pop();
			automat.push("E'");
			automat.push("T");
			automat.push("+");
			break;

		case "5":
			automat.pop();
			automat.push("E'");
			automat.push("T");
			automat.push("-");
			break;

		case "6":
			automat.pop();
			break;

		case "7":
			/*System.out.println("pop T");
			System.out.println("push T'F");*/
			automat.pop();
			automat.push("T'");
			automat.push("F");
			break;

		case "8":
			automat.pop();
			automat.push("T'");
			automat.push("F");
			automat.push("*");
			break;

		case "9":
			automat.pop();
			automat.push("T'");
			automat.push("F");
			automat.push("/");
			break;

		case "10":
			automat.pop();
			break;

		case "11":
			/*System.out.println("pop F");
			System.out.println("push Dig");*/
			automat.pop();
			automat.push(Constans.DIGIT);
			break;

		case "12":
			automat.pop();
			automat.push(")");
			automat.push("E");
			automat.push("(");
			break;

		case "13":
			automat.pop();
			automat.push("T'");
			automat.push("F");
			automat.push("^");
			break;
			
		case "STOP":			
			break;

		default:
			String error = ruleNumber + lexicalAnalyzer.getCaretPositon();
			throw new RuntimeException(error);
		}
	}

	public ArrayList<String> getCorrectInputTerminalSequence() {
		return correctInputTerminalSequence;
	}	
}
