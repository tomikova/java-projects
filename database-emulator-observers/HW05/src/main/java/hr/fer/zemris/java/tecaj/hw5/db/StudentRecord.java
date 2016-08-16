package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class models student record for storing in student database.
 * @author Tomislav
 *
 */

public class StudentRecord {
	
	/**
	 * Student jmbag.
	 */
	private String jmbag;
	/**
	 * Student last name.
	 */
	private String lastName;
	/**
	 * Student first name.
	 */
	private String firstName;
	/**
	 * Student final grade.
	 */
	private int finalGrade;
	
	/**
	 * Constructor with four parameters.
	 * @param jmbag Student jmbag.
	 * @param lastName Student last name.
	 * @param firstName Student first name.
	 * @param finalGrade Student final grade.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		checkData(jmbag, lastName, firstName, finalGrade);
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	
	/**
	 * Method retrieves student jmbag.
	 * @return Student jmbag.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Method retrieves student last name.
	 * @return Student last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Method retrieves student first name.
	 * @return Student first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Method retrieves student final grade.
	 * @return Student final grade.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj){
		if (obj instanceof StudentRecord){
			if (this.hashCode() == obj.hashCode()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode(){
		return this.jmbag.hashCode();
	}
	
	/**
	 * Method checks if student record is in expected format.
	 * @param jmbag Student jmbag.
	 * @param lastName Student last name.
	 * @param firstName Student first name.
	 * @param finalGrade Student final grade.
	 * throws IllegalArgumentException
	 * If jmbag is not numeric.
	 * If first or last name contains characters which are not letters.
	 * If final grade is not between one and five.
	 */
	private void checkData(String jmbag, String lastName, String firstName, int finalGrade){
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(jmbag);
		if(!matcher.matches()){
			throw new IllegalArgumentException("JMBAG must be numeric");
		}
		pattern = Pattern.compile("\\p{L}+");
		String[] multipleLastNames = lastName.split("\\s+|-");
		for (String name : multipleLastNames){
			matcher = pattern.matcher(name);
			if(!matcher.matches()){
				throw new IllegalArgumentException("Invalid last name");
			}
		}
		String[] multipleFirstNames = firstName.split("\\s+|-");
		for (String name : multipleFirstNames){
			matcher = pattern.matcher(name);
			if(!matcher.matches()){
				throw new IllegalArgumentException("Invalid first name");
			}
		}
		if (finalGrade < 1 || finalGrade > 5){
			throw new IllegalArgumentException("Invalid final grade, must be between 1-5");
		}
	}
}
