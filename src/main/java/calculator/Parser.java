package calculator;

import java.util.ArrayList;
import java.util.Stack;

/*** Синтаксический анализатор ****
 
       ***** Rules ********
        * 1.  S  -> E ; S
        * 2.  S  -> e 
        * 3.  E  -> T E’ 
        * 4.  E’ -> + T E’ 
        * 5.  E’ -> - T E’ 
        * 6.  E’ -> e 
        * 7.  T  -> F T’ 
        * 8.  T’ -> * F T’ 
        * 9.  T’ -> / F T’  
        * 10. T’ -> e 
        * 11. F  -> Digit
        * 12. F  -> ( E )
        * 13. T’ -> ^ F T’
        * 14. F  -> Function ( E )
        * 15. F  -> Function ( E , E)
       ***** Rules ********/	

public class Parser {

	private ArrayList<String> correctInputTerminalSequence;
	private LexicalAnalyzer lexicalAnalyzer;
	private Stack<String> automate = new Stack<String>();

	public Parser(String analizedInput) {
		lexicalAnalyzer = new LexicalAnalyzer(analizedInput);
	}
	
	public boolean startAnalizing() {
		correctInputTerminalSequence = new ArrayList<String>();
		lexicalAnalyzer.nextInputSymbol();
		
		correctInputTerminalSequence.add(lexicalAnalyzer.getCurrentTerminalValue());
		
		automate.push("!");
		automate.push("S");

		while (lexicalAnalyzer.hasNextSymbol()) {

			if (lexicalAnalyzer.getCurrentTerminalName().equals(Constans.DIGIT)) {
				doRule(GrammaTable.getOperationCode(automate.peek(), Constans.DIGIT));
				
			} else if (lexicalAnalyzer.getCurrentTerminalName().equals(Constans.FUNCTION)) {
				
				/*****************
				 *Start function parsing, maybe need to change rules 
				 *****************/
				
			} else {
				doRule(GrammaTable.getOperationCode(automate.peek(), lexicalAnalyzer.getCurrentTerminalValue()));
			}

			if (automate.peek().equals("!") && lexicalAnalyzer.getCurrentTerminalValue().equals("!")) {
				break;
			}
		}
		return true;
	}
	
	private void doRule(String ruleNumber) {

		switch (ruleNumber) {
		
		case "100":			
			automate.pop();			
			lexicalAnalyzer.nextInputSymbol();
			correctInputTerminalSequence.add(lexicalAnalyzer.getCurrentTerminalValue());
			break;
			
		case "1":			
			automate.pop();
			automate.push("E");
			break;

		case "2":
			automate.pop();			
			break;

		case "3":			
			automate.pop();
			automate.push("E'");
			automate.push("T");
			break;

		case "4":
			automate.pop();
			automate.push("E'");
			automate.push("T");
			automate.push("+");
			break;

		case "5":
			automate.pop();
			automate.push("E'");
			automate.push("T");
			automate.push("-");
			break;

		case "6":
			automate.pop();
			break;

		case "7":			
			automate.pop();
			automate.push("T'");
			automate.push("F");
			break;

		case "8":
			automate.pop();
			automate.push("T'");
			automate.push("F");
			automate.push("*");
			break;

		case "9":
			automate.pop();
			automate.push("T'");
			automate.push("F");
			automate.push("/");
			break;

		case "10":
			automate.pop();
			break;

		case "11":			
			automate.pop();
			automate.push(Constans.DIGIT);
			break;

		case "12":
			automate.pop();
			automate.push(")");
			automate.push("E");
			automate.push("(");
			break;

		case "13":
			automate.pop();
			automate.push("T'");
			automate.push("F");
			automate.push("^");
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
