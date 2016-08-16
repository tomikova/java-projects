package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface for objects that will perform actions on document 
 * tree nodes created using SmartScriptParser.
 * @author Tomislav
 *
 */

public interface INodeVisitor {
	
	/**
	 * Operations that will be performed when TextNode is visited.
	 * @param node Text node.
	 */
	public void visitTextNode(TextNode node);
	/**
	 * Operations that will be performed when ForLoopNode is visited.
	 * @param node FoorLoopNode node.
	 */
	public void visitForLoopNode(ForLoopNode node);
	/**
	 * Operations that will be performed when EchoNode is visited.
	 * @param node EchoNode node.
	 */
	public void visitEchoNode(EchoNode node);
	/**
	 * Operations that will be performed when DocumentNode is visited.
	 * @param node DocumentNode node.
	 */
	public void visitDocumentNode(DocumentNode node);
}