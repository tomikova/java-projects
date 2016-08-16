package hr.fer.zemris.java.custom.scripting.tokens;

/**
* @author Tomislav
*/

public class TokenConstantInteger extends Token {
	
	private int value;
	
	public TokenConstantInteger(int value){
		this.value = value;
	}
	
	public TokenConstantInteger(String value){
		this.value = Integer.parseInt(value);
	}
	
	/**
	* Metoda koja dohvaca vrijednost tokena.
	* @return Vrijednost tokena.
	*/

	public int getValue() {
		return value;
	}
	
	/**
	* Metoda koja dohvaca tekstualni format tokena.
	* @return Tekstualni format tokena.
	*/
	
	@Override
	public String asText(){
		return Integer.toString(value);
	}
}
