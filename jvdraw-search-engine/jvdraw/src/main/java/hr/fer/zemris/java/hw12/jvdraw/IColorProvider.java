package hr.fer.zemris.java.hw12.jvdraw;

import java.awt.Color;

/**
 * Interface for color providers. Method for retrieving current color is defined.
 * @author Tomislav
 *
 */
public interface IColorProvider {
	/**
	 * Method return currently selected color.
	 * @return Currently selected color.
	 */
	public Color getCurrentColor();
}
