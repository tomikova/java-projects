package hr.fer.zemris.java.hw10.jnotepadpp;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class models localization provider used for setting new localization parameters
 * and notifying its listeners about that change.. It implements singleton design pattern
 * and only one instance of localization provider can be obtained.
 * @author Tomislav
 *
 */

public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Default language.
	 */
	private static final String DEFAULT_LANGUAGE = "en";
	/**
	 * Language in string format.
	 */
	private String language;
	/**
	 * Object used to retrieve localization values.
	 */
	private ResourceBundle bundle;
	/**
	 * Singleton instance of LocalizationProvider.
	 */
	private static LocalizationProvider instance;
	
	/**
	 * Default constructor without parameters.
	 */
	private LocalizationProvider() {
		setLanguage(DEFAULT_LANGUAGE);
	}
	
	/**
	 * Method used to retrieve singleton instance of LocalizationProvider.
	 * @return LocalizationProvider.
	 */
	public static LocalizationProvider getInstance() {
		if (instance == null) {
			instance = new LocalizationProvider();
		}
		return instance;
	}
	
	/**
	 * Method for setting new language. When new language is set all listeners are 
	 * notified about language change.
	 * @param language
	 */
	public void setLanguage(String language) {
		if (language != null && !language.equals(this.language)) {
			this.language = language;
			Locale locale = Locale.forLanguageTag(this.language);
			this.bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw10.jnotepadpp.translations", locale);
			fire();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		String value = bundle.getString(key);
		return new String(value.getBytes(StandardCharsets.ISO_8859_1) ,
		StandardCharsets.UTF_8);
	}
}