package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.geometry.GeometricalObject;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class that is a drawing object model for list of drawing objects.
 * Objects list model is listener of drawing model and when notified about change
 * in drawing model notifies its own listeners.
 * @author Tomislav
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Drawing model which list model is using for retrieveing geometrical objects.
	 */
	private DrawingModel adaptee;
	
	/**
	 * Default list model constructor.
	 * @param adaptee Drawing model which list model is using for retrieveing geometrical objects.
	 */
	public DrawingObjectListModel(DrawingModel adaptee) {
		this.adaptee = adaptee;
		for(int i = 0; i < adaptee.getSize(); i++) {
			for(ListDataListener observer: getListDataListeners()) {
				observer.intervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, i, i));
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return adaptee.getSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GeometricalObject getElementAt(int index) {
		return adaptee.getObject(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		for(ListDataListener observer: getListDataListeners()) {
			observer.intervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index0, index1));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {	
	}

}
