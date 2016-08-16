package hr.fer.zemris.java.hw10.jnotepadpp;

/**
 * Interface used for components which will be used to provide localization
 * parameters to their subscribers/listeners.
 * @author Tomislav
 *
 */

public interface ILocalizationProvider {

	/**
	 * Method used to register new listener.
	 * @param listener Listener that will be added
	 */
	void addLocalizationListener(ILocalizationListener listener);
	/**
	 * Method used to remove listener.
	 * @param listener Listener that will be removed.
	 */
	void removeLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Method used to get localized value of provided key.
	 * @param key Key used to get localized value.
	 * @return Localized value.
	 */
	String getString(String key);
}
