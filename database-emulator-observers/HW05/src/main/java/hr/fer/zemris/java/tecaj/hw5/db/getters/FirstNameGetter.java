package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Class which implements IFieldValueGetter interface for retrieving 
 * first name field in student record.
 * @author Tomislav
 *
 */
public class FirstNameGetter implements IFieldValueGetter {

	/**
	 * Method for retrieving value of first name field in StudentRecord.
	 * @param record Student record.
	 * @return Value of first name field.
	 */
	@Override
	public String get(StudentRecord record){
		return record.getFirstName();
	}
}
