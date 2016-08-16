package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Class representing token that is function.
 * @author Tomislav
 *
 */
public class TokenFunction extends Token {
	
	/**
	 * Token function name.
	 */
	private String name;
	
	/**
	 * Constructor accepting token function name.
	 * @param name Token function name.
	 */
	public TokenFunction(String name){
		this.name = name;
	}
	
	/**
	 * Method return token name.
	 * @return Token name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText(){
		return "@"+name;
	}
}
