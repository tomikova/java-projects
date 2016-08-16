package hr.fer.zemris.java.custom.scripting.parser;

/**
* @author Tomislav
*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.tokens.*;

public class SmartScriptParser {
	
	private ObjectStack stack;
	
	public SmartScriptParser(String docBody){
		this.stack = new ObjectStack();
		DocumentNode root = new DocumentNode();
		this.stack.push(root);
		this.parseDocument(docBody);
	}
	
	/**
	* Metoda kojoj se delegira dokument koji se traba parsirati.
	* @param s Sadrzaj dokumenta koji se parsira.
	*/
	
	void parseDocument(String s){
		
		//dohvaca dva polja indeksa pocetka i kraja tagova u stringu
		Object[] indices = (Object[])getTagIndices(s);
		
		int[] startIndices = (int[]) indices[0];
		int[] endIndices = (int[]) indices[1];
		
		//cijepanje stringa na tagove i obicni teskt, pocevsi od pocetka stringa
		int startTextIndex = 0;
		for (int i = 0; i < startIndices.length; i++ ){
			if (startTextIndex != startIndices[i]){
				parseTextNode(s.substring(startTextIndex, startIndices[i]));
			}
			resolveTag(s.substring(startIndices[i]+2, endIndices[i]-2));
			startTextIndex = endIndices[i];
		}
		//ako nakon zadnjeg taga postoji tekst
		if (startTextIndex != s.length() ){
			parseTextNode(s.substring(startTextIndex, s.length()));
		}
	}
	
	
	/**
	* Metoda koja dohvaca sve pocetne i zavrsne indekse tagova u dokumentu.
	* @param s Sadrzaj dokumenta koji se parsira.
	* @return Vraca dva polja, polje pocetnih i polje zavrsnih 
	* indeksa tagova kao polje objekata.
	*/
	
	Object getTagIndices(String s){
		
		ObjectStack startIndicesStack = new ObjectStack();
		ObjectStack endIndicesStack = new ObjectStack();
		
		Pattern pattern = Pattern.compile("\\{\\$.*?\\$\\}");
		Matcher matcher = pattern.matcher(s);
		
		while(matcher.find()){
			int startIndex = matcher.start();
			if (startIndex != 0){
				if (s.charAt(startIndex-1) != '\\'){
					startIndicesStack.push(matcher.start());
					endIndicesStack.push(matcher.end());
				}
			}
			else{
				startIndicesStack.push(matcher.start());
				endIndicesStack.push(matcher.end());
			}
        }
		
		int[] startIndices = new int[startIndicesStack.size()];
		int[] endIndices = new int [endIndicesStack.size()];
		
		/*oba stacka su iste velicine, punjenje int polja od zadnjeg mjesta jer se na vrhu
		stacka nalazi zadnji element*/
		
		int counter = startIndicesStack.size() - 1;
		while(!startIndicesStack.isEmpty()){
			startIndices[counter] = (int)startIndicesStack.pop();
			endIndices[counter] = (int)endIndicesStack.pop();
			counter--;
		}
		
		return new Object[]{startIndices, endIndices};	
	}
	
	/**
	* Metoda kojoj se delegira stvaranje TextNode-a.
	* @param s Sadrzaj koji se treba pohraniti u TextNode.
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
	* Metoda koja odredjuje koji Node ce se stvoriti.
	* @param Ss Sadrzaj koji se treba pohraniti u Node.
	*/
	
	void resolveTag(String s){
		
		String [] parameters;
		parameters = parseString(s); 
		
		//ako je '=' tag i ako nema bjelina na pocetku razdvojiti ga
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
	* Metoda kojoj se delegira stvaranje ForLoopNode-a.
	* @param parameters Parametri for petlje i njen naziv. 
	* Prvi parametar je naziv for petlje, preostala 3 ili 4 parametra su parametri for petlje.
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
	* Metoda kojoj se delegira stvaranje EchoNode-a.
	* @param parameters Parametri EchoNode-a. Polje oznaka koje se tokeniziraju.
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
	* Metoda razdjeljuje elemente po bjelinama osim ako se nalaza unutar navodnika.
	* @param s Tekst koji se razdjeljuje.
	* @return Razdijeljeni elementi.
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
	* Metoda koja provjerava da li je tekst varijabla.
	* @param s Tekst koji se provjerava.
	* @return Vrijednost true/false ovisno da li je tekst varijabla.
	*/
	
	boolean isVariable(String s){
		Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z_0-9]*$");
		Matcher matcher = pattern.matcher(s);
		return matcher.find();
	}
	
	/**
	* Metoda koja provjerava da li je tekst string.
	* @param s Tekst koji se provjerava.
	* @return Vrijednost true/false ovisno da li je tekst string.
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
	* Metoda koja provjerava da li je tekst cjelobrojna vrijednost.
	* @param s Tekst koji se provjerava.
	* @return Vrijednost true/false ovisno da li je tekst cjelobrojna vrijednost.
	*/
	
	boolean isNumeric(String s){
		Pattern pattern = Pattern.compile("^[+-]??[0-9]+$");
		Matcher matcher = pattern.matcher(s);
		return matcher.find();
	}
	
	/**
	* Metoda koja provjerava da li je tekst operator.
	* @param s Tekst koji se provjerava.
	* @return Vrijednost true/false ovisno da li je tekst operator.
	*/
	
	boolean isOperator(String s){
		Pattern pattern = Pattern.compile("^[+-/%\\*]??$");
		Matcher matcher = pattern.matcher(s);
		return matcher.find();
	}
	
	/**
	* Metoda koja provjerava da li je tekst funkcija.
	* @param s Tekst koji se provjerava.
	* @return Vrijednost true/false ovisno da li je tekst funkcija.
	*/
	
	boolean isFunction(String s){
		if(s.charAt(0) != '@'){
			return false;
		}
		return isVariable(s.substring(1,s.length()));
	}
	
	/**
	* Metoda koja provjerava da li je tekst double vrijednost.
	* @param s Tekst koji se provjerava.
	* @return Vrijednost true/false ovisno da li je tekst double vrijednost.
	*/
	
	boolean isDouble(String s){
		Pattern pattern = Pattern.compile("^[+-]??[0-9]+\\.[0-9]+$");
		Matcher matcher = pattern.matcher(s);
		return matcher.find();
	}
	
	/**
	* Metoda koja provjerava da li je tekst ispravno formatiran.
	* Ujedno provjerava zatvorenost tagova.
	* @param s Tekst koji se provjerava.
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
	* Metoda koja vraca prvog cvora dokumenta na stogu.
	* @return Prvi cvor dokumenta na stogu.
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
