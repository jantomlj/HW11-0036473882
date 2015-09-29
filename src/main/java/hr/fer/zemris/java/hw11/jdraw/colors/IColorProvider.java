package hr.fer.zemris.java.hw11.jdraw.colors;

import java.awt.Color;

/**Interface that determines which methods color provider must implement
 * @author Jan Tomljanović
 */
public interface IColorProvider {

	/**Method that gets the current color that the color provider is providing
	 * @return current color that the color provider is providing
	 */
	public Color getCurrentColor ();
	
	/**Method by which the color provider can be identified
	 * @return name of the color provider
	 */
	public String getName ();
}
