package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception which can be raised while parsing document tree usually  
 * indicating that document is not well formatted.
 * @author Tomislav
 */

public class SmartScriptParserException extends RuntimeException {
	
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor without paraemters.
	 */
	public SmartScriptParserException(){
		super();
	}
	
	/**
	 * Constructor with one parameter.
	 * @param s Exception message.
	 */
	public SmartScriptParserException(String s){
		super(s);
	}
}
