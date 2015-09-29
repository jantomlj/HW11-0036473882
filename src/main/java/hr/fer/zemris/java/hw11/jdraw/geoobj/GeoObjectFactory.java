package hr.fer.zemris.java.hw11.jdraw.geoobj;

import java.awt.Color;
import java.awt.Point;

/**Class that contains static methods that produce {@link GeometricalObject}s
 * @author Jan Tomljanović
 */
public class GeoObjectFactory {

	private GeoObjectFactory() {
	}

	/**Method that creates a new geometrical object from given object id that specifies objects type, 
	 * two points that determine the size and place of the object, foreground and background colors, and
	 * listeners that should be wired to the object
	 * @param id string that specifes the object to be created
	 * @param first point
	 * @param second point
	 * @param fore -ground color
	 * @param back -ground color
	 * @param listeners
	 * @throws IllegalArgumentException
	 */
	public static GeometricalObject make(String id, Point first, Point second, Color fore, Color back,
			GeometricalObjectListener... listeners) {
		if (id == null || first == null || second == null || fore == null) {
			throw new IllegalArgumentException();
		}
		if (id.equals("Line")) {
			return new Line("Line", first.x, first.y, second.x, second.y, fore, listeners);
		}
		else if (id.equals("Circle")) {
			return new Circle("Circle", first.x, first.y,
					Point.distance(first.x, first.y, second.x, second.y), fore, listeners);
		}
		else if (id.equals("Filled circle")) {
			if (back == null) {
				throw new IllegalArgumentException();
			}
			return new FilledCircle("Filled circle", first.x, first.y,
					Point.distance(first.x, first.y, second.x, second.y), fore, back, listeners);
		}
		else {
			throw new IllegalArgumentException("Invalid geometrical object identifier");
		}
	}
	
	/**Method that takes object type and object data in string format and produces object
	 * @param name name of the object type
	 * @param data texutal data in the correct format
	 * @return produced geomterical object
	 * @throws IllegalArgumentException
	 */
	public static GeometricalObject fromText(String name, String data) {
		if (name.equals("LINE")) {
			return Line.fromText(data);
		}
		else if (name.equals("CIRCLE")) {
			return Circle.fromText(data);
		}
		else if (name.equals("FCIRCLE")) {
			return FilledCircle.fromText(data);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
}
