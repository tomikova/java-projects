package hr.fer.zemris.java.hw10.jnotepadpp;

/**
 * Interface for modeling localization listeners.
 * Consists of one method used to notify listener about localization change.
 * @author Tomislav
 *
 */
public interface ILocalizationListener {
	/**
	 * Method used to notify listener about localization change.
	 */
	void localizationChanged();
}
