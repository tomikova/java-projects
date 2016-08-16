package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

import hr.fer.zemris.java.custom.scripting.exec.function_strategies.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.tokens.*;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class responsibility is to execute smart script which was previously parsed
 * into tree by SmartScriptParser.
 * @author Tomislav
 *
 */

public class SmartScriptEngine {

	/**
	 * Document node that is root node of parsed tree.
	 */
	private DocumentNode documentNode;
	/**
	 * Object responsible for creating response to clients.
	 */
	private RequestContext requestContext;
	/**
	 * Stores multiple values for same key and provides a stack-like abstraction for these values.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	/**
	 * Map of function strategies that SmartScriptEngine supports.
	 */
	private HashMap<String, IFunctionStrategy> functionStrategies;

	/**
	 * Object visitor which implements operations that are performed when node is visited.
	 */
	private INodeVisitor visitor = new INodeVisitor() {
		
		/**
		 * Method iterates through all document node children and calls method accept visitor on them.
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			int numberOfChildren = node.numberOfChildren();
			for (int i = 0; i < numberOfChildren; i++) {
				node.getChild(i).accept(this);
			}
		}

		/**
		 * Method iterates through EchoNode tokens.
		 * If token is type of number or string its value is pushed on stack.
		 * If token is type of variable its lats value is obtained and pushed on stack.
		 * If token is type of operator, appropriate operation is performed and its result is pushed on stack.
		 * If token is type of function, appropriate function is performed and its result is pushed on stack.
		 * After iteration is complete, values from stack are sent to client using requestContext.
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			Deque<Object> tmpStack = new ArrayDeque<Object>();

			for(Token token : node.getTokens()) {
				if (token instanceof TokenConstantInteger 
						|| token instanceof TokenConstantDouble
						|| token instanceof TokenString) {
					tmpStack.push(token.getValue());
				}
				else if (token instanceof TokenVariable) {
					ValueWrapper valueWrapper = multistack.peek(((TokenVariable)token).getName());
					tmpStack.push(valueWrapper.getValue());
				}
				else if (token instanceof TokenOperator) {
					ValueWrapper firstOperand = new ValueWrapper(tmpStack.pop());	
					Object secondOperand = tmpStack.pop();
					String symbol = ((TokenOperator) token).getSymbol();
					if (symbol.equals("+")) {
						firstOperand.increment(secondOperand);
					}
					else if(symbol.equals("-")) {
						firstOperand.decrement(secondOperand);
					}
					else if(symbol.equals("*")) {
						firstOperand.multiply(secondOperand);
					}
					else if(symbol.equals("/")) {
						//division by zero provjeriti
						firstOperand.divide(secondOperand);
					}					
					tmpStack.push(firstOperand.getValue());
				}
				else if (token instanceof TokenFunction) {
					String functionName = ((TokenFunction) token).getName();
					functionStrategies.get(functionName).execute(tmpStack, requestContext);
				}
			}
			while(!tmpStack.isEmpty()) {
				Object value = tmpStack.pollLast();
				String toWrite;
				if (value instanceof String) {
					toWrite = (String)value;
				}
				else if (value instanceof Double) {
					toWrite = ((Double)value).toString();
				}
				else if (value instanceof Integer) {
					toWrite = ((Integer)value).toString();
				}
				else {
					throw new IllegalArgumentException("Type not supported");
				}
				try {
					requestContext.write(toWrite);
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}

		/**
		 * Method calls accept visitor method for all its children until end expression is reached. 
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			ValueWrapper variable = new ValueWrapper(node.getStartExpression().getValue());
			Object end = node.getEndExpression().getValue();
			Object step = node.getStepExpression().getValue();
			String variableName = node.getVariable().getName();
			multistack.push(variableName, variable);
			int numberOfChildren = node.numberOfChildren();
			while(multistack.peek(variableName).numCompare(end) != 1) {
				for (int i = 0; i < numberOfChildren; i++) {
					node.getChild(i).accept(this);
				}
				multistack.peek(variableName).increment(step);
			}
			multistack.pop(variableName);
		}

		/**
		 * Method sends TextNode text to client using RequestContext write method.
		 */
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}			
		}
	};

	/**
	 * Constructor with two parameters.
	 * @param documentNode Document node that is root node of parsed tree.
	 * @param requestContext Object responsible for creating response to clients.
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
		this.functionStrategies = new HashMap<>();
		this.functionStrategies.put("sin", new SinFunctionStrategy());
		this.functionStrategies.put("decfmt", new DecfmtFunctionStrategy());
		this.functionStrategies.put("dup", new DupFunctionStrategy());
		this.functionStrategies.put("swap", new SwapFunctionStrategy());
		this.functionStrategies.put("setMimeType", new SetMimeTypeFunctionStrategy());
		this.functionStrategies.put("paramGet", new ParamGetFunctionStrategy());
		this.functionStrategies.put("pparamGet", new PparamGetFunctionStrategy());
		this.functionStrategies.put("pparamSet", new PparamSetFunctionStrategy());
		this.functionStrategies.put("pparamDel", new PparamDelFunctionStrategy());
		this.functionStrategies.put("tparamGet", new TparamGetFunctionStrategy());
		this.functionStrategies.put("tparamSet", new TparamSetFunctionStrategy());
		this.functionStrategies.put("tparamDel", new TparamDelFunctionStrategy());
	}

	/**
	 * Method starts execution of smart script by calling accept visitor method of documentNode.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
