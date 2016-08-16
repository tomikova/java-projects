package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Sučelje koje implementiraju klase koje su tvornice za geometrijske likove.
 * @author Tomislav
 *
 */

public interface StvarateljLika {
	
	/**
	 * Metoda koja vraca naziv geometrijskog lika.
	 * @return Naziv geometrijskog lika.
	 */
	String nazivLika();
	
	/**
	 * Metoda tvornica koja stvara novi geometrijski lik.
	 * @param parametri Parametri geometrijskog lika.
	 * @return Novostvoreni geometrijski lik.
	 */
	
	GeometrijskiLik stvoriIzStringa(String parametri);
}
