package hr.fer.zemris.java.student0036461026.hw13;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Class is responsible for listening when application starts and shuts down.
 * @author Tomislav
 *
 */
@WebListener
public class Information implements ServletContextListener {

	/**
	 * Method notifies that application is starting and sets time attribute when that happened.
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Application is running");
		sce.getServletContext().setAttribute("time", System.currentTimeMillis());
	}

	/**
	 * Method notifies that application is shuting down.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Application is closing");
	}

}