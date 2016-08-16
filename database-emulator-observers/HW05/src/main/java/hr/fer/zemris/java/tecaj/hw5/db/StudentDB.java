package hr.fer.zemris.java.tecaj.hw5.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Program for reading and executing queries provided by user.
 * In order for program to work a database.txt file must be present in current directory.
 * Query expressions must be separated with keyword " and ".
 * Program stops when user type keyword "quit".
 * @author Tomislav
 *
 */

public class StudentDB {

	public static void main(String[] args) throws IOException {
		
		List<String> lines = Files.readAllLines(Paths.get("./database.txt"),StandardCharsets.UTF_8);
		
		StudentDatabase studentDatabase = new StudentDatabase(lines);
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in)));
		
		while(true){
			System.out.print(">");
			String input = reader.readLine().trim();
			if (input.equalsIgnoreCase("quit")){
				break;
			}
			try{
				
				String queryString = QueryProcess.getQueryString(input);
			
				QueryFilter filter = new QueryFilter(queryString);
				List<StudentRecord> studentRecords = studentDatabase.filter(filter);
				
				QueryProcess.printToString(studentRecords);
				System.out.println(QueryProcess.getStringOutput());
				
			}catch(Exception ex){
				System.out.println(ex.getMessage());
				continue;
			}
		}
		
	}
}
