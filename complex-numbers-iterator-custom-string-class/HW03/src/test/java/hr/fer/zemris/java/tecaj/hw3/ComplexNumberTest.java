package hr.fer.zemris.java.tecaj.hw3;

import org.junit.Test;

/**
 * Klasa u kojoj se nalaze testovi za ComplexNumber.
 * @author Tomislav
 */

import org.junit.Assert;

public class ComplexNumberTest {
	
	@Test
	public void fromRealTest() {
		ComplexNumber complex = ComplexNumber.fromReal(5.36);
		Assert.assertEquals(5.36,complex.getReal(), 1E-8);
	}
	
	@Test
	public void fromImaginaryTest() {
		ComplexNumber complex = ComplexNumber.fromImaginary(9.5945);
		Assert.assertEquals(9.5945,complex.getImaginary(), 1E-8);
	}
	
	@Test
	public void fromMagnitudeAndAngleTest() {
		ComplexNumber complex = ComplexNumber.fromMagnitudeAndAngle(5.14,Math.PI/3);
		String complexString = complex.toString();
		Assert.assertEquals("2,57+4,4514i",complexString);
	}
	
	@Test
	public void parseRealOnlyTest() {
		ComplexNumber complex = ComplexNumber.parse("4.598");
		String complexString = complex.toString();
		Assert.assertEquals("4,598",complexString);	
	}
	
	@Test
	public void parseImaginaryOnlyTest() {
		ComplexNumber complex = ComplexNumber.parse("i");
		String complexString = complex.toString();
		Assert.assertEquals("1i",complexString);	
	}
	
	@Test
	public void parseCompleteTest() {
		ComplexNumber complex = ComplexNumber.parse("+45.9-96.58i");
		String complexString = complex.toString();
		Assert.assertEquals("45,9-96,58i",complexString);	
	}
	
	@Test(expected=ComplexNumberException.class)
	public void complexNumberExceptionTest_1() {
		ComplexNumber.parse("+45sa.9-96.58i");
	}
	
	@Test(expected=ComplexNumberException.class)
	public void complexNumberExceptionTest_2() {
		ComplexNumber.parse("98..2");
	}
	
	@Test
	public void getMagnitudeTest() {
		ComplexNumber complex = ComplexNumber.fromMagnitudeAndAngle(5.14,Math.PI);
		Assert.assertEquals(5.14,complex.getMagnitude(), 1E-8);
	}
	
	@Test
	public void getAngleTest() {
		ComplexNumber complex = ComplexNumber.fromMagnitudeAndAngle(5.14,2*Math.PI/3);
		Assert.assertEquals(2.0943951,complex.getAngle(), 1E-7);
	}
	
	@Test
	public void addTest() {
		ComplexNumber complex1 = ComplexNumber.parse("2+3i");
		ComplexNumber complex2 = ComplexNumber.parse("7.81-6.12i");
		complex1 = complex1.add(complex2);
		String complexString = complex1.toString();
		Assert.assertEquals("9,81-3,12i",complexString);	
	}
	
	@Test
	public void subTest() {
		ComplexNumber complex1 = ComplexNumber.parse("2+3i");
		ComplexNumber complex2 = ComplexNumber.parse("7.81-6.12i");
		complex1 = complex1.sub(complex2);
		String complexString = complex1.toString();
		Assert.assertEquals("-5,81+9,12i",complexString);	
	}
	
	@Test
	public void mulTest() {
		ComplexNumber complex1 = ComplexNumber.parse("2+3i");
		ComplexNumber complex2 = ComplexNumber.parse("7.81-6.12i");
		complex1 = complex1.mul(complex2);
		String complexString = complex1.toString();
		Assert.assertEquals("33,98+11,19i",complexString);	
	}
	
	@Test
	public void divTest() {
		ComplexNumber complex1 = ComplexNumber.parse("2+3i");
		ComplexNumber complex2 = ComplexNumber.parse("7.81-6.12i");
		complex1 = complex1.div(complex2);
		String complexString = complex1.toString();
		Assert.assertEquals("-0,0278+0,3623i",complexString);	
	}
	
	@Test
	public void powerTest() {
		ComplexNumber complex = ComplexNumber.parse("7.81-6.12i");
		complex = complex.power(3);
		String complexString = complex.toString();
		Assert.assertEquals("-401,1771-890,6675i",complexString);	
	}
	
	@Test(expected=ComplexNumberException.class)
	public void complexNumberExceptionTest_3() {
		ComplexNumber complex = ComplexNumber.parse("7.81-6.12i");
		complex = complex.power(-2);
	}
	
	@Test
	public void rootTest() {
		ComplexNumber complex = ComplexNumber.parse("7.81-6.12i");
		ComplexNumber[] complexRoots = complex.root(3);
		String[] complexRootsStrings = new String[3];
		for(int i = 0; i < 3; i++){
			complexRootsStrings[i] = complexRoots[i].toString();
		}
		String[] expectedRoots = new String[] {"2,0963-0,4722i","-0,6392+2,0516i","-1,4571-1,5794i"};
		
		Assert.assertTrue(contains(expectedRoots[0],complexRootsStrings)
				         && contains(expectedRoots[1],complexRootsStrings)
				         && contains(expectedRoots[2],complexRootsStrings));	
	}
	
	@Test(expected=ComplexNumberException.class)
	public void complexNumberExceptionTest_4() {
		ComplexNumber complex = ComplexNumber.parse("7.81-6.12i");
		complex.root(0);
	}
	
	/**
	 * Metoda u kojoj se provjerava da li se korijen kompleksnog broja nalazi u
	 * polju korijena kompleksnog broja.
	 * @param root Kompleksni broj koji je jedan od korijena.
	 * @param rootsArray Polje korijena kompleksnog broja.
	 * @return true/false Ovisno da li je korijen zadrzan u polju korijena.
	 */
	
	private boolean contains(String root, String[] rootsArray){
		for (int i = 0; i < rootsArray.length; i++){
			if(rootsArray[i].equals(root)){
				return true;
			}
		}
		return false;
	}
}
