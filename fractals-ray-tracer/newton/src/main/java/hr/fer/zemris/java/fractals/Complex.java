package hr.fer.zemris.java.fractals;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class represents complex number and provides operations on complex numbers. 
 * @author Tomislav
 *
 */

public class Complex {

	/**
	 * Real part of complex number.
	 */
	private double re;
	/**
	 * Imaginary part of complex number.
	 */
	private double im;
	/**
	 * Default value for real and imaginary parts.
	 */
	private static final double DEFAULT = 0;
	
	/**
	 * Complex number with real and imaginary parts 0.
	 */
	public static final Complex ZERO = new Complex(0,0);
	/**
	 * Complex number with real part 1.
	 */
	public static final Complex ONE = new Complex(1,0);
	/**
	 * Complex number with real part -1.
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	/**
	 * Complex number with imaginary part 1.
	 */
	public static final Complex IM = new Complex(0,1);
	/**
	 * Complex number with imaginary part -1.
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Default constructor without parameters.
	 */
	public Complex() {
		this(DEFAULT,DEFAULT);
	}
	
	/**
	 * Constructor with two parameters.
	 * @param re Real part of complex number.
	 * @param im Imaginary part of complex number.
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Method returns real part of complex number.
	 * @return Real part of complex number.
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Method returns imaginary part of complex number.
	 * @return Imaginary part of complex number.
	 */
	public double getIm() {
		return im;
	}

	/**
	 * Method returns angle of complex number.
	 * @return Angle of complex number.
	 */
	public double angle(){
		double angle = Math.atan2(im,re);
		if (angle < 0){
			angle += 2*Math.PI;
		}
		return angle;
	}
	
	/**
	 * Method returns module of complex number.
	 * @return Module of complex number.
	 */
	public double module() {
		return  Math.sqrt(Math.pow(re,2) + Math.pow(im, 2));
	}
	
	/**
	 * Method multiplies two complex numbers.
	 * @param c Complex number multiplier.
	 * @return Complex number result of multiplication.
	 */
	public Complex multiply(Complex c) {
		double mulModule = this.module()*c.module();
		double mulAngle = this.angle()+c.angle();
		return new Complex(mulModule*Math.cos(mulAngle),mulModule*Math.sin(mulAngle));
	}
	
	/**
	 * Method divides two complex numbers.
	 * @param c Complex number divisor.
	 * @return Complex number result of division.
	 */
	public Complex divide(Complex c) {
		double mulModule = this.module()/c.module();
		double mulAngle = this.angle()-c.angle();
		return new Complex(mulModule*Math.cos(mulAngle),mulModule*Math.sin(mulAngle));
	}
	
	/**
	 * Method calculates power of complex number.
	 * @param power Power.
	 * @return Complex number result of power operation.
	 */
	public Complex power(int power){
		if (power < 0){
			throw new IllegalArgumentException("Power must be non-negative number.");
		}
		double powerModule = Math.pow(this.module(), power);
		double powerAngle = power*this.angle();
		return new Complex(powerModule*Math.cos(powerAngle),powerModule*Math.sin(powerAngle));
	}
	
	/**
	 * Method adds two complex numbers.
	 * @param c Complex number for add.
	 * @return Complex number result of add operation.
	 */
	public Complex add(Complex c) {
		return new Complex(re+c.re,im+c.im);
	}
	
	/**
	 * Method subtracts two complex numbers.
	 * @param c Complex number to be subtracted.
	 * @return Complex number result of subtract operation.
	 */
	public Complex sub(Complex c) {
		return new Complex(re-c.re,im-c.im);
	}
	
	/**
	 * Method negates complex number.
	 * @return Negated complex number.
	 */
	public Complex negate() {
		return new Complex(-re,-im);
	}
	
	/**
	 * Method returns string form of complex number.
	 * @return String form of complex number.
	 */
	@Override
	public String toString() { 
		String complexString = "";
		DecimalFormat decimFormat = new DecimalFormat("0.####;-0.####");
		if (!(Math.abs(re-0) < 1e-10)){
			complexString += decimFormat.format(re);
		}
		if (!(Math.abs(im-0) < 1e-10)){
			complexString += (re != 0 && im > 0 ? "+" : "") + decimFormat.format(im)+"i";
		}
		if (complexString.equals("")){
			complexString = "0";
		}	
		return complexString;
	}
	
	/**
	 * Method for parsing complex numbers from string.
	 * Expected formats:
	 * *[+/-][num_value][+/-][i][num_value]
	 * *[+/-][num-value]
	 * *[+/-][i]*[num_value]
	 * fields with * are optional.
	 * @param string String to be parsed.
	 * @return Parsed complex number.
	 * @throws IllegalArgumentException If string is not complex number.
	 */
	public static Complex parse(String string){
		string = string.replaceAll("\\s+","");
		if (string.equals("i") || string.equals("+i")){
			return Complex.IM;
		}
		if (string.equals("-i")){
			return Complex.IM_NEG;
		}
		Pattern pattern = Pattern.compile("^([+-]?)i(\\d*\\.??\\d+)$");
		Matcher matcher = pattern.matcher(string);
		if(matcher.matches()){
			double imaginary = Double.parseDouble(matcher.group(2));
			String sign = matcher.group(1);
			if (sign.equals("-")) {
				imaginary = -imaginary;
			}
			return new Complex(0,imaginary);
		}
		pattern = Pattern.compile("^([+-]?\\d*\\.??\\d+)$");
		matcher = pattern.matcher(string);
		if(matcher.matches()){
			double real = Double.parseDouble(matcher.group(1));
			return new Complex(real, 0);
		}
		pattern = Pattern.compile("^([+-]?\\d*\\.??\\d+)([+-]?)i(\\d*\\.??\\d+)$");
		matcher = pattern.matcher(string);
		if(matcher.matches()){
			double real = Double.parseDouble(matcher.group(1));
			double imaginary = Double.parseDouble(matcher.group(3));
			String sign = matcher.group(2);
			if (sign.equals("-")) {
				imaginary = -imaginary;
			}
			return new Complex(real,imaginary);
		}
		else{
		  throw new IllegalArgumentException("Unable to parse complex number.");
		}
	}
}
