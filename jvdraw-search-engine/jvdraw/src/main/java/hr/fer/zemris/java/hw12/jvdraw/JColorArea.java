package hr.fer.zemris.java.hw12.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Class is component used to select colors. It is implementing IColorProvider interface and
 * it is a subject for color change listeners.
 * @author Tomislav
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Currently selected color.
	 */
	private Color selectedColor;
	/**
	 * List of color change listeners.
	 */
	private List<ColorChangeListener> listeners = new ArrayList<>();

	/**
	 * Default constructor.
	 * @param selectedColor Color taht is selected by  default.
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		this.setBackground(selectedColor);
		this.setPreferredSize(getPreferredSize());
		this.addMouseListener(new MouseAdapter() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				Color oldColor = selectedColor;
				Color newColor = JColorChooser.showDialog(null, "Change Color",
						oldColor);
				if (newColor != null) {
					JColorArea.this.selectedColor = newColor;
					for (ColorChangeListener listener : listeners) {
						listener.newColorSelected(JColorArea.this, oldColor, newColor);
					}
					JColorArea.this.setBackground(newColor);
					JColorArea.this.repaint();
				}
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15,15);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		Dimension size = getSize();
        Rectangle rect = new Rectangle(0, 0, size.width, size.height);      
		g2d.setColor(getBackground());
		g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	/**
	 * Method adds new color change listener.
	 * @param l Color change listener to be added.
	 */
	public void addColorChangeListener(ColorChangeListener l) {
		if (!listeners.contains(l) && l != null) {
			listeners.add(l);
		}
	}
	
	/**
	 * Method removes color change listener.
	 * @param l Color change listener to be removed.
	 */
	public void removeColorChangeListener(ColorChangeListener l) {
		if (listeners.contains(l)) {
			listeners.remove(l);
		}
	}

}
