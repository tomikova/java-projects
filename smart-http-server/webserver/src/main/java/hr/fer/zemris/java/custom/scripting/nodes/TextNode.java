package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class models text node in document tree parsed with SmartScriptParser.
 * @author Tomislav
 *
 */

public class TextNode extends Node {
	
	/**
	 * Text node text.
	 */
	private String text;
	
	/**
	 * Default constructor with one parameter.
	 * @param text Text node text.
	 */
	public TextNode(String text){
		this.text = text;
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText() {
		return text;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
