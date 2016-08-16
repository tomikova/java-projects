package hr.fer.zemris.java.student0036461026.hw14.dao;

import hr.fer.zemris.java.student0036461026.hw14.model.Anketa;
import hr.fer.zemris.java.student0036461026.hw14.model.AnketaOpcija;

import java.util.List;

/**
 * Sučelje koje definira metode koje mora implementirati razred koji će
 * modelirati sloj pristupa podacim i pristupati podsustavu za perzistenciju podataka.
 * @author Tomislav
 *
 */
public interface DAO {

	/**
	 * Metoda koja dohvaća sve dostupne ankete iz perzistentnog izvora podataka.
	 * @return Lista anketa.
	 * @throws DAOException U slučaju pogreške prilikom dohvaćanja anketa.
	 */
	public List<Anketa> dohvatiSveAnkete() throws DAOException;
	
	/**
	 * Metoda koja dohvaća sve moguće opcije koje nudi odabrana anketa.
	 * @param pollID ID ankete.
	 * @param orderBy Način sortiranja opcija ankete.
	 * @return Lista opcija ankete.
	 */
	public List<AnketaOpcija> dohvatiSveOpcijeAnkete(long pollID, String orderBy);
	
	/**
	 * Metoda koja dohvaća željenu anketu.
	 * @param pollID ID ankete.
	 * @return Ukoliko anketa s predanim ID-em postoji vraća anketu, u protivnom vraća null.
	 */
	public Anketa dohvatiAnketu(long pollID);
	
	/**
	 * Metoda koja služi za ažuriranje glasova opcije ankete.
	 * @param id ID opcije ankete.
	 * @param addValue Vrijednost koja se želi nadodati na postojeći broj glasova.
	 */
	public void azurirajOpciju(long id, long addValue);
}
