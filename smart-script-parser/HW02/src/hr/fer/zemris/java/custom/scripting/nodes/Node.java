package hr.fer.zemris.java.custom.scripting.nodes;

/**
* @author Tomislav
*/

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;

public class Node {
	
	private ArrayBackedIndexedCollection children;
	
	
	/**
	* Metoda koja dodaje cvor kao djete trenutnog cvora.
	* @param child Cvor koji se dodaje kao dijete.
	*/
	public void addChildNode(Node child){
		if(children == null){
			children = new ArrayBackedIndexedCollection();
		}
		children.add(child);
	}
	
	/**
	* Metoda koja dohvaca broj cvorova djece.
	* @return Broj cvorova djece.
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
	* Metoda koja dohvaca cvor dijete.
	* @param index Indeks cvora djeteta.
	* @return Cvor dijete.
	*/
	
	public Node getChild(int index){
		return (Node)children.get(index);
	}
	
	/**
	* Metoda koja dohvaca cvor u tekstualnom formatu.
	* @return Tekstualni format cvora.
	*/
	public String getText() {
		return "";
	}
}
