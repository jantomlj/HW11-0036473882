package hr.fer.zemris.java.hw11.jnotepad;

/**Interface that classes that want to listen to some localization provider must implement.
 * @author Jan Tomljanović
 */
public interface ILocalizationListener {

	/**Method that doesn't return anything, and has no arguments. Basically just a signal to the
	 * listener that localization settings have changed. Usually, localization listener should then
	 * refresh his components that are dependent on the language.
	 */
	void localizationChanged();
}
