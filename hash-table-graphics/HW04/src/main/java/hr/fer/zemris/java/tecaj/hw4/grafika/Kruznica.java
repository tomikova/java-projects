package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa koja predstavlja geometrijski lik kruznicu.
 * @author Tomislav
 *
 */

public class Kruznica extends Elipsa {
	
	/**
	 * Objekt koji sadrzi metode tvornice za stvaranje kruznice.
	 */
	public static final StvarateljLika STVARATELJ = new KrugStvaratelj();
	
	/**
	 * Polozaj centra kruznice na osi apcisa.
	 */
	private int centarX;
	
	/**
	 * Polozaj centra kruznice na osi ordinata.
	 */
	private int centarY;
	
	/**
	 * Radijus kruznice.
	 */
	private int radijus;
	
	/**
	 * Konstruktor za geometrijski lik kruznica.
	 * @param centarX Polozaj centra kruznice na osi apcisa.
	 * @param centarY Polozaj centra kruznice na osi ordinata.
	 * @param radijus Radijus kruznice.
	 */
	public Kruznica(int centarX, int centarY, int radijus) {
		super(centarX,centarY,radijus,radijus);
		this.centarX = centarX;
		this.centarY = centarY;
		this.radijus = radijus;
	}
	
	/**
	 * Metoda dohvaca vrijednost polozaja centra kruznice na osi apcisa.
	 * @return Vrijednost polozaja centra kruznice na osi apcisa.
	 */
	
	public int getCentarX() {
		return centarX;
	}
	
	/**
	 * Metoda dohvaca vrijednost polozaja centra kruznice na osi ordinata.
	 * @return Vrijednost polozaja centra kruznice na osi ordinata.
	 */

	public int getCentarY() {
		return centarY;
	}
	
	/**
	 * Metoda dohvaca vrijednost radijusa kruznice.
	 * @return Vrijednost radijusa kruznice.
	 */

	public int getRadijus() {
		return radijus;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public boolean sadrziTocku(int x, int y){
		if (x*x+y*y <= radijus*radijus){
			return true;
		}
		return false;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	
	
	@Override
    public void popuniLik(Slika slika){
		
		boolean classLinijaExist = true;
		
		//provjera da li klasa Linija postoji
		try{
			Class.forName("hr.fer.zemris.java.tecaj.hw4.grafika.Linija", false, this.getClass().getClassLoader());
		}catch(Exception ex){
			classLinijaExist = false;
		}
		
		//ako klasa Linija postoji, iscrtati krug povezujuci tocke linijama
		if (classLinijaExist){
			int x = radijus;
			int y = 0;
			int radijusGreska = 1-x;
			Linija linija;
			while (x >= y){
				linija = new Linija(centarX - x, centarY + y, centarX + x, centarY + y);
				linija.popuniLik(slika);
				linija = new Linija(centarX - y, centarY + x, centarX + y, centarY + x);
				linija.popuniLik(slika);
				linija = new Linija(centarX - x, centarY - y, centarX + x, centarY - y);
				linija.popuniLik(slika);
				linija = new Linija(centarX - y, centarY - x, centarX + y, centarY - x);
				linija.popuniLik(slika);
				y++;
			    if (radijusGreska < 0){
			    	radijusGreska += 2 * y + 1;
			    }
			    else{ 	
			    	x--;
			    	radijusGreska += 2 * (y - x) + 1;
			    }
			}
		}		
		//inace aproksimirati kruznicu kvadratom i ispitati tocke
		else{
			int sirinaSlike = slika.getSirina();
			int visinaSlike = slika.getVisina();
			for(int y = -radijus; y <= radijus && (centarY+y) < visinaSlike; y++){
				for(int x = -radijus; x <= radijus && (centarX+x) < sirinaSlike; x++){
					if(sadrziTocku(x,y) && centarX+x >= 0 && centarY+y >= 0 ){
						slika.upaliTocku(centarX+x, centarY+y);
					}
				}
			}
		}
	}
	
	/**
	 * Klasa koja sluzi za stvaranje novih geometrijskih likova kruznice.
	 * @author Tomislav
	 *
	 */
	
    private static class KrugStvaratelj implements StvarateljLika {
		
    	/**
		 * {@inheritDoc}
		 */
    	
		@Override
		public String nazivLika() {
			return "KRUG";
		}
		
		/**
		 * Metoda koja stvara novi geometrijski lik kruznica.
		 * @param parametri Parametri kruznice.
		 * Broj parametara koji se ocekuje je 3.
		 * Prvi parametar je polozaj centra kruznice na osi apcisa.
		 * Drugi parametar je polozaj centra kruznice na osi ordinata.
		 * Treci parametar je duljina radijusa kruznice.
		 * Radijus ne smije biti manji od 0.
		 * @throws IllegalArgumentException U slucaju neispravnog broja parametara.
		 * U slucaju da parametri nisu cijeli brojevi.
		 * U slucaju da je radijus manji od 0.
		 */
		
		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			String[] paramPolje = parametri.trim().split("\\s+");
			if (paramPolje.length != 3){
				throw new IllegalArgumentException("Neispravan broj parametara");
			}
			
			int centarX, centarY, radijus;
			
			try{
				centarX = Integer.parseInt(paramPolje[0]);
				centarY = Integer.parseInt(paramPolje[1]);
				radijus = Integer.parseInt(paramPolje[2]);
			}catch(Exception ex){
				throw new IllegalArgumentException("Svi parametri moraju biti prirodni brojevi");
			}
			
			if (radijus < 0){
				throw new IllegalArgumentException("Radijus kruznice ne smije biti manji od 0");
			}	
			
			return new Kruznica(centarX, centarY, radijus);
		}
	}

}
