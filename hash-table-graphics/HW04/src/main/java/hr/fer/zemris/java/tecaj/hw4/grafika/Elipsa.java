package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa koja predstavlja geometrijski lik elipsu.
 * @author Tomislav
 *
 */

public class Elipsa extends GeometrijskiLik {
	
	/**
	 * Objekt koji sadrzi metode tvornice za stvaranje elipse.
	 */
	public static final StvarateljLika STVARATELJ = new ElipsaStvaratelj();
	
	/**
	 * Polozaj centra elipse na osi apcisa.
	 */
	private int centarX;
	
	/**
	 * Polozaj centra elipse na osi ordinata.
	 */
	private int centarY;
	
	/**
	 * Duljina velike polu-osi elipse.
	 */
	private int velikaPoluos;
	
	/**
	 * Duljina male polu-osi elipse.
	 */
	private int malaPoluos;
	
	/**
	 * Konstruktor za geometrijski lik elipsa.
	 * @param centarX Polozaj centra elipse na osi apcisa.
	 * @param centarY Polozaj centra elipse na osi ordinata.
	 * @param velikaPoluos Duljina velike polu-osi elipse.
	 * @param malaPoluos Duljina male polu-osi elipse.
	 */
	
	public Elipsa(int centarX, int centarY, int velikaPoluos, int malaPoluos) {
		this.centarX = centarX;
		this.centarY = centarY;
		this.velikaPoluos = velikaPoluos;
		this.malaPoluos = malaPoluos;
	}
	
	/**
	 * Metoda dohvaca vrijednost polozaja centra elsipse na osi apcisa.
	 * @return Vrijednost polozaja centra elsipse na osi apcisa.
	 */
	
	public int getCentarX() {
		return centarX;
	}

	/**
	 * Metoda dohvaca vrijednost polozaja centra elsipse na osi ordinata.
	 * @return Vrijednost polozaja centra elsipse na osi ordinata.
	 */
	
	public int getCentarY() {
		return centarY;
	}
	
	/**
	 * Metoda dohvaca vrijednost velike polu-osi elipse.
	 * @return Vrijednost velike polu-osi elipse.
	 */

	public int getVelikaPoluos() {
		return velikaPoluos;
	}
	
	/**
	 * Metoda dohvaca vrijednost male polu-osi elipse.
	 * @return Vrijednost male polu-osi elipse.
	 */

	public int getMalaPoluos() {
		return malaPoluos;
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public boolean sadrziTocku(int x, int y){
		double leftSide = Math.pow(malaPoluos,2)*Math.pow(x-centarX, 2)+
						  Math.pow(velikaPoluos, 2)*Math.pow(y-centarY, 2);
		double rightSide = Math.pow(malaPoluos*velikaPoluos, 2);
		if (leftSide <= rightSide){
			return true;
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
    public void popuniLik(Slika slika){
		//aproksimacija rubnih tocaka elipse
		int sirinaSlike = slika.getSirina();
		int visinaSlike = slika.getVisina();
		int hh = malaPoluos * malaPoluos;
		int ww = velikaPoluos * velikaPoluos;
		int hhww = hh * ww;
		int x0 = velikaPoluos;
		int dx = 0;
		if (centarY < visinaSlike && centarY >= 0){
			for (int x = -velikaPoluos; x <= velikaPoluos && centarX + x < sirinaSlike; x++){
				if (centarX + x >= 0){
					slika.upaliTocku(centarX + x, centarY);
				}
			}
		}
		for (int y = 1; y <= malaPoluos; y++){
			int x1 = x0 - (dx - 1);
			while (x1 > 0){
				if (x1*x1*hh + y*y*ww <= hhww){
					break;
				}
				x1--;
			}
			dx = x0 - x1;
			x0 = x1;
			for (int x = -x0; x <= x0 && (centarX + x) < sirinaSlike; x++){
				if (centarY - y < visinaSlike && centarY + y >= 0 && centarX + x >= 0){
					if (centarY - y >= 0){
						slika.upaliTocku(centarX + x, centarY - y);
					}
					if (centarY + y < visinaSlike){
						slika.upaliTocku(centarX + x, centarY + y);
					}
				}
		    }
		}
	}
	
	/**
	 * Klasa koja sluzi za stvaranje novih geometrijskih likova elipsa.
	 * @author Tomislav
	 *
	 */
	
    private static class ElipsaStvaratelj implements StvarateljLika {
		
    	/**
		 * {@inheritDoc}
		 */
    	
		@Override
		public String nazivLika() {
			return "ELIPSA";
		}
		
		/**
		 * Metoda koja stvara novi geometrijski lik elipsa.
		 * @param parametri Parametri elipse.
		 * Broj parametara koji se ocekuje je 4.
		 * Prvi parametar je polozaj centra elipse na osi apcisa.
		 * Drugi parametar je polozaj centra elipse na osi ordinata.
		 * Treci parametar je duljina velike polu-osi elipse.
		 * Cetvrti parametar je duljina male polu-osi elipse.
		 * Velika i mala polu-os ne smiju biti manji od 0.
		 * @throws IllegalArgumentException U slucaju neispravnog broja parametara.
		 * U slucaju da parametri nisu cijeli brojevi. 
		 * U slucaju da je velika ili mala polu-os manja od 0.
		 */
		
		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			String[] paramPolje = parametri.trim().split("\\s+");
			if (paramPolje.length != 4){
				throw new IllegalArgumentException("Neispravan broj parametara");
			}
			
			int centarX, centarY, velikaPoluos, malaPoluos;
			
			try{
				centarX = Integer.parseInt(paramPolje[0]);
				centarY = Integer.parseInt(paramPolje[1]);
				velikaPoluos = Integer.parseInt(paramPolje[2]);
				malaPoluos = Integer.parseInt(paramPolje[3]);
			}catch(Exception ex){
				throw new IllegalArgumentException("Svi parametri moraju biti prirodni brojevi");
			}
			
			if (velikaPoluos < 0 || malaPoluos < 0){
				throw new IllegalArgumentException("Velika i mala poluos elipse ne smiju biti manje od 0");
			}	
			
			return new Elipsa(centarX, centarY, velikaPoluos, malaPoluos);
		}
	}

}
