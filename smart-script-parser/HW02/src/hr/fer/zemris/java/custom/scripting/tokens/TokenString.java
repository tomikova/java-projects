package hr.fer.zemris.java.custom.scripting.tokens;

/**
* @author Tomislav
*/

public class TokenString extends Token {
	
	private String value;
	
	public TokenString(String value){
		this.value = value;
	}
	
	/**
	* Metoda koja dohvaca vrijednost tokena.
	* @return Vrijednost tokena.
	*/

	public String getValue() {
		return value;
	}
	
	/**
	* Metoda koja dohvaca tekstualni format tokena.
	* @return Tekstualni format tokena.
	*/
	
	@Override
	public String asText(){
		String text = value;
		text = text.replace("\\", "\\\\");
		text = text.replace("{", "\\{");
		text = text.replace("\n", "\\n");
		text = text.replace("\r", "\\r");
		text = text.replace("\t", "\\t");
		text = text.replace("\"", "\\\"");
		text = '"'+text+'"';
		return text;
	}
}
