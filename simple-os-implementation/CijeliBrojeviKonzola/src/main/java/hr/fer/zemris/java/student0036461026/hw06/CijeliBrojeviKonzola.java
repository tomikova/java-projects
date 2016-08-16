package hr.fer.zemris.java.student0036461026.hw06;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Program za testiranje da li je upisani broj paran, neparan i prost.
 * @author Tomislav
 *
 */

public class CijeliBrojeviKonzola {
	
	/**
	 * Metoda koja se poziva na početku programa.
	 * @param args Argumenti komandne linije.
	 * @throws IOException U slučaju greške prilikom učitavanja broja.
	 */
    public static void main( String[] args ) throws IOException
    {
    	BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in)));
    	
    	String line = null;
    	while(true) {
    		System.out.print("Unesite broj> ");
    		line = reader.readLine().trim();
    		if (line.equals("quit")) {
    			break;
    		}
    		if (!line.isEmpty()) {
    			int broj = Integer.parseInt(line);
    			System.out.println("Paran: "+(CijeliBrojevi.jeParan(broj) ? "DA" : "NE")+
    					", neparan: "+(CijeliBrojevi.jeNeparan(broj) ? "DA" : "NE")+
    					", prim: "+(CijeliBrojevi.jeProst(broj) ? "DA" : "NE"));
    		}
    	}
    	System.out.println("Hvala na druženju.");
    }
}
