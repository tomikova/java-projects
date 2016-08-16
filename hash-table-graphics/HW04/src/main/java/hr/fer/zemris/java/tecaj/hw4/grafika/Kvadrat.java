package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa koja predstavlja geometrijski lik kvadrat.
 * @author Tomislav
 *
 */

public class Kvadrat extends Pravokutnik {
	
	/**
	 * Objekt koji sadrzi metode tvornice za stvaranje kvadrata.
	 */
	public static final StvarateljLika STVARATELJ = new KvadratStvaratelj();
	
	/**
	 * Polozaj gornjeg-lijevog vrha kvadrata na osi apcisa.
	 */
	private int vrhX;
	
	/**
	 * Polozaj gornjeg-lijevog vrha kvadrata na osi ordinata.
	 */
	private int vrhY;
	
	/**
	 * Duljina stranice kvadrata.
	 */
	private int duljinaStranice;
	
	/**
	 * Konstruktor za geometrijski lik kvadrat.
	 * @param vrhX Polozaj gornjeg-lijevog vrha kvadrata na osi apcisa.
	 * @param vrhY Polozaj gornjeg-lijevog vrha kvadrata na osi ordinata.
	 * @param duljinaStranice Duljina stranice kvadrata.
	 */
	public Kvadrat(int vrhX, int vrhY, int duljinaStranice) {
		super(vrhX, vrhY, duljinaStranice, duljinaStranice);
		this.vrhX = vrhX;
		this.vrhY = vrhY;
		this.duljinaStranice = duljinaStranice;
	}
	
	/**
	 * Metoda koja dohvaca vrijednost gornjeg-lijevog vrha kvadrata na osi apcisa.
	 * @return Vrijednost gornjeg-lijevog vrha kvadrata na osi apcisa.
	 */
	public int getVrhX() {
		return vrhX;
	}

	/**
	 * Metoda koja dohvaca vrijednost gornjeg-lijevog vrha kvadrata na osi.
	 * @return Vrijednost gornjeg-lijevog vrha kvadrata na osi ordinata.
	 */
	public int getVrhY() {
		return vrhY;
	}
	
	/**
	 * Metoda koja dohvaca vrijednost duljine stranice kvadrata.
	 * @return Vrijednost duljine stranice kvadrata.
	 */
	public int getDuljinaStranice() {
		return duljinaStranice;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public boolean sadrziTocku(int x, int y){
		if(x < vrhX || x >= vrhX+duljinaStranice) return false;
		if(y < vrhY || y >= vrhY+duljinaStranice) return false;
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
    public void popuniLik(Slika slika){
		int sirinaSlike = slika.getSirina();
		int visinaSlike = slika.getVisina();
		for(int y = (vrhY < 0) ? 0 : vrhY, ye = vrhY+duljinaStranice; y < ye && y < visinaSlike && ye > 0; y++){
			for(int x = (vrhX < 0) ? 0 : vrhX, xe = vrhX+duljinaStranice; x < xe && x < sirinaSlike && xe > 0; x++){
				slika.upaliTocku(x, y);
			}
		}
	}
	
	/**
	 * Klasa koja sluzi za stvaranje novih geometrijskih likova kvadrata.
	 * @author Tomislav
	 *
	 */
	
    private static class KvadratStvaratelj implements StvarateljLika {
		
    	/**
    	 * {@inheritDoc}
    	 */
    	
		@Override
		public String nazivLika() {
			return "KVADRAT";
		}
		
		/**
		 * Metoda koja stvara novi geometrijski lik kvadrat.
		 * @param parametri Parametri kvadrata.
		 * Broj parametara koji se ocekuje je 3.
		 * Prvi parametar je polozaj gornjeg-lijevog vrha kvadrata na osi apcisa.
		 * Drugi parametar je polozaj gornjeg-lijevog vrha kvadrata na osi ordinata.
		 * Treci parametar je duljina stranice kvadrata.
		 * Duljina stranice kvadrata ne smije biti manja od 0.
		 * @throws IllegalArgumentException U slucaju neispravnog broja parametara.
		 * U slucaju da parametri nisu cijeli brojevi. 
		 * U slucaju da je duljina stranice kvadrata manja od 0.
		 */
		
		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			String[] paramPolje = parametri.trim().split("\\s+");
			if (paramPolje.length != 3){
				throw new IllegalArgumentException("Neispravan broj parametara");
			}
			
			int vrhX, vrhY, duljinaStranice;
			
			try{
				vrhX = Integer.parseInt(paramPolje[0]);
				vrhY = Integer.parseInt(paramPolje[1]);
				duljinaStranice = Integer.parseInt(paramPolje[2]);
			}catch(Exception ex){
				throw new IllegalArgumentException("Svi parametri moraju biti prirodni brojevi");
			}
			if (duljinaStranice < 0){
				throw new IllegalArgumentException("Duljina stranice kvadrata ne smije biti manja od 0");
			}
			
			return new Kvadrat(vrhX, vrhY, duljinaStranice);
		}
	}

}
