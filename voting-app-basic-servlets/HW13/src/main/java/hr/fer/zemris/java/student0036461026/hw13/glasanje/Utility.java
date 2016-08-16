package hr.fer.zemris.java.student0036461026.hw13.glasanje;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

/**
 * Klasa čija je zadaća učitavanje podataka o bendovima i rezultatima glasanja iz
 * tekstovnih datoteka te njihova pohrana u odgovarajuću strukturu podataka.
 * @author Tomislav
 *
 */
public class Utility {

	/**
	 * Metoda koja učitava podatke o bendovima iz tekstovne datoteke.
	 * @param datotekaBendovi Lokacija tekstovne datoteke s bendovima.
	 * @return Mapa koja sadrži podatke o bendovima gdje je ID benda ključ mape, a
	 * vrijednost je Bend objekt koji sadrži podatke o pojedinom bendu.
	 */
	public static Map<Integer, Bend> ucitajBendove(String datotekaBendovi) {
		return ucitajBendoveIGlasove(datotekaBendovi, null);
	}

	/**
	 * Metoda koja učitava podatke o bendovima i rezultatima glasanja iz tekstovne datoteke.
	 * @param datotekaBendovi Lokacija tekstovne datoteke s bendovima.
	 * @param datotekaGlasovi Lokacija tekstovne datoteke s rezultatima glasanja.
	 * @return Mapa koja sadrži podatke o bendovima gdje je ID benda ključ mape, a
	 * vrijednost je Bend objekt koji sadrži podatke o pojedinom bendu.
	 */
	public static Map<Integer, Bend> ucitajBendoveIGlasove(String datotekaBendovi, String datotekaGlasovi) {
		try {
			String[] definicijeBendovi = Files.readAllLines(Paths.get(datotekaBendovi),
					StandardCharsets. UTF_8).toArray(new String[0]);

			Map<Integer, Bend> bendovi = new TreeMap<Integer, Bend>();

			for (String bend : definicijeBendovi) {
				String[] params = bend.split("\t");
				bendovi.put(Integer.valueOf(params[0]), 
						new Bend(Integer.valueOf(params[0]), params[1], params[2]));
			}

			if (datotekaGlasovi != null) {
				String[] definicijeGlasovi = Files.readAllLines(Paths.get(datotekaGlasovi),
						StandardCharsets. UTF_8).toArray(new String[0]);
				for (String glas : definicijeGlasovi) {
					String[] params = glas.split("\t");
					Integer id = Integer.valueOf(params[0]);
					Integer glasovi = Integer.valueOf(params[1]);
					if (bendovi.containsKey(id)) {
						Bend bend = bendovi.get(id);
						bend.brGlasova = glasovi;
					}
				}
			}

			return bendovi;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Klasa u kojoj se pohranjuju podaci o bendu.
	 * @author Tomislav
	 *
	 */
	public static class Bend {
		/**
		 * Id benda.
		 */
		private int id;
		/**
		 * Naziv benda.
		 */
		private String name;
		/**
		 * Link o bendu.
		 */
		private String link;
		/**
		 * Broj glasova za bend.
		 */
		private int brGlasova;

		/**
		 * Osnovni konstruktor s tri parametra.
		 * @param id Id benda.
		 * @param name Naziv benda.
		 * @param link Link o bendu.
		 */
		public Bend(int id, String name, String link) {
			super();
			this.id = id;
			this.name = name;
			this.link = link;
			this.brGlasova = 0;
		}

		/**
		 * Metoda koja vraća Id benda.
		 * @return Id benda.
		 */
		public int getId() {
			return id;
		}

		/**
		 * Metoda koja vraća naziv benda.
		 * @return Naziv benda.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Metoda koja vraća link o bendu.
		 * @return Link o bendu.
		 */
		public String getLink() {
			return link;
		}

		/**
		 * Metoda koja vraća broj glasova za bend.
		 * @return Broj glasova za bend.
		 */
		public int getBrGlasova() {
			return brGlasova;
		}
	}
}
