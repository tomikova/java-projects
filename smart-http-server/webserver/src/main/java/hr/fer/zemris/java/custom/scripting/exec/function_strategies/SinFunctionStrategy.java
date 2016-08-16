package hr.fer.zemris.java.custom.scripting.exec.function_strategies;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Deque;

/**
 * Class implements IFunctionStrategy and calculates sinus function of given number.
 * It is expected that number is in degrees not radians.
 * @author Tomislav
 *
 */

public class SinFunctionStrategy implements IFunctionStrategy {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Deque<Object> stack, RequestContext requestContext) {
		Object value = stack.pop();
		Double result;
		if (value instanceof String) {
			Double number = Double.parseDouble((String)value);
			result = Math.sin(number*Math.PI/180);
		}
		else if (value instanceof Integer) {
			Integer number = (Integer)value;
			result = Math.sin(number*Math.PI/180);
		}
		else {
			Double number = (Double)value;
			result = Math.sin(number*Math.PI/180);
		}
		stack.push(result);
	}
}
