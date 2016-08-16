package hr.fer.zemris.java.student0036461026.hw14.model;

/**
 * Razred koje modelira jednu opciju ankete.
 * @author Tomislav
 *
 */
public  class AnketaOpcija {
	/**
	 * ID opcije ankete.
	 */
	private long id;
	/**
	 * Naziv opcije ankete.
	 */
	private String name;
	/**
	 * Link uz opciju ankete.
	 */
	private String link;
	/**
	 * Broj glasova za opciju ankete.
	 */
	private long brGlasova;
	
	/**
	 * ID ankete kojoj pripada opcija.
	 */
	private long anketaID; 
	
	/**
	 * Osnovni konstruktor s četiri parametra.
	 * @param id ID opcije ankete.
	 * @param name Naziv opcije ankete.
	 * @param link Link uz opciju ankete.
	 * @param brGlasova Broj glasova za opciju ankete.
	 * @param anketaID ID ankete kojoj pripada opcija.
	 */
	public AnketaOpcija(long id, String name, String link, long brGlasova, long anketaID) {
		super();
		this.id = id;
		this.name = name;
		this.link = link;
		this.brGlasova = brGlasova;
		this.anketaID = anketaID;
	}

	/**
	 * Metoda koja vraća ID opcije ankete.
	 * @return ID opcije ankete.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Metoda koja vraća naziv opcije ankete.
	 * @return Naziv opcije ankete.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Metoda koja vraća link uz opciju ankete.
	 * @return Link uz opciju ankete.
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Metoda koja vraća broj glasova za opciju ankete.
	 * @return Broj glasova za opciju ankete.
	 */
	public long getBrGlasova() {
		return brGlasova;
	}

	/**
	 * Metoda koja vraća ID ankete kojoj pripada opcija.
	 * @return ID ankete kojoj pripada opcija.
	 */
	public long getAnketaID() {
		return anketaID;
	}
}