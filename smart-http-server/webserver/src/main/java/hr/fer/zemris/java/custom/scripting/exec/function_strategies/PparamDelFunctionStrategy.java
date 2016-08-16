package hr.fer.zemris.java.custom.scripting.exec.function_strategies;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Deque;

/**
 * Class implements IFunctionStrategy and removes parameter from requestContext persistent parameters.
 * @author Tomislav
 *
 */

public class PparamDelFunctionStrategy implements IFunctionStrategy {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Deque<Object> stack, RequestContext requestContext) {
		String name = (String)stack.pop();
		requestContext.removePersistentParameter(name);
	}
}
