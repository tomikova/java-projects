package hr.fer.zemris.java.gui.calc.strategies;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.ObjectStack;

import javax.swing.JLabel;

/**
 * Class implements ActionStrategy interface and executes action when low priority component is pressed.
 * @author Tomislav
 *
 */

public class LowPriorityOperationStrategy implements ActionStrategy {

	/**
	 * Pressed component.
	 */
	private JLabel displayLabel;
	/**
	 * Calculator operands.
	 */
	private ObjectStack operands;
	/**
	 * Calculator operations.
	 */
	private ObjectStack operations;

	/**
	 * Constructor with three parameters.
	 * @param displayLabel Pressed component.
	 * @param operands Calculator operands.
	 * @param operations Calculator operations.
	 */
	public LowPriorityOperationStrategy(JLabel displayLabel, ObjectStack operands, ObjectStack operations) {
		this.displayLabel = displayLabel;
		this.operands = operands;
		this.operations = operations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(String text) {

		String displayText = displayLabel.getText();

		if(!Calculator.wasOperation) {

			if (!operands.isEmpty() && !operations.isEmpty() && (operations.peek().equals("*") || operations.peek().equals("/"))) {
				double firstOperand = (double)operands.pop();
				String operation = (String)operations.pop();
				double secondOperand = Double.parseDouble(displayText);
				double result;
				if (operation.equals("*")) {
					result = firstOperand * secondOperand;
					displayLabel.setText(String.valueOf(result));
					displayText = displayLabel.getText();
				}
				else if (operation.equals("/")) {
					result = firstOperand / secondOperand;
					displayLabel.setText(String.valueOf(result));
					displayText = displayLabel.getText();
				}
			}

			if (operations.isEmpty() || (!operations.peek().equals("+") && !operations.peek().equals("-") 
					&& !operations.peek().equals("x^n") && !operations.peek().equals("x^(1/n)"))) {
				operands.push(Double.parseDouble(displayText));
				operations.push(text);
			}
			else {
				String operation = (String)operations.pop();
				double firstOperand = (double)operands.pop();
				double secondOperand = Double.parseDouble(displayText);
				double result = 0;
				if (operation.equals("+")) {
					result = firstOperand + secondOperand;		
				}
				else if (operation.equals("-")) {
					result = firstOperand - secondOperand;
				}
				else if (operation.equals("x^n")) {
					result = Math.pow(firstOperand, secondOperand);
				}
				else if (operation.equals("x^(1/n)")) {
					result = Math.pow(firstOperand, 1/(double)secondOperand);
				}
				operands.push(result);
				operations.push(text);
				displayLabel.setText(String.valueOf(result));	
			}
		}
		else {
			operations.pop();
			operations.push(text);
		}
		Calculator.wasOperation = true;
	}
}
