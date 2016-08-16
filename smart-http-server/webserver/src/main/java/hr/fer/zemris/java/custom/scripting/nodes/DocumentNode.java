package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class models node which is root of parsed document tree.
 * @author Tomislav
 *
 */

public class DocumentNode extends Node {
	
	/**
	 * Document node text.
	 */
	private String text;
	
	/**
	 * Default constructor without parameters.
	 */
	public DocumentNode() {
		this.text = "";
	}
		
	/**
	 * Constructor with one parameter.
	 * @param text Document node text.
	 */
	public DocumentNode(String text){
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
		visitor.visitDocumentNode(this);		
	}
}
