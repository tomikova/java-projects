package hr.fer.zemris.java.gui.calc.strategies;

/**
 * Interface for executing actions of specific calculator components.
 * @author Tomislav
 *
 */
public interface ActionStrategy {
	
	/**
	 * Method executes component action.
	 * @param text Component labeled text.
	 */
	public void execute(String text);
}
