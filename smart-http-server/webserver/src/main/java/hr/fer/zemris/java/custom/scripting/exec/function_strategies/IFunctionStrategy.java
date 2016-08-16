package hr.fer.zemris.java.custom.scripting.exec.function_strategies;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Deque;

/**
 * Interface for SmartScriptEngine supported functions. It declares method execute
 * that is responsible for execution of function.
 * @author Tomislav
 *
 */

public interface IFunctionStrategy {
	/**
	 * Method performs operations of SmartScriptEngine function.
	 * @param stack Stack on which function result is pushed.
	 * @param requestContext Object used to send response to client. Here is used
	 * if function changes requestContext properties otherwise this parameter can be null.
	 */
	public void execute(Deque<Object> stack, RequestContext requestContext);
}
