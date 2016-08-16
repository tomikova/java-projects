package hr.fer.zemris.java.gui.layouts;

import hr.fer.zemris.java.gui.calc.LayoutObserver;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

/**
 * Class with custom layout for calculator components.
 * @author Tomislav
 *
 */

public class CalcLayout implements java.awt.LayoutManager2 {

	/**
	 * Number of calculator rows for components.
	 */
	private final static int ROWS = 5;
	/**
	 * Number of calculator columns for components.
	 */
	private final static int COLUMNS = 7;
	/**
	 * Distance between two components.
	 */
	private int distance;

	/**
	 * Minimum layout width;
	 */
	private int minWidth = 0;
	/**
	 * Minimum layout height.
	 */
	private int minHeight = 0;
	/**
	 * Preferred layout width;
	 */
	private int preferredWidth = 0;
	/**
	 * Prefered layout height.
	 */
	private int preferredHeight = 0;
	/**
	 * Minimum component width.
	 */
	private int minComponentWidth = 0;
	/**
	 * Minimum component height.
	 */
	private int minComponentHeight = 0;
	/**
	 * Preferred component width.
	 */
	private int preferredComponentWidth = 0;
	/**
	 * Preferred component height.
	 */
	private int preferredComponentHeight = 0;

	/**
	 * Flag indicates if minimum and preferred layout dimensions are known.
	 */
	private boolean sizeUnknown;

	/**
	 * Calculator components.
	 */
	private List<JComponent> components;
	/**
	 * Calculator component constraints.
	 */
	private List<RCPosition> constraints;
	/**
	 * Layout observers.
	 */
	private List<LayoutObserver> observers;

	/**
	 * Default constructor without parameters.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructor with one parameter.
	 * @param distance Distance between components.
	 */
	public CalcLayout(int distance) {
		this.distance = distance;
		this.components = new ArrayList<>();
		this.constraints = new ArrayList<>();
		this.observers = new ArrayList<>();
		this.sizeUnknown = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		this.components.add((JComponent)comp);
		this.constraints.add((RCPosition) constraints);
		this.sizeUnknown = true;
		setSizes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {

		Dimension dim = new Dimension(0, 0);

		setSizes();

		Insets insets = parent.getInsets();
		dim.width = minWidth
				+ insets.left + insets.right;
		dim.height = minHeight
				+ insets.top + insets.bottom;

		sizeUnknown = false;

		return dim;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {

		Dimension dim = new Dimension(0, 0);

		setSizes();

		Insets insets = parent.getInsets();

		dim.width = preferredWidth
				+ insets.left + insets.right;
		dim.height = preferredHeight
				+ insets.top + insets.bottom;

		sizeUnknown = false;

		return dim;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void layoutContainer(Container parent) {

		Insets insets = parent.getInsets();

		int maxWidth = parent.getWidth()
				- (insets.left + insets.right) - (COLUMNS-1)*distance;
		int maxHeight = parent.getHeight()
				- (insets.top + insets.bottom) - (ROWS-1)*distance;

		if (sizeUnknown) {
			setSizes();
		}

		int width = preferredWidth - (COLUMNS-1)*distance;
		int height = preferredHeight - (ROWS-1)*distance;

		double xScaleFactor = maxWidth/(double)(width);
		double yScaleFactor = maxHeight/(double)(height);

		for (int i = 0; i < components.size(); i++) {
			int row = constraints.get(i).getRow();
			int column = constraints.get(i).getColumn();
			int x = (int)Math.floor(insets.left + (column-1) * (preferredComponentWidth * xScaleFactor + distance));
			int y = (int)Math.floor(insets.top + (row-1) * (preferredComponentHeight * yScaleFactor + distance));

			if (column == 1 && row == 1) {
				components.get(i).setBounds(x,y, (int)Math.floor(5*preferredComponentWidth*xScaleFactor + 4*distance), 
						(int)Math.floor(preferredComponentHeight*yScaleFactor));
			}
			else {
				components.get(i).setBounds(x,y, (int)Math.floor(preferredComponentWidth*xScaleFactor), 
						(int)Math.floor(preferredComponentHeight*yScaleFactor));
			}
		}

		for (LayoutObserver observer : observers) {
			observer.notifyObserver();
		}
	}
	
	/**
	 * Method calculates minimum and preferred layout dimensions.
	 */
	private void setSizes() {

		int nComps = components.size();
		Dimension d = null;

		preferredWidth = 0;
		preferredHeight = 0;
		minWidth = 0;
		minHeight = 0;

		minComponentWidth = 0;
		minComponentHeight = 0;
		preferredComponentWidth = 0;
		preferredComponentHeight = 0;


		for (int i = 0; i < nComps; i++) {
			Component c = components.get(i);
			d = c.getPreferredSize();
			if (d == null) {
				d = new Dimension(0,0);
			}
			int width = constraints.get(i).getColumn() == 1 ? (int)Math.ceil((d.width - 4*distance)/(double)5) : d.width;

			if (width > preferredComponentWidth ) {
				preferredComponentWidth = width;
			}
			if (d.height > preferredComponentHeight) {
				preferredComponentHeight = d.height;
			}
			d = c.getMinimumSize();
			if (d == null) {
				d = new Dimension(0,0);
			}
			width = (constraints.get(i).getRow() == 1 && constraints.get(i).getColumn() == 1)  ? 
					(int)Math.ceil((d.width - 4*distance)/(double)5) : d.width;

					if (width > minComponentWidth ) {
						minComponentWidth = width;
					}
					if (d.height > minComponentHeight) {
						minComponentHeight = d.height;
					}
		}

		if (nComps > 0) {
			preferredWidth = COLUMNS * preferredComponentWidth + (COLUMNS-1) * distance;
			preferredHeight = ROWS * preferredComponentHeight + (ROWS-1) * distance;

			minWidth = COLUMNS * minComponentWidth + (COLUMNS-1) * distance;
			minHeight = ROWS * minComponentHeight + (ROWS-1) * distance;
		}
	}

	/**
	 * Method returns layout components.
	 * @return Layout components.
	 */
	public List<JComponent> getComponents() {
		return components;
	}

	/**
	 * Method returns layout components constraints.
	 * @return Layout components constraints.
	 */
	public List<RCPosition> getConstraints() {
		return constraints;
	}

	/**
	 * Method adds observer to layout observers list.
	 * @param observer Layout observer to add.
	 */
	public void attach(LayoutObserver observer) {
		observers.add(observer);
	}

	/**
	 * Method removes observer from layout observers list.
	 * @param observer Layout observer to remove.
	 */
	public void detach(LayoutObserver observer) {
		observers.remove(observer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidateLayout(Container target) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
	}

}
