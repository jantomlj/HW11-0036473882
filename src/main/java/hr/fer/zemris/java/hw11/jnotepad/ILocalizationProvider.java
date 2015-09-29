package hr.fer.zemris.java.hw11.jnotepad;

/**Interface that defines which methods must class have for being localization provider.
 * @author Jan Tomljanović
 */
public interface ILocalizationProvider {

	/**Method that gets the key for the wanted text in the argument and returns
	 * specified text in the language that is currently set by this localization provider.
	 * @param key for some expression
	 * @return expression in the current language
	 */
	String getString (String key);
	
	/**Method that adds localization listener for this localization provider
	 * @param listener
	 */
	void addLocalizationListener (ILocalizationListener listener);
	
	/**Method that removes specified localization listener from this localization provider
	 * @param listener
	 */
	void removeLocalizationListener (ILocalizationListener listener);
	
}
