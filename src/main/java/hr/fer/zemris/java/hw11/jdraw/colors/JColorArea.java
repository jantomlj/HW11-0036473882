package hr.fer.zemris.java.hw11.jdraw.colors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**Class that implements color provider and is a component that can be displayed
 * @author Jan Tomljanović
 */
public class JColorArea extends JComponent implements IColorProvider {

	private static final long serialVersionUID = 1L;

	private Color oldColor;
	private Color selectedColor;
	private List<ColorChangeListener> listeners = new LinkedList<>();
	private String name;

	/**Constructs a new instance of the class with given name, default color and listeners
	 * @param name color area name
	 * @param color default color to be set
	 * @param listeners that will be notified when color changes
	 */
	public JColorArea(String name, Color color, ColorChangeListener ... listeners) {
		this.selectedColor = color;
		this.oldColor = color;
		this.name = name;
		if (listeners != null) {
			this.listeners = new LinkedList<>(Arrays.asList(listeners));
		}
		fireListenerSignal();
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color col = JColorChooser.showDialog(JColorArea.this, "Colors", Color.WHITE);
				if (col != null) {
					selectedColor = col;
					((JColorArea)e.getSource()).repaint();
					fireListenerSignal();
				}
			}
		});
	}

	private void fireListenerSignal () {
		for (ColorChangeListener listener : listeners) {
			listener.newColorSelected(this, oldColor, selectedColor);
		}
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		Insets insets = this.getInsets();
		Dimension dim = this.getSize();
		g.setColor(selectedColor);
		g.fillRect(insets.left, insets.right, dim.width - (insets.left + insets.right), dim.height
				- (insets.top + insets.bottom));

	}

	/**Method that add a new listener
	 * @param listener new listener
	 */
	public void addColorChangeListener(ColorChangeListener listener) {
		if (!listeners.contains(listener)) {
			listeners = new LinkedList<>(listeners);
			listeners.add(listener);
		}
	}

	/**Method that removes given listener from its internal list of listeners
	 * @param listener to be removed form the list
	 */
	public void removeColorChangeListener(ColorChangeListener listener) {
		listeners = new LinkedList<>(listeners);
		listeners.remove(listener);
	}


	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}
	
	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
	
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public String getName() {
		return name;
	}
	
}
