package hr.fer.zemris.java.hw11.jnotepad;

import java.util.Locale;
import java.util.ResourceBundle;

/**Localization provider is a singleton class that determines the language of the program. Language
 * can be set manually and is english by default. Translations are read from the .propertis files in
 * the same package as the program.
 * 
 * @author Jan Tomljanović
 */
public class LocalizationProvider extends AbstractLocalizationProvider{

	/** only existing instance of this class */
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	@SuppressWarnings("unused")
	private String language;
	private ResourceBundle bundle;
	
	private LocalizationProvider() {
		setLanguage("en");
	};
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	/**Manually sets the language to the given language.
	 * @param language that will be set
	 * @throws IllegalArgumentException
	 */
	public void setLanguage (String language){
		if (language == null) {
			throw new IllegalArgumentException();
		}
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepad.prijevodi", locale);
		this.fire();
	}
	
	/**Static method that returns the reference to the instance of this class
	 * @return localization provider
	 */
	public static LocalizationProvider getInstance () {
		return instance;
	}

}
