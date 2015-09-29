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

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**Class that is an implementation of a line object in {@link JDraw} program.
 * @author Jan Tomljanović
 */
public class Line implements GeometricalObject {

	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private Color color;
	private String name;
	
	private List<GeometricalObjectListener> listeners = new LinkedList<>();
	
	/**Creates a new line
	 * @param name of the line
	 * @param x1 x coordinate of the starting point
	 * @param y1 y coordinate of the starting point
	 * @param x2 x coodrinate of the ending point
	 * @param y2 y coodrinate of the ending point
	 * @param color of the line
	 * @param listeners can be null
	 * @throws IllegalArgumentException
	 */
	public Line (String name, int x1, int y1, int x2, int y2, Color color, GeometricalObjectListener[] listeners) {
		if (name == null || color == null) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.color = color;
		if (listeners != null) {
			this.listeners = new LinkedList<>(Arrays.asList(listeners));
		}
	}
		
		
	@Override
	public String getName() {
		return name;
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
		g.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void propertiesDialog (Component parent) {
		JPanel linePanel = new JPanel(new GridLayout(5, 2, 5, 8));
		linePanel.setPreferredSize(new Dimension(200, 200));
		
		JLabel lx1 = new JLabel("x1 coordinate: ");
		JLabel ly1 = new JLabel("y1 coordinate: ");
		JLabel lx2 = new JLabel("x2 coordinate: ");
		JLabel ly2 = new JLabel("y2 coordinate: ");
		JLabel col = new JLabel("Color: ");
		
		JTextField fx1 = new JTextField(String.valueOf(x1));
		JTextField fy1 = new JTextField(String.valueOf(y1));
		JTextField fx2 = new JTextField(String.valueOf(x2));
		JTextField fy2 = new JTextField(String.valueOf(y2));
		JColorArea colarea = new JColorArea("Area", color);
		
		linePanel.add(lx1);
		linePanel.add(fx1);
		linePanel.add(ly1);
		linePanel.add(fy1);
		linePanel.add(lx2);
		linePanel.add(fx2);
		linePanel.add(ly2);
		linePanel.add(fy2);
		linePanel.add(col);
		linePanel.add(colarea);
		
		int option = JOptionPane.showConfirmDialog(parent, linePanel, "Line propertis", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.CANCEL_OPTION) {
			return;
		}
		try {
			int nx1 = Integer.parseInt(fx1.getText());
			if (x1 != nx1) {
				setX1(nx1);
			}
			int nx2 = Integer.parseInt(fx2.getText());
			if (x2 != nx2) {
				setX2(nx2);
			}
			int ny1 = Integer.parseInt(fy1.getText());
			if (y1 != ny1) {
				setY1(ny1);
			}
			int ny2= Integer.parseInt(fy2.getText());
			if (x1 != ny2) {
				setY2(ny2);
			}
			Color ncol = colarea.getCurrentColor();
			if (color != ncol) {
				setColor(ncol);
			}
		} catch (NumberFormatException ignorable) {}
	}
	
	@Override
	public String toText () {
		String ret = "";
		ret += "LINE ";
		ret += x1 + " " + y1 + " " + x2 + " " + y2 + " ";
		ret += color.getRed() + " " + color.getGreen() + " " + color.getBlue();
		return ret;
	}
		
	/**Method that creates a new line specified by the data in textual format.
	 * <br> data format: <code> x1 y1 x2 y2 R G B </code> <br>
	 * (x1, y1) is the coordinate of the starting point, (x2, y2) of the endig point and
	 * R, G, B specifies the color of the line
	 * @param data textual data for the line
	 * @return new Line
	 */
	public static Line fromText(String data) {
		if (data == null) {
			throw new IllegalArgumentException();
		}
		String[] dats = data.trim().split(" ");
		if (dats.length != 7) {
			throw new IllegalArgumentException();
		}
		try {
			int x1 = Integer.parseInt(dats[0].trim());
			int y1 = Integer.parseInt(dats[1].trim());
			int x2 = Integer.parseInt(dats[2].trim());
			int y2 = Integer.parseInt(dats[3].trim());
			int r = Integer.parseInt(dats[4].trim());
			int g = Integer.parseInt(dats[5].trim());
			int b = Integer.parseInt(dats[6].trim());
			return new Line("Line", x1, y1, x2, y2, new Color(r, g, b), null);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
	}
	
	/**Sets the x coordinate of the starting point
	 * @param x1 x coordinate of the starting point
	 */
	public void setX1(int x1) {
		this.x1 = x1;
		fireListenerSignal();
	}

	/**Sets the y coordinate of the starting point
	 * @param y1 y coordinate of the starting point
	 */
	public void setY1(int y1) {
		this.y1 = y1;
		fireListenerSignal();
	}

	/**Sets the x coordinate of the ending point
	 * @param x2 x coordinate of the ending point
	 */
	public void setX2(int x2) {
		this.x2 = x2;
		fireListenerSignal();
	}

	/**Sets the y coordinate of the ending point
	 * @param y2 y coordinate of the ending point
	 */
	public void setY2(int y2) {
		this.y2 = y2;
		fireListenerSignal();
	}
	
	/**Gets the x coordinate of the starting point
	 * @return x coordinate of the starting point
	 */
	public int getX1() {
		return x1;
	}


	/**Gets the y coordinate of the starting point
	 * @return y coordinate of the starting point
	 */
	public int getY1() {
		return y1;
	}


	/**Gets the x coordinate of the ending point
	 * @return x coordinate of the ending point
	 */
	public int getX2() {
		return x2;
	}

	/**Gets the y coordinate of the ending point
	 * @return y coordinate of the ending point
	 */
	public int getY2() {
		return y2;
	}

	/**Gets the color of the line
	 * @return color
	 */
	public Color getColor() {
		return color;
	}


	/**Sets the color of the line to the given color
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
		fireListenerSignal();
	}


	@Override
	public void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		fireListenerSignal();
	}
	
	private void fireListenerSignal () {
		for (GeometricalObjectListener l : listeners) {
			l.gemetricalObjectChanged(this);
		}
	}
	
	@Override
	public String toString () {
		return name;
	}
	
}
