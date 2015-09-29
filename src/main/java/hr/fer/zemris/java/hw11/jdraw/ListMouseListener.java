package hr.fer.zemris.java.hw11.jdraw;

import hr.fer.zemris.java.hw11.jdraw.geoobj.GeometricalObject;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;

/**Mouse listener for a list that displays names of drawn objects. Objects can be modified
 * by double clicking on their name in the list
 * 
 * @author Jan Tomljanović
 */
public class ListMouseListener extends MouseAdapter {

	private Component parent;
	
	/**Contructs list mouse listener with given parent component.
	 * @param parent component
	 */
	public ListMouseListener (Component parent) {
		if (parent == null) {
			throw new IllegalArgumentException();
		}
		this.parent = parent;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() != 2) {
			return;
		}
		@SuppressWarnings("unchecked")
		JList<GeometricalObject> list = (JList<GeometricalObject>) e.getComponent();
		int index = list.getSelectedIndex();
		if (index == -1) {
			return;
		}
		list.getModel().getElementAt(index).propertiesDialog(parent);
	}
	
	
}
