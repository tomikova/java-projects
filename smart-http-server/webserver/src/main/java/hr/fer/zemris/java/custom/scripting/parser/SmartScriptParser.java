package hr.fer.zemris.java.custom.scripting.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.tokens.*;

/**
 * Class that models parser for smart scripts. Parser is building document tree by creating
 * DocumentNodes, ForLoopNodes, EchoNodes and TextNodes found in smart script based on nodes tag rules.
 * @author Tomislav
 *
 */
public class SmartScriptParser {
	
	/**
	 * Stack used while creating document tree.
	 */
	private ObjectStack stack;
	
	/**
	 * Constructor with one parameter.
	 * @param docBody Smart script body text.
	 */
	public SmartScriptParser(String docBody){
		this.stack = new ObjectStack();
		DocumentNode root = new DocumentNode();
		this.stack.push(root);
		this.parseDocument(docBody);
	}
	
	/**
	* Method that accepts document for parsing.
	* @param s Document that needs to be parsed.
	*/	
	void parseDocument(String s){
		
		//gets two arrays of indexes that are begins and ends of tags
		Object[] indices = (Object[])getTagIndices(s);
		
		int[] startIndices = (int[]) indices[0];
		int[] endIndices = (int[]) indices[1];
		
		//split string on tags and text
		int startTextIndex = 0;
		for (int i = 0; i < startIndices.length; i++ ){
			if (startTextIndex != startIndices[i]){
				parseTextNode(s.substring(startTextIndex, startIndices[i]));
			}
			resolveTag(s.substring(startIndices[i]+2, endIndices[i]-2));
			startTextIndex = endIndices[i];
		}
		//if after final tag there is still text left
		if (startTextIndex != s.length() ){
			parseTextNode(s.substring(startTextIndex, s.length()));
		}
	}
	
	
	/**
	* Method returns arrays of all start and end indexes of tags in document.
	* @param s Document that needs to be parsed.
	* @return Returns two arrays of begin and end indexes as single object.
	*/
	
	Object getTagIndices(String s){
		
		ObjectStack startIndicesStack = new ObjectStack();
		ObjectStack endIndicesStack = new ObjectStack();
		
		Pattern pattern = Pattern.compile("(?s)\\{\\$.*?\\$\\}");
		Matcher matcher = pattern.matcher(s);
		
		while(matcher.find()){
			int startIndex = matcher.start();
			if (startIndex != 0){
				if (s.charAt(startIndex-1) != '\\'){
					startIndicesStack.push(matcher.start());
					endIndicesStack.push(matcher.end());
				}
				else {
					int subIndex = getSubstringIndex(s.substring(matcher.start()+1, matcher.end()), 
							matcher.start(), 1);
					if (matcher.start() != subIndex) {
						startIndicesStack.push(subIndex);
						endIndicesStack.push(matcher.end());
					}
				}
			}
			else{
				startIndicesStack.push(matcher.start());
				endIndicesStack.push(matcher.end());
			}
        }
		
		int[] startIndices = new int[startIndicesStack.size()];
		int[] endIndices = new int [endIndicesStack.size()];
		
		int counter = startIndicesStack.size() - 1;
		while(!startIndicesStack.isEmpty()){
			startIndices[counter] = (int)startIndicesStack.pop();
			endIndices[counter] = (int)endIndicesStack.pop();
			counter--;
		}
		return new Object[]{startIndices, endIndices};	
	}
	
	/**
	 * Method that finds tag pattern in given text.
	 * @param s Text that will be tested for pattern existance.
	 * @param index Index of begin of first tag match.
	 * @param counter Offset of first tag begin index.
	 * @return Index of begin of first pattern match in given text in whole document.
	 */
	private int getSubstringIndex(String s, int index, int counter) {
		Pattern pattern = Pattern.compile("(?s)\\{\\$.*?\\$\\}");
		Matcher matcher = pattern.matcher(s);
		
		while(matcher.find()){
			int startIndex = matcher.start();
			if (startIndex != 0){
				if (s.charAt(startIndex-1) != '\\'){
					return index+counter+matcher.start();
				}
				else {
					return getSubstringIndex(s.substring(matcher.start()+1, matcher.end()),
							index,counter+matcher.start()+1); 
				}
			}
			else{
				return index+counter;
			}
        }
		return index;
	}
	
	/**
	* Method for parsing and craeting TextNode.
	* @param s Content that needs to be stored in TextNode.
	*/
		
	void parseTextNode(String s){
		checkText(s);
		TextNode childNode = new TextNode(s);
		if (stack.size() == 0){
			throw new SmartScriptParserException("Stack is empty.");
		}
		Node parentNode = (Node)stack.peek();
		parentNode.addChildNode(childNode);
	}
	
	/**
	* Method determines which node will be created from given tag.
	* @param S Content that needs to be stored in node after being resolved.
	*/
	
	void resolveTag(String s){
		
		String [] parameters;
		parameters = parseString(s); 
		
		if (parameters[0].charAt(0) == '=' && parameters[0].length() > 1){
			String[] tmp = new String[parameters.length+1];
			tmp[0] = parameters[0].substring(0,1);
			parameters[0] = parameters[0].substring(1,parameters[0].length());
			for (int i = 0; i < parameters.length; i++){
				tmp[i+1] = parameters[i];
			}
			parameters = tmp;
		}
			
		if (parameters[0].toLowerCase().equals("for")){
			resolveForLoopNode(parameters);
		}
		else if (parameters[0].equals("=")){
			resolveEchoNode(parameters);
		}
		else if (parameters[0].toLowerCase().equals("end")){
			if (stack.size() == 0){
				throw new SmartScriptParserException("Stack is empty.");
			}
			stack.pop();
		}
		else{
			throw new SmartScriptParserException("Invalid tag name.");
		}
	}
	
	/**
	* Method for creating ForLoopNode.
	* @param parameters ForLoopNode parameters and name. 
	* First parameter is foor loop node name, rest 3 or 4 parameters are for loop node
	* variable name, start and end expressions and step expression if exist.
	*/
	void resolveForLoopNode(String[] parameters){
		if (parameters.length < 4 || parameters.length > 5){
			throw new SmartScriptParserException("Error parsing for loop. Invalid construction.");
		}
		if(!isVariable(parameters[1])){
			throw new SmartScriptParserException("First lop argument is not a variable.");
		}
		Token[] tokens = new Token[4];
		tokens[0] = new TokenVariable(parameters[1]);
		for(int i = 2; i < parameters.length; i++){
			if(isVariable(parameters[i])){
				tokens[i-1] = new TokenVariable(parameters[i]);
			}
			else if (isNumeric(parameters[i])){
				tokens[i-1] = new TokenConstantInteger(parameters[i]);
			}
			else if (isDouble(parameters[i])){
				tokens[i-1] = new TokenConstantDouble(parameters[i]);
			}
			else if (isString(parameters[i])){
				if(isNumeric(parameters[i].substring(1,parameters[i].length()-1)) ||
						isDouble(parameters[i].substring(1,parameters[i].length()-1))){
					tokens[i-1] = new TokenString(parameters[i].substring(1,parameters[i].length()-1));
				}
				else{
					throw new SmartScriptParserException("Invalid loop argument.");
				}
			}
			else{
				throw new SmartScriptParserException("Invalid loop argument.");
			}
		}
		ForLoopNode forLoopNode = new ForLoopNode((TokenVariable)tokens[0], 
				tokens[1], tokens[2], tokens[3]);
		
		if (stack.size() == 0){
			throw new SmartScriptParserException("Stack is empty.");
		}
		Node parentNode = (Node)stack.peek();
		parentNode.addChildNode(forLoopNode);
		stack.push(forLoopNode);
	}
	
	/**
	* Metoda for creating EchoNode.
	* @param parameters EchoNode parameters. Array of parameters that will be tokenized.
	*/
	
	void resolveEchoNode(String[] parameters){
		ObjectStack paramStack = new ObjectStack();
		for (int i = 1; i < parameters.length; i++){
			if (isVariable(parameters[i])){
				TokenVariable token = new TokenVariable(parameters[i]);
				paramStack.push(token);
			}
			else if(isOperator(parameters[i])){
				TokenOperator token = new TokenOperator(parameters[i]);
				paramStack.push(token);
			}
			else if(isString(parameters[i])){
				TokenString token = new TokenString(parameters[i].substring(1,parameters[i].length()-1));
				paramStack.push(token);
			}
			else if(isNumeric(parameters[i])){
				TokenConstantInteger token = new TokenConstantInteger(parameters[i]);
				paramStack.push(token);
			}
			else if(isDouble(parameters[i])){
				TokenConstantDouble token = new TokenConstantDouble(parameters[i]);
				paramStack.push(token);
			}
			else if(isFunction(parameters[i])){
				TokenFunction token = new TokenFunction
						(parameters[i].substring(1,parameters[i].length()));
				paramStack.push(token);
			}
			else{
				throw new SmartScriptParserException("Invalid echo argument.");
			}
		}
		
		Token[] tokens = new Token[paramStack.size()];
		int counter = paramStack.size() - 1;
		while(!paramStack.isEmpty()){
			tokens[counter] = (Token)paramStack.pop();
			counter--;
		}
		EchoNode echoNode = new EchoNode(tokens);
		if (stack.size() == 0){
			throw new SmartScriptParserException("Stack is empty.");
		}
		Node parentNode = (Node)stack.peek();
		parentNode.addChildNode(echoNode);
	}
	
	/**
	* Method for split on whitespace if whitespace is not in quotes.
	* @param s Text that will be split.
	* @return Split text elements.
	*/
	String[] parseString(String s){
		
		ObjectStack stringStack = new ObjectStack();
		String tmp = "";
		boolean flag = false;
		int stringLength = s.length();
		int iteration = 0;
		
		while (iteration < stringLength){
			char c = s.charAt(iteration);
			if (c == '"'){
				if (!flag){
					if (tmp.length() > 0){
						String[] splitedStrings = tmp.split("\\s+");
						for (int i = 0; i< splitedStrings.length; i++ ){
							splitedStrings[i] = splitedStrings[i].trim();
							if (!splitedStrings[i].equals("")){
								stringStack.push(splitedStrings[i]);
							}
						}
						tmp = "";
					}
					flag = true;
					tmp += c;
					iteration++;
					continue;
				}
				else{
					flag = false;
					tmp += c;
					stringStack.push(tmp);
					tmp = "";
					iteration++;
					continue;
				}
			}
			if (flag){
				if (c == '\\' && iteration < stringLength-1){
					if (s.charAt(iteration+1) == '\\'){
						tmp += '\\';
						iteration += 2;
					}
					else if (s.charAt(iteration+1) == '{'){
						tmp += '{';
						iteration += 2;
					}
					else if (s.charAt(iteration+1) == 'n'){
						tmp += '\n';
						iteration += 2;
					}
					else if (s.charAt(iteration+1) == 'r'){
						tmp += '\r';
						iteration += 2;
					}
					else if (s.charAt(iteration+1) == 't'){
						tmp += '\t';
						iteration += 2;
					}
					else if (s.charAt(iteration+1) == '"'){
						tmp += '"';
						iteration += 2;
					}
				}
				else{
					tmp += c;
					iteration++;
				}
				continue;
			}
			else{
				tmp += c;
				iteration++;
				if (iteration == stringLength){
					if (tmp.length() > 0){
						String[] splitedStrings = tmp.split("\\s+");
						for (int i = 0; i< splitedStrings.length; i++ ){
							splitedStrings[i] = splitedStrings[i].trim();
							if (!splitedStrings[i].equals("")){
								stringStack.push(splitedStrings[i]);
							}
						}
						tmp = "";
					}
				}
			}
		}
		
		String[] strings = new String[stringStack.size()];
		int counter = stringStack.size() - 1;
		while(!stringStack.isEmpty()){
			strings[counter] = (String)stringStack.pop();
			counter--;
		}
		
		return strings;
	}
	
	/**
	* Method checks if text is variable.
	* @param s Text that will be checked.
	* @return True/false value depending if text is variable.
	*/
	boolean isVariable(String s){
		Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z_0-9]*$");
		Matcher matcher = pattern.matcher(s);
		return matcher.find();
	}
	
	/**
	* Method checks if text is string.
	* @param s Text that will be checked.
	* @return True/false value depending if text is string.
	*/	
	boolean isString(String s){
		if (s.length() > 1){
			if (s.charAt(0) == '"' && s.charAt(s.length()-1) == '"'){
				return true;
			}
		}
		return false;
	}
	
	/**
	* Method checks if text is numeric.
	* @param s Text that will be checked.
	* @return True/false value depending if text is numeric.
	*/
	boolean isNumeric(String s){
		Pattern pattern = Pattern.compile("^[+-]??[0-9]+$");
		Matcher matcher = pattern.matcher(s);
		return matcher.find();
	}
	
	/**
	* Method checks if text is operator.
	* @param s Text that will be checked.
	* @return True/false value depending if text is operator.
	*/
	boolean isOperator(String s){
		Pattern pattern = Pattern.compile("^[+-/%\\*]??$");
		Matcher matcher = pattern.matcher(s);
		return matcher.find();
	}
	
	/**
	* Method checks if text is function.
	* @param s Text that will be checked.
	* @return True/false value depending if text is function.
	*/
	boolean isFunction(String s){
		if(s.charAt(0) != '@'){
			return false;
		}
		return isVariable(s.substring(1,s.length()));
	}
	
	
	/**
	* Method checks if text is double value.
	* @param s Text that will be checked.
	* @return True/false value depending if text is double value.
	*/
	boolean isDouble(String s){
		Pattern pattern = Pattern.compile("^[+-]??[0-9]+\\.[0-9]+$");
		Matcher matcher = pattern.matcher(s);
		return matcher.find();
	}
	
	/**
	* Method checks if text is well formatted. It also checks if all tags are closed.
	* @param s Text that will be checked.
	*/
	void checkText(String s){
		char start = s.charAt(0);
		if (start == '{'){
			throw new SmartScriptParserException("Tag is not closed.");
		}
		int length = s.length();
		for(int i = 1; i < length; i++ ){
			if (s.charAt(i) == '\\'){
				if (i+1 < length && s.charAt(i+1) == '\\'){
					if (i+2 < length && s.charAt(i+2) == '{'){
						throw new SmartScriptParserException("Tag is not closed.");
					}
				}
			}
			else if (s.charAt(i) == '{' && s.charAt(i-1) != '\\'){
				throw new SmartScriptParserException("Tag is not closed.");
			}
		}
	}
	
	/**
	* Method return document node that is root node of parsed document tree.
	* @return Document node.
	*/
	
	public DocumentNode getDocumentNode(){
		if (stack.size() == 0){
			throw new SmartScriptParserException("Stack is empty.");
		}
		DocumentNode documentNode = null;
		try{
			documentNode = (DocumentNode)stack.pop();
		}catch (Exception e){
			throw new SmartScriptParserException("Tag is not closed.");
		}
		return documentNode;
	}
}
