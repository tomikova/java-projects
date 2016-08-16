package hr.fer.zemris.java.gui.charts;

/**
 * Class holds values that will be drawn on chart.
 * @author Tomislav
 *
 */
public class XYValue {
	
	/**
	 * X axis value.
	 */
	private final int x;
	/**
	 * Y axis value.
	 */
	private final int y;
	
	/**
	 * Constructor with two parameters.
	 * @param x X axis value.
	 * @param y Y axis value.
	 * @throws IllegalArgumentException In case x or y axis values are negative.
	 */
	public XYValue(int x, int y) {
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException("X and y axis values must be not negative.");
		}
		this.x = x;
		this.y = y;
	}

	/**
	 * Method returns x axis value.
	 * @return X axis value.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Method returns y axis value.
	 * @return Y axis value.
	 */
	public int getY() {
		return y;
	}
	
}
