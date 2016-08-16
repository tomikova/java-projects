package hr.fer.zemris.java.custom.scripting.exec.function_strategies;

import java.util.Deque;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class implements IFunctionStrategy and sets requestContext temporary parameter under
 * provided name to provided value.
 * @author Tomislav
 *
 */

public class TparamSetFunctionStrategy implements IFunctionStrategy {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Deque<Object> stack, RequestContext requestContext) {
		String name = (String)stack.pop();
		String value;
		Object obj = stack.pop();
		if (obj instanceof String) {
			value = (String)obj;
		}
		else if (obj instanceof Integer) {
			value = ((Integer)obj).toString();
		}
		else {
			value = ((Double)obj).toString();
		}
		requestContext.setTemporaryParameter(name, value);
	}
}
