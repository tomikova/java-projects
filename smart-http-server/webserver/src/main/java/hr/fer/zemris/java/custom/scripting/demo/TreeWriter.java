package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Program reads smart script and calls SmartScriptParser for parsing smart script.
 * After parsing is complete it prints parsed smart script content using visitor design pattern.
 * Expected number of arguments is 1 - smart script path.
 * Example of argument: lib\SmartScriptExamples\doc2.txt
 * @author Tomislav
 *
 */

public class TreeWriter {

	/**
	 * Method called at program start.
	 * @param args Command line arguments
	 * @throws IOException If error occurs while reading file.
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
			e.printStackTrace();
			System.exit(-1);
		}

		WriterVisitor visitor = new WriterVisitor();
		try {
			parser.getDocumentNode().accept(visitor);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}	
	}
	
	/**
	 * WriteVisitor implements INodeVisitor and prints node text on visit.
	 * @author Tomislav
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			System.out.print(node.getText());	
			int numberOfChildren = node.numberOfChildren();
			for (int i = 0; i < numberOfChildren; i++) {
				node.getChild(i).accept(this);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node.getText());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node.getText());
			int numberOfChildren = node.numberOfChildren();
			for (int i = 0; i < numberOfChildren; i++) {
				node.getChild(i).accept(this);
			}
			System.out.print("{$END$}");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText());
		}
	}
}
