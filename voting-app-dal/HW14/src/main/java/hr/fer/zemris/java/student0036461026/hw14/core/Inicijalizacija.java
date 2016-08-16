package hr.fer.zemris.java.student0036461026.hw14.core;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Klasa čiji je zadatak prilikom pokretanja aplikacije uspostaviti vezu
 * s bazom podataka na osnovu podataka iz konfiguracijske datoteke te storiti bazen veza.
 * Provjerava postojanje odgovarajućih tablica u bazi podataka. Ako tablice ne postoje 
 * stvaraju se. Također ako su tablice prazne pune se podacima.
 * @author Tomislav
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	/**
	 * Metoda stvara bazen veza te vrši inicijalizaciju potrebnih tablica baze 
	 * podataka te dojavljuje uspješno pokretanje aplikacije.
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		String propertiesRoot = sce.getServletContext().getRealPath("/WEB-INF");
		String connectionURL = Utility.getConnectionURL(propertiesRoot);

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {	
			throw new RuntimeException("Pogreška pri inicijalizaciji bazena konekcija");
		}
		cpds.setJdbcUrl(connectionURL);
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		Connection con = null;
		try {
			con = cpds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("Baza podataka nije dostupna");
		}

		String root = sce.getServletContext().getRealPath("/WEB-INF");
		Utility.createTables(con);
		Utility.populateTable(con, "Polls", root);

		try {
			con.close(); 
		} catch(SQLException ignorable) {};


		System.out.println("Aplikacija je pokrenuta");
		sce.getServletContext().setAttribute("time", System.currentTimeMillis());
	}

	/**
	 * Metoda uništava bazen veze i dojavljuje završetak aplikacije.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = 
				(ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Aplikacija završava");
	}

}