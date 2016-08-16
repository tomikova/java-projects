package hr.fer.zemris.java.tecaj.hw5.observer2;

import java.util.LinkedList;
import java.util.List;

/**
 * Class for storing integer value.
 * @author Tomislav
 *
 */
public class IntegerStorage {
	
	/**
	 * Stored integer value.
	 */
	private int value;
	/**
	 * List of objects which will be informed in case of stored integer value change.
	 */
	private List<IntegerStorageObserver> observers;
	
	/**
	 * Constructor with one parameter.
	 * @param initialValue Integer value which will be stored.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Method adds object in a list of objects which will be informed in case of stored integer value change.
	 * @param observer Object that needs to be informed of stored integer value change.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observers == null){
			observers = new LinkedList<IntegerStorageObserver>();
		}
		if (!observers.contains(observer)){
			observers.add(observer);
		}
	}
	
	/**
	 * Method removes object from object observers list.
	 * @param observer Object that will be removed.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}
	
	/**
	 * Method removes all observer objects from observers list.
	 */
	public void clearObservers() {
		observers.clear();
	}
	
	/**
	 * Method retrieves stored integer value;
	 * @return Stored integer value.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Method sets new integer value and notifies about that change all observers.
	 * @param value New value which will be stored.
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if(this.value!=value) {
			// Update current value
			IntegerStorageChange istorageChange = new IntegerStorageChange(this, this.value, value);
			this.value = value;
			// Notify all registered observers
			if(observers!=null) {
				for(IntegerStorageObserver observer : observers) {
					observer.valueChanged(istorageChange);
				}
			}
		}
	}
}
