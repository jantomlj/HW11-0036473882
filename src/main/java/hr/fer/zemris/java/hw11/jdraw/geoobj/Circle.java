package hr.fer.zemris.java.hw11.jdraw.geoobj;

import hr.fer.zemris.java.hw11.jdraw.JDraw;
import hr.fer.zemris.java.hw11.jdraw.colors.JColorArea;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**Class that is an implementation of a circle in the {@link JDraw} program.
 */
public class Circle implements GeometricalObject {

	private String name;
	private int x;
	private int y;
	private double r;
	private Color color;
	private List<GeometricalObjectListener> listeners = new LinkedList<>();
	
	/**Creates a new circle with given name, coordinates of the center, radius, color and listeners.
	 * @param name of the circle
	 * @param x coordinate of the center
	 * @param y coordinate of the center
	 * @param color of the circle
	 * @param listeners that will listen to all changes.
	 * @throws IllegalArgumentException
	 */
	public Circle (String name, int x, int y, double radius, Color color, GeometricalObjectListener ... listeners) {
		if (name == null || color == null) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.x = x;
		this.y = y;
		this.r = radius;
		this.color = color;
		if (listeners != null) {
			this.listeners = new LinkedList<>(Arrays.asList(listeners));
		}
	}
	
	/**Informs all its listeners that cirlce has changed
	 */
	protected void fireListenersSignal () {
		for (GeometricalObjectListener l : listeners) {
			l.gemetricalObjectChanged(this);
		}
	}
	
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}

	@Override
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		if (!listeners.contains(l)) {
			listeners = new LinkedList<>(listeners);
			listeners.add(l);
		}
	}

	@Override
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners = new LinkedList<>(listeners);
		listeners.remove(l);
	}

	@Override
	public void paintYourself(Graphics g) {
		g.setColor(color);
		g.drawOval((int)(x-r), (int)(y-r), (int)(2 * r), (int)(2 * r));
	}

	@Override
	public void propertiesDialog(Component parent) {
		JPanel linePanel = new JPanel(new GridLayout(5, 2, 5, 8));
		linePanel.setPreferredSize(new Dimension(200, 200));
		
		JLabel lx = new JLabel("x coordinate: ");
		JLabel ly = new JLabel("y coordinate: ");
		JLabel lr = new JLabel("radius: ");
		JLabel col = new JLabel("Color: ");
		
		JTextField fx = new JTextField(String.valueOf(x));
		JTextField fy = new JTextField(String.valueOf(y));
		JTextField fr = new JTextField(String.format(Locale.CANADA, "%.3f", r));
		JColorArea colarea = new JColorArea("Area", color);
		
		linePanel.add(lx);
		linePanel.add(fx);
		linePanel.add(ly);
		linePanel.add(fy);
		linePanel.add(lr);
		linePanel.add(fr);
		linePanel.add(col);
		linePanel.add(colarea);
		
		int option = JOptionPane.showConfirmDialog(parent, linePanel, "Line propertis", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.CANCEL_OPTION) {
			return;
		}
		try {
			int nx = Integer.parseInt(fx.getText());
			if (x != nx) {
				setX(nx);
			}
			int ny = Integer.parseInt(fy.getText());
			if (y != ny) {
				setY(ny);
			}
			double nr = Double.parseDouble(fr.getText());
			if (Double.compare(nr, r) != 0) {
				setR(nr);
			}
			Color ncol = colarea.getCurrentColor();
			if (color != ncol) {
				setColor(ncol);
			}
		} catch (NumberFormatException ignorable) {}
	}
	
	@Override
	public String toText () {
		String ret = "CIRCLE ";
		ret += x + " " + y + " " + r + " ";
		ret += color.getRed() + " " + color.getGreen() + " " + color.getBlue();
		return ret;
	}
	
	/**Method that constructs a new Circle from data for it in special text format: <br>
	 * Text format : <code> x y radius R G B </code>
	 * <br> x and y are center coordinates, R G B are integers that determine the color
	 * @param data
	 * @return new circle
	 * @throws IllegalArgumentException
	 */
	public static Circle fromText(String data) {
		if (data == null) {
			throw new IllegalArgumentException();
		}
		String[] dats = data.trim().split(" ");
		if (dats.length != 6) {
			throw new IllegalArgumentException();
		}
		try {
			int x = Integer.parseInt(dats[0].trim());
			int y = Integer.parseInt(dats[1].trim());
			double rad = Double.parseDouble(dats[2].trim());
			int r = Integer.parseInt(dats[3].trim());
			int g = Integer.parseInt(dats[4].trim());
			int b = Integer.parseInt(dats[5].trim());
			return new Circle("Circle", x, y, rad, new Color(r, g, b));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
	}
	
	/**Gets the x coordinate of the center
	 * @return x coordinate of the center
	 */
	public int getX() {
		return x;
	}

	/**Sets the x coordinate of the center
	 * @param x coordinate of the center
	 */
	public void setX(int x) {
		this.x = x;
		fireListenersSignal();
	}

	/**Gets the y coordinate of the center
	 * @return y coordinate of the center
	 */
	public int getY() {
		return y;
	}

	/**Sets the y coordinate of the center
	 * @param y coordinate of the center
	 */
	public void setY(int y) {
		this.y = y;
		fireListenersSignal();
	}

	/**Gets the radius of the circle
	 * @return radius
	 */
	public double getR() {
		return r;
	}

	/**Sets the radius of the circle
	 * @param radius
	 */
	public void setR(double r) {
		this.r = r;
		fireListenersSignal();
	}

	/**Gets the color of the circle
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**Sets the color of the circle
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
		fireListenersSignal();
	}
	
	@Override
	public String toString () {
		return name;
	}
}
