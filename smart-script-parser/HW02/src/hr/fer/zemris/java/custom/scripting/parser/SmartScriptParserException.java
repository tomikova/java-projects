package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Razred iznimke koji se javlja prilikom parsiranja dokumenta.
 * @author Tomislav
 */

public class SmartScriptParserException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public SmartScriptParserException(){
		super();
	}
	
	public SmartScriptParserException(String s){
		super(s);
	}
}
