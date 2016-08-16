package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Interface with one method execute which return result of operation.
 * @author Tomislav
 *
 */

public interface IOperationStrategy {
	/**
	 * Method executes operation.
	 * @return Result of operation.
	 */
	public Object execute();
}
