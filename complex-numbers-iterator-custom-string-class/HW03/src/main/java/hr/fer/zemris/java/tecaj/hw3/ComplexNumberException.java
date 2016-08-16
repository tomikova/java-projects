package hr.fer.zemris.java.tecaj.hw3;

/**
 * Klasa iznimke koja se moze pojaviti u radu s kompleksnim brojevima
 * u klasi {@code ComplexNumber}
 * @author Tomislav
 */

public class ComplexNumberException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor bez parametara.
	 */
	
	public ComplexNumberException(){
		super();
	}
	
	/**
	 * Konstruktor s jednim parametrom.
	 * @param message Poruka greske.
	 */
	
	public ComplexNumberException(String message){
		super(message);
	}
}