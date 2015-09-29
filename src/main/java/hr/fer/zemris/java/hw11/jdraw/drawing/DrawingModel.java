package hr.fer.zemris.java.hw11.jdraw.drawing;

import hr.fer.zemris.java.hw11.jdraw.geoobj.GeometricalObject;

/**Interface that is a model for {@link JDrawingCanvas}. It should provide a list
 * of {@link GeometricalObject} that is contains.
 * @author Jan Tomljanović
 */
public interface DrawingModel {

	/**Gets the number of stored geometrical objects in the model
	 * @return numer of objects
	 */
	public int getSize();

	/**Method that gets the object int the model at the given position in the list
	 * @return specified geometrical object
	 */
	public GeometricalObject getObject(int index);

	/**Method that adds given geometrical object to the model
	 * @param objects given object to be added to the model
	 */
	public void add(GeometricalObject object);

	/**Adds a new listener to the model.
	 * @param l new listener
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**Removes given listener from the model
	 * @param l
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

}
