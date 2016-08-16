package hr.fer.zemris.java.webserver.demo;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Demo program testing implementation of RequestContext class.
 * @author Tomislav
 *
 */

public class DemoRequestContext {

	/**
	 * Method called at program start.
	 * @param args Command line arguments.
	 * @throws IOException If I/O error occurs while reading or writing.
	 */
	public static void main(String[] args) throws IOException {
		demo1("lib/RequestContextTests/primjer1.txt", "ISO-8859-2");
		demo1("lib/RequestContextTests/primjer2.txt", "UTF-8");
		demo2("lib/RequestContextTests/primjer3.txt", "UTF-8");
	}
	
	/**
	 * Method is testing  work of RequestContext, it produces file given as method parameter.
	 * @param filePath  Path to the file.
	 * @param encoding Used text encoding.
	 * @throws IOException If I/O error occurs while reading or writing.
	 */
	private static void demo1(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
	/**
	 * Method is testing work of RequestContext, it produces file given as method parameter.
	 * @param filePath Path to the file.
	 * @param encoding Used text encoding.
	 * @throws IOException If I/O error occurs while reading or writing.
	 */
	private static void demo2(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", "127.0.0.1", "/", 3600));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, "/", null));
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
}
