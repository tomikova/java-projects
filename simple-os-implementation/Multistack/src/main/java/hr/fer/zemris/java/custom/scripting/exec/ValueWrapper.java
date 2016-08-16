package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class is a wrapper for String, Integer, Double or null objects.
 * @author Tomislav
 *
 */

public class ValueWrapper {

	/**
	 * Object value.
	 */
	private Object value;

	/**
	 * Constructor with one parameter.
	 * @param value Object value.
	 */
	public ValueWrapper(Object value) {
		checkType(value);
	}

	/**
	 * Method retrieves object value.
	 * @return Object value.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Method for setting new object value.
	 * @param value New object value.
	 */
	public void setValue(Object value) {
		checkType(value);
	}


	/**
	 * Method increments value for a provided value.
	 * @param incValue Increment value.
	 */
	public void increment(Object incValue) {
		value = getExecutor("increment",incValue).execute();
	}

	/**
	 * Method decrements value for a provided value.
	 * @param decValue Decrement value.
	 */
	public void decrement(Object decValue) {
		value = getExecutor("decrement",decValue).execute();
	}

	/**
	 * Method multiplies value for a provided value.
	 * @param mulValue Multiplier value.
	 */
	public void multiply(Object mulValue) {
		value = getExecutor("multiply",mulValue).execute();
	}

	/**
	 * Method divides value for a provided value.
	 * @param divValue Divisor value.
	 */
	public void divide(Object divValue) {
		value = getExecutor("divide",divValue).execute();
	}

	/**
	 * Method compares two objects.
	 * @param withValue Value of object which will be used for comparison with currently stored value.
	 * @return
	 * Value 1 if value of currently stored object is greater than value of provided object.
	 * Value -1 if if value of currently stored object is smaller than value of provided object.
	 * Value 0 if object values are equal.
	 */
	public int numCompare(Object withValue) {
		ValueHolder original = new ValueHolder(value);
		ValueHolder argument = new ValueHolder(withValue);
		return Double.compare(original.getDoubleValue(), argument.getDoubleValue());
	}

	/**
	 * Method retrieves object of IOperationStrategy interface for execution of desired operation.
	 * @param operation Operation name.
	 * @param argument Second object argument for operation.
	 * @return Object of IOperationStrategy interface for execution of desired operation.
	 */
	private IOperationStrategy getExecutor(String operation, Object argument) {
		switch(operation){
		case "increment":
			return new OperationIncrement(new ValueHolder(value), new ValueHolder(argument));
		case "decrement": 
			return new OperationDecrement(new ValueHolder(value), new ValueHolder(argument));
		case "multiply": 
			return new OperationMultiply(new ValueHolder(value), new ValueHolder(argument));
		case "divide": 
			return new OperationDivide(new ValueHolder(value), new ValueHolder(argument));
		default:
			return null;
		}
	}
	
	/**
	 * Method checks if type of provided object is supported.
	 * @param value Object value.
	 * @throws IllegalArgumentException If type of provided object is not supported.
	 */
	private void checkType(Object value) {
		if (value instanceof String || value instanceof Integer || value instanceof Double || value == null) {
			this.value = value;
		}
		else {
			throw new IllegalArgumentException("Type not supported");
		}
	}
}
