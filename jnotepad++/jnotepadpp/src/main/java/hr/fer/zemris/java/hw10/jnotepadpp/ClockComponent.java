package hr.fer.zemris.java.hw10.jnotepadpp;

import java.util.Date;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import java.text.SimpleDateFormat;

/**
 * Component that is used to display clock on provided label.
 * @author Tomislav
 *
 */

public class ClockComponent {

	/**
	 * Default date format.
	 */
	private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
	
	/**
	 * Time in string format.
	 */
	private String time;
	/**
	 * Flag indicating when clock should stop.
	 */
	private volatile boolean flag = false;
	/**
	 * Label on which time will be displayed.
	 */
	private JLabel label;
	/**
	 * Object for formatting date.
	 */
	private SimpleDateFormat sdf;

	/**
	 * Default constructor with one parameter.
	 * @param label Label on which time will be displayed.
	 */
	public ClockComponent(JLabel label) {
		this.label = label;
		this.sdf = new SimpleDateFormat(DATE_FORMAT);
		Thread t = new Thread(() -> {
			while (true) {
				SwingUtilities.invokeLater(() -> { 
					time = sdf.format(new Date());
					setTime();
				});
				try {
					Thread.sleep(500);

				} catch (Exception ignorable) {
				}
				if (flag) {
					break;
				}
			}
		});
		t.setDaemon(true);
        t.start();
        time = sdf.format(new Date());
	}
	
	/**
	 * Method sets the flag which will indicate clock to stop.
	 */
	public void stop() {
        flag = true;
    }
	
	/**
	 * Method sets label text.
	 */
	private void setTime() {
		label.setText(time);
	}
}
