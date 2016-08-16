package hr.fer.zemris.java.custom.collections;

/**
 * Exception class raised when stack is empty.
 * @author Tomislav
 */

public class EmptyStackException extends RuntimeException {
	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 */
	public EmptyStackException(){
		super();
	}
	/**
	 * Constructor with one parameter.
	 * @param s Exception message.
	 */
	public EmptyStackException(String s){
		super(s);
	}
}
