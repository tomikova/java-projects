package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa koja predstavlja geometrijski lik pravokutnik.
 * @author Tomislav
 *
 */

public class Pravokutnik extends GeometrijskiLik {
	
	/**
	 * Objekt koji sadrzi metode tvornice za stvaranje pravokutnika.
	 */
	public static final StvarateljLika STVARATELJ = new PravokutnikStvaratelj();
	
	/**
	 * Polozaj gornjeg-lijevog vrha pravokutnika na osi apcisa.
	 */
	private int vrhX;
	
	/**
	 * Polozaj gornjeg-lijevog vrha pravokutnika na osi ordinata.
	 */
	private int vrhY;
	
	/**
	 * Sirina pravokutnika.
	 */
	private int sirina;
	
	/**
	 * Visina pravokutnika.
	 */
	private int visina;
	
	/**
	 * Konstruktor za geometrijski lik pravokutnik.
	 * @param vrhX Polozaj gornjeg-lijevog vrha pravokutnika na osi apcisa.
	 * @param vrhY Polozaj gornjeg-lijevog vrha pravokutnika na osi ordinata.
	 * @param sirina Sirina pravokutnika.
	 * @param visina Visina pravokutnika.
	 */
	
	public Pravokutnik(int vrhX, int vrhY, int sirina, int visina) {
		this.vrhX = vrhX;
		this.vrhY = vrhY;
		this.sirina = sirina;
		this.visina = visina;
	}
	
	/**
	 * Metoda koja dohvaca vrijednost gornjeg-lijevog vrha pravokutnika na osi apcisa.
	 * @return Vrijednost gornjeg-lijevog vrha pravokutnika na osi apcisa.
	 */
	
	public int getVrhX() {
		return vrhX;
	}

	/**
	 * Metoda koja dohvaca vrijednost gornjeg-lijevog vrha pravokutnika na osi ordinata.
	 * @return Vrijednost gornjeg-lijevog vrha pravokutnika na osi ordinata.
	 */
	
	public int getVrhY() {
		return vrhY;
	}

	/**
	 * Metoda koja dohvaca vrijednost sirine pravokutnika.
	 * @return Vrijednost sirine pravokutnika.
	 */
	
	public int getSirina() {
		return sirina;
	}
	
	/**
	 * Metoda koja dohvaca vrijednost visine pravokutnika.
	 * @return Vrijednost visine pravokutnika.
	 */

	public int getVisina() {
		return visina;
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public boolean sadrziTocku(int x, int y){
		if(x < vrhX || x >= vrhX+sirina) return false;
		if(y < vrhY || y >= vrhY+visina) return false;
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
    public void popuniLik(Slika slika){
		int sirinaSlike = slika.getSirina();
		int visinaSlike = slika.getVisina();	
		for(int y = (vrhY < 0) ? 0 : vrhY , ye = vrhY+visina; y < ye && y < visinaSlike && ye > 0; y++){
			for(int x = (vrhX < 0) ? 0 : vrhX, xe = vrhX+sirina; x < xe && x < sirinaSlike && xe > 0; x++){
				slika.upaliTocku(x, y);
			}
		}
	}
	
	/**
	 * Klasa koja sluzi za stvaranje novih geometrijskih likova pravokutnika.
	 * @author Tomislav
	 *
	 */
	
	private static class PravokutnikStvaratelj implements StvarateljLika {
		
		/**
    	 * {@inheritDoc}
    	 */
		
		@Override
		public String nazivLika() {
			return "PRAVOKUTNIK";
		}
		
		/**
		 * Metoda koja stvara novi geometrijski lik pravokutnik.
		 * @param parametri Parametri pravokutnika.
		 * Broj parametara koji se ocekuje je 4.
		 * Prvi parametar je polozaj gornjeg-lijevog vrha pravokutnika na osi apcisa.
		 * Drugi parametar je polozaj gornjeg-lijevog vrha pravokutnika na osi ordinata.
		 * Treci parametar je sirina pravokutnika.
		 * Cetvrti parametar je visina pravokutnika.
		 * Sirina i visina pravokutnika ne smiju biti manji od 0.
		 * @throws IllegalArgumentException U slucaju neispravnog broja parametara.
		 * U slucaju da parametri nisu cijeli brojevi. 
		 * U slucaju da je sirina ili visina pravokutnika manja od 0.
		 */
		
		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			String[] paramPolje = parametri.trim().split("\\s+");
			if (paramPolje.length != 4){
				throw new IllegalArgumentException("Neispravan broj parametara");
			}
			int vrhX, vrhY, sirina, visina;	
			try{
				vrhX = Integer.parseInt(paramPolje[0]);
				vrhY = Integer.parseInt(paramPolje[1]);
				sirina = Integer.parseInt(paramPolje[2]);
				visina = Integer.parseInt(paramPolje[3]);
			}catch(Exception ex){
				throw new IllegalArgumentException("Svi parametri moraju biti prirodni brojevi");
			}
			if (sirina < 0 || visina < 0){
				throw new IllegalArgumentException("Sirina i visina pravokutnika ne smiju biti manji od 0");
			}		
			return new Pravokutnik(vrhX, vrhY, sirina, visina);
		}
	}

}
