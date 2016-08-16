package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Interface for retrieving field in student record.
 * @author Tomislav
 *
 */
public interface IFieldValueGetter {
	/**
	 * Method for retrieving value of a field in StudentRecord.
	 * @param record Student record.
	 * @return Value of a field.
	 */
	public String get(StudentRecord record);
}
