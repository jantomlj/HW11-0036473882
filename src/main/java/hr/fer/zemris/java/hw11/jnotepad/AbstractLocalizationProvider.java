package hr.fer.zemris.java.hw11.jnotepad;

import java.util.LinkedList;
import java.util.List;

/**Abstract class that implements basic methods for localization provider.
 * @author Jan Tomljanović
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	private List<ILocalizationListener> listeners = new LinkedList<>();
	
	public AbstractLocalizationProvider () {};
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		if (!listeners.contains(listener)) {
			listeners = new LinkedList<>(listeners);
			listeners.add(listener);
		}
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners = new LinkedList<>(listeners);
		listeners.remove(listener);
	}
	
	/**Method that dispatches the signal that language settings have changed to all
	 * listeners the provider has.
	 */
	public void fire () {
		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}

}
