package hr.fer.zemris.java.custom.collections;

/**
* @author Tomislav
*/

public class ArrayBackedIndexedCollection {

	private int size;
	private int capacity;
	private Object[] elements;
	
	public ArrayBackedIndexedCollection(){
		this(16);
	}
	
	public ArrayBackedIndexedCollection(int initialCapacity){
		if (initialCapacity < 1){
			throw new IllegalArgumentException("Capacity less than 1 provided.");
		}
		this.capacity = initialCapacity;
		this.elements = new Object[initialCapacity];
		this.size = 0;
	}
	
	/**
	* Metoda koja provjerava da li je polje prazno.
	* @return Vrijednost true/false ovisno da li je polje prazano.
	*/
	
	public boolean isEmpty(){
		if (size == 0){
			return true;
		}
		return false;
	}
	
	/**
	* Metoda koja vraca velicinu polja.
	* @return Velicina polja.
	*/
	
	public int size(){
		return size;
	}
	
	/**
	* Metoda koja dodaje element u polje.
	* @param value Element koji se dodaje u polje.
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
	* Metoda koja vraca element polja na zadanom indeksu.
	* @param index Indeks elementa.
	* @return Element na zadanom indeksu.
	*/
	
	public Object get(int index){
		if (index < 0 || index > size-1){
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}
	
	/**
	* Metoda koja brise element polja na zadanom indeksu.
	* @param index Indeks elementa.
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
	* Metoda koja dodaje element u polje na zadano mjesto.
	* @param value Element koji se dodaje u polje.
	* @param position Mjesto na koje se element dodaje.
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
	* Metoda koja dohvaca indeks zadanog objekta.
	* @param value Element ciji indeks se zeli saznati.
	* @return Indekst elementa.
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
	* Metoda koja provjerava da li polje sadrzi element.
	* @param value Element za kojeg se zeli provjeriti da li postoji.
	* @return Vrijednost true/false ovisno da li polje sadrzi element.
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
	* Metoda koja uklanja sve elemente iz polja.
	*/
	
	public void clear(){
		//set elements to null to let GC do its work
		for (int i = 0; i < size; i++){
			elements[i] = null;
		}
		size = 0;
	}
	
	/**
	* Metoda koja dvostruko povecava velicinu polja.
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
