package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;

/**
 * Class models echo node in document tree parsed with SmartScriptParser.
 * @author Tomislav
 *
 */

public class EchoNode extends Node{
	
	/**
	 * Echo node tokens.
	 */
	private Token[] tokens;
	
	/**
	 * Default constructor without parameters.
	 * @param tokens Echo node tokens.
	 */
	public EchoNode(Token[] tokens){
		this.tokens = tokens;
	}
	
	
	/**
	* Method returns echo node tokens.
	* @return Echo node tokens.
	*/
	
	public Token[] getTokens() {
		return tokens;
	}

	/**
	 * {@inheritDoc}
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);		
	}
}
