package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa koja predstavlja geometrijski lik.
 * @author Tomislav
 *
 */

abstract public class GeometrijskiLik {
	
	/**
	 * Metoda koja provjerava da li tocka slike pripada geometrijskom liku.
	 * @param x Polozaj tocke slike na osi apcisa.
	 * @param y Polozaj tocke slike na osi oordinata.
	 * @return Vrijednost true/false ovisno da li tocka pripada geometrijskom liku.
	 */
	
	public abstract boolean sadrziTocku(int x, int y);
	
	/**
	 * Metoda koja crta geometrijski lik na slici.
	 * @param slika Slika na kojoj ce se iscrtati geometrijski lik.
	 */
	
	public void popuniLik(Slika slika){
		for(int y = 0, visina = slika.getVisina(), sirina = slika.getSirina(); y < visina; y++){
			for(int x = 0; x < sirina; x++){
				if(sadrziTocku(x, y)){
					slika.upaliTocku(x, y);
				}
			}
		}
	}

}
