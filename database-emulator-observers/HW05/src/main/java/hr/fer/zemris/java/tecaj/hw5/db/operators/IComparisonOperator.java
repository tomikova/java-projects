package hr.fer.zemris.java.tecaj.hw5.db.operators;

/**
 * Interface for determining if comparison operator is satisfied.
 * @author Tomislav
 *
 */
public interface IComparisonOperator {
	/**
	 * Method determines if two provided values satisfy comparison operator.
	 * @param value1 First string value which will be tested.
	 * @param value2 Second string value which will be tested.
	 * @return Value true/false depending if comparison operator is satisfied.
	 */
	boolean satisfied(String value1, String value2);
}
