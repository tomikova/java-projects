package hr.fer.zemris.java.custom.collections;

/**
 * Razred iznimke koji se javlja ukoliko je stog prazan.
 * @author Tomislav
 */

public class EmptyStackException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	public EmptyStackException(){
		super();
	}
	public EmptyStackException(String s){
		super(s);
	}
}
