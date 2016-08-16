package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Interface for IntegerStorage observers.
 * @author Tomislav
 *
 */
public interface IntegerStorageObserver {
	/**
	 * Method takes actions depending on change in IntegerStorage.
	 * @param istorageChange IntegerStorageChange object.
	 */
	public void valueChanged(IntegerStorageChange istorageChange);
}
