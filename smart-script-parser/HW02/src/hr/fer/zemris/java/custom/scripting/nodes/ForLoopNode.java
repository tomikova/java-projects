package hr.fer.zemris.java.custom.scripting.nodes;

/**
* @author Tomislav
*/

import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

public class ForLoopNode extends Node {
	
	private TokenVariable variable;
	private Token startExpression;
	private Token endExpression;
	private Token stepExpression;
	
	public ForLoopNode(TokenVariable variable, Token startExpression,
			Token endExpression, Token stepExpression){
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
		
	}
	
	/**
	* Metoda koja dohvaca vrijednost varijable cvora.
	* @return Vrijednost varijable cvora.
	*/
	
	public TokenVariable getVariable() {
		return variable;
	}
	
	/**
	* Metoda koja dohvaca vrijednost pocetka izraza cvora.
	* @return Pocetak izraza cvora.
	*/
	
	public Token getStartExpression() {
		return startExpression;
	}
	
	/**
	* Metoda koja dohvaca vrijednost zavrsetka izraza cvora.
	* @return Zavrsetak izraza cvora.
	*/
	
	public Token getEndExpression() {
		return endExpression;
	}
	
	/**
	* Metoda koja dohvaca vrijednost koraka izraza cvora.
	* @return Korak izraza cvora.
	*/
	
	public Token getStepExpression() {
		return stepExpression;
	}
	
	/**
	* Metoda koja dohvaca tekstualni format cvora.
	* @return Tekstualni format cvora.
	*/
	
	@Override
	public String getText(){
		String text = null;
		StringBuilder sb = new StringBuilder();
		sb.append("{$FOR ");
		sb.append(variable.asText()+" ");
		sb.append(startExpression.asText()+" ");
		sb.append(endExpression.asText()+" ");
		if (stepExpression != null){
			sb.append(stepExpression.asText());
		}
		sb.append("$}");
		text = sb.toString();
		return text;
	}
	
	
}
