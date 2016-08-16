package hr.fer.zemris.java.student0036461026.hw14.model;

/**
 * Razred koji modelira anketu.
 * @author Tomislav
 *
 */
public class Anketa {
	
	/**
	 * ID ankete.
	 */
	private long id;
	
	/**
	 * Naziv ankete.
	 */
	private String naziv;
	
	/**
	 * Poruka uz anketu.
	 */
	private String poruka;

	/**
	 * Konstruktor razreda ankete.
	 * @param id ID ankete.
	 * @param naziv Naziv ankete.
	 * @param poruka Poruka uz anketu.
	 */
	public Anketa(long id, String naziv, String poruka) {
		this.id = id;
		this.naziv = naziv;
		this.poruka = poruka;
	}

	/**
	 * Metoda dohvaća ID ankete.
	 * @return ID ankete.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Metoda dohvaća naziv ankete.
	 * @return Naziv ankete.
	 */
	public String getNaziv() {
		return naziv;
	}

	/**
	 * Metoda dohvaća poruku ankete.
	 * @return Poruka uz anketu.
	 */
	public String getPoruka() {
		return poruka;
	}
	
}
