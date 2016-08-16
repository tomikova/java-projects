package hr.fer.zemris.java.custom.scripting.exec.function_strategies;

import hr.fer.zemris.java.webserver.RequestContext;

import java.text.DecimalFormat;
import java.util.Deque;

/**
 * Class implements IFunctionStrategy and sets decimal format of number.
 * @author Tomislav
 *
 */

public class DecfmtFunctionStrategy implements IFunctionStrategy {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Deque<Object> stack, RequestContext requestContext) {
		String pattern = (String)stack.pop();
		Object value = stack.pop();
		Double number;
		if (value instanceof String) {
			number = Double.parseDouble((String)value);
		}
		else if (value instanceof Integer) {
			number = ((Integer)value).doubleValue();
		}
		else {
			number = (Double)value;
		}
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		String formatted = (decimalFormat.format(number));
		formatted = formatted.replace(",", ".");
		stack.push(formatted);
	}
}
