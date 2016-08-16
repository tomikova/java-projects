package hr.fer.zemris.java.hw12.jvdraw;

/**
 * Interface for drawing model listeners that needs to be informed about changes
 * considering geometrical objects in drawing model.
 * @author Tomislav
 *
 */
public interface DrawingModelListener {
	/**
	 * Method informs listeners that new objects have been added.
	 * @param source Drawing model in which objects have been added.
	 * @param index0 Index of first object added.
	 * @param index1 Index of last object added.
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);
	/**
	 * Method informs listeners that objects have been removed.
	 * @param source Drawing model in which objects have been removed.
	 * @param index0 Index of first object removed.
	 * @param index1 Index of last object removed.
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	/**
	 * Method informs listeners that objects have been changed.
	 * @param source Drawing model in which objects have been changed.
	 * @param index0 Index of first object changed.
	 * @param index1 Index of last object changed.
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
