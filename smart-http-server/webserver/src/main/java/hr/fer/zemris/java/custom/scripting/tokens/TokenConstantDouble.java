package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Class representing token for storing constant double value.
 * @author Tomislav
 *
 */

public class TokenConstantDouble extends Token {
	
	/**
	 * Token value.
	 */
	private double value;
	
	/**
	 * Constructor accepting token value as double.
	 * @param value Token value as double.
	 */
	public TokenConstantDouble(double value){
		this.value = value;
	}
	
	/**
	 * Constructor accepting token value as String.
	 * @param value Token value as String.
	 */
	public TokenConstantDouble(String value){
		this.value = Double.parseDouble(value);
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
		return Double.toString(value);
	}
}
