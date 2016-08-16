package hr.fer.zemris.java.hw10.jnotepadpp;

/**
 * Class extends AbstractLocalizationProvider, it holds object that is type of
 * ILocalizationProvider that is used to provide informations about localization change.
 * If change occurs instance of this class notifies its listeners about that change.
 * @author Tomislav
 *
 */

public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Indicates if listener is registered to parent ILocalization provider. 
	 */
	private boolean connected;
	/**
	 * Object used to provide informations about localization change.
	 */
	private ILocalizationProvider parent;
	/**
	 * Object that is used to provide informations about localization change.
	 */
	private ILocalizationListener listener;
	
	/**
	 * Constructor with one parameter.
	 * @param parent Object that is used to provide informations about localization change.
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		this.connected = false;
	}
	
	/**
	 * Method for registering listener to parent ILocalizationProvider object,
	 * which indicate that connection is established.
	 */
	public void connect() {
		if (!connected) {
			parent.addLocalizationListener( listener = new ILocalizationListener() {
				
				@Override
				public void localizationChanged() {
					fire();		
				}
			});
			connected = true;
		}
	}
	
	/**
	 * Method for removing listener from parent ILocalizationProvider object,
	 * which indicates that connection is closed.
	 */
	public void disconnect() {
		if (connected) {
			parent.removeLocalizationListener(listener);
			connected = false;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

}