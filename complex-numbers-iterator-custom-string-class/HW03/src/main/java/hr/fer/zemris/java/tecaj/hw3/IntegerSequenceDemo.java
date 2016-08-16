package hr.fer.zemris.java.tecaj.hw3;

/**
 * Program u kojem se ispituje skraceni oblik for petlje 
 * uporabom implementiranog iteratora.
 * @author Tomislav
 */

public class IntegerSequenceDemo {

	/**
	 * Pocetna metoda programa.
	 * @param args Argumenti iz komandne linije.
	 */
	
	public static void main(String[] args) {
		
		IntegerSequence range = new IntegerSequence(1, 11, 2);
		for(int i : range) {
			for(int j : range) {
				System.out.println("i="+i+", j="+j);
			}
		}
	}
}
