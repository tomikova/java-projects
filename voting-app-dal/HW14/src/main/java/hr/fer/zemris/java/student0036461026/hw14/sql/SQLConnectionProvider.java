package hr.fer.zemris.java.student0036461026.hw14.sql;

import java.sql.Connection;

/**
 * Pohrana veze prema bazi podataka u ThreadLocal objekt koji je mapa
 * čiji su ključevi identifikatori dretve koje rade operaciju nad mapom.
 * @author Tomislav
 *
 */
public class SQLConnectionProvider {

	/**
	 * Mapa čiji su ključevi identifikatori dretve koje rade operaciju nad mapom.
	 */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	

	/**
	 * Pohrana veze prema bazi podataka u ThreadLocal objekt.
	 * @param con Veza prema bazi podataka. Ukoliko je predana veza 
	 * jednaka null tada se zapravo postojeća veza u ThreadLocal objektu briše.
	 */
	public static void setConnection(Connection con) {
		if(con==null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}

	/**
	 * Metoda koja dohvaća vezu prema bazi podataka.
	 * @return Veza prema bazi podataka.
	 */
	public static Connection getConnection() {
		return connections.get();
	}
	
}
