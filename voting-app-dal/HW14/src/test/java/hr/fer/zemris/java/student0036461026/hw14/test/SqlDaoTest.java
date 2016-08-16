package hr.fer.zemris.java.student0036461026.hw14.test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import hr.fer.zemris.java.student0036461026.hw14.core.Utility;
import hr.fer.zemris.java.student0036461026.hw14.dao.DAOException;
import hr.fer.zemris.java.student0036461026.hw14.dao.DAOProvider;
import hr.fer.zemris.java.student0036461026.hw14.model.Anketa;
import hr.fer.zemris.java.student0036461026.hw14.model.AnketaOpcija;
import hr.fer.zemris.java.student0036461026.hw14.sql.SQLConnectionProvider;

import org.junit.Assert;
import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Unit testovi za SQLDAO implementaciju.
 * @author Tomislav
 *
 */
public class SqlDaoTest {
	
	/**
	 * Označava da je stvoren bazen veza i odgovarajuće tablice u bazi podataka.
	 */
	private static boolean initialized = false;
	/**
	 * Bazen veza.
	 */
	private static ComboPooledDataSource cpds;
	
	/**
	 * Metoda koja stvara bazen veza i odgovarajuće tablice u bazi podataka.
	 */
	private void initialize() {
		String root = "web/aplikacija5/WEB-INF";
		String connectionURL = Utility.getConnectionURL(root);
		cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {	
			throw new RuntimeException("Pogreška pri inicijalizaciji bazena konekcija");
		}
		cpds.setJdbcUrl(connectionURL);
		Connection con = getConnection();
		Utility.createTables(con);
		Utility.populateTable(con, "Polls", root);
		initialized = true;
		closeConnection(con);
	}
	
	/**
	 * Metoda dohvaća vezu prema bazi podataka.
	 * @return Veza prema bazi podataka.
	 */
	private Connection getConnection() {
		Connection con = null;
		try {
			con = cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	/**
	 * Metoda prekida vezu prema bazi podataka.
	 * @param con Veza prema bazi podataka koja će biti prekinuta.
	 */
	private void closeConnection(Connection con) {
		SQLConnectionProvider.setConnection(null);
		try {
			con.close(); 
		} catch(SQLException ignorable) {};
	}
	
	@Test
	public void dohvatiSveAnketeTest() {
		if(!initialized) {
			initialize();
		}
		Connection con = getConnection();
		SQLConnectionProvider.setConnection(con);
		List<Anketa> ankete = DAOProvider.getDao().dohvatiSveAnkete();
		Assert.assertEquals(2, ankete.size());
		closeConnection(con);
	}
	
	@Test
	public void dohvatiAnketuTest() {
		if(!initialized) {
			initialize();
		}
		Connection con = getConnection();
		SQLConnectionProvider.setConnection(con);
		Anketa anketa = DAOProvider.getDao().dohvatiAnketu(1);
		Assert.assertEquals("Glasanje za omiljeni bend", anketa.getNaziv());
		Assert.assertEquals("Od sljedećih bendova, koji Vam je bend najdraži?"
				+ " Kliknite na link kako biste glasali!", anketa.getPoruka());
		closeConnection(con);
	}
	
	@Test
	public void dohvatiAnketuIdNePostojiTest() {
		if(!initialized) {
			initialize();
		}
		Connection con = getConnection();
		SQLConnectionProvider.setConnection(con);
		Anketa anketa = DAOProvider.getDao().dohvatiAnketu(5);
		Assert.assertEquals(null, anketa);
		closeConnection(con);
	}
	
	@Test
	public void dohvatiSveOpcijeAnketePoredajPoIDTest() {
		if(!initialized) {
			initialize();
		}
		Connection con = getConnection();
		SQLConnectionProvider.setConnection(con);
		List<AnketaOpcija> opcije = DAOProvider.getDao().dohvatiSveOpcijeAnkete(1, "id");
		Assert.assertEquals(7, opcije.size());
		Assert.assertTrue(opcije.get(0).getId() < opcije.get(1).getId());
		closeConnection(con);
	}
	
	@Test
	public void dohvatiSveOpcijeAnketePoredajPoGlasovimaTest() {
		if(!initialized) {
			initialize();
		}
		Connection con = getConnection();
		SQLConnectionProvider.setConnection(con);
		List<AnketaOpcija> opcije = DAOProvider.getDao().dohvatiSveOpcijeAnkete(1, "votesCount DESC");
		Assert.assertEquals(7, opcije.size());
		Assert.assertTrue(opcije.get(0).getBrGlasova() >= opcije.get(1).getBrGlasova());
		closeConnection(con);
	}
	
	@Test(expected=DAOException.class)
	public void dohvatiSveOpcijeAnketePoredajAtributNePostojiTest() {
		if(!initialized) {
			initialize();
		}
		Connection con = getConnection();
		SQLConnectionProvider.setConnection(con);
		try {
			DAOProvider.getDao().dohvatiSveOpcijeAnkete(1, "pogresno");
		} catch (Exception e) {
			closeConnection(con);
			throw e;
		}
	}
	
	@Test
	public void azurirajOpcijuAnketeTest() {
		if(!initialized) {
			initialize();
		}
		Connection con = getConnection();
		SQLConnectionProvider.setConnection(con);
		List<AnketaOpcija> opcije = DAOProvider.getDao().dohvatiSveOpcijeAnkete(1, "id");
		AnketaOpcija opcija = opcije.get(0);
		long opcijaID = opcija.getId();
		long brojGlasova = opcija.getBrGlasova();
		DAOProvider.getDao().azurirajOpciju(opcijaID, 5);
		opcije = DAOProvider.getDao().dohvatiSveOpcijeAnkete(1, "id");
		AnketaOpcija azuriranaOpcija = opcije.get(0);
		long azuriranaOpcijaID = azuriranaOpcija.getId();
		long azuriraniBrojGlasova = azuriranaOpcija.getBrGlasova();
		Assert.assertEquals(opcijaID, azuriranaOpcijaID);
		Assert.assertTrue(azuriraniBrojGlasova-brojGlasova == 5);
		closeConnection(con);
	}
	
}
