package hr.fer.zemris.java.hw12.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Class represents geometrical object line.
 * @author Tomislav
 *
 */
public class Line extends GeometricalObject {

	/**
	 * Line start point.
	 */
	private Point startPoint;
	/**
	 * Line end point.
	 */
	private Point endPoint;
	/**
	 * Line color.
	 */
	private Color color;

	/**
	 * Default line constructor.
	 * @param startPoint Line start point.
	 * @param endPoint Line end point.
	 * @param color Line color.
	 */
	public Line(Point startPoint, Point endPoint, Color color) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.color = color;
	}

	/**
	 * Method returns line start point.
	 * @return Line start point.
	 */
	public Point getStartPoint() {
		return startPoint;
	}

	/**
	 * Method sets line start point.
	 * @param startPoint New line start point.
	 */
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	/**
	 * Method return line end point.
	 * @return Line end point.
	 */
	public Point getEndPoint() {
		return endPoint;
	}

	/**
	 * Method sets line end point.
	 * @param endPoint New line end point.
	 */
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * Method returns line color.
	 * @return Line color.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Method sets line color.
	 * @param color New line color.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "LINE "+startPoint.x+" "+startPoint.y+" "+endPoint.x+" "+endPoint.y+" "
				+color.getRed()+" "+color.getGreen()+" "+color.getBlue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle getBoundingRectangle() {
		int leftXCoordinate;
		int leftYCoordinate; 
		int rightXCoordinate;
		int rightYCoordinate;

		if (startPoint.x < endPoint.x) {
			leftXCoordinate = startPoint.x;
			rightXCoordinate = endPoint.x;
		}
		else {
			leftXCoordinate = endPoint.x;
			rightXCoordinate = startPoint.x;
		}

		if (startPoint.y < endPoint.y) {
			leftYCoordinate = startPoint.y;
			rightYCoordinate = endPoint.y;
		}
		else {
			leftYCoordinate = endPoint.y;
			rightYCoordinate = startPoint.y;
		}
		return new Rectangle(leftXCoordinate, leftYCoordinate, 
				rightXCoordinate-leftXCoordinate, rightYCoordinate-leftYCoordinate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(Graphics2D g2d, int shiftX, int shiftY) {
		g2d.setColor(color);
		g2d.drawLine(startPoint.x+shiftX, startPoint.y+shiftY, endPoint.x+shiftX, endPoint.y+shiftY);
	}

}
