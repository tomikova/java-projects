package hr.fer.zemris.java.hw12.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Class extending JLabel that is color change listener and it 
 * displays currently selected colors.
 * @author Tomislav
 *
 */
public class JColorLabel extends JLabel implements ColorChangeListener {
	
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Background color provider.
	 */
	IColorProvider bgColorProvider;
	/**
	 * Foreground color provider.
	 */
	IColorProvider fgColorProvider;
	
	/**
	 * Default color label constructor.
	 * @param bgColorProvider Background color provider.
	 * @param fgColorProvider  Foreground color provider.
	 */
	public JColorLabel(IColorProvider bgColorProvider, IColorProvider fgColorProvider) {
		this.bgColorProvider = bgColorProvider;
		this.fgColorProvider = fgColorProvider;
		setLabelText();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor) {
		setLabelText();	
	}
	
	/**
	 * Method sets label text.
	 */
	private void setLabelText() {
		Color bgColor = bgColorProvider.getCurrentColor();
		Color fgColor = fgColorProvider.getCurrentColor();
		
		this.setText("Foreground color: ("+fgColor.getRed()+", "+fgColor.getGreen()+", "+fgColor.getBlue()+
				") Background color: ("+bgColor.getRed()+", "+bgColor.getGreen()+", "+bgColor.getBlue()+")");
	}
}
