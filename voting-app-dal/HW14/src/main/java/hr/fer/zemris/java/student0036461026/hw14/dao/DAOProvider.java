package hr.fer.zemris.java.student0036461026.hw14.dao;

import hr.fer.zemris.java.student0036461026.hw14.sql.SQLDAO;

/**
 * Singleton razred koji zna koga treba vratiti kao pružatelja
 * usluge pristupa podsustavu za perzistenciju podataka.
 * @author Tomislav
 *
 */
public class DAOProvider {

	/**
	 * Pružatelj usluge pristupa podsustavu za perzistenciju podataka.
	 */
	private static DAO dao = new SQLDAO();
	
	/**
	 * Metoda vraća pružatelja usluge pristupa podsustavu za perzistenciju podataka.
	 * @return Pružatelj usluge pristupa podsustavu za perzistenciju podataka.
	 */
	public static DAO getDao() {
		return dao;
	}
	
}
