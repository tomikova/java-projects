package hr.fer.zemris.java.hw2;

/**
* @author Tomislav
*/

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class SmartScriptTester {
	
	/**
	* Metoda koja se poziva prilikom pokretanja programa.
	* @param args Argumenti iz komandne linije.
	* 
	* Potrebno je zadati punu stazu do dokumenta unutar dvostrukih navodnika
	* 
	* primjer argumenta "E:\eclipse_workspaces\java1\HW02-0036461026\src\examples\doc1.txt"
	* primjer argumenta "E:\eclipse_workspaces\java1\HW02-0036461026\src\examples\doc2.txt"
	*/

	public static void main(String[] args) throws IOException {
		
		if (args.length < 1){
			System.out.println("No path to file provided.");
			System.exit(0);
		}
		
		String filepath = args[0];
		
		String docBody = new String(
				Files.readAllBytes(Paths.get(filepath)),StandardCharsets.UTF_8);
		
		SmartScriptParser parser = null;
		
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.out.println(e.getMessage());
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = null;
		try{
			document = parser.getDocumentNode();
		}catch(SmartScriptParserException e){
			System.out.println("Unable to parse document!");
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original
		// content of docBody
		
		System.out.println("\n2nd pass:\n");
		
		//drugi ispis, moraju biti jednaki
		SmartScriptParser parser2 = null;
		
		try {
			parser2 = new SmartScriptParser(originalDocumentBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.out.println(e.getMessage());
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document2 = parser2.getDocumentNode();
		String secondDocumentBody = createOriginalDocumentBody(document2);
		System.out.println(secondDocumentBody);
		

	}
	
	/**
	* Metoda koja rekonstruira originalni dokument.
	* @param node Cvor koji sadrzi strukturu dokumenta.
	* @return Rekonstruirani dokument.
	*/
	static String createOriginalDocumentBody(Node node){
		String documentText = node.getText();
		int numberOfChildren = node.numberOfChildren();
		for (int i = 0; i < numberOfChildren; i++ ){
			documentText += createOriginalDocumentBody(node.getChild(i));
		}
		/*if (numberOfChildren != 0 && node instanceof ForLoopNode) {
			documentText += "{$END$}";
		}*/
		if (node instanceof ForLoopNode) {
			documentText += "{$END$}";
		}
		return documentText;
	}
}
