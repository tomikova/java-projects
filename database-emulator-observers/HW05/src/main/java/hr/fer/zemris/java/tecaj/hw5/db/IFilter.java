package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Interface for modeling filters for testing if StudentRecord 
 * objects satisfies implemented filter conditions.
 * @author Tomislav
 *
 */
public interface IFilter {
	/**
	 * Method tests if StudentRecord object satisfies filter conditions.
	 * @param record StudentRecord object which will be tested.
	 * @return Value true/false depending on if StudentRecord object satisfies filter conditions.
	 */
	boolean accepts(StudentRecord record);
}
