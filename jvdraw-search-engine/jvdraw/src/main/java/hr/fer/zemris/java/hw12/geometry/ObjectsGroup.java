package hr.fer.zemris.java.hw12.geometry;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class represents group of geometrical objects. It is treated as single
 * geometrical object following composite design pattern.
 * @author Tomislav
 *
 */
public class ObjectsGroup extends GeometricalObject {

	/**
	 * List of feometrical objects in object group.
	 */
	private List<GeometricalObject> objects = new ArrayList<>();

	/**
	 * Method adds geometrical object to object group.
	 * @param o Geometrical object to add.
	 */
	public void add(GeometricalObject o) {
		if (!objects.contains(o)) {
			objects.add(o);
		}
	}

	/**
	 * Method removes geometrical object from object group.
	 * @param o Geometrical object to be removed.
	 */
	public void remove(GeometricalObject o) {
		if (objects.contains(o)) {
			objects.remove(o);
		}
	}
	
	/**
	 * Removes all objects from object group.
	 */
	public void removeAll() {
		objects.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(Graphics2D g2d, int shiftX, int shiftY) {
		for (GeometricalObject obj : objects) {
			obj.draw(g2d, shiftX, shiftY);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle getBoundingRectangle() {
		if (objects.isEmpty()) {
			return new Rectangle(0,0,0,0);
		}
		Iterator<GeometricalObject> it = objects.iterator();
		Rectangle result = it.next().getBoundingRectangle();
		int left = (int)result.getMinX();
		int right = (int)result.getMaxX();
		int top = (int)result.getMinY();
		int bottom = (int)result.getMaxY();
		for(;it.hasNext();) {
			Rectangle r = it.next().getBoundingRectangle();
			left = Math.min(left, (int)r.getMinX());
			top = Math.min(top, (int)r.getMinY());
			right = Math.max(right, (int)r.getMaxX());
			bottom = Math.max(bottom, (int)r.getMaxY());
		}
		return new Rectangle(left,top,right-left,bottom-top);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "ObjectGroup";
	}
}
