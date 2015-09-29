package hr.fer.zemris.java.hw11.jnotepad;

import javax.swing.JMenu;

/**This class is an implementation of {@link JMenu} class that chagnes it's text when localizations
 * settings change.
 * @author Jan Tomljanović
 */
public class LJMenu extends JMenu {

	private static final long serialVersionUID = 1L;

	private String key;
	private ILocalizationProvider provider;
	private ILocalizationListener mySpecListener = new ILocalizationListener() {
		
		@Override
		public void localizationChanged() {
			update();
		}
	};
	
	/**Creates a new instance with the given text key and localization provider
	 * @param key for the text on the menu
	 * @param provider
	 * @throws IllegalArgumentException
	 */
	public LJMenu (String key, ILocalizationProvider provider) {
		if (key == null || provider == null) {
			throw new IllegalArgumentException();
		}
		this.provider = provider;
		this.key = key;
		provider.addLocalizationListener(mySpecListener);
		update();
	}
	
	/**Method that updates menu text when localization changes occur
	 */
	protected void update () {
		this.setText(provider.getString(key));
	}
}
