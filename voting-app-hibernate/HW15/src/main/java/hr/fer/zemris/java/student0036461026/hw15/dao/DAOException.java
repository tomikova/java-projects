package hr.fer.zemris.java.student0036461026.hw15.dao;

/**
 * Exception class that can occur when accessing subsystem for data persistence.
 * @author Tomislav
 *
 */

public class DAOException extends RuntimeException {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with two parameters.
	 * @param message Exception message.
	 * @param cause Cause of exception.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor with one parameter.
	 * @param message Exception message.
	 */
	public DAOException(String message) {
		super(message);
	}
}
