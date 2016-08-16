package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.getters.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator;

/**
 * Class models one conditional expression of a query.
 * @author Tomislav
 *
 */

public class ConditionalExpression {
	
	/**
	 * Object for getting field value to which expression applies.
	 */
	private IFieldValueGetter fieldGetter;
	
	/**
	 * String value that will be compared with field value.
	 */
	private String stringLiteral;
	
	/**
	 * Operator for field and string literal comparison.
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Constructor with three parameters.
	 * @param fieldGetter Object for getting field value to which expression applies.
	 * @param stringLiteral String value that will be compared with field value.
	 * @param operator Operator for field and string literal comparison.
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, 
								IComparisonOperator operator){
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = operator;
	}

	/**
	 * Method retrieves field value getter object.
	 * @return Field value getter object.
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Method retrieves string literal of conditional expression.
	 * @return String literal of conditional expression.
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Method retrieves conditional expression operator.
	 * @return Conditional expression operator.
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
}
