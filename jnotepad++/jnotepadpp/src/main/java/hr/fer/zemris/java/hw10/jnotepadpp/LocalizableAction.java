package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Localized action that changes its parameters on localization change.
 * @author Tomislav
 *
 */

public class LocalizableAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Key used to get localized name.
	 */
	private String key;
	/**
	 * Localization provider which notifies when localization is changed.
	 */
	private ILocalizationProvider lp;
	
	/**
	 * Default constructor with two parameters.
	 * @param key Key used to get localized name.
	 * @param lp Localization provider which notifies when localization is changed.
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.lp = lp;
		this.lp.addLocalizationListener(new ILocalizationListener() {
			
			/**
			 * Method retrieves new localized name and updates this action.
			 */
			@Override
			public void localizationChanged() {
				String translation = (LocalizableAction.this)
						.lp.getString((LocalizableAction.this).key);
				putValue(Action.NAME, translation);
				
				String transDesc = (LocalizableAction.this)
						.lp.getString((LocalizableAction.this).key + "Desc");
				putValue(Action.SHORT_DESCRIPTION, transDesc);
			}
		});
		
		String translation = this.lp.getString(key);
		putValue(Action.NAME, translation);
		String transDesc = this.lp.getString(key + "Desc");
		putValue(Action.SHORT_DESCRIPTION, transDesc);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	/**
	 * Method updating this component parameters on localization change.
	 * @param key Action key which name will be set.
	 * @param newValue New action key name.
	 */
	@Override
	public void putValue(String key, Object newValue) {
		super.putValue(key, newValue);
	}
}
