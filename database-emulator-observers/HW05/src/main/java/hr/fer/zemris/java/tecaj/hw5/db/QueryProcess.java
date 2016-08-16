package hr.fer.zemris.java.tecaj.hw5.db;

import java.io.StringWriter;
import java.util.List;

/**
 * Class for processing query and its results.
 * @author Tomislav
 *
 */

public final class QueryProcess {
	
	/**
	 * StringWriter object which holds textual output of query result.
	 */
	private static StringWriter stringWriter;
	
	/**
	 * Method retrieves query result in string representation.
	 * @return String Query result in string representation.
	 */
	public static String getStringOutput() {
		return stringWriter.toString();
	}
	
	/**
	 * Method retrieves query string without keyword "query" from user input. 
	 * @param input User input for a query.
	 * @return Query string.
	 * @throws IllegalArgumentException
	 * If user input is empty.
	 * If keyword "query" doesn't exist as first word of user input for a query.
	 */
	public static String getQueryString(String input){
		if (input.isEmpty()){
			throw new IllegalArgumentException("No arguments provided");
		}
		
		if (!input.split("\\s+")[0].equals("query")){
			throw new UnsupportedOperationException("Operation is not supported");
		}
		return input.split("query")[1].trim();
	}
	
	/**
	 * Method processes and stores query results in textual format. 
	 * @param studentRecords List of StudentRecord query results.
	 */
	public static void printToString(List<StudentRecord> studentRecords){
		stringWriter = new StringWriter();
		if (!studentRecords.isEmpty()){
			int largestLastNameSize = 0;
			int largestFirstNameSize = 0;
			
			for (StudentRecord record : studentRecords){
				int lastNameLength = record.getLastName().length();
				int firstNameLength = record.getFirstName().length();
				if (lastNameLength > largestLastNameSize){
					largestLastNameSize = lastNameLength;
				}
				if (firstNameLength > largestFirstNameSize){
					largestFirstNameSize = firstNameLength;
				}
			}
			stringWriter.write("+============+");
			for(int i = 0; i < 2+largestLastNameSize; i++){
				stringWriter.write("=");
			}
			stringWriter.write("+");
			for(int i = 0; i < 2+largestFirstNameSize; i++){
				stringWriter.write("=");
			}
			stringWriter.write("+===+");
			
			for (StudentRecord record : studentRecords){
				stringWriter.write("\n| "+record.getJmbag()+" |");
				stringWriter.write(" "+record.getLastName());
				for(int i = 0, length = record.getLastName().length(); i <= largestLastNameSize-length; i++){
					stringWriter.write(" ");
				}
				stringWriter.write("| "+record.getFirstName());
				for(int i = 0, length = record.getFirstName().length(); i <= largestFirstNameSize-length; i++){
					stringWriter.write(" ");
				}
				stringWriter.write("| "+record.getFinalGrade()+" |");
			}
			stringWriter.write("\n+============+");
			for(int i = 0; i < 2+largestLastNameSize; i++){
				stringWriter.write("=");
			}
			stringWriter.write("+");
			for(int i = 0; i < 2+largestFirstNameSize; i++){
				stringWriter.write("=");
			}
			stringWriter.write("+===+");
		}
		stringWriter.write("\nRecords selected: "+studentRecords.size());
	}

}
