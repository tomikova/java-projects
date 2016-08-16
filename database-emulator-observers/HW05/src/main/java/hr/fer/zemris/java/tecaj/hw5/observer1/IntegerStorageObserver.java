package hr.fer.zemris.java.tecaj.hw5.observer1;

/**
 * Interface for IntegerStorage observers.
 * @author Tomislav
 *
 */
public interface IntegerStorageObserver {
	/**
	 * Method takes actions depending on change in IntegerStorage.
	 * @param istorage IntegerStorage object.
	 */
	public void valueChanged(IntegerStorage istorage);
}
