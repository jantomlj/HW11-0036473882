package hr.fer.zemris.java.hw11.jnotepad;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**Class that is a {@link LocalizationProviderBridge} adapted for handling localization of a single
 * {@link JFrame}. While the frame is opened, bridge will be connected. When frame is going to close, 
 * bridge automatically disconnects.
 * @author Jan Tomljanović
 */
public class FormLocalizationProvider extends LocalizationProviderBridge implements WindowListener {

	/**Creates a new instance that will depend on the information provider given in the argument will provide and
	 * responsible for the frame specified in the argument.
	 * @param provider with the information about the localization settings
	 * @param frame that the instance will be responsible for
	 * @throws IllegalArgumentException
	 */
	public FormLocalizationProvider (ILocalizationProvider provider, JFrame frame) {
		super(provider);
		if (provider == null || frame == null) {
			throw new IllegalArgumentException();
		}
		frame.addWindowListener(this);
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		connect();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		disconnect();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

}
