package hr.fer.zemris.java.custom.collections;

/**
* @author Tomislav
*/

public class ObjectStack {
	
	private ArrayBackedIndexedCollection adaptee;
	
	public ObjectStack(){
		this.adaptee = new ArrayBackedIndexedCollection();
	}
	
	/**
	* Metoda koja provjerava da li je stog prazan.
	* @return Vrijednost true/false ovisno da li je stog prazan.
	*/
	
	public boolean isEmpty(){
		return adaptee.isEmpty();
	}
	
	/**
	* Metoda koja vraca velicinu stoga.
	* @return Velicina stoga.
	*/
	
	public int size(){
		return adaptee.size();
	}
	
	/**
	* Metoda koja dodaje element na stog.
	* @param value Element koji se dodaje na stog.
	*/
	
	public void push(Object value){
		if (value == null){
			throw new IllegalArgumentException("Null object given.");
		}
		adaptee.add(value);
	}
	
	/**
	* Metoda koja vraca element s vrha stoga. Element se brise sa stoga.
	* @return Element s vrha stoga.
	*/
	
	public Object pop(){
		int size = adaptee.size();
		if (size == 0){
			throw new EmptyStackException("Stack is empty.");
		}
		Object poppedObject = adaptee.get(size-1);
		adaptee.remove(size-1);
		return poppedObject;
	}
	
	/**
	* Metoda koja vraca element s vrha stoga. Element se ne brise sa stoga.
	* @return Element s vrha stoga.
	*/
	
	public Object peek(){
		int size = adaptee.size();
		if (size == 0){
			throw new EmptyStackException("Stack is empty.");
		}
		return adaptee.get(size-1);
	}
	
	/**
	* Metoda koja uklanja sve elemente sa stoga.
	*/
	
	public void clear(){
		adaptee.clear();
	}
}
