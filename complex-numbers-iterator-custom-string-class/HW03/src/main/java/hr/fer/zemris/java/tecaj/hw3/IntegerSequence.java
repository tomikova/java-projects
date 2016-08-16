package hr.fer.zemris.java.tecaj.hw3;

import java.util.Iterator;

 /**
 * Klasa koja omogucava iteraciju od jednog do drugog Integer broja u zadanom koraku.
 * @author Tomislav
 */

public class IntegerSequence implements Iterable<Integer> {
	
	private int start;
	private int end;
	private int step;
	
	/**
	 * Konstruktor koji prima tri parametra.
	 * @param start Pocetni broj.
	 * @param end Krajnji broj.
	 * @param step Korak iteracije.
	 */
	
	public IntegerSequence(int start, int end, int step){
		if (!((start <= end && step > 0) || (start >= end && step < 0))){
			throw new IllegalArgumentException("Invalid arguments!");
		}
		this.start = start;
		this.end = end;
		this.step = step;
	}
	
	/**
	 * Metoda koja dohvaca iterator za iteraciju po elementima. 
	 */
	
	@Override
	public Iterator<Integer> iterator() {
		return new NumberIterator();
	}
	
	/**
	 * Klasa koja implementira iterator Integer  brojeva.
	 */
	
	private class NumberIterator implements Iterator<Integer>{
		
		private int current;
		
		/**
		 * Konstruktor iteratora.
		 */
		
		public NumberIterator(){
			this.current = start;
		}
		
		/**
		 * Metoda koja provjerava da li postoji sljedeci element niza.
		 * @return true/false vrijednost ovisno da li postoji sljedeci element niza.
		 */
		
		@Override 
		public boolean hasNext(){
			if (step > 0){
				return current <= end;
			}
			else{
				return current >= end;
			}
		}
		
		/**
		 * Metoda koja dohvaca sljedeci element niza.
		 * @return Sljedeci element niza.
		 */
		
		@Override
		public Integer next(){
			if (!hasNext()){
				throw new RuntimeException("No more elements left!");
			}
			int value = current;
			current += step;
			return value;
		}
		
		/**
		 * Metoda koja brise element niza i ovdje nije podrzana.
		 * @throws UnsupportedOperationException Metoda nije podrzana.
		 */
		
		@Override
		public void remove(){
			throw new UnsupportedOperationException("Operation not supported!");
		}	
	}	
}
