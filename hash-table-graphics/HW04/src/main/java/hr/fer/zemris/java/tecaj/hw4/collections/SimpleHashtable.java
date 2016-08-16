package hr.fer.zemris.java.tecaj.hw4.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Class implements hash table for storing pairs (key,value)
 * @author Tomislav
 *
 */

public class SimpleHashtable implements Iterable<SimpleHashtable.TableEntry> {
	
	/**
	 * Default capacity of hash table.
	 */
	private static final int DEFAULT = 16;
	
	/**
	 * Number of hash table entries at initialization.
	 */
	private static final int ZERO = 0;
	
	/**
	 * Hash table.
	 */
	private TableEntry[] table;
	
	/**
	 * Size of hash table.
	 */
	private int size;
	
	/**
	 * Number of modifications made in hash table.
	 */
	private int modificationCount;
	
	/**
	 * Default constructor without parameters.
	 * Initializes hash table to the default size of 16.
	 */
	
	public SimpleHashtable() {
		this.table = new TableEntry[DEFAULT];
		this.size = ZERO;
		this.modificationCount = ZERO;
	}
	
	/**
	 * Constructor with one parameter. Initializes hash table to the size of
	 * first multiple of 2 that is greater than provided capacity parameter.
	 * @param capacity Desired capacity of hash map.
	 */
	
	public SimpleHashtable(int capacity) {
		if (capacity < 1){
			throw new IllegalArgumentException("capacity must be greater than 0");
		}
		int power = 0;
		int size = 1;
		do{
			size = (int)Math.pow(2, power);
			power++;
		}while(capacity > size);
		this.table = new TableEntry[size];
		this.size = ZERO;
		this.modificationCount = ZERO;
	}
	
	/**
	 * Method checks percentage of used storage in hash map.
	 * If percentage of used storage is more than 75%, size of hash table
	 * is enlarged by 2. 
	 */
	
	private void checkAvailability(){
		if ((double)size/table.length >= 0.75){
			TableEntry[] copiedTable = Arrays.copyOf(table, table.length);
			TableEntry[] resizedTable = new TableEntry[table.length*2];
			clear();
			table = resizedTable;
			for (int i = 0; i < copiedTable.length; i++){
				TableEntry iter = copiedTable[i];
				while (iter != null){
					put(iter.key,iter.value);
					iter = iter.next;
				}
			}
			modificationCount++;
		}
	}
	
	/**
	 * Method removes all entries in hash table. 
	 */
	
	public void clear()	{
		for (int i = 0; i < table.length; i++){
			table[i] = null;
		}
		size = 0;
		modificationCount++;
	}
	
	/**
	 * Method adds new entry (key,value) in hash table. If key is already present
	 * in hash table value under that key is replaced with the new value.
	 * @param key Object used as key in hash table. Key can't be null.
	 * @param value Object used as value of a provided key.
	 * @throws IllegalArgumentException If provided key is null.
	 */
	
	public void put(Object key, Object value){
		if (key == null){
			throw new IllegalArgumentException("key can't be null");
		}
		
		int slotIndex = Math.abs(key.hashCode())%table.length;
		
		if (table[slotIndex] == null){
			TableEntry newEntry = new TableEntry(key,value,null);
			table[slotIndex] = newEntry;
			size++;
			checkAvailability();
			modificationCount++;
		}
		else{
			TableEntry iter = table[slotIndex];
			TableEntry previous = null;
			boolean updated = false;
			while(iter != null){
				if (iter.key.equals(key)){
					iter.value = value;
					updated = true;
					break;
				}
				previous = iter;
				iter = iter.next;
			}
			if (!updated){
				TableEntry newEntry = new TableEntry(key,value,null);
				previous.next = newEntry;
				size++;
				checkAvailability();
			}
			modificationCount++;
		}
	}
	
	/**
	 * Method retrieves object used as value of provided key.
	 * @param key Object used as key in hash table.
	 * @return Object used as value of provided key. If key doesn't exist null is returned.
	 */
	
	public Object get(Object key){
		if (key == null){
			return null;
		}
		for(SimpleHashtable.TableEntry pair : this) {
			if (pair.key.equals(key)){
				return pair.value;
			}
		}
		return null;
	}
	
	public int size(){
		return size;
	}
	
	/**
	 * Method check if key exist in hash table.
	 * @param key Object whose existence in hash table as key will be checked.
	 * @return Value true/false depending on key existence in hash table.
	 */
	
	public boolean containsKey(Object key){
		for(SimpleHashtable.TableEntry pair : this) {
			if (pair.key.equals(key)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method checks if provided value is stored under some key in hash table.
	 * @param value Object whose existence in hash table as value will be checked.
	 * @return Value true/false depending on value existence in hash table.
	 */
	
	public boolean containsValue(Object value){
		for(SimpleHashtable.TableEntry pair : this) {
			if (pair.value.equals(value)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method removes entry (key,value) stored under provided 
	 * key if that key exist in hash table.
	 * @param key Object used as key in hash table.
	 */
	
	public void remove(Object key){
		if (key != null){
			int slotIndex = Math.abs(key.hashCode())%table.length;
			TableEntry iter = table[slotIndex];
			TableEntry previous = null;
			while(iter!= null){
				if(iter.key.equals(key)){
					if (previous != null){
						previous.next = iter.next;
					}
					else{
						table[slotIndex] = iter.next;
					}
					iter = null;
					size--;
					break;
				}
				previous = iter;
				iter = iter.next;
			}
			modificationCount++;
		}
	}
	
	/**
	 * Method checks if hash table is empty.
	 * @return Value true/false depending if hash table is empty or not.
	 */
	
	public boolean isEmpty(){
		if (size == 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Method retrieves String representation of hash table.
	 * @return String representation of hash table.
	 */
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(SimpleHashtable.TableEntry pair : this) {
			sb.append(pair.key.toString()+"="+pair.value.toString()+", ");
		}
		if (sb.length() > 1){
			sb.setLength(sb.length()-2);
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public Iterator<SimpleHashtable.TableEntry> iterator() {
		return new TableEntryIterator();
	}
	
	/**
	 * Class for iterating through hash table entries.
	 * @author Tomislav
	 *
	 */
	
	private class TableEntryIterator implements Iterator<SimpleHashtable.TableEntry> {
		
		/**
		 * Index of first hash table bucket.
		 */
		private static final int INIT = 0;
		
		/**
		 * Current entry in iteration process.
		 */
		private TableEntry current;
		
		/**
		 * Previous entry in iteration process.
		 */
		private TableEntry previous;
		
		/**
		 * Current index of hash table bucket in iteration process.
		 */
		private int index;
		
		/**
		 * Number of modifications made in hash table.
		 */
		private int modCountIter;
		
		/**
		 * Default iterator constructor. Initializes iterator.
		 */
		
		public TableEntryIterator() {
			this.current = table[INIT];
			this.previous = null;
			this.modCountIter = modificationCount;
			this.index = INIT;
		}
		
		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public boolean hasNext(){
			if (modCountIter != modificationCount){
				throw new ConcurrentModificationException();
			}
			if (current != null){
				return true;
			}
			else{
				index++;
				for (int i = index; i < table.length; i++){
					if (table[i] != null){
						index = i;
						current = table[i];
						return true;
					}
				}
				return false;
			}
		}
		
		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public SimpleHashtable.TableEntry next(){
			if (modCountIter != modificationCount){
				throw new ConcurrentModificationException();
			}
			if (!hasNext()){
				throw new IllegalStateException("No more elements left!");
			}
			previous = current;
			current = current.next;
			return previous;
		}
		
		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public void remove(){
			if (modCountIter != modificationCount){
				throw new ConcurrentModificationException();
			}
			if (previous == null){
				throw new IllegalStateException("Illegal operation");
			}
			SimpleHashtable.this.remove(previous.key);
			previous = null;
			modCountIter++;
		}
	}
	
	/**
	 * Class which represents hash table entry (key,value).
	 * @author Tomislav
	 *
	 */
	
	public static class TableEntry{
		
		/**
		 * Object used as hash table key.
		 */
		private Object key;
		
		/**
		 * Object used as hash table value under provided key.
		 */
		
		private Object value;
		
		/**
		 * Next hash table entry.
		 */
		private TableEntry next;
		
		/**
		 * Hash table entry constructor.
		 * @param key Hash table key.
		 * @param value Hash table value stored under provided key.
		 * @param entry Next hash table entry.
		 */
		
		public TableEntry(Object key, Object value, TableEntry entry) {
			this.key = key;
			this.value = value;
			this.next = entry;
		}
		
		/**
		 * Method retrieves key of a hash map.
		 * @return Key of a hash map.
		 */

		public Object getKey() {
			return key;
		}
		
		/**
		 * Method retrieves value stored under key.
		 * @return Value stored under key.
		 */

		public Object getValue() {
			return value;
		}
		
		/**
		 * Method retrieves next hash table entry.
		 * @return Next hash table entry.
		 */

		public TableEntry getNext() {
			return next;
		}
		
	}
	
}
