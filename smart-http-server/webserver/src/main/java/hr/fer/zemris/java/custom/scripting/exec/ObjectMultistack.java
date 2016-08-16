package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * Class stores multiple values for same key and it provides a stack-like abstraction for these values.
 * @author Tomislav
 *
 */
public class ObjectMultistack {

	/**
	 * Map with String as key and MultistackEntry object as value.
	 */
	private Map<String, MultistackEntry> map;

	/**
	 * Constructor without parameters.
	 * Initializes map.
	 */
	public ObjectMultistack() {
		this.map = new HashMap<>();
	}

	/**
	 * Method pushes MultistackEntry object on top of stack.
	 * @param name Value of the map key.
	 * @param valueWrapper ValueWrapper objects which will be pushed on top of the stack.
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		MultistackEntry newEntry = new MultistackEntry(valueWrapper);
		if (map.containsKey(name)) {
			MultistackEntry top = map.get(name);
			newEntry.previous = top;
			map.put(name, newEntry);
		}
		else {
			map.put(name, newEntry);
		}
	}
	
	/**
	 * Method retrieves ValueWrapper object from top of the stack 
	 * under provided key and deletes him from stack.
	 * @param name Value of the map key.
	 * @return ValueWrapper object from top of the stack under provided key.
	 * @throws EmptyStackException If key doesn't exist in map.
	 */
	public ValueWrapper pop(String name) {
		if (!map.containsKey(name)){
			throw new EmptyStackException();
		}
		MultistackEntry result = map.get(name);
		MultistackEntry newTop = result.previous;
		if (newTop == null){
			map.remove(name);
		}
		else {
			map.put(name, newTop);
		}
		return result.getValue();
	}
	
	/**
	 * Method retrieves ValueWrapper object from top of the stack under provided key.
	 * @param name Value of the map key.
	 * @return ValueWrapper object from top of the stack under provided key.
	 * @throws EmptyStackException If key doesn't exist in map.
	 */
	public ValueWrapper peek(String name) { 
		if (!map.containsKey(name)){
			throw new EmptyStackException();
		}
		return map.get(name).getValue();
	}
	
	/**
	 * Method checks if stack for provided key is empty (key doesn't exist in map).
	 * @param name Value of the map key.
	 * @return Value true/false depending if stack is empty.
	 */
	public boolean isEmpty(String name) {
		if (map.containsKey(name)){
			return false;
		}
		return true;
	}

	/**
	 * Class represents node of a singly linked list which holds ValueWrapper objects.
	 * @author Tomislav
	 *
	 */
	private static class MultistackEntry{

		/**
		 * ValueWrapper object value.
		 */
		private ValueWrapper value;
		/**
		 * Previous MultistackEntry object value in list.
		 */
		private MultistackEntry previous;

		/**
		 * Constructor with one parameter.
		 * @param value ValueWrapper object value.
		 */
		public MultistackEntry(ValueWrapper value) {
			this.value = value;
			this.previous = null;
		}
		
		/**
		 * Method retrieves ValueWrapper object value.
		 * @return ValueWrapper object value.
		 */
		public ValueWrapper getValue() {
			return value;
		}		
	}
}
