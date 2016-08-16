package hr.fer.zemris.java.student0036461026.hw14.core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Klasa koja pruža metode za:
 * 						- učitavanje konfiguracijske datoteke baze podataka i dohvaćanje URL-a veze.
 * 						- stvaranje i punjenje podacima odgovarajućih tablica u bazi podataka.
 * @author Tomislav
 *
 */
public class Utility {

	/**
	 * SQL create naredba za stvaranje tablice Polls.
	 */
	private static final String TABLE_POLLS_DEFINITION = 
			"CREATE TABLE Polls "
					+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
					+ " title VARCHAR(150) NOT NULL,"
					+ " message CLOB(2048) NOT NULL)";

	/**
	 * SQL create naredba za stvaranje tablice PollOptions.
	 */
	private static final String TABLE_POLLOPTIONS_DEFINITION = 
			"CREATE TABLE PollOptions "
					+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
					+ " optionTitle VARCHAR(100) NOT NULL,"
					+ " optionLink VARCHAR(150) NOT NULL,"
					+ " pollID BIGINT, votesCount BIGINT,"
					+ " FOREIGN KEY (pollID) REFERENCES Polls(id))";
	
	/**
	 * Naziv referencirane tablice na tablicu Polls.
	 */
	private static String referencedTableName = "PollOptions";
	
	/**
	 * Metoda učitava konfiguracijsku datoteku baze podataka i stvara URL veze na bazu podataka.
	 * @param propertiesRoot Direktorij u kojem se nalazi konfiguracijska datoteka baze podataka.
	 * @return URL veze na bazu podataka.
	 */
	public static String getConnectionURL(String propertiesRoot) {
		Properties dbConfig = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(propertiesRoot+"/dbsettings.properties");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Nije pronađena konfiguracijska datoteka");
		}
		try {
			dbConfig.load(is);
		} catch (IOException e) {
			throw new RuntimeException("Neuspjelo učitavanje konfiguracijske datoteke");
		}
		String host = dbConfig.getProperty("host");
		String port = dbConfig.getProperty("port");
		String dbName = dbConfig.getProperty("name");
		String user = dbConfig.getProperty("user");
		String password = dbConfig.getProperty("password");

		String connectionURL = "jdbc:derby://"+host+":"+port+"/" 
				+ dbName + ";user="+user+";password="+password;
		return connectionURL;
	}

	/**
	 * Metoda stvara tablice Polls i PollOptions u bazi podataka.
	 * @param con Veza prema bazi podataka.
	 */
	public static void createTables(Connection con) {
		createTable(con, "Polls", TABLE_POLLS_DEFINITION);
		createTable(con, "PollOptions", TABLE_POLLOPTIONS_DEFINITION);
	}

	/**
	 * Metoda stvara tablicu u bazi podataka ako ona već ne postoji.
	 * @param con Veza prema bazi podataka.
	 * @param tableName Naziv tablice.
	 * @param createStatement SQL create naredba za stvaranje tablice.
	 */
	public static void createTable(Connection con, String tableName, String createStatement) {
		try {
			if(con!=null) {
				DatabaseMetaData dbmd = con.getMetaData();
				try (ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(),null)) {
					if(!rs.next()) {
						try (PreparedStatement pst = con.prepareStatement(createStatement)) {
							pst.executeUpdate();
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Greška prilikom postavljanja tablica");
		}
	}

	/**
	 * Metoda koja puni tablicu podacima.
	 * @param con Veza prema bazi podataka.
	 * @param tableName Naziv tablice.
	 * @param root Direktorij u kojem se nalazi datoteka s podacima koja mora biti
	 * jednakog naziva kao i tablica koja se puni, pisana malim slovima.
	 */
	public static void populateTable(Connection con, String tableName, String root) {
		try (PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM "+tableName)) {
			try (ResultSet rset = pst.executeQuery()) {
				rset.next();
				int count = rset.getInt(1);
				if (count == 0) {
					try (InputStream is = new FileInputStream(root+"/"+tableName.toLowerCase()+".txt")) {
						try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
							if (is!=null) {
								try (PreparedStatement pst2 = con.prepareStatement
										("INSERT INTO "+tableName+" (title, message) values (?,?)",
												Statement.RETURN_GENERATED_KEYS)) {
									String str = null;
									List<Long> pollIDs = new ArrayList<>();
									while ((str = reader.readLine()) != null) {
										String[] params = str.split("\t");
										pst2.setString(1, params[0]);
										pst2.setString(2, params[1]);
										pst2.executeUpdate();
										try (ResultSet rset2 = pst2.getGeneratedKeys()) {
											if (rset2 != null && rset2.next()) {
												Long newID = rset2.getLong(1);
												pollIDs.add(newID);
											}
										}
									}
									populateReferencedTable(con,referencedTableName, root, pollIDs);
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda koja stvara tablicu koja se referencira na drugu tablicu. 
	 * @param con Veza prema bazi podataka.
	 * @param tableName Naziv tablice.
	 * @param root Direktorij u kojem se nalazi datoteka s podacima koja mora biti
	 * jednakog naziva kao i tablica koja se puni, pisana malim slovima.
	 * @param pollIds Lista primarnih ključeva tablice na koju se referencira.
	 */
	public static void populateReferencedTable(Connection con, String tableName, 
			String root, List<Long> pollIds) {
		List<Integer> toAdd = new ArrayList<>();
		for (int i = 0; i < pollIds.size(); i++) {
			try (PreparedStatement pst = con.prepareStatement
					("SELECT COUNT(pollID) FROM "+tableName+" WHERE pollID = "+pollIds.get(i))) {
				try (ResultSet rset = pst.executeQuery()) {
					rset.next();
					int count = rset.getInt(1);
					if (count == 0) {
						toAdd.add(i+1);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try (InputStream is = new FileInputStream(root+"/"+tableName.toLowerCase()+".txt")) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
				if (is != null) {
					String str = null;
					while ((str = reader.readLine()) != null) {
						String[] params = str.split("\t");
						Integer rowID = Integer.valueOf(params[0]);
						if (toAdd.contains(rowID)) {
							String optionTitle = params[1];
							String optionLink = params[2];
							Long pollID = pollIds.get(rowID-1);
							Long votesCount = Long.parseLong(params[3]);
							try (PreparedStatement pst = con.prepareStatement
									("INSERT INTO "+tableName+" (optionTitle, optionLink, pollID, votesCount)"
											+ " values (?,?,?,?)")) {
								pst.setString(1, optionTitle);
								pst.setString(2, optionLink);
								pst.setLong(3, pollID);
								pst.setLong(4, votesCount);
								pst.executeUpdate();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
