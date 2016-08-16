package hr.fer.zemris.java.hw10.jnotepadpp;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class which implements some of the ILocalizaionProvider functionalities.
 * Implements method for adding, removing and notifying listeners.
 * @author Tomislav
 *
 */

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	
	/**
	 * Component listeners.
	 */
	private List<ILocalizationListener> listeners;
	
	/**
	 * Default constructor without parameters, initializes list of listeners.
	 */
	public AbstractLocalizationProvider() {
		this.listeners = new ArrayList<>();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Method for notifying listeners about localization change.
	 */
	public void fire() {
		for (ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}
	}
}
