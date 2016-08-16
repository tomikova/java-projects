package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Class which implements IFieldValueGetter interface for retrieving 
 * last name field in student record.
 * @author Tomislav
 *
 */
public class LastNameGetter implements IFieldValueGetter {
	
	/**
	 * Method for retrieving value of last name field in StudentRecord.
	 * @param record Student record.
	 * @return Value of last name field.
	 */
	@Override
	public String get(StudentRecord record){
		return record.getLastName();
	}
}
