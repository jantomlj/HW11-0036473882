package hr.fer.zemris.java.hw11.jdraw.geoobj;

import hr.fer.zemris.java.hw11.jdraw.JDraw;
import hr.fer.zemris.java.hw11.jdraw.colors.JColorArea;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**Class that is an implementation of a filled circle in {@link JDraw} program.
 * @author Jan Tomljanović
 */
public class FilledCircle extends Circle {

	private Color back;
	
	/**Creates a new filled circle with given name, coordinate of the center, raduis, fore- and background
	 * colors and listeners
	 * @param name of the filled circle
	 * @param x coordinate of the center
	 * @param y coordinate of the center
	 * @param radius of the filled circle
	 * @param fore foreground color, edge color of the filled circle
	 * @param back background color, filling color
	 * @param listeners
	 * @throws IllegalArgumentException
	 */
	public FilledCircle (String name, int x, int y, double radius, Color fore, Color back,
			GeometricalObjectListener ... listeners) {
		super(name, x, y, radius, fore, listeners);
		this.back = back;
	}
	
	
	/**Sets background color to the given color
	 * @param color
	 */
	public void setBGColor (Color color) {
		this.back = color;
		fireListenersSignal();
	}
	
	/**Gets background color of the filled circle
	 * @return background color 
	 */
	public Color getBGColor () {
		return back;
	}
	
	@Override
	public void paintYourself(Graphics g) {
		g.setColor(back);
		g.fillOval((int)(getX()-getR()), (int)(getY()-getR()), (int)(2 * getR()), (int)(2 * getR()));
		super.paintYourself(g);
	}
	
	@Override
	public void propertiesDialog(Component parent) {
		JPanel linePanel = new JPanel(new GridLayout(5, 2, 5, 8));
		linePanel.setPreferredSize(new Dimension(200, 200));
		
		JLabel lx = new JLabel("x coordinate: ");
		JLabel ly = new JLabel("y coordinate: ");
		JLabel lr = new JLabel("radius: ");
		JLabel fcol = new JLabel("FGColor: ");
		JLabel bcol = new JLabel("BGColor: ");
		
		JTextField fx = new JTextField(String.valueOf(getX()));
		JTextField fy = new JTextField(String.valueOf(getY()));
		JTextField fr = new JTextField(String.format(Locale.CANADA, "%.3f", getR()));
		JColorArea fcolarea = new JColorArea("Area", getColor());
		JColorArea bcolarea = new JColorArea("Area", getBGColor());
		
		linePanel.add(lx);
		linePanel.add(fx);
		linePanel.add(ly);
		linePanel.add(fy);
		linePanel.add(lr);
		linePanel.add(fr);
		linePanel.add(fcol);
		linePanel.add(fcolarea);
		linePanel.add(bcol);
		linePanel.add(bcolarea);
		
		int option = JOptionPane.showConfirmDialog(parent, linePanel, "Line propertis", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.CANCEL_OPTION) {
			return;
		}
		try {
			int nx = Integer.parseInt(fx.getText());
			if (getX() != nx) {
				setX(nx);
			}
			int ny = Integer.parseInt(fy.getText());
			if (getY() != ny) {
				setY(ny);
			}
			double nr = Double.parseDouble(fr.getText());
			if (Double.compare(nr, getR()) != 0) {
				setR(nr);
			}
			Color nfcol = fcolarea.getCurrentColor();
			if (getColor() != nfcol) {
				setColor(nfcol);
			}
			Color nbcol = bcolarea.getCurrentColor();
			if (getBGColor() != nbcol) {
				setBGColor(nbcol);
			}
		} catch (NumberFormatException ignorable) {}
	}
	
	/**Creates a new FilledCircle from data in textual format <br>
	 * data format : <code> x y radius RF GF BF RB GB BB </code>
	 * <br>x and y are center coordinates, RF, GF and BF are rgb color specifications for
	 * foreground color, and Rb, GB and BB for background color
	 * @param data
	 * @return new filled circle
	 * @throws IllegalArgumentException
	 */
	public static FilledCircle fromText(String data) {
		if (data == null) {
			throw new IllegalArgumentException();
		}
		String[] dats = data.trim().split(" ");
		if (dats.length != 9) {
			throw new IllegalArgumentException();
		}
		try {
			int x = Integer.parseInt(dats[0].trim());
			int y = Integer.parseInt(dats[1].trim());
			double rad = Double.parseDouble(dats[2].trim());
			int r = Integer.parseInt(dats[3].trim());
			int g = Integer.parseInt(dats[4].trim());
			int b = Integer.parseInt(dats[5].trim());
			int rb = Integer.parseInt(dats[6].trim());
			int gb = Integer.parseInt(dats[7].trim());
			int bb = Integer.parseInt(dats[8].trim());
			return new FilledCircle("Circle", x, y, rad, new Color(r, g, b), new Color(rb, gb, bb));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public String toText () {
		String ret = "FCIRCLE ";
		ret += getX() + " " + getY() + " " + getR() + " ";
		ret += getColor().getRed() + " " + getColor().getGreen() + " " + getColor().getBlue() + " ";
		ret += back.getRed() + " " + back.getGreen() + " " + back.getBlue();
		return ret;
	}
	
}
