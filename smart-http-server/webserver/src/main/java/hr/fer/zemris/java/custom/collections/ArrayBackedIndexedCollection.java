package hr.fer.zemris.java.custom.collections;

/**
 * Array based collection.
* @author Tomislav
*/

public class ArrayBackedIndexedCollection {

	/**
	 * Size of array.
	 */
	private int size;
	/**
	 * Array capacity.
	 */
	private int capacity;
	/**
	 * Array elements.
	 */
	private Object[] elements;
	
	/**
	 * Default constructor.
	 */
	public ArrayBackedIndexedCollection(){
		this(16);
	}
	
	/**
	 * Constructor with one parameter.
	 * @param initialCapacity Initial array capacity.
	 */
	public ArrayBackedIndexedCollection(int initialCapacity){
		if (initialCapacity < 1){
			throw new IllegalArgumentException("Capacity less than 1 provided.");
		}
		this.capacity = initialCapacity;
		this.elements = new Object[initialCapacity];
		this.size = 0;
	}
	
	/**
	* Method checks if array is empty.
	* @return True/false value depending if array is empty.
	*/
	
	public boolean isEmpty(){
		if (size == 0){
			return true;
		}
		return false;
	}
	
	/**
	* MMethod retuens array size.
	* @return Array size.
	*/
	
	public int size(){
		return size;
	}
	
	/**
	* Method adds element to array.
	* @param value Element to add.
	*/
	
	public void add(Object value){
		if (value == null){
			throw new IllegalArgumentException("Null object given.");
		}
		if (size == capacity){
			resize();
		}	
		elements[size] = value;	
		size++;
	}
	
	/**
	* Method returns element on provided index.
	* @param index Element index.
	* @return Element on provided index.
	*/
	
	public Object get(int index){
		if (index < 0 || index > size-1){
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}
	
	/**
	* Method removes element on provided index.
	* @param index Element index.
	*/
	
	public void remove(int index){
		if (index < 0 || index > size()-1){
			throw new IndexOutOfBoundsException();
		}
		for (int i = index; i < size-1; i++){
			elements[i] = elements[i+1];
		}
		elements[size-1] = null;
		size--;
	}
	
	/**
	* Method adds element to array on provided position.
	* @param value Element to add.
	* @param position Element position index.
	*/
	
	public void insert(Object value, int position){
		if (value == null){
			throw new IllegalArgumentException("Null object given.");
		}
		if (position < 0 || position > size){
			throw new IndexOutOfBoundsException();
		}
		if (size == capacity){
			resize();
		}
		Object current = value;
		Object next = elements[position];
		for (int i = position; i < size; i++){
			elements[i] = current;
			current = next;
			next = elements[i+1];
		}
		elements[size] = current;
		size++;
	}
	
	/**
	* Method returns index of provided object.
	* @param value Element which index is requested.
	* @return Element index or -1 id element doesn't exist in array.
	*/
	
	public int indexOf(Object value){
		for (int i = 0; i < size; i++){
			if (elements[i].equals(value)){
				return i;
			}
		}
		return -1;
	}
	
	/**
	* Method checks if element exists in array.
	* @param value Element for check.
	* @return Value true/false depending if element exists in array.
	*/
	
	public boolean contains(Object value){
		for (int i = 0; i < size; i++){
			if (elements[i].equals(value)){
				return true;
			}
		}
		return false;
	}
	
	/**
	* Method removes all elements from array.
	*/
	
	public void clear(){
		//set elements to null to let GC do its work
		for (int i = 0; i < size; i++){
			elements[i] = null;
		}
		size = 0;
	}
	
	/**
	* Method doubles array capacity.
	*/
	
	private void resize(){
		Object[] resizedElements = new Object[2*capacity];
		for (int i = 0; i < capacity; i++){
			resizedElements[i] = elements[i];
		}
		elements = resizedElements;
		capacity *= 2;
	}
}
