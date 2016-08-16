package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Class representing token for storing constant integer value.
 * @author Tomislav
 *
 */

public class TokenConstantInteger extends Token {
	
	/**
	 * Token value.
	 */
	private int value;
	
	/**
	 * Constructor accepting token value int.
	 * @param value Token value as double.
	 */
	public TokenConstantInteger(int value){
		this.value = value;
	}
	
	/**
	 * Constructor accepting token value as String.
	 * @param value Token value as String.
	 */
	public TokenConstantInteger(String value){
		this.value = Integer.parseInt(value);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValue() {
		return value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText(){
		return Integer.toString(value);
	}
}
