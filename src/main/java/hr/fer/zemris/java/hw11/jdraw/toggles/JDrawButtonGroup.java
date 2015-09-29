package hr.fer.zemris.java.hw11.jdraw.toggles;

import hr.fer.zemris.java.hw11.jdraw.JDraw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;

/**Class that implements button group for use in {@link JDraw} program.
 * @author Jan Tomljanović
 */
public class JDrawButtonGroup extends ButtonGroup {

	private static final long serialVersionUID = 1L;

	private List<ButtonGroupListener> listeners = new LinkedList<>();


	/**Creates a new button group.
	 * @param buttons list of buttons that will be added to the group
	 * @param listeners of the group
	 */
	public JDrawButtonGroup(List<JToggleButton> buttons, ButtonGroupListener... listeners) {
		for (JToggleButton button : buttons) {
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					fireListenersSignal();
				}
			});
			this.add(button);
		}
		if (listeners != null) {
			this.listeners = new LinkedList<>(Arrays.asList(listeners));
		}
		fireListenersSignal();
	}


	private void fireListenersSignal() {
		AbstractButton but = null;
		for (AbstractButton b : this.buttons) {
			if (b.isSelected()) {
				but = b;
			}
		}
		for (ButtonGroupListener listener : listeners) {
			listener.buttonGroupStateChanged(this, but.getName());
		}
	}

	/**Adds a new listener for the group
	 * @param listener
	 */
	public void addButtonGroupListener(ButtonGroupListener listener) {
		if (!listeners.contains(listener)) {
			listeners = new LinkedList<>(listeners);
			listeners.add(listener);
		}
	}

	/**Removes the given listener from the group
	 * @param listener
	 */
	public void removeButtonGroupListener(ButtonGroupListener listener) {
		listeners = new LinkedList<>(listeners);
		listeners.remove(listener);
	}


}
