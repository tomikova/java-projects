package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Class representing token for storing strings.
 * @author Tomislav
 *
 */

public class TokenString extends Token {
	
	/**
	 * Token string value.
	 */
	private String value;
	
	/**
	 * Constructor accepting token string value.
	 * @param value Token string value.
	 */
	public TokenString(String value){
		this.value = value;
	}
	

	/**
	 * Method returns token string value.
	 * @return Token string value.
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText(){
		String text = value;
		text = text.replace("\\", "\\\\");
		text = text.replace("{", "\\{");
		text = text.replace("\n", "\\n");
		text = text.replace("\r", "\\r");
		text = text.replace("\t", "\\t");
		text = text.replace("\"", "\\\"");
		text = '"'+text+'"';
		return text;
	}
}
