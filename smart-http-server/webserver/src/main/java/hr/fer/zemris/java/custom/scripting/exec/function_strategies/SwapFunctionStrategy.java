package hr.fer.zemris.java.custom.scripting.exec.function_strategies;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Deque;

/**
 * Class implements IFunctionStrategy and switches places of two numbers on stack.
 * @author Tomislav
 *
 */

public class SwapFunctionStrategy implements IFunctionStrategy {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Deque<Object> stack, RequestContext requestContext) {
		Object first = stack.pop();
		Object second = stack.pop();
		stack.push(second);
		stack.push(first);
	}
}
