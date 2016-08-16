package hr.fer.zemris.java.hw12.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Class represents geometrical object circle.
 * @author Tomislav
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * Circle center point.
	 */
	Point center;
	/**
	 * Circle radius.
	 */
	int radius;
	/**
	 * Color of circles outline.
	 */
	Color outlineColor;

	/**
	 * Default circle constructor.
	 * @param center Circle center point.
	 * @param radius Circle radius.
	 * @param outlineColor Color of circle outline.
	 */
	public Circle(Point center, int radius, Color outlineColor) {
		this.center = center;
		this.radius = radius;
		this.outlineColor = outlineColor;
	}

	/**
	 * Method returns circle center point.
	 * @return Circle center point.
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Method sets circle center point.
	 * @param center New circle center point.
	 */
	public void setCenter(Point center) {
		this.center = center;
	}

	/**
	 * Method returns circle radius.
	 * @return Circle radius.
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Method sets circle radius.
	 * @param radius New circle radius.
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * Method returns circle oitline color.
	 * @return Circle outline color.
	 */
	public Color getOutlineColor() {
		return outlineColor;
	}

	/**
	 * Methd sets circle outline color.
	 * @param outlineColor New circle outline color.
	 */
	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "CIRCLE "+center.x+" "+center.y+" "+radius+" "
				+outlineColor.getRed()+" "+outlineColor.getGreen()+" "+outlineColor.getBlue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle getBoundingRectangle() {
		return new Rectangle(center.x-radius,center.y-radius,2*radius,2*radius);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(Graphics2D g2d, int shiftX, int shiftY) {
		g2d.setColor(outlineColor);
		g2d.drawOval(center.x+shiftX-radius, center.y+shiftY-radius, 
				2*radius, 2*radius);
	}


}
