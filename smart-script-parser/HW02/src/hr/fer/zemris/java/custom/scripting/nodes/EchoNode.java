package hr.fer.zemris.java.custom.scripting.nodes;

/**
* @author Tomislav
*/

import hr.fer.zemris.java.custom.scripting.tokens.Token;

public class EchoNode extends Node{
	
	private Token[] tokens;
	
	public EchoNode(Token[] tokens){
		this.tokens = tokens;
	}
	
	
	/**
	* Metoda koja dohvaca elemente cvora.
	* @return Elementi cvora.
	*/
	
	public Token[] getTokens() {
		return tokens;
	}

	/**
	* Metoda koja dohvaca tekstualni format cvora.
	* @return Tekstualni format cvora.
	*/
	
	@Override
	public String getText(){
		String text = null;
		StringBuilder sb = new StringBuilder();
		sb.append("{$= ");
		for(int i = 0; i < tokens.length; i++){
			sb.append(tokens[i].asText()+" ");
		}
		sb.append("$}");
		text = sb.toString();
		return text;
	}
}
