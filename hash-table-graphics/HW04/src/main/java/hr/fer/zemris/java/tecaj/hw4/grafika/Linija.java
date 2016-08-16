package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Klasa koja predstavlja geometrijski lik liniju.
 * @author Tomislav
 *
 */

public class Linija extends GeometrijskiLik {
	
	/**
	 * Objekt koji sadrzi metode tvornice za stvaranje linije.
	 */
	public static final StvarateljLika STVARATELJ = new LinijaStvaratelj();
	
	/**
	 * Polozaj pocetne tocke linije na osi apcisa.
	 */
	private int pocetakX;
	
	/**
	 * Polozaj pocetne tocke linije na osi ordinata.
	 */
	private int pocetakY;
	
	/**
	 * Polozaj krajnje tocke linije na osi apcisa.
	 */
	private int krajX;
	
	/**
	 * Polozaj krajnje tocke linije na osi ordinata.
	 */
	private int krajY;
	
	/**
	 * Konstruktor za geometrijski lik linija.
	 * @param x1 Polozaj pocetne tocke linije na osi apcisa.
	 * @param y1 Polozaj pocetne tocke linije na osi ordinata.
	 * @param x2 Polozaj krajnje tocke linije na osi apcisa.
	 * @param y2 Polozaj krajnje tocke linije na osi ordinata.
	 */
	
	public Linija(int x1, int y1,int x2, int y2) {
		if (x1 <= x2){
			this.pocetakX = x1;
			this.pocetakY = y1;
			this.krajX = x2;
			this.krajY = y2;
		}
		else{
			this.pocetakX = x2;
			this.pocetakY = y2;
			this.krajX = x1;
			this.krajY = y1;
		}
	}
	
	/**
	 * Metoda koja dohvaca vrijednost pocetne tocke linije na osi apcisa.
	 * @return Vrijednost pocetne tocke linije na osi apcisa.
	 */
	
	public int getPocetakX() {
		return pocetakX;
	}

	/**
	 * Metoda koja dohvaca vrijednost pocetne tocke linije na osi ordinata.
	 * @return Vrijednost pocetne tocke linije na osi ordinata.
	 */
	
	public int getPocetakY() {
		return pocetakY;
	}
	
	/**
	 * Metoda koja dohvaca vrijednost krajnje tocke linije na osi apcisa.
	 * @return Vrijednost krajnje tocke linije na osi apcisa.
	 */

	public int getKrajX() {
		return krajX;
	}

	/**
	 * Metoda koja dohvaca vrijednost krajnje tocke linije na osi ordinata.
	 * @return Vrijednost krajnje tocke linije na osi ordinata.
	 */
	
	public int getKrajY() {
		return krajY;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public boolean sadrziTocku(int x, int y){
		//uvjet da tocke leze na istom pravcu
		int produkt = (y-pocetakY) * (krajX - pocetakX) - (x-pocetakX) * (krajY - pocetakY);
		if (produkt != 0){
			return false;
		}
		
		//ako je skalarni produkt manji od 0, tocka se ne nalazi na liniji
		int skalarniProdukt = (x-pocetakX)*(krajX-pocetakX)+(y-pocetakY)*(krajY-pocetakY);
		if (skalarniProdukt < 0){
			return false;
		}
		
		//ako je skalarni produkt veci od kvadrata udaljenosti krajnjih tocaka linije,
		//tocka se ne nalazi na liniji
		int kvadratUdaljenosti = (int)(Math.pow(krajX-pocetakX, 2) + Math.pow(krajY-pocetakY, 2));
		if (skalarniProdukt > kvadratUdaljenosti){
			return false;
		}
		return true;
	}
	
	/**
	 * Metoda koja crta geometrijski lik liniju na slici.
	 * Za crtanje linije koristi se Bresenham-ov algoritam.
	 * @param slika Slika na kojoj ce se iscrtati geometrijski lik.
	 */
	@Override
    public void popuniLik(Slika slika){
		int sirinaSlike = slika.getSirina();
		int visinaSlike = slika.getVisina();
		//ako je linija okomita
		if (krajX - pocetakX == 0){
			int pocetak = pocetakY;
			int kraj = krajY;	
			if (pocetakY > krajY){
				pocetak = krajY;
				kraj = pocetakY;
			}
			for (int y = pocetak < 0 ? 0 : pocetak; y <= kraj && y < visinaSlike && pocetakX < sirinaSlike && pocetakX >= 0 ; y++ ){
				slika.upaliTocku(pocetakX, y);
			}
		}
		//inace primjeni Bresenham-ov algoritam
		else{
			double deltaX = krajX - pocetakX;
			double deltaY = krajY - pocetakY;
			double greska = 0;
			double deltaGreska = Math.abs(deltaY/deltaX);
			int y = pocetakY;
			for (int x = pocetakX < 0 ? 0 : pocetakX; x <= krajX && x < sirinaSlike; x++ ){
				if(y < visinaSlike && y >= 0){
					slika.upaliTocku(x, y);
				}
				greska += deltaGreska;
				while (greska >= 0.5){
					if(y < visinaSlike && y >= 0){
						slika.upaliTocku(x, y);
					}
					y = y + (int)Math.signum(krajY - pocetakY);
					greska -= 1.0;
				}
			}
		}
	}
	
	/**
	 * Klasa koja sluzi za stvaranje novih geometrijskih likova linija.
	 * @author Tomislav
	 *
	 */
    private static class LinijaStvaratelj implements StvarateljLika {
		
    	/**
    	 * {@inheritDoc}
    	 */
    	
		@Override
		public String nazivLika() {
			return "LINIJA";
		}
		
		/**
		 * Metoda koja stvara novi geometrijski lik liniju.
		 * @param parametri Parametri linije.
		 * Broj parametara koji se ocekuje je 4.
		 * Prvi parametar je polozaj pocetne tocke linije na osi apcisa.
		 * Drugi parametar je polozaj pocetne tocke linije na osi ordinata.
		 * Treci parametar je polozaj krajnje tocke linije na osi apcisa.
		 * Cetvrti parametar je polozaj krajnje tocke linije na osi ordinata.
		 * @throws IllegalArgumentException U slucaju neispravnog broja parametara.
		 * U slucaju da parametri nisu cijeli brojevi. 
		 */
		
		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			String[] paramPolje = parametri.trim().split("\\s+");
			if (paramPolje.length != 4){
				throw new IllegalArgumentException("Neispravan broj parametara");
			}
			
			int x1,y1,x2,y2;
			
			try{
				x1 = Integer.parseInt(paramPolje[0]);
				y1 = Integer.parseInt(paramPolje[1]);
				x2 = Integer.parseInt(paramPolje[2]);
				y2 = Integer.parseInt(paramPolje[3]);
			}catch(Exception ex){
				throw new IllegalArgumentException("Svi parametri moraju biti prirodni brojevi");
			}
			
			return new Linija(x1,y1,x2,y2);
		}
	}

}
