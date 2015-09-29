package hr.fer.zemris.java.hw11.jdraw.geoobj;

import hr.fer.zemris.java.hw11.jdraw.JDraw;

import java.awt.Component;
import java.awt.Graphics;

/**
 * Design of a geometrical object in the {@link JDraw} program.
 * 
 * @author Jan Tomljanović
 */
public interface GeometricalObject {

	/**
	 * Gets the name of the object
	 * 
	 * @return name of the object
	 */
	String getName();

	/**
	 * Sets the neame of the object to the one given in the argument
	 * 
	 * @param name
	 *            new name of the object
	 */
	void setName(String name);

	/**
	 * Adds a geometrical object listener to the object
	 * 
	 * @param l
	 *            listener to be added
	 */
	void addGeometricalObjectListener(GeometricalObjectListener l);

	/**
	 * Removes geometrical object listener from the object
	 * 
	 * @param l
	 *            listener to be removed
	 */
	void removeGeometricalObjectListener(GeometricalObjectListener l);

	/**
	 * Method that paints this object with the given graphics
	 * 
	 * @prama g graphics that will paint the object
	 */
	void paintYourself(Graphics g);

	/**
	 * Method that must be called within some opened frame. It opens a properties dialog that
	 * displays object properties. User can manualy change each property of the object
	 * 
	 * @param parent
	 *            component of the dialog that will be opened
	 */
	void propertiesDialog(Component parent);

	/**
	 * Method that produces a textual writing of the object that includes all object's
	 * properties
	 * 
	 * @return textual writing of the object
	 */
	String toText();
}
