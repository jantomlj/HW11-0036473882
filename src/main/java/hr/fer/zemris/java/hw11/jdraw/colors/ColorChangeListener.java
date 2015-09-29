package hr.fer.zemris.java.hw11.jdraw.colors;

import java.awt.Color;

/**Interface that determines method to be implemented in the class that can listen to some color
 * provider
 * 
 * @author Jan Tomljanović
 */
public interface ColorChangeListener {

	/**Method that is called when color provider changes its color.
	 * @param source source of the color change
	 * @param oldColor
	 * @param newColor
	 */
	public void newColorSelected (IColorProvider source, Color oldColor, Color newColor);
}
