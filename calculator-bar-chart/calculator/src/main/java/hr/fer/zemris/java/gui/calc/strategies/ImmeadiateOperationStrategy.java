package hr.fer.zemris.java.gui.calc.strategies;

import hr.fer.zemris.java.gui.calc.Calculator;

import javax.swing.JLabel;

/**
 * Class implements ActionStrategy interface and executes action when immediate operation component is pressed.
 * @author Tomislav
 *
 */

public class ImmeadiateOperationStrategy implements ActionStrategy {

	/**
	 * Pressed component.
	 */
	private JLabel displayLabel;

	/**
	 * Constructor with one parameter.
	 * @param dispayLabel Pressed component.
	 */
	public ImmeadiateOperationStrategy(JLabel dispayLabel) {
		this.displayLabel = dispayLabel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(String text) {		
		String displayText = displayLabel.getText();

		double result = 0;
		double number = Double.parseDouble(displayText);

		if (text.equals("cos")) {
			result = Math.cos(number);
		}
		else if (text.equals("sin")) {
			result = Math.sin(number);
		}
		else if (text.equals("tan")) {
			result = Math.tan(number);
		}
		else if (text.equals("ctg")) {
			result = 1.0 / Math.tan(number);
		}
		else if (text.equals("log")) {
			result = Math.log10(number);
		}
		else if (text.equals("ln")) {
			result = Math.log(number);
		}
		else if (text.equals("acos")) {
			result = Math.acos(number);
		}
		else if (text.equals("asin")) {
			result = Math.asin(number);
		}
		else if (text.equals("atan")) {
			result = Math.atan(number);
		}
		else if (text.equals("actg")) {
			result =  Math.atan(1.0 / (1.0 / Math.tan(number)));
		}
		else if (text.equals("10^n")) {
			result = Math.pow(10, number);
		}
		else if (text.equals("e^n")) {
			result = Math.pow(Math.E, number);
		}
		else if (text.equals("+/-")) {
			result = -number;
		}
		else if (text.equals("1/x")) {
			result = 1.0 / number;
		}
		else if (text.equals("clr")) {
			result = 0;
		}

		String resultString = String.valueOf(result);

		if (resultString.equals("0.0") || resultString.equals("-0.0")) {
			resultString = "0";
		}

		Calculator.wasOperation = false;
		Calculator.operationResult = true;
		displayLabel.setText(resultString);
	}
}
