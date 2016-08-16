package hr.fer.zemris.java.tecaj.hw3;

/**
 * Klasa iznimke koja se moze pojaviti u radu sa stringovima
 * u klasi {@code CString}
 * @author Tomislav
 *
 */

public class CStringException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor bez parametara.
	 */
	 
	public CStringException(){
		super();
	}
	
	/**
	 * Konstruktor s jednim parametrom.
	 * @param message Poruka greske.
	 */
	
	public CStringException(String message){
		super(message);
	}
}