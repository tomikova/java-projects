package hr.fer.zemris.java.tecaj.hw4.grafika.demo;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.tecaj.hw4.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw4.grafika.*;
import hr.fer.zemris.java.tecaj_3.prikaz.PrikaznikSlike;
import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Program za iscrtavanje likova na slici.
 * Ocekivani broj parametara je 3.
 * Prvi parametar je put do datoteke koja sadrzi podatke o 
 * geometrijskim likovima za iscrtavanje u ocekivanom formatu.
 * Drugi parametar je sirina slike.
 * Treci parametar je visina slike.
 * Sirina i visina slike ne smiju biti manji od 0.
 * @author Tomislav
 *
 */

public class Crtalo {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * @param args Argumenti iz naredbenog retka.
	 * @throws IOException U slucaju da se dogodila greska prilikom citanja datoteke.
	 */
	public static void main(String[] args) throws IOException {
		
		if (args.length != 3){
			throw new IllegalArgumentException("Neispravan broj parametara");
		}
		
		
		int sirinaSlike = Integer.parseInt(args[1]);
		int visinaSlike = Integer.parseInt(args[2]);
		
		if (sirinaSlike < 0 || visinaSlike < 0){
			throw new IllegalArgumentException("Neispravne dimenzije slike");
		}
		
		Class<?>[] razredi = new Class<?>[]{Linija.class, Pravokutnik.class, 
											Kvadrat.class, Elipsa.class, Kruznica.class};
		
		SimpleHashtable stvaratelji = podesi(razredi);
		String[] definicije = Files.readAllLines(Paths.get(args[0]),
				StandardCharsets. UTF_8).toArray(new String[0]);
		
		GeometrijskiLik[] likovi = new GeometrijskiLik[definicije.length];
		
		int index = 0;
		for (String def : definicije){
			String definicija = def.trim();
			String[] parametri = definicija.split("\\s+");
			String lik = parametri[0];
			String ostatak = "";
			for (int i = 1; i < parametri.length; i++ ){
				ostatak+= parametri[i]+" ";
			}
			StvarateljLika stvaratelj = (StvarateljLika)stvaratelji.get(lik);
			try{
				likovi[index++] = stvaratelj.stvoriIzStringa(ostatak);
			}catch(IllegalArgumentException ex){
				System.out.println(ex.getMessage());
				System.out.println("Greška se u kodu dogodila u: "+ex.getStackTrace()[0]);
				System.out.println("Greška u datoteci u liniji: "+index);
				System.out.println(def);
				System.out.println("\nSlika neće biti iscrtana!");
				System.exit(0);
			}
		}
		
		Slika slika = new Slika(sirinaSlike, visinaSlike);
		
		for (GeometrijskiLik lik : likovi){
			lik.popuniLik(slika);
		}
		
		PrikaznikSlike.prikaziSliku(slika, 1);
	}
	
	/**
	 * Metoda koja stvara hash tablicu stvaratelja geometrijskih likova.
	 * @param razredi Klase geometrijskih likova koji se crtaju na slici.
	 * @return Hash tablica s kljucem koji je naziv geometrijskog lika i vrijednosti koja
	 * je objekt koji sadrzi metode tvornice za geometrijski lik.
	 */
	private static SimpleHashtable podesi(Class<?>[] razredi) {
		SimpleHashtable stvaratelji = new SimpleHashtable();
		for(Class<?> razred : razredi){
			try {
				Field field = razred.getDeclaredField("STVARATELJ");
				StvarateljLika stvaratelj = (StvarateljLika)field.get(null);
				stvaratelji.put(stvaratelj.nazivLika(), stvaratelj);
			} catch(Exception ex){
				throw new IllegalArgumentException("Nije moguće doći do stvaratelja za razred "+
						                   razred.getName()+".", ex);
			}
		}
		return stvaratelji;
		
	}

}
