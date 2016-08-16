package hr.fer.zemris.java.tecaj.hw4.collections.demo;

import java.util.Iterator;
import hr.fer.zemris.java.tecaj.hw4.collections.SimpleHashtable;

/**
 * Program demonstrates work of hash table.
 * @author Tomislav
 *
 */

public class SimpleHashtableDemo {
	
	/**
	 * Method called at the beginning of program.
	 * @param args Command line arguments.
	 */

	public static void main(String[] args) {
		
		SimpleHashtable examMarks = new SimpleHashtable(2);
		// fill data:
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5)); // overwrites old grade for Ivana
		// query collection:
		Integer kristinaGrade = (Integer)examMarks.get("Kristina");
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
		// What is collection's size? Must be four!
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
		
		System.out.println(examMarks.toString());
		
		System.out.println();
		
		
		for(SimpleHashtable.TableEntry pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}
		
		System.out.println();
		
		for(SimpleHashtable.TableEntry pair1 : examMarks) {
			for(SimpleHashtable.TableEntry pair2 : examMarks) {		
				System.out.printf("(%s => %d) - (%s => %d)%n",
						pair1.getKey(), pair1.getValue(),
						pair2.getKey(), pair2.getValue());
			}
		}
		
		Iterator<SimpleHashtable.TableEntry> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove();
			}
		}
		
		System.out.println();
		
		for(SimpleHashtable.TableEntry pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}
		
	}

}
