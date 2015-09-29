package hr.fer.zemris.java.hw11.jdraw.drawing;

import hr.fer.zemris.java.hw11.jdraw.JDraw;
import hr.fer.zemris.java.hw11.jdraw.colors.ColorChangeListener;
import hr.fer.zemris.java.hw11.jdraw.colors.IColorProvider;
import hr.fer.zemris.java.hw11.jdraw.toggles.ButtonGroupListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;

/**Central element of the {@link JDraw} program. User can draw objects in the canvas. <br>
 * Implements {@link ColorChangeListener}, {@link DrawingModelListener} and {@link ButtonGroupListener}.
 * @author Jan Tomljanović
 */
public class JDrawingCanvas extends JComponent implements ColorChangeListener, DrawingModelListener,
		ButtonGroupListener {

	private static final long serialVersionUID = 1L;

	private static final int PREFERED_WIDTH = 850;
	private static final int PREFERED_HEIGHT = 500;

	private String buttonPressed;
	private Color currentBGcolor;
	private Color currentFGcolor;
	private ConcreteDrawingModel model;
	private MouseListener myListener;

	public JDrawingCanvas(ConcreteDrawingModel model) {
		if (model == null) {
			throw new IllegalArgumentException();
		}
		this.model = model;
		model.addDrawingModelListener(this);
		this.setOpaque(true);
		myListener = new CanvasMouseListener(this, model);
		this.addMouseListener(myListener);
	}

	@Override
	public void buttonGroupStateChanged(ButtonGroup source, String buttonName) {
		buttonPressed = buttonName;
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		if (source.getName().equals("foreground")) {
			currentFGcolor = source.getCurrentColor();
		} else if (source.getName().equals("background")) {
			currentBGcolor = source.getCurrentColor();
		}
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		this.repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		this.repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		this.repaint();
	}


	@Override
	protected void paintComponent(Graphics g) {
		Insets insets = this.getInsets();
		g.setColor(Color.white);
		g.fillRect(insets.left, insets.right, this.getSize().width - insets.left - insets.right, this.getSize().height
				- insets.top - insets.bottom);

		for (int i = 0, kraj = model.getSize(); i < kraj; i++) {
			model.getObject(i).paintYourself(g);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREFERED_WIDTH, PREFERED_HEIGHT);
	}

	protected String getButtonPressed() {
		return buttonPressed;
	}

	protected Color getCurrentBGcolor() {
		return currentBGcolor;
	}

	protected Color getCurrentFGcolor() {
		return currentFGcolor;
	}


}
