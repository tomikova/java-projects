package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Class provides implementation of http server. Server listens clients requests and its
 * processing them either with sending clients requested resource, error or information response messages.
 * 
 * This program when started expects one argument - server configuration file.
 * Argument example: config\server.properties
 * @author Tomislav
 *
 */

public class SmartHttpServer {

	/**
	 * Default package where workers are situated.
	 */
	private static final String DEFAULT_WORKERS_PACKAGE = "hr.fer.zemris.java.webserver.workers";
	/**
	 * Server address.
	 */
	private String address;
	/**
	 * Port on which server listens client requests.
	 */
	private int port;
	/**
	 * Number of threads server is using to serve clients.
	 */
	private int workerThreads;
	/**
	 * Time period in which session is valid.
	 */
	private int sessionTimeout;
	/**
	 * Map of supported mime types.
	 */
	private Map<String,String> mimeTypes = new HashMap<String, String>();
	/**
	 * Map of workers server is using. Workers are mapped with their name as key.
	 */
	private Map<String,IWebWorker> workersMap = new HashMap<>();
	/**
	 * Main server thread that listens clients requests and is putting them in queue for execution.
	 */
	private ServerThread serverThread = null;
	/**
	 * Container for worker threads that needs to be executed.
	 */
	private ExecutorService threadPool;

	/**
	 * Path of document root server where server is offering resources.
	 */
	private Path documentRoot;
	/**
	 * Map of client sessions.
	 */
	private Map<String, SessionMapEntry> sessions =
			new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * Object used for creating random session ID name.
	 */
	private Random sessionRandom = new Random();
	/**
	 * Flag indicating should server and all its services be stopped.
	 */
	private volatile boolean stopIndicator = false;

	/**
	 * Constructor of server accepting server configuration file.
	 * @param configFileName Server configuration file.
	 */
	public SmartHttpServer(String configFileName) {
		Properties serverProp = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(configFileName);
		} catch (FileNotFoundException e) {
			System.out.println("Configuration file not found");
			System.exit(0);
		}
		try {
			serverProp.load(is);
		} catch (IOException e) {
			System.out.println("Unable to load configuration file");
			System.exit(0);
		}
		this.address = serverProp.getProperty("server.address");
		this.port = Integer.parseInt(serverProp.getProperty("server.port"));
		this.workerThreads = Integer.parseInt(serverProp.getProperty("server.workerThreads"));
		this.sessionTimeout = Integer.parseInt(serverProp.getProperty("session.timeout"));
		this.documentRoot = Paths.get(serverProp.getProperty("server.documentRoot"));
		String mimeConfig = serverProp.getProperty("server.mimeConfig");
		String workersConfig = serverProp.getProperty("server.workers");
		try {
			is = new FileInputStream(mimeConfig);
			Properties mimeTypes = new Properties();
			mimeTypes.load(is);
			for (String key : mimeTypes.stringPropertyNames()) {
				String value = mimeTypes.getProperty(key);
				this.mimeTypes.put(key, value);
			}
		} catch (IOException e) {
			System.out.println("Error while loading mime types");
			System.exit(0);
		}
		try {
			is = new FileInputStream(workersConfig);
			Properties workers = new Properties();
			workers.load(is);
			try {
				for (String key : workers.stringPropertyNames()) {
					String fqcn = workers.getProperty(key);
					if (this.workersMap.containsKey(key)) {
						throw new IllegalArgumentException("Paths with same name found");
					}
					Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
					Object newObject = referenceToClass.newInstance();
					IWebWorker iww = (IWebWorker)newObject;
					this.workersMap.put(key, iww);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
		} catch (IOException e) {
			System.out.println("Error while loading workers");
			System.exit(0);
		}
	}

	/**
	 * Method used to start the server.
	 */
	protected synchronized void start() {
		if (!stopIndicator && serverThread == null || !serverThread.isAlive()) {
			serverThread = new ServerThread();
			serverThread.setDaemon(true);
			threadPool = Executors.newFixedThreadPool(workerThreads);
			serverThread.start();
		}
	}

	/**
	 * Method used to stop the server.
	 */
	protected synchronized void stop() {
		if (!stopIndicator && serverThread != null && serverThread.isAlive()) {
			stopIndicator = true;
			while(true) {
				try {
					serverThread.join();
					break;
				} catch (InterruptedException ignorable) {
				}
			}
			threadPool.shutdown();
			while (!threadPool.isTerminated()) {
				try {
					threadPool.awaitTermination(100, TimeUnit.MILLISECONDS);
				} catch (InterruptedException ignorable) {
				}
			}
		}
	}

	/**
	 * Class models main server thread that listens client requests and its putting them in
	 * queue for execution.
	 * @author Tomislav
	 *
	 */
	protected class ServerThread extends Thread {

		/**
		 * Method listens client requests and its putting them in queue for execution. It is also
		 * responsible for starting and stoping session remove thread.
		 */
		@Override
		public void run() {
			ServerSocket serverSocket;
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(
						new InetSocketAddress(address, port));
				serverSocket.setSoTimeout(100);
				SessionRemoveThread srThread = new SessionRemoveThread();
				srThread.setDaemon(true);
				srThread.start();
				while(true) {
					if (stopIndicator) {
						serverSocket.close();
						while(true) {
							try {
								//indicate sessionRemoveThread that stop is requested,
								//do final session cleanup and terminate
								srThread.interrupt();
								srThread.join();
								break;
							} catch (InterruptedException ignorable) {
							}
						}
						break;			
					}
					try {
						Socket client = serverSocket.accept();
						ClientWorker cw = new ClientWorker(client);
						threadPool.submit(cw);
					} catch (SocketTimeoutException ignorable) {					
					}
				}

			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}
	}

	/**
	 * Class of thread that is used to check which server sessions are not
	 * valid anymore. Thread does this job in periods that are defined by
	 * thread sleep time.
	 * @author Tomislav
	 *
	 */
	protected class SessionRemoveThread extends Thread {

		/**
		 * Time thread will sleep after which will be invoked to do its job.
		 */
		private static final long SLEEP_TIME = 300_000;

		/**
		 * Method check which server sessions are not valid anymore. If such sessions are
		 * found they are removed from server session map.
		 */
		@Override
		public synchronized void run() {
			while(true) {			
				Iterator<Entry<String, SessionMapEntry>> it = sessions.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, SessionMapEntry> pair = 
							(Map.Entry<String, SessionMapEntry>)it.next();
					if (System.currentTimeMillis()/1000 > pair.getValue().validUntil) {
						it.remove();
					}
				}
				if (stopIndicator) {
					break;
				}
				try {
					//sleep for 5 minutes
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException ignorable) {
				}		
			}
		}
	}

	/**
	 * Class of client workers. Class is defining objects that are responsible for handling and
	 * processing clients request and sending them response after request is processed.
	 * @author Tomislav
	 *
	 */
	private class ClientWorker implements Runnable {
		/**
		 * Socket used for I/O communication with client.
		 */
		private Socket csocket;
		/**
		 * Stream for reading client request.
		 */
		private PushbackInputStream istream;
		/**
		 * Stream for sending response to client.
		 */
		private OutputStream ostream;
		/**
		 * Version of http protocol.
		 */
		private String version;
		/**
		 * Used http method. Only GET is supported.
		 */
		private String method;
		/**
		 * Map of parameters found in client request.
		 */
		private Map<String,String> params = new HashMap<String, String>();
		/**
		 * Map of parameters that are saved during the time session is active.
		 */
		private Map<String,String> permPrams = null;
		/**
		 * Map of cookies that will be sent in response to client.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Session ID.
		 */
		private String SID;
		/**
		 * Default client worker constructor accepting client socket.
		 * @param csocket Client socket used for communication.
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}
		/**
		 * Method processes client request. Http protocol must match HTTP/1.0 or HTTP/1.1,
		 * used method must be GET otherwise error message is sent.
		 * Error messages are also sent in following situations: 
		 * 	-If reaquest header cant be read or is not in expected format.
		 *	-If client is trying to access resource he is not having permission.
		 *	-If requested resource cant be found on this server. 
		 */
		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = new BufferedOutputStream(csocket.getOutputStream());
				byte[] requestHeader = readRequest(istream);
				if(requestHeader==null) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				String requestStr = new String(
						requestHeader, 
						StandardCharsets.US_ASCII);
				List<String> request = readRequest(requestStr);
				checkSession(request);
				String[] firstLine = request.isEmpty() ? 
						null : request.get(0).split(" ");
				if(firstLine==null || firstLine.length != 3) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				method = firstLine[0].toUpperCase();
				if(!method.equals("GET")) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				version = firstLine[2].toUpperCase();
				if(!(version.equals("HTTP/1.1") || version.equals("HTTP/1.0"))) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				String requestedPath = firstLine[1];
				String path;
				String paramString;
				String[] splitRequestedPath = requestedPath.split("\\?");
				if (splitRequestedPath.length == 1) {
					path = splitRequestedPath[0];
					paramString = null;
				}
				else {
					path = splitRequestedPath[0];
					paramString = splitRequestedPath[1];
				}
				parseParameters(paramString);
				Path sourcePath = Paths.get(documentRoot.toString()+path);						
				if (!sourcePath.startsWith(documentRoot)) {
					sendError(ostream, 403, "Forbidden");
					return;
				}

				if (outputCookies.isEmpty()) {
					RCCookie cookie = new RCCookie("sid",SID, address, "/", null);
					cookie.setHttpOnly(true);
					outputCookies.add(cookie);
				}
				else {
					for (int i = 0; i < outputCookies.size(); i++ ) {
						RCCookie toCheck = outputCookies.get(i);
						if (toCheck.getName().equals("sid") && !toCheck.getValue().equals(SID)) {
							RCCookie cookie = new RCCookie("sid",SID, address, "/", null);
							cookie.setHttpOnly(true);
							outputCookies.set(i, cookie);
						}
					}
				}

				RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);

				if (path.startsWith("/ext/")) {
					String workerName = path.replace("/ext/", "");
					try {
						Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(
								DEFAULT_WORKERS_PACKAGE+"."+workerName);
						Object newObject = referenceToClass.newInstance();
						IWebWorker iww = (IWebWorker)newObject;
						iww.processRequest(rc);
					} catch (Exception e) {
						sendError(ostream, 404, "Not Found");
					}
				}

				else if (workersMap.containsKey(path)) {
					workersMap.get(path).processRequest(rc);
				}
				else if (sourcePath.toFile().exists() && sourcePath.toFile().isFile()
						&& sourcePath.toFile().canRead()) {
					String[] tmp = path.split("\\.");
					String fileExtension;
					if (tmp.length != 2 || !mimeTypes.containsKey(tmp[1])) {
						fileExtension = "application/octet-stream";
					}
					else {
						fileExtension = mimeTypes.get(tmp[1]);
					}

					rc.setMimeType(fileExtension);
					rc.setStatusCode(200);
					rc.setStatusText("OK");

					if (tmp.length == 2 && tmp[1].equals("smscr")) {		
						String documentBody = new String(
								Files.readAllBytes(Paths.get(documentRoot.toString()+path)),
								StandardCharsets.UTF_8);				
						new SmartScriptEngine(
								new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
					}
					else {
						rc.write(Files.readAllBytes(sourcePath));
					}
				}
				else {
					sendError(ostream, 404, "Not Found");
					return;
				}
				ostream.flush();
			} catch (IOException ignorable) {
			} finally {
				try {
					csocket.close();
				} catch (IOException ignorable) {
				}
			}
		}

		/**
		 * Method used to parse parameters found in client request.
		 * @param paramString String containing line with all parameters.
		 */
		private void parseParameters(String paramString) {
			if (paramString == null) {
				return;
			}
			String[] params = paramString.split("&");
			for (String par : params) {
				String[] param = par.split("=");
				this.params.put(param[0], param[1]);
			}
		}

		/**
		 * Method used to check if session for this client already exist and 
		 * if it exist then it is checked if its still valid. If session exist 
		 * and its valid she is updated to new values. If its not valid or if it 
		 * doesnt exist new session is created for this client.
		 * @param request
		 */
		private synchronized void checkSession(List<String> request) {
			String sidCandidate = null;
			for (String headerLine : request) {
				if (headerLine.startsWith("Cookie:")) {
					String cookieLine = headerLine.replace("Cookie:", "").trim();
					String[] cookies = cookieLine.split(";");
					for (String cookie : cookies) {
						String[] cookieParams = cookie.split("=");
						String cookieName = cookieParams[0];
						String cookieValue = cookieParams[1].replace("\"", "");
						if (cookieName.equals("sid")) {
							sidCandidate = cookieValue;
						}
					}
				}
			}
			if (sidCandidate == null) {
				createNewSessionMapEntry();
			}
			else {
				SessionMapEntry sessionMapEntry = sessions.get(sidCandidate);
				if (sessionMapEntry == null) {
					createNewSessionMapEntry();
				}
				else if (System.currentTimeMillis()/1000 > sessionMapEntry.validUntil) {
					sessions.remove(sessionMapEntry);
					createNewSessionMapEntry();
				}
				else {
					sessionMapEntry.validUntil = System.currentTimeMillis()/1000 + sessionTimeout;
					this.SID = sessionMapEntry.sid;
					permPrams = sessionMapEntry.map;
				}
			}
		}

		/**
		 * Method creates new session for client.
		 */
		private synchronized void createNewSessionMapEntry() {
			String sid = generateSid();
			SessionMapEntry sessionMapEntry = new  SessionMapEntry(sid,
					System.currentTimeMillis()/1000 + sessionTimeout);
			sessions.put(sid, sessionMapEntry);
			this.SID = sid;
			permPrams = sessionMapEntry.map;
		}

		/**
		 * Method is generating new session ID.
		 * @return Generated session ID.
		 */
		private String generateSid() {
			String sid = "";
			for (int i = 0; i < 20; i++) {
				sid += (char)(sessionRandom.nextInt(26) + 65);
			}
			return sid;
		}

		/**
		 * Method returns request header lines.
		 * @param requestHeader Whole request header in single String.
		 * @return Request header lines.
		 */
		private List<String> readRequest(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for(String s : requestHeader.split("\n")) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				if(c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Method sends error response message to client.
		 * @param cos Client output stream where error message will be written.
		 * @param statusCode Response message status code.
		 * @param statusText Response message status text.
		 * @throws IOException If error occurs while writing response message.
		 */
		private void sendError(OutputStream cos, 
				int statusCode, String statusText) throws IOException {

			String response = "<html><head><title>"+statusText+"</title></head>"
					+ "<body><b>"+statusCode+" "+statusText+"</b></body><html>";
			cos.write(
					("HTTP/1.1 "+statusCode+" "+statusText+"\r\n"+
							"Server: simple java server\r\n"+
							"Content-Type: text/html;charset=UTF-8\r\n"+
							"Content-Length: "+response.length()+"\r\n"+
							"Connection: close\r\n"+
							"\r\n"+response).getBytes(StandardCharsets.US_ASCII));
			cos.flush();
		}

		/**
		 * Method reads client request from input stream and returns it in byte array.
		 * @param is Input stream from which request is read.
		 * @return Read request in byte array.
		 * @throws IOException If error occurs while raeding request.
		 */
		private byte[] readRequest(InputStream is) 
				throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l:		while(true) {
				int b = is.read();
				if(b==-1) return null;
				if(b!=13) {
					bos.write(b);
				}
				switch(state) {
				case 0: 
					if(b==13) { 
						state=1; 
					} 
					else if(b==10) {
						state=4;
					}
					break;
				case 1: 
					if(b==10) {
						state=2; 
					} 
					else {
						state=0;
					}
					break;
				case 2: 
					if(b==13) { 
						state=3; 
					} 
					else {
						state=0;
					}
					break;
				case 3: 
					if(b==10) { 
						break l; 
					} 
					else {
						state=0;
					}
					break;
				case 4: 
					if(b==10) { 
						break l; 
					} 
					else {
						state=0;
					}
					break;
				}
			}
			return bos.toByteArray();
		}
	}

	/**
	 * Class models sessions for clients.
	 * @author Tomislav
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * Session ID.
		 */
		String sid;
		/**
		 * Time until session is valid.
		 */
		long validUntil;
		/**
		 * Map that holds parameters saved in this session.
		 */
		Map<String,String> map;

		/**
		 * Default session constructor.
		 * @param sid Session ID.
		 * @param validUntil Time until session is valid.
		 */
		public SessionMapEntry(String sid, long validUntil) {
			this.sid = sid;
			this.validUntil = validUntil;
			this.map = new HashMap<>();
		}
	}

	/**
	 * Method called at program start.
	 * @param args Command line arguments.
	 * @throws IOException If I/O error occurs while communicating with user.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length < 1){
			System.out.println("Configuration file not provided.");
			System.exit(0);
		}
		String configurationFile = args[0];
		SmartHttpServer server = new SmartHttpServer(configurationFile);

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in)));

		boolean isRunning = false;

		System.out.println("To start the server type: \"start\"\n"
				+ "To stop the server type: \"stop\"\n"
				+ "To quit application type: \"quit\"");
		while(true) {
			System.out.print(">");
			String input = reader.readLine().trim();
			if (input.equalsIgnoreCase("quit")){
				if (isRunning) {
					System.out.println("Closing services...server will shutdown shortly");
					server.stop();
					System.out.println("Server shutdown");
				}
				break;
			}
			else if(input.equalsIgnoreCase("start")) {
				if (isRunning) {
					System.out.println("Server is already running");
				}
				else {
					server.start();
					System.out.println("Server is up and running");
					isRunning = true;
				}
			}
			else if(input.equalsIgnoreCase("stop")) {
				if (isRunning) {
					System.out.println("Closing services...server will stop");
					server.stop();
					server.stopIndicator = false;
					isRunning = false;
					System.out.println("Server stopped");
				}
				else {
					System.out.println("Server is not running");
				}
			}
		}
	}
}
