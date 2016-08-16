package hr.fer.zemris.java.custom.scripting.tokens;

/**
* @author Tomislav
*/

public class TokenVariable extends Token {
	
	private String name;
	
	public TokenVariable(String name){
		this.name = name;
	}
	
	/**
	* Metoda koja dohvaca naziv tokena.
	* @return Naziv tokena.
	*/

	public String getName() {
		return name;
	}
	
	/**
	* Metoda koja dohvaca tekstualni format tokena.
	* @return Tekstualni format tokena.
	*/
	
	@Override
	public String asText(){
		return name;
	}
}
