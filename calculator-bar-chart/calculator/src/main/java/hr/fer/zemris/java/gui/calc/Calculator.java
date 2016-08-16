package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.gui.calc.strategies.*;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * Program is a simple calculator.
 * @author Tomislav
 *
 */

public class Calculator {

	/**
	 * Default distance between components.
	 */
	private static final int DEFAULT = 3;
	/**
	 * Calculator components.
	 */
	private static List<JComponent> components = new ArrayList<>();
	/**
	 * Calculator components with inverse functionality.
	 */
	private static List<JComponent> invComponents = new ArrayList<>();
	/**
	 * Calculator components with inverse functionality default names.
	 */
	private static List<String> names = new ArrayList<>();
	/**
	 * Calculator components with inverse functionality inverse names.
	 */
	private static List<String> invNames = new ArrayList<>();
	/**
	 * Components constraints.
	 */
	private static List<RCPosition> constraints = new ArrayList<>();
	/**
	 * Flag indicates if last pressed component was operation component.
	 */
	public static boolean wasOperation = false;
	/**
	 * Flag indicates if displayed number is result of operation.
	 */
	public static boolean operationResult = false;
	/**
	 * Operands stack.
	 */
	private static ObjectStack operands = new ObjectStack();
	/**
	 * Operations stack.
	 */
	private static ObjectStack operations = new ObjectStack();
	/**
	 * Calculator memory stack.
	 */
	private static ObjectStack memory = new ObjectStack();
	/**
	 * Used layout manager.
	 */
	private static CalcLayout layout;
	/**
	 * Label for displaying result.
	 */
	private static JLabel displayLabel;
	/**
	 * Inverse functionality checkbox.
	 */
	private static JCheckBox checkBox;

	/**
	 * Method called at start of program.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {

		CalculatorObserver observer = new CalculatorObserver();

		JFrame frame = new JFrame();
		frame.setSize(450, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		layout = new CalcLayout(DEFAULT);
		layout.attach(observer);

		//display label
		JPanel panel = new JPanel(layout);
		displayLabel = new JLabel("0");
		displayLabel.setOpaque(true);
		displayLabel.setBackground(new Color(255,211,32,255));
		displayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(displayLabel, new RCPosition(1,1));

		//inv checkbox
		checkBox = new JCheckBox("inv");
		checkBox.addActionListener(e -> {
			List<String> list = checkBox.isSelected() ? invNames : names;
			for (int i = 0; i < invComponents.size(); i++) {
				JLabel label = (JLabel)invComponents.get(i);
				label.setText(list.get(i));
			}
		});
		components.add(checkBox);
		constraints.add(new RCPosition(5,7));

		setLabels();

		setNumberLabels(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new NumberPressedStrategy(displayLabel).execute(((JLabel)e.getComponent()).getText());
			}});

		setImmediateOperationLabels(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ImmeadiateOperationStrategy(displayLabel).execute(((JLabel)e.getComponent()).getText());
			}});

		setHighPriorityOperationLabels(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new HighPriorityOperationStrategy(displayLabel, operands, operations).execute(((JLabel)e.getComponent()).getText());
			}});
		
		setLowPriorityOperationLabels(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new LowPriorityOperationStrategy(displayLabel, operands, operations).execute(((JLabel)e.getComponent()).getText());
			}});
		
		setEqualsOperationLabel(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new EqualsOperationStrategy(displayLabel, operands, operations).execute(((JLabel)e.getComponent()).getText());
			}});

		addComponents(panel);

		frame.add(panel);
		frame.setVisible(true);
	}

	/**
	 * Method sets labels and their actions.
	 */
	private static void setLabels() {
		
		JLabel label = new JLabel("res");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				operands.clear();
				operations.clear();
				memory.clear();
				wasOperation = false;
				operationResult = false;
				displayLabel.setText("0");
			}});
		components.add(label);
		constraints.add(new RCPosition(2,7));
		
		label = new JLabel("push");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				memory.push(Double.parseDouble(displayLabel.getText()));
			}});
		components.add(label);
		constraints.add(new RCPosition(3,7));

		label = new JLabel("pop");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (memory.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Memory is empty!");
				}
				else {
					double num = (double)memory.pop();
					displayLabel.setText(String.valueOf(num));
					wasOperation = false;
				}
			}});
		components.add(label);
		constraints.add(new RCPosition(4,7));
	}

	/**
	 * Method sets number labels and their actions.
	 * @param mouseAdapter Object that executes action when label is pressed.
	 */
	private static void setNumberLabels(MouseAdapter mouseAdapter) {
		JLabel label = new JLabel("7");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(2,3));

		label = new JLabel("8");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(2,4));

		label = new JLabel("9");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(2,5));

		label = new JLabel("4");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(3,3));

		label = new JLabel("5");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(3,4));

		label = new JLabel("6");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(3,5));

		label = new JLabel("1");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(4,3));

		label = new JLabel("2");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(4,4));

		label = new JLabel("3");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(4,5));

		label = new JLabel("0");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(5,3));

		label = new JLabel(".");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(5,5));
	}

	/**
	 * Method sets immediate operations labels and their actions.
	 * @param mouseAdapter Object that executes action when label is pressed.
	 */
	private static void setImmediateOperationLabels(MouseAdapter mouseAdapter) {
		JLabel label = new JLabel("1/x");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(2,1));

		label = new JLabel("clr");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(1,7));

		label = new JLabel("sin");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		invComponents.add(label);
		names.add("sin");
		invNames.add("asin");
		constraints.add(new RCPosition(2,2));

		label = new JLabel("log");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		invComponents.add(label);
		names.add("log");
		invNames.add("10^n");
		constraints.add(new RCPosition(3,1));

		label = new JLabel("cos");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		invComponents.add(label);
		names.add("cos");
		invNames.add("acos");
		constraints.add(new RCPosition(3,2));

		label = new JLabel("ln");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		invComponents.add(label);
		names.add("ln");
		invNames.add("e^n");
		constraints.add(new RCPosition(4,1));

		label = new JLabel("tan");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		invComponents.add(label);
		names.add("tan");
		invNames.add("atan");
		constraints.add(new RCPosition(4,2));

		label = new JLabel("ctg");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		invComponents.add(label);
		names.add("ctg");
		invNames.add("actg");
		constraints.add(new RCPosition(5,2));

		label = new JLabel("+/-");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(5,4));
	}

	/**
	 * Method sets high priority operations labels and their actions.
	 * @param mouseAdapter Object that executes action when label is pressed.
	 */
	private static void setHighPriorityOperationLabels(MouseAdapter mouseAdapter) {
		JLabel label = new JLabel("/");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(2,6));

		label = new JLabel("*");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(3,6));
	}
	
	/**
	 * Method sets low priority operations labels and their actions.
	 * @param mouseAdapter Object that executes action when label is pressed.
	 */
	private static void setLowPriorityOperationLabels(MouseAdapter mouseAdapter) {
		JLabel label = new JLabel("-");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(4,6));
		
		label = new JLabel("+");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(5,6));
		
		label = new JLabel("x^n");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		invComponents.add(label);
		names.add("x^n");
		invNames.add("x^(1/n)");
		constraints.add(new RCPosition(5,1));
	}
	
	/**
	 * Method sets equals operation label and her action.
	 * @param mouseAdapter Object that executes action when label is pressed.
	 */
	private static void setEqualsOperationLabel(MouseAdapter mouseAdapter) {
		JLabel label = new JLabel("=");
		label.addMouseListener(mouseAdapter);
		components.add(label);
		constraints.add(new RCPosition(1,6));
	}

	/**
	 * Method adds child components to parent component.
	 * @param panel Parent component.
	 */
	private static void addComponents(JPanel panel) {		
		for(int i = 0; i < components.size(); i++) {
			JComponent component = components.get(i);
			RCPosition constraint = constraints.get(i);
			component.setOpaque(true);
			component.setBackground(new Color(114,159,207,255));

			if (component instanceof JLabel) {
				((JLabel)component).setHorizontalAlignment(SwingConstants.CENTER);
			}
			else {
				((JCheckBox)component).setHorizontalAlignment(SwingConstants.CENTER);
			}

			panel.add(component, constraint);
		}	
	}

	/**
	 * Class implements LayoutObserver interface for receiving informations about layout changes.
	 * @author Tomislav
	 *
	 */
	static class CalculatorObserver implements LayoutObserver {

		/**
		 * Method calculates text font size for all components based on 
		 * current frame window dimensions and component with maximum text dimensions.
		 */
		@Override
		public void notifyObserver() {

			double ratio = Double.MAX_VALUE;
			List<JComponent> components = layout.getComponents();

			for (int i = 0, size = components.size(); i < size; i++) {
				JComponent component = components.get(i);
				if (layout.getConstraints().get(i).getRow() == 1 && layout.getConstraints().get(i).getColumn() == 1) {
					continue;
				}
				Font labelFont = component.getFont();
				String componentText;
				if (component instanceof JLabel) {
					componentText = ((JLabel)component).getText();
				}
				else {
					componentText = ((JCheckBox)component).getText();
				}
				int stringWidth = component.getFontMetrics(labelFont).stringWidth(componentText);
				int componentWidth = component.getBounds().width;
				double widthRatio = (double)componentWidth / (double)stringWidth;
				if (widthRatio < ratio) {
					ratio = widthRatio;
				}
			}

			for (JComponent component : layout.getComponents()) {

				Font labelFont = component.getFont();
				int newFontSize = (int)(labelFont.getSize() * ratio/1.2);
				int componentHeight = component.getBounds().height;
				int fontSizeToUse = Math.min(newFontSize, componentHeight);
				component.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
			}
		}	
	}
}
