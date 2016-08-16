package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class implements worker which task is to ouput parameters names and values in table 
 * received in request. Finally worker sends that data as response to client.
 * @author Tomislav
 *
 */
public class EchoParams implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");
		try {
			context.write("<table border><tr><th>  Name  <th>  Value  ");
			for (String name : context.getParameterNames()) {
				context.write("<tr><td>"+name+"<td>"+context.getParameter(name));
			}
			context.write("</table>");

		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
