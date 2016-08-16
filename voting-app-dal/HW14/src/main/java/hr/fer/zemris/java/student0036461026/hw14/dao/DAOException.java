package hr.fer.zemris.java.student0036461026.hw14.dao;

/**
 * Razred pogreške koja se može dogoditi prilikom pristupa
 *  podsustavu za perzistenciju podataka.
 * @author Tomislav
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor s četiri parametra.
	 * @param message Poruka iznimke.
	 * @param cause Razlog iznimke.
	 * @param enableSuppression Omogućavanje suzbijanja.
	 * @param writableStackTrace Omogućavanje ispisa pogreške.
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Konstruktor s dva parametra.
	 * @param message Poruka iznimke.
	 * @param cause Razlog iznimke.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Konstruktor s jednim parametrom.
	 * @param message Poruka iznimke.
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Konstruktor s jednim parametrom.
	 * @param cause Razlog iznimke.
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}
