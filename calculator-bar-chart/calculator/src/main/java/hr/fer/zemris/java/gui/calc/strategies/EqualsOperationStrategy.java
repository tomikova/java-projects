package hr.fer.zemris.java.gui.calc.strategies;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.ObjectStack;

import javax.swing.JLabel;

/**
 * Class implements ActionStrategy interface and executes action when equals component is pressed.
 * @author Tomislav
 *
 */

public class EqualsOperationStrategy implements ActionStrategy {

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
	public EqualsOperationStrategy(JLabel displayLabel, ObjectStack operands, ObjectStack operations) {
		this.displayLabel = displayLabel;
		this.operands = operands;
		this.operations = operations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(String text) {

		double displayNum = Double.parseDouble(displayLabel.getText());

		if(Calculator.wasOperation) {
			operations.pop();
			Calculator.wasOperation = false;
		}

		if (!operations.isEmpty()) {

			//maximum two operations exist on stack
			if (operations.peek().equals("*") || operations.peek().equals("/")) {
				String operation = (String)operations.pop();
				double firstOperand = (double)operands.pop();
				double result = 0;
				if (operation.equals("*")) {
					result = firstOperand * displayNum;		
				}
				else if (operation.equals("/")) {
					result = firstOperand / displayNum;
				}
				displayNum = result;
			}

			//if stack is not empty its one of the low priority operations
			if (!operations.isEmpty()) {
				if (operations.peek().equals("+") || operations.peek().equals("-") || 
						operations.peek().equals("x^n") || operations.peek().equals("x^(1/n)")) {
					String operation = (String)operations.pop();
					double firstOperand = (double)operands.pop();
					double result = 0;
					if (operation.equals("+")) {
						result = firstOperand + displayNum;	
					}
					else if (operation.equals("-")) {
						result = firstOperand - displayNum;
					}
					else if (operation.equals("x^n")) {
						result = Math.pow(firstOperand, displayNum);			
					}
					else if (operation.equals("x^(1/n)")) {
						result = Math.pow(firstOperand, 1/(double)displayNum);
					}
					displayNum = result;
				}
			}
		}

		Calculator.operationResult = true;
		displayLabel.setText(String.valueOf(displayNum));
	}

}
