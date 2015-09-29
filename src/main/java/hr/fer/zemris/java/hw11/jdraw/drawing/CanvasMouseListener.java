package hr.fer.zemris.java.hw11.jdraw.drawing;

import hr.fer.zemris.java.hw11.jdraw.geoobj.GeoObjectFactory;
import hr.fer.zemris.java.hw11.jdraw.geoobj.GeometricalObject;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**Class that implements Mouse listener adapted for listening clicks in {@link JDrawingCanvas}.
 * @author Jan Tomljanović
 */
public class CanvasMouseListener extends MouseAdapter {

	private boolean clicked;
	private JDrawingCanvas source;
	private CanvasMouseMotionListener motion;
	private ConcreteDrawingModel model;

	private Point first;
	private Point second;

	/**Creates a new instance with given source and drawing model that
	 * @param source
	 * @param model
	 */
	public CanvasMouseListener(JDrawingCanvas source, ConcreteDrawingModel model) {
		if (source == null || model == null) {
			throw new IllegalArgumentException();
		}
		this.model = model;
		this.source = source;
		clicked = false;
		first = null;
		second = null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!clicked) {
			clicked = true;
			first = e.getPoint();
			motion = new CanvasMouseMotionListener(model, first, source, source.getButtonPressed());
			source.addMouseMotionListener(motion);
			return;
		}
		second = e.getPoint();
		source.removeMouseMotionListener(motion);
		GeometricalObject newObj = GeoObjectFactory.make(source.getButtonPressed(), first, second,
				source.getCurrentFGcolor(), source.getCurrentBGcolor());
		model.remove(model.getSize() - 1); // removing the fiction object that motionlistener has added
		newObj.setName(newObj.getName() + " #" + model.getSize());
		model.add(newObj);
		clicked = false;
		first = null;
		second = null;
	}

}
