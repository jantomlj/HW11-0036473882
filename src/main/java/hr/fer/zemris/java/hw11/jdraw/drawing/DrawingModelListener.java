package hr.fer.zemris.java.hw11.jdraw.drawing;

/** Interface that determines which method a class must have if it should be a listener of
 * a {@link DrawingModel}
 * @author Jan Tomljanović
 */
public interface DrawingModelListener {

	/**Method that informs that new objects have been added to the model
	 * @param source model that has changed
	 * @param index0 starting index of the added objects
	 * @param index1 ending index of the added objects
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**Method that informs that new objects have been removed from the model
	 * @param source model that has changed
	 * @param index0 starting index of the removed objects
	 * @param index1 ending index of the removed objects
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**Method that informs that objects in the model have been changed
	 * @param source model that has changed
	 * @param index0 starting index of the changed objects
	 * @param index1 ending index of the changed objects
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
