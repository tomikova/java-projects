package hr.fer.zemris.java.student0036461026.hw06;

/**
 * Klasa koja testira cijele brojeve na parnost, neparnost te da li je broj prost.
 * @author Tomislav
 *
 */

public class CijeliBrojevi  {
	
	/**
	 * Metoda provjerava da li je broj neparan.
	 * @param broj Broj koji se želi testirati na parnost.
	 * @return Vrijednost true/false ovisno da li je broj neparan.
	 */
	public static boolean jeNeparan(int broj) { 
		if (broj %2 != 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Metoda provjerava da li je broj paran.
	 * @param broj Broj koji se želi testirati na parnost.
	 * @return Vrijednost true/false ovisno da li je broj paran.
	 */
    public static boolean jeParan(int broj) {
    	return !jeNeparan(broj);
    }
    
    /**
	 * Metoda provjerava da li je broj prost.
	 * @param broj Broj za kojeg se želi utvrditi da li je prost.
	 * @return Vrijednost true/false ovisno da li je broj prost.
	 */
    public static boolean jeProst(int broj) {
    	broj = Math.abs(broj);
    	if (broj == 0) {
    		throw new IllegalArgumentException("Broj ne smije biti 0.");
    	}
    	else if (broj == 1) {
    		return true;
    	}
    	while(true){
			boolean isPrime = true;
			for (int i = 2, upperBound = (int)Math.ceil(Math.sqrt(broj)); i <= upperBound ; i++ ){
				if (broj % i == 0){
					isPrime = false;
					break;
				}
			}
			return isPrime;
		}
    }
}
