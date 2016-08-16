package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Program demonstrates work of implemented observer design pattern.
 * @author Tomislav
 *
 */
public class ObserverExample {

	public static void main(String[] args) {
		
		IntegerStorage istorage = new IntegerStorage(20);
		
		istorage.addObserver(new SquareValue());
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue());
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}

}
