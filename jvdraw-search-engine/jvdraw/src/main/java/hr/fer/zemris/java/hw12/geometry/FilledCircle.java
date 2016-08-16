package hr.fer.zemris.java.hw12.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Class represents filled circle geometrical objects.
 * @author Tomislav
 *
 */
public class FilledCircle extends Circle {
	
	/**
	 * Color of circle fill.
	 */
	private Color fillColor;

	/**
	 * Default filled circle constructor.
	 * @param center Circle center point.
	 * @param radius Circle radius.
	 * @param outlineColor Color of circle outline.
	 * @param fillColor Color of circle fill.
	 */
	public FilledCircle(Point center, int radius,
			Color outlineColor, Color fillColor) {
		super(center, radius, outlineColor);
		this.fillColor = fillColor;
	}

	/**
	 * Method returns circle fill color.
	 * @return Circle fill color.
	 */
	public Color getFillColor() {
		return fillColor;
	}

	/**
	 * Method sets circle fill color.
	 * @param fillColor New circle fill color.
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "FCIRCLE "+center.x+" "+center.y+" "+radius+" "
				+outlineColor.getRed()+" "+outlineColor.getGreen()+" "+outlineColor.getBlue()+" "
				+fillColor.getRed()+" "+fillColor.getGreen()+" "+fillColor.getBlue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(Graphics2D g2d, int shiftX, int shiftY) {
		g2d.setColor(fillColor);
		g2d.fillOval(center.x-radius+shiftX, center.y-radius+shiftY, 
				2*radius, 2*radius);
		g2d.setColor(outlineColor);
		g2d.drawOval(center.x-radius+shiftX, center.y-radius+shiftY, 
				2*radius, 2*radius);
	}
}
