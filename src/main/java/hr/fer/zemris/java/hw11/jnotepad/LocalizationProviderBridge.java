package hr.fer.zemris.java.hw11.jnotepad;

/**Localization provider that can connect and disconnect with the provider that offers
 * real information about the language. While connected, bridge listens to the provider and
 * resends signal of the language change when it occurs.
 * 
 * @author Jan Tomljanović
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	private ILocalizationProvider provider;
	private ILocalizationListener myPrivateSpecialListener = new ILocalizationListener() {

		@Override
		public void localizationChanged() {
			fire();
		}
	};
	private boolean connected;


	/**Creates a new bridge that is going to get information about the langugage from the given provider, 
	 * when connected.
	 * @param provider contains information about the language.
	 * @throws IllegalArgumentException
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		if (provider == null) {
			throw new IllegalArgumentException();
		}
		this.provider = provider;
		connected = false;
	}

	/**Method that, in idea, connects listeners of this bridge provider to the provider with information
	 * about the language through this bridge provider. Bridge will now get signals from the provider about
	 * languages changes and resend the signal to it's own listeners when it occurs.
	 */
	public void connect() {
		if (!connected) {
			provider.addLocalizationListener(myPrivateSpecialListener);
			connected = true;
			fire(); // call so that all listeners adjust their languages the moment they are connected with provider
		}
	}
	
	/**Method that breaks the contact between this bridge provider and the real localization provider.
	 * Changes in localization settings that occur in the real provider will not be signaled to the bridge.
	 */
	public void disconnect () {
		if (connected) {
		provider.removeLocalizationListener(myPrivateSpecialListener);
		connected = false;
		}
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

}
