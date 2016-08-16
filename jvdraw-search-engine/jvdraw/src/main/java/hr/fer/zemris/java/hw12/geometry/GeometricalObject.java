package hr.fer.zemris.java.hw12.geometry;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Abstract class from which all geometrical objects are inherited.
 * It defines methods for founding bounding rectangle of geometrical object
 * and methods for drawing geometrical objects.
 * @author Tomislav
 *
 */
public abstract class GeometricalObject {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract String toString();
	
	/**
	 * Method founds minimal bounding rectangle of geometrical object.
	 * @return Minimal bounding rectangle of geometrical object.
	 */
	public abstract Rectangle getBoundingRectangle();
	
	/**
	 * Method used for drawing geometrical objects.
	 * @param g2d Graphics object.
	 * @param shiftX Indicates how much objects and in what direction
	 * object must be shifted on x axis.
	 * @param shiftY Indicates how much objects and in what direction
	 * object must be shifted on y axis.
	 */
	public abstract void draw(Graphics2D g2d, int shiftX, int shiftY);
	
	/**
	 * Default draw method with no shifts.
	 * @param g2d Graphical object
	 */
	public void draw(Graphics2D g2d) {
		this.draw(g2d, 0, 0);
	}
}
