package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Class is extension of LocalizationProviderBridge, it holds object that is type of
 * ILocalizationProvider that is used to provide informations about localization change.
 * Additionaly this class registers window listener to provided JFrame component,
 * which when opened will establish connection with ILocalizationProvider object and 
 * will close connection when closed.
 * If change occurs instance of this class notifies its listeners about that change.
 * @author Tomislav
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	
	/**
	 * Default constructor with two parameters.
	 * @param provider Object Used to provide informations about localization change.
	 * @param frame JFrame component which will establish connection.
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		
		frame.addWindowListener( new WindowAdapter() {
			
			/**
			 * Establish connection with ILocalizationProvider object.
			 */
			@Override
			public void windowOpened(WindowEvent e) {
				super.windowOpened(e);
				connect();
			}
			
			/**
			 * Close connection to ILocalizationProvider object.
			 */
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				disconnect();
			}
		});
	}
}
