package hr.fer.zemris.java.custom.collections.demo;

/**
* @author Tomislav
*/

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

public class StackDemo {
	
	/**
	* Metoda koja se poziva prilikom pokretanja programa.
	* @param args Argumenti iz komandne linije.
	*/
	
	public static void main(String[] args) {
		
		if(args.length < 1){
			System.out.println("Invalid expression.");
			System.exit(4);
		}
		
		String[] parameters = args[0].split("\\s+");
		int argsLength = parameters.length;
		String[] operators = {"+","-","/","*","%"};
		int opLength = operators.length;
		ObjectStack stack = new ObjectStack();
		
		for (int i = 0; i < argsLength; i++ ){
			boolean isOperator = false;
			for (int j = 0; j < opLength; j++){
				if (parameters[i].equals(operators[j])){
					int x = 0;
					int y = 0;
					try{
						y = Integer.parseInt((String)stack.pop());
						x = Integer.parseInt((String)stack.pop());
					}catch(EmptyStackException ex){
						System.out.println(ex.getMessage());
						System.exit(0);
					}catch(Exception ex){
						System.out.println("Invalid expression.");
						System.exit(1);
					}
					int result = calculate(x,y,operators[j]);
					stack.push(Integer.toString(result));
					isOperator = true;
					break;
				}
			}
			if(!isOperator){
				if (i == argsLength-1){
					System.out.println("Invalid expression.");
					System.exit(4);
				}
				stack.push(parameters[i]);
			}
		}
		
		if(stack.size() > 1){
			System.out.println("Invalid expression.");
			System.exit(1);
		}
		
		System.out.println("Expression evaluates to "+(String)stack.pop());
	}
	
	/**
	* Metoda koja izvrsava zeljenu operaciju.
	* @param x Vrijednost prvog operanda.
	* @param y Vrijednost drugog operanda.
	* @param operator Operator za izvrsavanje zeljene funkcije.
	* @return Cjelobrojna vrijednost rezultata izvrsavanja operacije.
	*/
	
	public static int calculate(int x, int y, String operator){
		int result = 0;
		if (operator.equals("+")){
			result = x + y;
		}
		else if (operator.equals("-")){
			result = x - y;
		}
		else if (operator.equals("*")){
			result = x * y;
		}
		else if (operator.equals("/")){
			if (y == 0){
				System.out.println("Error: Division by 0");
				System.exit(2);
			}
			result = x / y;
		}
		else if (operator.equals("%")){
			if (y == 0){
				System.out.println("Error: Division by 0");
				System.exit(3);
			}
			result = x % y;
		}
		return result;
	}
}
