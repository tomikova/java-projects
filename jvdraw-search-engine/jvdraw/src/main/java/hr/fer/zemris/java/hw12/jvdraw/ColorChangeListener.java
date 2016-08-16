package hr.fer.zemris.java.hw12.jvdraw;

import java.awt.Color;

/**
 * Interface for color change listener. Interface provides method for notifying
 * that a new color has been selected.
 * @author Tomislav
 *
 */
public interface ColorChangeListener {
	
	/**
	 * Method used to notify listeners that new color has been selected.
	 * @param source Color provider.
	 * @param oldColor Old color.
	 * @param newColor New selected color.
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
