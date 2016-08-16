package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Class representing token that is variable.
 * @author Tomislav
 *
 */

public class TokenVariable extends Token {
	
	/**
	 * Token variable name.
	 */
	private String name;
	
	/**
	 * Constructor accepting token variable name.
	 * @param name Token variable name.
	 */
	public TokenVariable(String name){
		this.name = name;
	}
	
	
	/**
	 * Method returns token variable name.
	 * @return Token variable name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText(){
		return name;
	}
}
