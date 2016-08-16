package hr.fer.zemris.java.tecaj.hw5.db.operators;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class which implements IComparisonOperator interface for determining if 
 * equal comparison operator is satisfied.
 * @author Tomislav
 *
 */
public class EqualOperator implements IComparisonOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean satisfied(String value1, String value2){
		Pattern pattern = Pattern.compile("^"+value2+"$");
		Matcher matcher = pattern.matcher(value1);
		if (matcher.find()){
			return true;
		}
		return false;
	}
}
