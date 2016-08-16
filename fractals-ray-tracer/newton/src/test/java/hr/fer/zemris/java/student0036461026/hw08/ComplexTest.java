package hr.fer.zemris.java.student0036461026.hw08;

import hr.fer.zemris.java.fractals.Complex;
import hr.fer.zemris.java.fractals.ComplexPolynomial;
import hr.fer.zemris.java.fractals.ComplexRootedPolynomial;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for Complex, ComplexPolynomial and ComplexRootedPolynomial.
 * @author Tomislav
 *
 */

public class ComplexTest {
	
	@Test
    public void complexConstructorsTest()
    {
		Complex complex1 = new Complex();
		Complex complex2 = new Complex(5,6);
        
        Assert.assertEquals("0", complex1.toString());
        Assert.assertEquals("5+6i", complex2.toString());
    }
	
	@Test
    public void complexGettersTest()
    {
		Complex complex = new Complex(5,6);
        
        Assert.assertEquals(5, complex.getRe(), 1e-6);
        Assert.assertEquals(6, complex.getIm(), 1e-6);
    }
	
	@Test
    public void complexAngleTest()
    {
		Complex complex = new Complex(5,6);
        
        Assert.assertEquals(0.8760575, complex.angle(), 1e-6);
    }
	
	@Test
    public void complexModuleTest()
    {
		Complex complex = new Complex(5,6);
        
        Assert.assertEquals(7.8102496, complex.module(), 1e-6);
    }
	
	@Test
    public void complexMultiplyTest()
    {
		Complex complex1 = new Complex(5,6);
		Complex complex2 = new Complex(7,-2);
		Complex res = complex1.multiply(complex2);     
        Assert.assertEquals("47+32i", res.toString());
        complex2 = new Complex();
        res = complex1.multiply(complex2);  
        Assert.assertEquals("0", res.toString());
    }
	
	@Test
    public void complexDivideTest()
    {
		Complex complex1 = new Complex(5,6);
		Complex complex2 = new Complex(7,-2);
		Complex res = complex1.divide(complex2);
        
        Assert.assertEquals("0,434+0,9811i", res.toString());
    }
	
	@Test
    public void complexPowerTest()
    {
		Complex complex = new Complex(5,6);
		Complex res = complex.power(4);
        
        Assert.assertEquals("-3479-1320i", res.toString());
    }
	
	@Test
    public void complexAddTest()
    {
		Complex complex1 = new Complex(5,6);
		Complex complex2 = new Complex(7,-2);
		Complex res = complex1.add(complex2);
        
        Assert.assertEquals("12+4i", res.toString());
    }
	
	@Test
    public void complexSubTest()
    {
		Complex complex1 = new Complex(5,6);
		Complex complex2 = new Complex(7,-2);
		Complex res = complex1.sub(complex2);
        
        Assert.assertEquals("-2+8i", res.toString());
    }
	
	@Test
    public void complexNegateTest()
    {
		Complex complex = new Complex(5,6);
		Complex res = complex.negate();
        
        Assert.assertEquals("-5-6i", res.toString());
    }
	
	@Test
    public void complexParseTest()
    {
		Complex complex = Complex.parse("1");      
        Assert.assertEquals("1", complex.toString());
        complex = Complex.parse("-1+i0");
        Assert.assertEquals("-1", complex.toString());
        complex = Complex.parse("i");
        Assert.assertEquals("1i", complex.toString());
        complex = Complex.parse("-i");
        Assert.assertEquals("-1i", complex.toString());
        complex = Complex.parse("+i");
        Assert.assertEquals("1i", complex.toString());
        complex = Complex.parse("i5");
        Assert.assertEquals("5i", complex.toString());
        complex = Complex.parse("-i5");
        Assert.assertEquals("-5i", complex.toString());   
        complex = Complex.parse("0-i1");
        Assert.assertEquals("-1i", complex.toString());
        complex = Complex.parse("2-i6");
        Assert.assertEquals("2-6i", complex.toString());
    }
	
	@Test
    public void ComplexRootedPolynomialApplyTest()
    {
		
		ComplexRootedPolynomial complexRooted = new ComplexRootedPolynomial(
				Complex.parse("1"), Complex.parse("-1+i0"), Complex.parse("i"), Complex.parse("0-i1"));
		
		Complex applyResult = complexRooted.apply(new Complex(5,0));
		Assert.assertEquals("624", applyResult.toString());
		applyResult = complexRooted.apply(new Complex(5,-3));
		Assert.assertEquals("-645-960i", applyResult.toString());
    }
	
	@Test
    public void ComplexRootedToStringTest()
    {	
		ComplexRootedPolynomial complexRooted = new ComplexRootedPolynomial(
				Complex.parse("1"), Complex.parse("-1+i0"), Complex.parse("i"), Complex.parse("0-i1"));

		Assert.assertEquals("(z-1)*(z+1)*(z-1i)*(z+1i)", complexRooted.toString());
		
		complexRooted = new ComplexRootedPolynomial(
				Complex.parse("0+i2"), Complex.parse("-1-i4"), Complex.parse("-i"), Complex.parse("1-i0"));
		Assert.assertEquals("(z-2i)*(z+1+4i)*(z+1i)*(z-1)", complexRooted.toString());
    }
	
	
	@Test
    public void ComplexPolynomialConstructorTest()
    {
		ComplexPolynomial polynomial = new ComplexPolynomial(
				Complex.ONE, Complex.ZERO, Complex.ZERO, Complex.ZERO, Complex.ONE);
		
		Assert.assertEquals("(1)+(0)z+(0)z^2+(0)z^3+(1)z^4", polynomial.toString());
		
		Assert.assertEquals(4, polynomial.order());
		
		polynomial = new ComplexPolynomial(
				Complex.parse("1"), Complex.parse("-1+i0"), Complex.parse("i"), Complex.parse("0-i1"));
		
		Assert.assertEquals("(1)+(-1)z+(1i)z^2+(-1i)z^3", polynomial.toString());
		
		polynomial = new ComplexPolynomial(
				Complex.parse("1"), Complex.parse("5"), Complex.parse("2"), Complex.parse("7+i2"));
		
		Assert.assertEquals("(1)+(5)z+(2)z^2+(7+2i)z^3", polynomial.toString());
		
		Assert.assertEquals(3, polynomial.order());
    }
	
	@Test
    public void ComplexPolynomialDeriveTest()
    {
		ComplexPolynomial polynomial = new ComplexPolynomial(
				Complex.parse("1"), Complex.parse("5"), Complex.parse("2"), Complex.parse("7+i2"));
		
		ComplexPolynomial derive = polynomial.derive();
		Assert.assertEquals("(5)+(4)z+(21+6i)z^2", derive.toString());
		Assert.assertEquals(2, derive.order());
		
		polynomial = new ComplexPolynomial(Complex.parse("5"));
		derive = polynomial.derive();
		Assert.assertEquals("(0)", derive.toString());
		Assert.assertEquals(0, derive.order());
    }
	
	@Test
    public void ComplexPolynomialApplyTest()
    {
		ComplexPolynomial polynomial = new ComplexPolynomial(
				Complex.ONE_NEG, Complex.ZERO, Complex.ZERO, Complex.ZERO, Complex.ONE);
		
		Complex result = polynomial.apply(new Complex(5,0));
		Assert.assertEquals("624", result.toString());		
    }
	
	@Test
    public void ComplexPolynomialMultiplyTest()
    {
		ComplexPolynomial polynomial1 = new ComplexPolynomial(
				Complex.ONE_NEG, Complex.ZERO, Complex.ZERO, Complex.ZERO, Complex.ONE);
		
		ComplexPolynomial polynomial2 = new ComplexPolynomial(
				Complex.ONE_NEG, Complex.ZERO, Complex.ZERO, Complex.ZERO, Complex.ONE);
		
		ComplexPolynomial result = polynomial1.multiply(polynomial2);
		
		Assert.assertEquals("(1)+(0)z+(0)z^2+(0)z^3+(-2)z^4+(0)z^5+(0)z^6+(0)z^7+(1)z^8", result.toString());
		Assert.assertEquals(8,result.order());
    }
	
	
	@Test
    public void ComplexRootedToComplexPolynomTest()
    {
		
		ComplexRootedPolynomial complexRooted = new ComplexRootedPolynomial(
				Complex.parse("1"), Complex.parse("-1+i0"), Complex.parse("i"), Complex.parse("0-i1"));
		
		ComplexPolynomial polynomial = complexRooted.toComplexPolynom();
		Complex applyResult = polynomial.apply(new Complex(5,0));
		Assert.assertEquals("624", applyResult.toString());
		applyResult = polynomial.apply(new Complex(5,-3));
		Assert.assertEquals("-645-960i", applyResult.toString());
		Assert.assertEquals(4, polynomial.order());
    }
	
	@Test
    public void indexOfClosestRootTest()
    {
		
		ComplexRootedPolynomial complexRooted = new ComplexRootedPolynomial(
				Complex.parse("1"), Complex.parse("-1+i0"), Complex.parse("i"), Complex.parse("0-i1"));
		
		int index = complexRooted.indexOfClosestRootFor(new Complex(-0.9995,0), 1e-2);
		Assert.assertEquals(1,index);
    }
}