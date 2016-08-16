package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

/**
 * Class models foor loop node in document tree parsed with SmartScriptParser.
 * @author Tomislav
 *
 */

public class ForLoopNode extends Node {
	
	/**
	 * Token that is representing variable in for loop expression.
	 */
	private TokenVariable variable;
	/**
	 * Token representing start expression value.
	 */
	private Token startExpression;
	/**
	 * Token representing end expression value.
	 */
	private Token endExpression;
	/**
	 * Token representing step expression value.
	 */
	private Token stepExpression;
	
	/**
	 * Default constructor.
	 * @param variable Token that is representing variable in for loop expression.
	 * @param startExpression Token representing start expression value.
	 * @param endExpression Token representing end expression value.
	 * @param stepExpression Token representing step expression value. Can be null.
	 */
	public ForLoopNode(TokenVariable variable, Token startExpression,
			Token endExpression, Token stepExpression){
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		if (stepExpression == null) {
			this.stepExpression = new TokenConstantInteger(1);
		}
		else {
			this.stepExpression = stepExpression;
		}
	}
	
	/**
	* Method return nodes variable token.
	* @return Variable token.
	*/
	
	public TokenVariable getVariable() {
		return variable;
	}
	
	/**
	* Method returns start expression token.
	* @return Start expression token.
	*/
	
	public Token getStartExpression() {
		return startExpression;
	}
	
	/**
	* Method returns end expression token.
	* @return End expression token.
	*/
	
	public Token getEndExpression() {
		return endExpression;
	}
	
	/**
	* Method returns step expression token.
	* @return Step expression token.
	*/
	
	public Token getStepExpression() {
		return stepExpression;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText(){
		String text = null;
		StringBuilder sb = new StringBuilder();
		sb.append("{$FOR ");
		sb.append(variable.asText()+" ");
		sb.append(startExpression.asText()+" ");
		sb.append(endExpression.asText()+" ");
		if (stepExpression != null){
			sb.append(stepExpression.asText());
		}
		sb.append("$}");
		text = sb.toString();
		return text;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);	
	}
}
