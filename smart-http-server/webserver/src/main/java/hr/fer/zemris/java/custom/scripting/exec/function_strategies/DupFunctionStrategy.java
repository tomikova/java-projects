package hr.fer.zemris.java.custom.scripting.exec.function_strategies;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Deque;

/**
 * Class implements IFunctionStrategy and it duplicates variable from stack top.
 * @author Tomislav
 *
 */

public class DupFunctionStrategy implements IFunctionStrategy {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Deque<Object> stack, RequestContext requestContext) {
		Object op = stack.pop();
		stack.push(op);
		stack.push(op);		
	}
}
