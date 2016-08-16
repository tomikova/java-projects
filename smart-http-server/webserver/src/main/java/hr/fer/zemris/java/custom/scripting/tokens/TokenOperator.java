package hr.fer.zemris.java.custom.scripting.tokens;


/**
 * Class representing token that is operator.
 * @author Tomislav
 *
 */

public class TokenOperator extends Token {
	
	/**
	 * Token operator symbol.
	 */
	private String symbol;
	
	/**
	 * Constructor accepting token operator symbol.
	 * @param symbol Token operator symbol.
	 */
	public TokenOperator(String symbol){
		this.symbol = symbol;
	}
	
	/**
	 * Method returns token symbol.
	 * @return Token symbol.
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText(){
		return symbol;
	}
}
