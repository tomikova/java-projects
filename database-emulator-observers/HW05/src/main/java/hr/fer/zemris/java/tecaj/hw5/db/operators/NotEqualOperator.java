package hr.fer.zemris.java.tecaj.hw5.db.operators;

/**
 * Class which implements IComparisonOperator interface for determining if 
 * not equal comparison operator is satisfied.
 * @author Tomislav
 *
 */
public class NotEqualOperator implements IComparisonOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean satisfied(String value1, String value2){
		if (value1.compareTo(value2) != 0){
			return true;
		}
		return false;
	}
}
