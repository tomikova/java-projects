package hr.fer.zemris.java.gui.calc.strategies;

import hr.fer.zemris.java.gui.calc.Calculator;

import javax.swing.JLabel;

/**
 * Class implements ActionStrategy interface and executes action when number or dot component is pressed.
 * @author Tomislav
 *
 */

public class NumberPressedStrategy implements ActionStrategy {

	/**
	 * Pressed component.
	 */
	private JLabel displayLabel;

	/**
	 * Constructor with one parameter.
	 * @param dispayLabel Pressed component.
	 */
	public NumberPressedStrategy(JLabel dispayLabel) {
		this.displayLabel = dispayLabel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(String text) {

		if (Calculator.wasOperation || Calculator.operationResult) {
			displayLabel.setText("0");
			Calculator.operationResult = false;
		}
		
		String displayText = displayLabel.getText();
		
		if (!displayText.equals("NaN") && !displayText.equals("Infinity") && !displayText.equals("-Infinity")) {
			displayText = displayText.equals("0") ? (text.equals(".") ? 
					displayText += text : text) : (!text.equals(".") ? 
							displayText += text : (!displayText.contains(".") ? 
									displayText + text : displayText));
			Calculator.wasOperation = false;
		}

		displayLabel.setText(displayText);
	}
}
