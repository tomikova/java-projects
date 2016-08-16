package hr.fer.zemris.java.custom.scripting.tokens;

/**
* @author Tomislav
*/

public class TokenConstantDouble extends Token {
	
	private double value;
	
	public TokenConstantDouble(double value){
		this.value = value;
	}
	
	public TokenConstantDouble(String value){
		this.value = Double.parseDouble(value);
	}
	
	/**
	* Metoda koja dohvaca vrijednost tokena.
	* @return Vrijednost tokena.
	*/

	public double getValue() {
		return value;
	}
	
	/**
	* Metoda koja dohvaca tekstualni format tokena.
	* @return Tekstualni format tokena.
	*/
	
	@Override
	public String asText(){
		return Double.toString(value);
	}
}
