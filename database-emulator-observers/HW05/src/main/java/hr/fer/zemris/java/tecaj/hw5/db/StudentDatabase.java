package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Class models database of student records.
 * @author Tomislav
 *
 */
public class StudentDatabase {
	
	/**
	 * List which holds student records.
	 */
	private LinkedList<StudentRecord> students;
	/**
	 * Map for fast student record access using jmbag as key.
	 */
	private HashMap<String, StudentRecord> jmbagIndex;
	
	/**
	 * Constructor with one parameter.
	 * @param students List of students records in string format.
	 * @throws IllegalArgumentException
	 * If the number of data for one student record is not four.
	 * If final grade is not natural number.
	 */
	public StudentDatabase(List<String> students) {
		this.students = new LinkedList<StudentRecord>();
		this.jmbagIndex = new HashMap<String, StudentRecord>();
		for (String student : students){
			String[] data = student.split("\\t+");
			if (data.length != 4){
				throw new IllegalArgumentException("Expected number of student record data is 4");
			}
			String jmbag = data[0];
			String lastName = data[1];
			String firstName = data[2];
			int finalGrade;
			try{
				 finalGrade = Integer.parseInt(data[3]);
			}catch(Exception ex){
				throw new IllegalArgumentException("Final grade must be natural number");
			}	
			StudentRecord studentRecord = new StudentRecord(jmbag, lastName, firstName, finalGrade);
			this.students.add(studentRecord);
			this.jmbagIndex.put(jmbag, studentRecord);
		}
	}
	
	/**
	 * Method retrieves list of student records.
	 * @return List of student records.
	 */
	public LinkedList<StudentRecord> getStudents() {
		return students;
	}

	/**
	 * Method retrieves map for fast student record access using jmbag as key.
	 * @return Map for fast student record access using jmbag as key.
	 */
	public HashMap<String, StudentRecord> getJmbagIndex() {
		return jmbagIndex;
	}

	/**
	 * Method retrieves student record based on his jmbag.
	 * @param jmbag Jmbag of a student.
	 * @return Student record based on his jmbag. Returns null if record is not found.
	 */
	public StudentRecord forJMBAG(String jmbag){
		return jmbagIndex.get(jmbag);
	}
	
	/**
	 * Method retrieves list of student records which satisfies conditions provided with filter.
	 * @param filter Object with conditions student record must satisfy in order to be placed in result list.
	 * @return List of student records which satisfies conditions provided with filter.
	 */
	public List<StudentRecord> filter(IFilter filter){
		LinkedList<StudentRecord> filterList = new LinkedList<StudentRecord>();
		if (filter instanceof QueryFilter && ((QueryFilter) filter).getJMBAG().isPresent()){
			System.out.println("Using index for record retrieval.");
			StudentRecord student = forJMBAG(((QueryFilter) filter).getJMBAG().get());
			if (student != null && filter.accepts(student)){
				filterList.add(student);
			}
		}
		else{
			for (StudentRecord student : students){
				if (filter.accepts(student)){
					filterList.add(student);
				}
			}
		}
		return filterList;
	}
}
