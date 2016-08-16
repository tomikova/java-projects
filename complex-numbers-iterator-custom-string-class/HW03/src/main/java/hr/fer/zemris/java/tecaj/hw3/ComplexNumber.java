package hr.fer.zemris.java.tecaj.hw3;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Klasa koja pruza podrsku za rad s kompleksnim brojevima.
 * @author Tomislav
 */

public class ComplexNumber {
	
	private double real;
	private double imaginary;
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int NEG_ONE = -1;
	
	/**
	 * Konstruktor koji prima dva parametra.
	 * @param real Realni dio kompleksnog broja.
	 * @param imaginary Imaginarni dio kompleksnog broja.
	 */
	
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Metoda koja stvara kompleksni broj na osnovu samo realnog dijela.
	 * Imaginarni dio se postavlja na vrijednost 0.
	 * @param real Realni dio kompleksnog broja.
	 * @return Stvoreni kompleksni broj.
	 */
	
	public static ComplexNumber fromReal(double real){
		return new ComplexNumber(real,ZERO);
	}
	
	/**
	 * Metoda koja stvara kompleksni broj na osnovu samo imaginarnog dijela.
	 * Realni dio se postavlja na vrijednost 0.
	 * @param imaginary Imaginarni dio kompleksnog broja.
	 * @return Stvoreni kompleksni broj.
	 */
	
	public static ComplexNumber fromImaginary(double imaginary){
		return new ComplexNumber(ZERO,imaginary);
	}
	
	/**
	 * Metoda koja stvara kompleksni broj na osnovu modula i kuta.
	 * @param magnitude Modul kompleksnog broja.
	 * @param angle Kut kompleksnog broja.
	 * @return Stvoreni kompleksni broj.
	 */
	
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle){
		return new ComplexNumber(magnitude*Math.cos(angle),magnitude*Math.sin(angle));
	}
	
	/**
	 * Metoda koja parsira kompleksni broj iz string vrijednosti.
	 * Puni format kompleksnog broja koji se trazi je: 
	 * *[+/-][numericka vrijednost][+/-][numericka vrijedost][i]
	 * Ostali formati koji se prihvacaju su:
	 * *[+/-][numericka vrijednost] te 
	 * *[+/-]*[numericka vrijednost][i]
	 * * vrijednost je opcionalna.
	 * @param string  String koji se pokusava parsirati kao kompleksni broj.
	 * @return Stvoreni kompleksni broj.
	 * @throws ComplexNumberException Ako string nije kompleksni broj ili ako je
	 * kompleksni broj zadan u krivom formatu.
	 */
	
	public static ComplexNumber parse(String string){
		string = string.replaceAll("\\s+","");
		if (string.equals("i") || string.equals("+i")){
			return new ComplexNumber(ZERO,ONE);
		}
		if (string.equals("-i")){
			return new ComplexNumber(ZERO,NEG_ONE);
		}
		Pattern pattern = Pattern.compile("^([+-]?\\d*\\.??\\d+)i$");
		Matcher matcher = pattern.matcher(string);
		if(matcher.matches()){
			double imaginary = Double.parseDouble(matcher.group(1));
			return new ComplexNumber(ZERO,imaginary);
		}
		pattern = Pattern.compile("^([+-]?\\d*\\.??\\d+)$");
		matcher = pattern.matcher(string);
		if(matcher.matches()){
			double real = Double.parseDouble(matcher.group(1));
			return new ComplexNumber(real,ZERO);
		}
		pattern = Pattern.compile("^([+-]?\\d*\\.??\\d+)([+-]?\\d*\\.??\\d+)i$");
		matcher = pattern.matcher(string);
		if(matcher.matches()){
			double real = Double.parseDouble(matcher.group(1));
			double imaginary = Double.parseDouble(matcher.group(2));
			return new ComplexNumber(real,imaginary);
		}
		else{
		  throw new ComplexNumberException("Unable to parse complex number.");
		}
	}
	
	/**
	 * Metoda koja dohvaca realni dio kompleksnog broja.
	 * @return Realni dio kompleksnog broja.
	 */
	
	public double getReal(){
		return real;
	}
	
	/**
	 * Metoda koja dohvaca imaginarni dio kompleksnog broja.
	 * @return Imaginarni dio kompleksnog broja.
	 */
	
	public double getImaginary(){
		return imaginary;
	}
	
	/**
	 * Metoda koja dohvaca modul kompleksnog broja.
	 * @return Modul kompleksnog broja.
	 */
	
	public double getMagnitude(){
		return  Math.sqrt(Math.pow(real,2) + Math.pow(imaginary, 2));
	}
	
	/**
	 * Metoda koja dohvaca kut kompleksnog broja.
	 * @return Kut kompleksnog broja.
	 */
	
	public double getAngle(){
		double angle = Math.atan2(imaginary,real);
		if (angle < 0){
			angle += 2*Math.PI;
		}
		return angle;
	}
	
	/**
	 * Metoda za zbrajanje kompleksnih brojeva.
	 * @param complex Kompleksni broj koji ce se pribrojiti.
	 * @return Kompleksni broj nastao operacijom zbrajanja.
	 */
	
	public ComplexNumber add(ComplexNumber complex){
		return new ComplexNumber(real+complex.real,imaginary+complex.imaginary);
	}
	
	/**
	 * Metoda za oduzimanje kompleksnih brojeva.
	 * @param complex Kompleksni broj koji ce se oduzeti.
	 * @return Kompleksni broj nastao operacijom oduzimanja.
	 */
	
	public ComplexNumber sub(ComplexNumber complex){
		return new ComplexNumber(real-complex.real,imaginary-complex.imaginary);
	}
	
	/**
	 * Metoda za mnozenje kompleksnih brojeva.
	 * @param complex Kompleksni broj s kojim ce se mnoziti.
	 * @return Kompleksni broj nastao operacijom mnozenja.
	 */
	
	public ComplexNumber mul(ComplexNumber complex){		
		double mulMagnitude = this.getMagnitude()*complex.getMagnitude();
		double mulAngle = this.getAngle()+complex.getAngle();
		return new ComplexNumber(mulMagnitude*Math.cos(mulAngle),mulMagnitude*Math.sin(mulAngle));
	}
	
	/**
	 * Metoda za djeljenje kompleksnih brojeva.
	 * @param complex Kompleksni broj s kojim ce se djeliti.
	 * @return Kompleksni broj nastao operacijom djeljenja.
	 */
	
	public ComplexNumber div(ComplexNumber complex){		
		double mulMagnitude = this.getMagnitude()/complex.getMagnitude();
		double mulAngle = this.getAngle()-complex.getAngle();
		return new ComplexNumber(mulMagnitude*Math.cos(mulAngle),mulMagnitude*Math.sin(mulAngle));
	}
	
	/**
	 * Metoda za potenciranje kompleksnog broja.
	 * @param power Potencija kompleksnog broja. Vrijednost ne  smije biti negativna.
	 * @return Kompleksni broj nastao operacijom potenciranja.
	 * @throws ComplexNumberException Ako je potencija manje od nula.
	 */
	
	public ComplexNumber power(int power){
		if (power < 0){
			throw new ComplexNumberException("Power must be non-negative number.");
		}
		double powerMagnitude = Math.pow(this.getMagnitude(), power);
		double powerAngle = power*this.getAngle();
		return new ComplexNumber(powerMagnitude*Math.cos(powerAngle),powerMagnitude*Math.sin(powerAngle));
	}
	
	/**
	 * Metoda za korijenovanje kompleksnog broja. 
	 * @param root Korijen kompleksnog broja. Vrijednost mora biti pozitivan prirodan broj. 
	 * @return Kompleksni brojevi nastali operacijom korijenovanja.
	 * @throws ComplexNumberException Ako korijen nije pozitivan prirodan broj.
	 */
	
	public ComplexNumber[] root(int root){
		if (root < 1){
			throw new ComplexNumberException("Root must be positive number");
		}
		double rootMagnitude = Math.pow(this.getMagnitude(), 1./root);
		double angle = this.getAngle();
		ComplexNumber[] complexNumbers = new ComplexNumber[root];
		for (int i = 0; i < root; i++){
			complexNumbers[i] = new ComplexNumber(rootMagnitude*Math.cos((angle+2*i*Math.PI)/root),
					                             rootMagnitude*Math.sin((angle+2*i*Math.PI)/root));
		}
		return complexNumbers;
	}
	
	/**
	 * Metoda koja pretvara kompleksni broj u String format.
	 */
	
	public String toString(){
		String complexString = "";
		DecimalFormat decimFormat = new DecimalFormat("0.####;-0.####");
		if (real != 0){
			complexString += decimFormat.format(real);
		}
		if (imaginary != 0){
			complexString += (real != 0 && imaginary > 0 ? "+" : "") + decimFormat.format(imaginary)+"i";
		}
		if (complexString.equals("")){
			complexString = "0";
		}	
		return complexString;
	}
	

}
