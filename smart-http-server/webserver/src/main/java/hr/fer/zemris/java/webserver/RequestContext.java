package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class models objects that are responsible for shaping and sending response to clients.
 * @author Tomislav
 *
 */

public class RequestContext {

	/**
	 * Default response message text encoding.
	 */
	private final static String ENCODING_DEFAUT = "UTF-8";
	/**
	 * Default response message status code.
	 */
	private final static int STATUS_CODE_DEFAULT = 200;
	/**
	 * Default response message status text.
	 */
	private final static String STATUS_TEXT_DEFAULT = "OK";
	/**
	 * Default mime type.
	 */
	private final static String MIME_TYPE_DEFAULT = "text/html";
	/**
	 * Deefault flag indicating is header already generated while creating content for client.
	 */
	private final static boolean HEADER_GENERATED_DEFAULT = false;

	/**
	 * Output stream where content for client is written.
	 */
	private OutputStream outputStream;
	/**
	 * Charset for used encoding.
	 */
	private Charset charset;

	/**
	 * Response message text encoding.
	 */
	private String encoding;
	/**
	 * Response message status code.
	 */
	private int statusCode;
	/**
	 * Response message status text.
	 */
	private String statusText;
	/**
	 * Used mime type.
	 */
	private String mimeType;

	/**
	 * Parameters found in client request.
	 */
	private final Map<String,String> parameters;
	/**
	 * Temporary parameters used while creating content for client.
	 */
	private Map<String,String> temporaryParameters;
	/**
	 * Parameters that are saved during the time session is active.
	 */
	private Map<String,String> persistentParameters;
	/**
	 * Cookies that will be sent in response to client.
	 */
	private List<RCCookie> outputCookies;
	/**
	 * Flag indicating is header already generated while creating content for client.
	 */
	private boolean headerGenerated;


	/**
	 * Default constructor for object making response to client.
	 * @param outputStream Output stream where content for client is written.
	 * @param parameters  Parameters found in client request.
	 * @param persistentParameters Parameters that are saved during the time session is active.
	 * @param outputCookies Cookies that will be sent in response to client.
	 */
	public RequestContext(OutputStream outputStream, Map<String,String> parameters,
			Map<String,String> persistentParameters, List<RCCookie> outputCookies) {
		if (outputStream == null) {
			throw new IllegalArgumentException("Stream must not be null");
		}
		this.outputStream = outputStream;
		this.encoding = ENCODING_DEFAUT;
		this.statusCode = STATUS_CODE_DEFAULT;
		this.statusText = STATUS_TEXT_DEFAULT;
		this.mimeType = MIME_TYPE_DEFAULT;
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
		this.temporaryParameters = new HashMap<>();
		this.headerGenerated = HEADER_GENERATED_DEFAULT;
	}

	/**
	 * Method sets response message text encoding.
	 * @param encoding Encoding.
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new RuntimeException("Header already generated");
		}
		this.encoding = encoding;
	}

	/**
	 * Method sets response message status code.
	 * @param statusCode Status code.
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new RuntimeException("Header already generated");
		}
		this.statusCode = statusCode;
	}

	/**
	 * Method sets response message status text.
	 * @param statusText Status tex.
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new RuntimeException("Header already generated");
		}
		this.statusText = statusText;
	}

	/**
	 * Method sets response message mime type.
	 * @param mimeType Mime type.
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new RuntimeException("Header already generated");
		}
		this.mimeType = mimeType;
	}
	
	/**
	 * Method sets that will be sent in response to client.
	 * @param outputCookies Cookies for client.
	 */
	public void setOutputCookies(List<RCCookie> outputCookies) {
		if (headerGenerated) {
			throw new RuntimeException("Header already generated");
		}
		this.outputCookies = outputCookies;
	}
	
	/**
	 * Method returns output cookies used to send to client.
	 * @return Output cookies used to send to client.
	 */
	public List<RCCookie> getOutputCookies() {
		return outputCookies;
	}

	/**
	 * Method returns parameter found in client request.
	 * @param name Parameter name.
	 * @return Parameter or null if such parameter does not exist.
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Method returns parameters found in client request.
	 * @return Parameters found in client request.
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Method returns parameter that is saved during the time session is active.
	 * @param name Parameter name.
	 * @return Parameter or null if such parameter does not exist.
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * Method returns parameters that are saved during the time session is active.
	 * @return Parameters that are saved during the time session is active.
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * Method sets persistant parameter on new value.
	 * @param name Parameter name.
	 * @param value Parameter value.
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	/**
	 * Method removes persistent parameter from persistent parameters list.
	 * @param name Parameter name.
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * Method returns temporary parameter used while creating content for client.
	 * @param name Parameter name.
	 * @return Temporary parameter used while creating content for client.
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * Method returns temporary parameters used while creating content for client.
	 * @return Temporary parameters used while creating content for client.
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	
	/**
	 * Method sets temporary parameter on new value.
	 * @param name Parameter name.
	 * @param value Parameter value.
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Method removes temporary parameter from temporary parameters list.
	 * @param name Parameter name.
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	/**
	 * Method adds cookie to the list of output cookies.
	 * @param cookie Cookie to add.
	 */
	public void addRCCookie (RCCookie cookie) {
		outputCookies.add(cookie);
	}
	
	/**
	 * Method writes data to client as response.
	 * @param data Data for writting.
	 * @return Returns current RequestContext used for writting.
	 * @throws IOException If any error occurs while writting data.
	 */
	public RequestContext write(byte[] data) throws IOException {
		charset = Charset.forName(encoding);
		if (!headerGenerated) {
			headerGenerated = true;
			try {
				generateHeader();
			} catch (IOException ex) {
				headerGenerated = false;
			}
		}
		outputStream.write(data);
		return this;
	}
	
	/**
	 * Method writes data to client as response.
	 * @param text Data in text format for writting.
	 * @return Returns current RequestContext used for writting.
	 * @throws IOException If any error occurs while writting data.
	 */
	public RequestContext write(String text) throws IOException {
		charset = Charset.forName(encoding);
		byte [] data = text.getBytes(charset);
		return write(data);
	}
	
	/**
	 * Method generates response header that is sent to client.
	 * @throws IOException If any error occurs while writting data.
	 */
	private void generateHeader() throws IOException {
		String header = "HTTP/1.1 "+statusCode+" "+statusText+"\r\n";
		header += "Content-Type: "+mimeType;
		header += mimeType.contains("text/") ? "; charset="+encoding+"\r\n" : "\r\n";
		for (RCCookie cookie : outputCookies) {
			header += "Set-Cookie: "+cookie.name+"=\""+cookie.value+'"';
			header += cookie.domain != null ? "; Domain="+cookie.domain : "";
			header += cookie.path != null ? "; Path="+cookie.path : "";
			header += cookie.maxAge != null ? "; Max-Age="+cookie.maxAge : "";
			header += cookie.httpOnly ? "; HttpOnly" : "";
			header += "\r\n";
		}
		header += "\r\n";
		byte[] headerData = header.getBytes(StandardCharsets.ISO_8859_1);
		outputStream.write(headerData);
	}

	/**
	 * Class models cookie which are sent in response to client.
	 * @author Tomislav
	 *
	 */
	public static class RCCookie {

		/**
		 * Cookie name.
		 */
		private final String name;
		/**
		 * Cookie value.
		 */
		private final String value;
		/**
		 * Server domain for this cookie.
		 */
		private final String domain;
		/**
		 * Path to content for which this cookie is used.
		 */
		private final String path;
		/**
		 * Maximum time cookie is still valid.
		 */
		private final Integer maxAge;
		
		/**
		 * Indicating is cookie httpOnly cookie.
		 */
		private boolean httpOnly = false;

		/**
		 * Default constructor for creating cookie objects.
		 * @param name Cookie name.
		 * @param value Cookie value.
		 * @param domain Server domain for this cookie.
		 * @param path Path to content for which this cookie is used.
		 * @param maxAge Maximum time cookie is still valid.
		 */
		public RCCookie(String name, String value, String domain, String path,
				Integer maxAge) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Method returns cookie name.
		 * @return Cookie name.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Method returns cookie value.
		 * @return Cookie value.
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Method returns server domain for this cookie.
		 * @return Server domain for this cookie.
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Method returns path to content for which this cookie is used.
		 * @return Path to content for which this cookie is used.
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Method returns maximum time cookie is still valid.
		 * @return Maximum time cookie is still valid.
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
		
		/**
		 * Method sets cookie httpOnly status
		 * @param flag Flag indicating is cookie httpOnly cookie.
		 */
		public void setHttpOnly(boolean flag) {
			httpOnly = flag;
		}
	}
}
