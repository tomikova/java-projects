package hr.fer.zemris.java.custom.scripting.nodes;

/**
* @author Tomislav
*/

public class DocumentNode extends Node {
	
	private String text;
	
	public DocumentNode() {
		this.text = "";
	}
		
	public DocumentNode(String text){
		this.text = text;
	}
	
	/**
	* Metoda koja dohvaca tekstualni format cvora.
	* @return Tekstualni format cvora.
	*/
	
	@Override
	public String getText() {
		return text;
	}
}
