package hr.fer.zemris.java.gui.calc;

/**
 * Class simulates stack.
* @author Tomislav
*/

public class ObjectStack {
	
	/**
	 * Real object used for achieving stack funcionality.
	 */
	private ArrayBackedIndexedCollection adaptee;
	
	public ObjectStack(){
		this.adaptee = new ArrayBackedIndexedCollection();
	}
	
	/**
	* MMethod check if stack is empty.
	* @return Value true/false depending if stack is empty.
	*/
	
	public boolean isEmpty(){
		return adaptee.isEmpty();
	}
	
	/**
	* Method returns stack size.
	* @return Stack size.
	*/
	
	public int size(){
		return adaptee.size();
	}
	
	/**
	* Method adds element on stack.
	* @param value Element to be added on stack..
	*/
	
	public void push(Object value){
		if (value == null){
			throw new IllegalArgumentException("Null object given.");
		}
		adaptee.add(value);
	}
	
	/**
	* Method return element from top of the stack. Element is removed from stack.
	* @return Element from top of the stack.
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
	* Method returns element from top of the stack. Element is not removed from stack.
	* @return Element from top of the stack.
	*/
	
	public Object peek(){
		int size = adaptee.size();
		if (size == 0){
			throw new EmptyStackException("Stack is empty.");
		}
		return adaptee.get(size-1);
	}
	
	/**
	* Method removes all elements from stack.
	*/
	
	public void clear(){
		adaptee.clear();
	}
}
