package hr.fer.zemris.java.hw12.jvdraw;

import java.awt.Rectangle;
import java.util.List;

import hr.fer.zemris.java.hw12.geometry.GeometricalObject;

/**
 * Interface for objects that will be used as storage of geometrical objects.
 * Model provides methods for adding and removing geometrical objects and 
 * its subject for drawing model listener objects that needs to be informed
 * about any change considering geometrical objects.
 * @author Tomislav
 *
 */
public interface DrawingModel {	
	/**
	 * Method returns number of stored geometrical objects. 
	 * @return  Number of stored geometrical objects. 
	 */
	public int getSize();
	/**
	 * Method returns geometrical object at given index.
	 * @param index Index of geometrical object.
	 * @return Geometrical object at given index.
	 */
	public GeometricalObject getObject(int index);
	/**
	 * Method adds new geometrical object.
	 * @param object Geometrical object to be added.
	 */
	public void add(GeometricalObject object);
	/**
	 * Method adds new drawing model listener.
	 * @param l Drawing model listener to be added.
	 */
	public void addDrawingModelListener(DrawingModelListener l);
	/**
	 * Method removes drawing model listener.
	 * @param l Drawing model listener to be removed.
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
	/**
	 * Method notifies that geometrical object has changed.
	 * @param object Geometrical object that has changed.
	 */
	public void change(GeometricalObject object);
	/**
	 * Method removes all stored geometrical objects.
	 */
	public void removeAllObjects();
	/**
	 * Method adds geometrical objects.
	 * @param objects List of geometrical objects to be added.
	 */
	public void add(List<GeometricalObject> objects);
	/**
	 * Method returns minimal bounding rectangle of stored geometrical objects.
	 * @return Minimal bounding rectangle of stored geometrical objects.
	 */
	public Rectangle getBoundingRectangle();
}
