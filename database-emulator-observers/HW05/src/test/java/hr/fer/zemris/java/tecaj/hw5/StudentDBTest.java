package hr.fer.zemris.java.tecaj.hw5;

import hr.fer.zemris.java.tecaj.hw5.db.QueryFilter;
import hr.fer.zemris.java.tecaj.hw5.db.QueryProcess;
import hr.fer.zemris.java.tecaj.hw5.db.StudentDatabase;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class for testing work of student database.
 * @author Tomislav
 *
 */
public class StudentDBTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void queryInputCheckEmptyTest() {
		QueryProcess.getQueryString("");
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void queryInputCheckIllegalOperationTest() {
		QueryProcess.getQueryString("stop firstName=\"B*nić\" and jmbag=\"0000000004\"");
	}
	
	@Test
	public void getQueryStringTest() {
		String queryString = QueryProcess.getQueryString("query firstName=\"B*nić\" and jmbag=\"0000000004\"");
		Assert.assertEquals("firstName=\"B*nić\" and jmbag=\"0000000004\"",queryString);
	}
	
	@Test
	public void gqueryPrintTestTest() {
		LinkedList<StudentRecord> students = new LinkedList<StudentRecord>();
		StudentRecord student = new StudentRecord("0000000004", "Božić", "Marin", 5);
		students.add(student);
		QueryProcess.printToString(students);
		String output = "+============+=======+=======+===+"+
				        "\n| 0000000004 | Božić | Marin | 5 |"+
				        "\n+============+=======+=======+===+"+
				        "\nRecords selected: 1";
		Assert.assertEquals(output,QueryProcess.getStringOutput());
	}
	
	@Test
	public void studentDBConstructorTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000001	Akšamović	Marin	2");
		students.add("0000000004	Božić	Marin	5");
		students.add("0000000028	Kosanović	Nenad	5");
		StudentDatabase studentDatabase = new StudentDatabase(students);
		
		List<StudentRecord> studentRecords = studentDatabase.getStudents();
		Map<String, StudentRecord> index = studentDatabase.getJmbagIndex();
		
		Assert.assertEquals(3, studentRecords.size());
		Assert.assertTrue(index.containsKey("0000000001"));
		Assert.assertTrue(index.containsKey("0000000004"));
		Assert.assertTrue(index.containsKey("0000000028"));
		Assert.assertFalse(index.containsKey("0000000015"));	
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void studentDBInvalidRecordSizeTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000004	Božić	Marin	5	57");
		new StudentDatabase(students);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void finalGradeNotNaturalNumberTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000001	Akšamović	Marin	dva");
		new StudentDatabase(students);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void jmbagNotNumericTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("some_jmbag	Akšamović	Marin	2");
		new StudentDatabase(students);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void invalidLastNameTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000001	Akšam589ović	Marin	2");
		new StudentDatabase(students);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void invalidFirstNameTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000001	Akšamović	Marin584	2");
		new StudentDatabase(students);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void finalGradeNotBetweenOneAndFiveTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000004	Božić	Marin	7");
		new StudentDatabase(students);
	}
	
	@Test
	public void studentRecordEqualTest() {
		StudentRecord student1 = new StudentRecord("0000000004", "Bičanić", "Ivan", 5);
		StudentRecord student2 = new StudentRecord("0000000004", "Šimunić", "Ana", 2);
		
		Assert.assertTrue(student1.equals(student2));
	}
	
	@Test
	public void studentRecordNotEqualTest() {
		StudentRecord student1 = new StudentRecord("0000000004", "Bičanić", "Ivan", 5);
		StudentRecord student2 = new StudentRecord("0000000007", "Šimunić", "Ana", 2);
		
		Assert.assertFalse(student1.equals(student2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void queryFilterFieldNotValidTest() {
		new QueryFilter("\"Ante\"=firstName");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void queryFilterFieldDoesntExistTest() {
		new QueryFilter("notExistingField=\"Ante\"");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void queryFilterRightSideNotValidTest() {
		new QueryFilter("firstName=lastName");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void queryFilterMoreThanOneWildCardTest() {
		new QueryFilter("firstName=\"B*n*ić\"");
	}
	
	@Test
	public void queryFilterOptionalJmbagTest() {
		QueryFilter filter = new QueryFilter("firstName=\"B*nić\" and jmbag=\"0000000004\"");
		Assert.assertTrue(filter.getJMBAG().isPresent());
		Assert.assertEquals("0000000004",filter.getJMBAG().get());
	}
	
	@Test
	public void queryFilterAcceptsTest() {
		QueryFilter filter = new QueryFilter("lastName=\"B*nić\" and jmbag=\"0000000004\"");
		StudentRecord studentRecord = new StudentRecord("0000000004", "Bičanić", "Ivan", 5);
		Assert.assertTrue(filter.accepts(studentRecord));
	}
	
	@Test
	public void studentDBForJmbagTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000001	Akšamović	Marin	2");
		students.add("0000000004	Božić	Marin	5");
		students.add("0000000028	Kosanović	Nenad	5");
		StudentDatabase studentDatabase = new StudentDatabase(students);
		
		StudentRecord studentRecord = studentDatabase.forJMBAG("0000000028");
		Assert.assertEquals("0000000028", studentRecord.getJmbag());
		Assert.assertEquals("Kosanović", studentRecord.getLastName());
		Assert.assertEquals("Nenad", studentRecord.getFirstName());
		Assert.assertEquals(5, studentRecord.getFinalGrade());
	}
	
	@Test
	public void studentDBFilterTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000001	Bakšamović	Marin	2");
		students.add("0000000004	Božić	Marin	5");
		students.add("0000000028	Bosanović	Nenad	5");
		StudentDatabase studentDatabase = new StudentDatabase(students);
		QueryFilter filter = new QueryFilter("lastName=\"B*\"");
		List<StudentRecord> result = studentDatabase.filter(filter);
		Assert.assertEquals(3,result.size());
		Assert.assertEquals("Božić",result.get(1).getLastName());
	}
	
	@Test
	public void qeryWildCardEndTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000034	Majić	Diana	3");
		students.add("0000000035	Marić	Ivan	4");
		students.add("0000000036	Markić	Ante	5");
		students.add("0000000037	Markoč	Domagoj	2");
		students.add("0000000038	Markotić	Josip	3");
		students.add("0000000039	Martinec	Jelena	4");
		StudentDatabase studentDatabase = new StudentDatabase(students);
		QueryFilter filter = new QueryFilter("lastName=\"Marko*\"");
		List<StudentRecord> result = studentDatabase.filter(filter);
		
		String resultString = "";
		
		for(StudentRecord record : result){
			resultString += record.getJmbag()+record.getLastName()+record.getFirstName()+record.getFinalGrade();
		}
	
		Assert.assertEquals("0000000037MarkočDomagoj2"+
							"0000000038MarkotićJosip3",resultString);
	}
	
	@Test
	public void qeryWildCardBeginningTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000034	Majić	Diana	3");
		students.add("0000000035	Marić	Ivan	4");
		students.add("0000000036	Markić	Ante	5");
		students.add("0000000037	Markoč	Domagoj	2");
		students.add("0000000038	Markotić	Josip	3");
		students.add("0000000039	Martinec	Jelena	4");
		StudentDatabase studentDatabase = new StudentDatabase(students);
		QueryFilter filter = new QueryFilter("lastName=\"*ec\"");
		List<StudentRecord> result = studentDatabase.filter(filter);
		
		String resultString = "";
		
		for(StudentRecord record : result){
			resultString += record.getJmbag()+record.getLastName()+record.getFirstName()+record.getFinalGrade();
		}
	
		Assert.assertEquals("0000000039MartinecJelena4",resultString);
	}
	
	@Test
	public void qeryWildCardMiddleTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000034	Majić	Diana	3");
		students.add("0000000035	Marić	Ivan	4");
		students.add("0000000036	Markić	Ante	5");
		students.add("0000000037	Markoč	Domagoj	2");
		students.add("0000000038	Markotić	Josip	3");
		students.add("0000000039	Martinec	Jelena	4");
		StudentDatabase studentDatabase = new StudentDatabase(students);
		QueryFilter filter = new QueryFilter("lastName=\"Mar*nec\"");
		List<StudentRecord> result = studentDatabase.filter(filter);
		
		String resultString = "";
		
		for(StudentRecord record : result){
			resultString += record.getJmbag()+record.getLastName()+record.getFirstName()+record.getFinalGrade();
		}
	
		Assert.assertEquals("0000000039MartinecJelena4",resultString);
	}
	
	@Test
	public void qeryGreaterThanOperatorTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000034	Majić	Diana	3");
		students.add("0000000035	Marić	Ivan	4");
		students.add("0000000036	Markić	Ante	5");
		students.add("0000000037	Markoč	Domagoj	2");
		students.add("0000000038	Markotić	Josip	3");
		students.add("0000000039	Martinec	Jelena	4");
		StudentDatabase studentDatabase = new StudentDatabase(students);
		QueryFilter filter = new QueryFilter("firstName>\"Ivan\"");
		List<StudentRecord> result = studentDatabase.filter(filter);
		
		String resultString = "";
		
		for(StudentRecord record : result){
			resultString += record.getJmbag()+record.getLastName()+record.getFirstName()+record.getFinalGrade();
		}
		
		Assert.assertEquals("0000000038MarkotićJosip3"+
							"0000000039MartinecJelena4",resultString);
	}
	
	@Test
	public void qeryGreaterOrEqualOperatorTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000034	Majić	Diana	3");
		students.add("0000000035	Marić	Ivan	4");
		students.add("0000000036	Markić	Ante	5");
		students.add("0000000037	Markoč	Domagoj	2");
		students.add("0000000038	Markotić	Josip	3");
		students.add("0000000039	Martinec	Jelena	4");
		StudentDatabase studentDatabase = new StudentDatabase(students);
		QueryFilter filter = new QueryFilter("firstName>=\"Ivan\"");
		List<StudentRecord> result = studentDatabase.filter(filter);
		
		String resultString = "";
		
		for(StudentRecord record : result){
			resultString += record.getJmbag()+record.getLastName()+record.getFirstName()+record.getFinalGrade();
		}
		
		Assert.assertEquals("0000000035MarićIvan4"
				            +"0000000038MarkotićJosip3"
				            +"0000000039MartinecJelena4",resultString);
	}
	
	@Test
	public void qeryLessThanTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000034	Majić	Diana	3");
		students.add("0000000035	Marić	Ivan	4");
		students.add("0000000036	Markić	Ante	5");
		students.add("0000000037	Markoč	Domagoj	2");
		students.add("0000000038	Markotić	Josip	3");
		students.add("0000000039	Martinec	Jelena	4");
		StudentDatabase studentDatabase = new StudentDatabase(students);
		QueryFilter filter = new QueryFilter("firstName>=\"Ivan\" and jmbag<\"0000000039\"");
		List<StudentRecord> result = studentDatabase.filter(filter);
		
		String resultString = "";
		
		for(StudentRecord record : result){
			resultString += record.getJmbag()+record.getLastName()+record.getFirstName()+record.getFinalGrade();
		}
		
		Assert.assertEquals("0000000035MarićIvan4"
				            +"0000000038MarkotićJosip3",resultString);
	}
	
	@Test
	public void qeryLessOrEqualTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000034	Majić	Diana	3");
		students.add("0000000035	Marić	Ivan	4");
		students.add("0000000036	Markić	Ante	5");
		students.add("0000000037	Markoč	Domagoj	2");
		students.add("0000000038	Markotić	Josip	3");
		students.add("0000000039	Martinec	Jelena	4");
		StudentDatabase studentDatabase = new StudentDatabase(students);
		QueryFilter filter = new QueryFilter("jmbag<=\"0000000035\"");
		List<StudentRecord> result = studentDatabase.filter(filter);
		
		String resultString = "";
		
		for(StudentRecord record : result){
			resultString += record.getJmbag()+record.getLastName()+record.getFirstName()+record.getFinalGrade();
		}
		
		Assert.assertEquals("0000000034MajićDiana3"
				            +"0000000035MarićIvan4",resultString);
	}
	
	@Test
	public void qeryEqualTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000034	Majić	Diana	3");
		students.add("0000000035	Marić	Ivan	4");
		students.add("0000000036	Markić	Ante	5");
		students.add("0000000037	Markoč	Domagoj	2");
		students.add("0000000038	Markotić	Josip	3");
		students.add("0000000039	Martinec	Jelena	4");
		StudentDatabase studentDatabase = new StudentDatabase(students);
		QueryFilter filter = new QueryFilter("firstName=\"Ante\" and lastName=\"Markić\"");
		List<StudentRecord> result = studentDatabase.filter(filter);
		
		String resultString = "";
		
		for(StudentRecord record : result){
			resultString += record.getJmbag()+record.getLastName()+record.getFirstName()+record.getFinalGrade();
		}
		
		Assert.assertEquals("0000000036MarkićAnte5",resultString);
	}
	
	@Test
	public void qeryEqualDoesntExistTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000034	Majić	Diana	3");
		students.add("0000000035	Marić	Ivan	4");
		students.add("0000000036	Markić	Ante	5");
		students.add("0000000037	Markoč	Domagoj	2");
		students.add("0000000038	Markotić	Josip	3");
		students.add("0000000039	Martinec	Jelena	4");
		StudentDatabase studentDatabase = new StudentDatabase(students);
		QueryFilter filter = new QueryFilter("jmbag=\"   0000000036\"");
		List<StudentRecord> result = studentDatabase.filter(filter);
		Assert.assertEquals(0, result.size());
	}
	
	@Test
	public void qeryEqualIndexTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000034	Majić	Diana	3");
		students.add("0000000035	Marić	Ivan	4");
		students.add("0000000036	Markić	Ante	5");
		students.add("0000000037	Markoč	Domagoj	2");
		students.add("0000000038	Markotić	Josip	3");
		students.add("0000000039	Martinec	Jelena	4");
		StudentDatabase studentDatabase = new StudentDatabase(students);
		QueryFilter filter = new QueryFilter("jmbag=\"0000000036\"");
		List<StudentRecord> result = studentDatabase.filter(filter);
		
		String resultString = "";
		
		for(StudentRecord record : result){
			resultString += record.getJmbag()+record.getLastName()+record.getFirstName()+record.getFinalGrade();
		}
		
		Assert.assertEquals("0000000036MarkićAnte5",resultString);
	}
	
	@Test
	public void qeryEqualIndexNotPossibleTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000034	Majić	Diana	3");
		students.add("0000000035	Marić	Ivan	4");
		students.add("0000000056	Markić	Ante	5");
		students.add("0000000037	Markoč	Domagoj	2");
		students.add("0000000038	Markotić	Josip	3");
		students.add("0000000049	Martinec	Jelena	4");
		StudentDatabase studentDatabase = new StudentDatabase(students);
		QueryFilter filter = new QueryFilter("jmbag=\"000000003*\"");
		List<StudentRecord> result = studentDatabase.filter(filter);
		
		Assert.assertEquals(4,result.size());
	}
	
	@Test
	public void qeryNotEqualTest() {
		LinkedList<String> students = new LinkedList<String>();
		students.add("0000000034	Majić	Diana	3");
		students.add("0000000035	Marić	Ivan	4");
		students.add("0000000036	Markić	Ante	5");
		students.add("0000000037	Markoč	Domagoj	2");
		students.add("0000000038	Markotić	Josip	3");
		students.add("0000000039	Martinec	Jelena	4");
		StudentDatabase studentDatabase = new StudentDatabase(students);
		QueryFilter filter = new QueryFilter("firstName!=\"Jelena\" and lastName!=\"Markoč\" and jmbag!=\"0000000035\"");
		List<StudentRecord> result = studentDatabase.filter(filter);
		
		String resultString = "";
		
		for(StudentRecord record : result){
			resultString += record.getJmbag()+record.getLastName()+record.getFirstName()+record.getFinalGrade();
		}
		
		Assert.assertEquals("0000000034MajićDiana30000000036MarkićAnte5"
				            +"0000000038MarkotićJosip3",resultString);
	}
	
	
}
