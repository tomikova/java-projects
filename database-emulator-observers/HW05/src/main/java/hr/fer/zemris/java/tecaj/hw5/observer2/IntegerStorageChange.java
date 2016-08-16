package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Class models change occurred in IntegerStorage.
 * @author Tomislav
 *
 */

public class IntegerStorageChange {
	
	/**
	 * IntegerStorage object.
	 */
	private final IntegerStorage istorage;
	/**
	 * IntegerStorage stored value before change.
	 */
	private final int valueBefore;
	/**
	 * IntegerStorage stored value after change.
	 */
	private final int valueCurrent;
	
	/**
	 * Constructor with three parameters.
	 * @param istorage IntegerStorage object.
	 * @param valueBefore IntegerStorage stored value before change.
	 * @param valueCurrent IntegerStorage stored value after change.
	 */
	public IntegerStorageChange(IntegerStorage istorage, int valueBefore, int valueCurrent) {
		
		this.istorage = istorage;
		this.valueBefore = valueBefore;
		this.valueCurrent = valueCurrent;
	}

	/**
	 * Method retrieves IntegerStorage object.
	 * @return IntegerStorage object.
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * Method retrieves IntegerStorage stored value before change.
	 * @return IntegerStorage stored value before change.
	 */
	public int getValueBefore() {
		return valueBefore;
	}

	/**
	 * Method retrieves IntegerStorage stored value after change.
	 * @return IntegerStorage stored value after change.
	 */
	public int getValueCurrent() {
		return valueCurrent;
	}
	
}
