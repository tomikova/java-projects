package hr.fer.zemris.java.tecaj.hw5.observer1;

/**
 * Program demonstrates work of implemented observer design pattern.
 * @author Tomislav
 *
 */
public class ObserverExample {

	public static void main(String[] args) {
		
		IntegerStorage istorage = new IntegerStorage(20);
		
		IntegerStorageObserver observer = new SquareValue();
		
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		istorage.removeObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue());
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}

}
