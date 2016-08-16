package hr.fer.zemris.java.custom.scripting.nodes;

/**
* @author Tomislav
*/

public class TextNode extends Node {
	
	private String text;
	
	public TextNode(String text){
		this.text = text;
	}	
	
	/**
	* Metoda koja dohvaca cvor u tekstualnom formatu.
	* @return Tekstualni format cvora.
	*/
	
	@Override
	public String getText() {
		return text;
	}
}
