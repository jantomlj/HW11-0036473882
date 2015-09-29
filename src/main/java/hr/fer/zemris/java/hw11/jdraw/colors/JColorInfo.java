package hr.fer.zemris.java.hw11.jdraw.colors;

import java.awt.Color;

import javax.swing.JLabel;

/**Class that contains information about the current state of fore- and background color set in the
 * program
 * @author Jan Tomljanović
 */
public class JColorInfo extends JLabel implements ColorChangeListener {

	private static final long serialVersionUID = 1L;

	private Color foreGround;
	private Color backGround;

	/**Constructs new instance and sets default fore- and backgroung colors to white.
	 */
	public JColorInfo() {
		foreGround = Color.white;
		backGround = Color.white;
		setText();
	}
	
	

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		if (source.getName().equals("foreground") ) {
			foreGround = source.getCurrentColor();
		}
		else if (source.getName().equals("background") ) {
			backGround = source.getCurrentColor();
		}
		setText();
	}


	private void setText() {
		this.setText("  Foreground color: (" + foreGround.getRed() + ", " + foreGround.getGreen() + ", "
				+ foreGround.getBlue() + "), background color: (" + backGround.getRed() + ", " + backGround.getGreen()
				+ ", " + backGround.getBlue() + ")");
	}
}
