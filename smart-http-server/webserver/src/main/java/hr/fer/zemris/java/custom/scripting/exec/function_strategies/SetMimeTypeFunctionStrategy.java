package hr.fer.zemris.java.custom.scripting.exec.function_strategies;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Deque;

/**
 * Class implements IFunctionStrategy and sets requestContext mime type to provided value.
 * @author Tomislav
 *
 */

public class SetMimeTypeFunctionStrategy implements IFunctionStrategy {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(Deque<Object> stack, RequestContext requestContext) {
		String mimeType = (String)stack.pop();
		requestContext.setMimeType(mimeType);
	}

}
