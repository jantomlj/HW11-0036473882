package hr.fer.zemris.java.hw11.jdraw.drawing;

import hr.fer.zemris.java.hw11.jdraw.geoobj.Circle;
import hr.fer.zemris.java.hw11.jdraw.geoobj.FilledCircle;
import hr.fer.zemris.java.hw11.jdraw.geoobj.GeoObjectFactory;
import hr.fer.zemris.java.hw11.jdraw.geoobj.GeometricalObject;
import hr.fer.zemris.java.hw11.jdraw.geoobj.Line;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**Class that implements mouse motion listener that draws objects as mouse moves.
 * Used by {@link CanvasMouseListener} when drawing objects.
 * @author Jan Tomljanović
 */
public class CanvasMouseMotionListener extends MouseMotionAdapter {

	private String shape;
	private Point start;
	private GeometricalObject myObject;

	/**Creates a new instance wtih given drawing model, starting point of the drawings that will be made, source and
	 * shape that determines what will be drawn
	 * @param model
	 * @param start point of the object that will be drawn
	 * @param source
	 * @param shape text that determines which geometrical object will be drawn.7
	 * @throws IllegalArgumentException
	 */
	public CanvasMouseMotionListener(ConcreteDrawingModel model, Point start, JDrawingCanvas source, String shape) {
		if (model == null || start == null || source == null) {
			throw new IllegalArgumentException();
		}
		this.start = start;
		this.shape = shape;
		myObject = GeoObjectFactory.make(this.shape, start, start, source.getCurrentFGcolor(),
				source.getCurrentBGcolor());
		model.add(myObject);
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		Point point = e.getPoint();
		fixMyObject(point);
	}

	private void fixMyObject(Point second) {
		if (shape.equals("Line")) {
			Line l = (Line) myObject;
			l.setX2(second.x);
			l.setY2(second.y);
		} 
		else if (shape.equals("Circle")) {
			Circle c = (Circle) myObject;
			double radius = Point.distance(start.x, start.y, second.x, second.y);
			c.setR(radius);
		}
		else if (shape.equals("Filled circle")) {
			FilledCircle c = (FilledCircle) myObject;
			double radius = Point.distance(start.x, start.y, second.x, second.y);
			c.setR(radius);
		}
		else {
			throw new IllegalStateException("Shape is in bad state");
		}
	}
}
