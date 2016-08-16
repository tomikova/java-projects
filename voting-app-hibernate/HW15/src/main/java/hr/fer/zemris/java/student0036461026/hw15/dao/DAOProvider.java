package hr.fer.zemris.java.student0036461026.hw15.dao;

import hr.fer.zemris.java.student0036461026.hw15.dao.jpa.JPADAOImpl;

/**
 * Singleton class that knows who is used as service provider for access
 * to subsystem for data persistence.
 * @author Tomislav
 *
 */
public class DAOProvider {

	/**
	 * Provider of access to subsystem for data persistence.
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Method returns provider of access to subsystem for data persistence.
	 * @return Provider of access to subsystem for data persistence.
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}
