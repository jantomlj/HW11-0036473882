package hr.fer.zemris.java.hw11.jdraw.geoobj;

/**Interface for every class that wants to listen to changes in a geometrical object
 * @author Jan Tomljanović
 */
public interface GeometricalObjectListener {

	/**Method that is called when geometrical object has been changed
	 * @param source object on which the change occured
	 */
	void gemetricalObjectChanged (GeometricalObject source);
}
