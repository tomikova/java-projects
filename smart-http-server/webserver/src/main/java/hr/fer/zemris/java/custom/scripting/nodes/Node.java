package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;

/**
 * Class models node that is part of document tree created using SmartScriptParser.
 * @author Tomislav
 *
 */

public abstract class Node {
	
	/**
	 * List of nodes child nodes.
	 */
	private ArrayBackedIndexedCollection children;
	
	
	/**
	 * Method which calls visitor method to visit this node.
	 * @param visitor Visitor object that will perform operations on node visit.
	 */
	public abstract void accept(INodeVisitor visitor);
	
	
	/**
	* Method adds node as a child of current node.
	* @param child Child node to add.
	*/
	public void addChildNode(Node child){
		if(children == null){
			children = new ArrayBackedIndexedCollection();
		}
		children.add(child);
	}
	
	/**
	* Method returns number of this node children.
	* @return Number of children nodes.
	*/
	
	public int numberOfChildren(){
		if (children == null){
			return 0;
		}
		else{
			return children.size();
		}
	}
	
	/**
	* Method returns child node.
	* @param index Child node index.
	* @return Child node.
	*/
	
	public Node getChild(int index){
		return (Node)children.get(index);
	}
	
	/**
	* Method return text format of current node.
	* @return Text format of node.
	*/
	public abstract String getText();
}
