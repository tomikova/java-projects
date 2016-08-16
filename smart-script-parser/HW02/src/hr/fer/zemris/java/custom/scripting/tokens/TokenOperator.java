package hr.fer.zemris.java.custom.scripting.tokens;

/**
* @author Tomislav
*/


public class TokenOperator extends Token {
	
	private String symbol;
	
	public TokenOperator(String symbol){
		this.symbol = symbol;
	}
	
	/**
	* Metoda koja dohvaca simbol tokena.
	* @return Simbol tokena.
	*/

	public String getSymbol() {
		return symbol;
	}
	
	/**
	* Metoda koja dohvaca tekstualni format tokena.
	* @return Tekstualni format tokena.
	*/
	
	@Override
	public String asText(){
		return symbol;
	}
}
