package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Class which implements IFieldValueGetter interface for retrieving 
 * jmbag field in student record.
 * @author Tomislav
 *
 */
public class JmbagGetter implements IFieldValueGetter {
	
	/**
	 * Method for retrieving value of jmbag field in StudentRecord.
	 * @param record Student record.
	 * @return Value of jmbag field.
	 */
	@Override
	public String get(StudentRecord record){
		return record.getJmbag();
	}
}
