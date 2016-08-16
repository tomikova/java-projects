package hr.fer.zemris.java.gui.charts;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 * Class models and paints BarChart component.
 * @author Tomislav
 *
 */

public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Used BarChart object.
	 */
	private BarChart barChart;
	
	/**
	 * Constructor with one parameter.
	 * @param barChart Used BarChart object.
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int vDiff = this.getRootPane().getHeight() - getHeight();
		barChart.draw(g2, getWidth(), getHeight(), vDiff);
	}
}
