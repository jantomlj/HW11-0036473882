package hr.fer.zemris.java.hw11.jdraw.toggles;

import javax.swing.ButtonGroup;

/**Interface that defines method that all classes that want to listen to group of buttons should have
 * @author Jan Tomljanović
 */
public interface ButtonGroupListener {

	/**Method that is called when state of the button group has changed
	 * @param source of the change
	 * @param buttonName name of the button that is now selected
	 */
	public void buttonGroupStateChanged (ButtonGroup source, String buttonName);
}
