package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.exec.ValueHolder.Type;

/**
 * Operation divides value for a provided value.
 * @author Tomislav
 *
 */

public class OperationDivide implements IOperationStrategy {

	/**
	 * Value before operation.
	 */
	private ValueHolder original;
	/**
	 * Divisor value.
	 */
	private ValueHolder argument;
	
	/**
	 * Constructor with two parameters.
	 * @param original Object which holds original value before operation.
	 * @param argument Object which holds divisor value.
	 */
	public OperationDivide(ValueHolder original, ValueHolder argument) {
		this.original = original;
		this.argument = argument;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object execute() {
		double result = original.getDoubleValue() / argument.getDoubleValue();
		if (original.getType() == Type.INTEGER && argument.getType() == Type.INTEGER){
			return Integer.valueOf((int)result);
		}
		else {
			return Double.valueOf(result);
		}
	}
}
