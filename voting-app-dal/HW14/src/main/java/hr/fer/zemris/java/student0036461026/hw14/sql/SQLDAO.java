package hr.fer.zemris.java.student0036461026.hw14.sql;

import hr.fer.zemris.java.student0036461026.hw14.dao.DAO;
import hr.fer.zemris.java.student0036461026.hw14.dao.DAOException;
import hr.fer.zemris.java.student0036461026.hw14.model.Anketa;
import hr.fer.zemris.java.student0036461026.hw14.model.AnketaOpcija;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna
 * implementacija očekuje da joj veza prema bazi podataka stoji na raspolaganju 
 * preko {@link SQLConnectionProvider} razreda.
 * @author Tomislav
 *
 */
public class SQLDAO implements DAO {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Anketa> dohvatiSveAnkete() throws DAOException {
		List<Anketa> ankete = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		try (PreparedStatement pst = con.prepareStatement
				("SELECT id, title, message FROM Polls ORDER BY id")) {
			try (ResultSet rs = pst.executeQuery()) {
				while(rs!=null && rs.next()) {
					Long id = rs.getLong(1);
					String naziv = rs.getString(2);
					String poruka = rs.getString(3);
					Anketa anketa = new Anketa(id, naziv, poruka);
					ankete.add(anketa);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Greška prilikom dohvaćanja anketa");
		}
		return ankete;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Anketa dohvatiAnketu(long pollId) throws DAOException {
		Anketa anketa = null;
		Connection con = SQLConnectionProvider.getConnection();
		try (PreparedStatement pst = con.prepareStatement
				("SELECT id, title, message FROM Polls WHERE id=?")) {
			pst.setLong(1, Long.valueOf(pollId));
			try (ResultSet rs = pst.executeQuery()) {
				while(rs!=null && rs.next()) {
					Long id = rs.getLong(1);
					String naziv = rs.getString(2);
					String poruka = rs.getString(3);
					anketa = new Anketa(id, naziv, poruka);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Greška prilikom dohvaćanja anketa");
		}
		return anketa;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<AnketaOpcija> dohvatiSveOpcijeAnkete(long pollID, String orderBy) {
		List<AnketaOpcija> opcije = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		try (PreparedStatement pst = con.prepareStatement
				("SELECT id, optionTitle, optionLink, votesCount"
						+ " FROM PollOptions WHERE pollID=? ORDER BY "+orderBy)) {
			pst.setLong(1, Long.valueOf(pollID));
			try (ResultSet rs = pst.executeQuery()) {
				while(rs!=null && rs.next()) {
					Long id = rs.getLong(1);
					String naziv = rs.getString(2);
					String link = rs.getString(3);
					Long brGlasova = rs.getLong(4);
					AnketaOpcija opcija = new AnketaOpcija(id, naziv, link, brGlasova, pollID);
					opcije.add(opcija);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Greška prilikom dohvaćanja opcija ankete");
		}
		return opcije;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void azurirajOpciju(long id, long addValue) {
		Connection con = SQLConnectionProvider.getConnection();
		try (PreparedStatement pst = con.prepareStatement
				("SELECT votesCount FROM PollOptions WHERE id=?")) {
			pst.setLong(1, Long.valueOf(id));
			try (ResultSet rs = pst.executeQuery()) {
				while(rs!=null && rs.next()) {
					Long brGlasova = rs.getLong(1);
					try (PreparedStatement pst2 = con.prepareStatement
							("UPDATE PollOptions SET votesCount=? WHERE id=?")) {
						pst2.setLong(1, Long.valueOf(brGlasova+addValue));
						pst2.setLong(2, Long.valueOf(id));
						pst2.executeUpdate();
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Greška prilikom ažuriranja glasova");
		}
	}
}
