package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Exception which occurs if stack is empty.
 * @author Tomislav
 *
 */

public class EmptyStackException extends RuntimeException {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor without parameters.
	 */
	public EmptyStackException() {
		super();
	}
	
	/**
	 * Constructor with one parameter.
	 * @param message Message of exception.
	 */
	public EmptyStackException(String message) {
		super(message);
	}
}

