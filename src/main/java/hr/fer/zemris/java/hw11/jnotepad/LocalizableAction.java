package hr.fer.zemris.java.hw11.jnotepad;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**Action that is updates its name when localization setting change.
 * @author Jan Tomljanović
 */
public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private String key;
	private ILocalizationProvider provider;
	private ILocalizationListener mySpecListener = new ILocalizationListener() {
		
		@Override
		public void localizationChanged() {
			update();
		}
	};
	
	/**Creates a new instance that gets the key for it's name and the localization
	 * provider
	 * @param key name key
	 * @param provider
	 * @throws IllegalArgumentException
	 */
	public LocalizableAction (String key, ILocalizationProvider provider) {
		if (key == null || provider == null) {
			throw new IllegalArgumentException();
		}
		this.provider = provider;
		this.key = key;
		provider.addLocalizationListener(mySpecListener);
		update();
	}
	
	/**Method that updates the name action and alerts all action listeners
	 * about the change
	 */
	protected void updateName() {
		String old = Action.NAME;
		String nev = provider.getString(key);
		this.putValue(Action.NAME, nev);
		this.firePropertyChange(NAME, old, nev);
	}
	
	/**Method that updates action and alerts all action listeners
	 * about the change
	 */
	protected void update () {
		updateName();
	}
	
	
	

}
