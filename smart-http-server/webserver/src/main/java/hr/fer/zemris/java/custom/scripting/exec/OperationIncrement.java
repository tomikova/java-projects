package hr.fer.zemris.java.custom.scripting.exec;
import hr.fer.zemris.java.custom.scripting.exec.ValueHolder.Type;

/**
 * Operation increments value for a provided value.
 * @author Tomislav
 *
 */

public class OperationIncrement implements IOperationStrategy {
	
	/**
	 * Value before operation.
	 */
	private ValueHolder original;
	/**
	 * Increment value.
	 */
	private ValueHolder argument;
	
	/**
	 * Constructor with two parameters.
	 * @param original Object which holds original value before operation.
	 * @param argument Object which holds increment value.
	 */
	public OperationIncrement(ValueHolder original, ValueHolder argument) {
		this.original = original;
		this.argument = argument;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object execute() {
		double result = original.getDoubleValue() + argument.getDoubleValue();
		if (original.getType() == Type.INTEGER && argument.getType() == Type.INTEGER){
			return Integer.valueOf((int)result);
		}
		else {
			return Double.valueOf(result);
		}
	}
}
