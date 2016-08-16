package hr.fer.zemris.java.webserver;

/**
 * Interface for any object that can process request, it gets RequestContext as 
 * parameter and it is expected to create a content for client.
 * @author Tomislav
 *
 */

public interface IWebWorker {
	
	/**
	 * Method that process request creating content for client.
	 * @param context Object responsible for shaping and creating response to client.
	 */
	public void processRequest(RequestContext context);
}
