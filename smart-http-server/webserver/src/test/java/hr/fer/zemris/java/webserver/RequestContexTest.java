package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for testing work of RequestContext and RCCookie classes.
 * @author Tomislav
 *
 */
public class RequestContexTest {

	@Test
	public void constructorTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		new RequestContext(bos, 
				new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorOutputStreamIsNullTest() {
		new RequestContext(null, 
				new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
	}
	
	@Test
	public void encodingTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		RequestContext rc =new RequestContext(bos, 
				new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding("UTF-8");
		String output = null;
		try {
			rc.write("");
		} catch (IOException ignorable) {
		}
		try {
			output = bos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		Assert.assertEquals(output, "HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n");
		try {
			bos.close();
		} catch (IOException e) {
		}
		
		bos = new ByteArrayOutputStream();
		rc =new RequestContext(bos, 
				new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding("ISO-8859-2");
		output = null;
		try {
			rc.write("");
		} catch (IOException ignorable) {
		}
		try {
			output = bos.toString("ISO-8859-2");
		} catch (UnsupportedEncodingException e) {
		}
		Assert.assertEquals(output, "HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=ISO-8859-2\r\n\r\n");
		try {
			bos.close();
		} catch (IOException e) {
		}
	}
	
	@Test
	public void mimeTypeTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		RequestContext rc =new RequestContext(bos, 
				new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setMimeType("image/gif");
		String output = null;
		try {
			rc.write("");
		} catch (IOException ignorable) {
		}
		try {
			output = bos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		Assert.assertEquals(output, "HTTP/1.1 200 OK\r\nContent-Type: image/gif\r\n\r\n");
		try {
			bos.close();
		} catch (IOException e) {
		}
		
		bos = new ByteArrayOutputStream();
		rc =new RequestContext(bos, 
				new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setMimeType("text/plain");
		output = null;
		try {
			rc.write("");
		} catch (IOException ignorable) {
		}
		try {
			output = bos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		Assert.assertEquals(output, "HTTP/1.1 200 OK\r\nContent-Type: text/plain; charset=UTF-8\r\n\r\n");
		try {
			bos.close();
		} catch (IOException e) {
		}
	}
	
	@Test
	public void statusCodeAndTextTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		RequestContext rc =new RequestContext(bos, 
				new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setStatusCode(207);
		rc.setStatusText("Test1");
		String output = null;
		try {
			rc.write("");
		} catch (IOException ignorable) {
		}
		try {
			output = bos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		Assert.assertEquals(output, "HTTP/1.1 207 Test1\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n");
		try {
			bos.close();
		} catch (IOException e) {
		}
		bos = new ByteArrayOutputStream();
		rc =new RequestContext(bos, 
				new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setStatusCode(1532);
		rc.setStatusText("Test2");
		output = null;
		try {
			rc.write("");
		} catch (IOException ignorable) {
		}
		try {
			output = bos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		Assert.assertEquals(output, "HTTP/1.1 1532 Test2\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n");
		try {
			bos.close();
		} catch (IOException e) {
		}
	}
	
	@Test
	public void parametersGettersTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		HashMap<String, String> parameters = new HashMap<>();
		HashMap<String, String> persistentParameters = new HashMap<>();
		List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		outputCookies.add(new RCCookie("korisnik", "perica", "127.0.0.1", "/", 3600));
		parameters.put("name", "Tomislav");
		parameters.put("car", "Ferrari");
		persistentParameters.put("weather", "sunny");
		
		RequestContext rc =new RequestContext(bos, 
				parameters,
				persistentParameters,
				outputCookies);
		
		Assert.assertEquals("Ferrari", rc.getParameter("car"));
		Assert.assertEquals(null, rc.getParameter("building"));
		Assert.assertEquals("sunny", rc.getPersistentParameter("weather"));
		Assert.assertEquals(null, rc.getPersistentParameter("building"));
		Assert.assertEquals(null, rc.getTemporaryParameter("building"));
		
		Assert.assertEquals(2, rc.getParameterNames().size());
		Assert.assertEquals(1, rc.getPersistentParameterNames().size());
		Assert.assertEquals(0, rc.getTemporaryParameterNames().size());
		Assert.assertEquals(1, rc.getOutputCookies().size());
	}
	
	@Test
	public void parametersSettersTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		HashMap<String, String> parameters = new HashMap<>();
		HashMap<String, String> persistentParameters = new HashMap<>();
		List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		outputCookies.add(new RCCookie("korisnik", "perica", "127.0.0.1", "/", 3600));
		parameters.put("name", "Tomislav");
		parameters.put("car", "Ferrari");
		persistentParameters.put("weather", "sunny");
		
		RequestContext rc =new RequestContext(bos, 
				parameters,
				persistentParameters,
				outputCookies);
		
		rc.setPersistentParameter("car", "Ford");
		Assert.assertEquals("Ford", rc.getPersistentParameter("car"));
		Assert.assertEquals("sunny", rc.getPersistentParameter("weather"));
		Assert.assertEquals(null, rc.getPersistentParameter("building"));
		rc.setPersistentParameter("building", "B4");
		Assert.assertEquals("B4", rc.getPersistentParameter("building"));
		
		Assert.assertEquals(null, rc.getTemporaryParameter("building"));
		rc.setTemporaryParameter("building", "C");
		Assert.assertEquals("C", rc.getTemporaryParameter("building"));
		
		Assert.assertEquals(2, rc.getParameterNames().size());
		Assert.assertEquals(3, rc.getPersistentParameterNames().size());
		Assert.assertEquals(1, rc.getTemporaryParameterNames().size());
		Assert.assertEquals(1, rc.getOutputCookies().size());
		
		rc.addRCCookie(new RCCookie("korisnik2", "ivica", "127.0.0.1", "/", 3600));
		Assert.assertEquals(2, rc.getOutputCookies().size());
		rc.setOutputCookies(new ArrayList<RequestContext.RCCookie>());
		Assert.assertEquals(0, rc.getOutputCookies().size());
	}
	
	@Test
	public void parametersRemoveTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		HashMap<String, String> parameters = new HashMap<>();
		HashMap<String, String> persistentParameters = new HashMap<>();
		List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		outputCookies.add(new RCCookie("korisnik", "perica", "127.0.0.1", "/", 3600));
		parameters.put("name", "Tomislav");
		parameters.put("car", "Ferrari");
		persistentParameters.put("weather", "sunny");
		
		RequestContext rc =new RequestContext(bos, 
				parameters,
				persistentParameters,
				outputCookies);
		
		Assert.assertEquals("sunny", rc.getPersistentParameter("weather"));
		Assert.assertEquals(null, rc.getPersistentParameter("building"));
		Assert.assertEquals(null, rc.getTemporaryParameter("building"));
		rc.setTemporaryParameter("building", "C");
		Assert.assertEquals("C", rc.getTemporaryParameter("building"));
		
		Assert.assertEquals(2, rc.getParameterNames().size());
		Assert.assertEquals(1, rc.getPersistentParameterNames().size());
		Assert.assertEquals(1, rc.getTemporaryParameterNames().size());
		
		rc.removePersistentParameter("weather");
		rc.removeTemporaryParameter("building");
		Assert.assertEquals(2, rc.getParameterNames().size());
		Assert.assertEquals(0, rc.getPersistentParameterNames().size());
		Assert.assertEquals(0, rc.getTemporaryParameterNames().size());
	}
	
	@Test
	public void writeWithCookiesTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		outputCookies.add(new RCCookie("korisnik", "perica", "127.0.0.1", "/", 3600));
		RequestContext rc =new RequestContext(bos, 
				new HashMap<String, String>(),
				new HashMap<String, String>(),
				outputCookies);
		String output = null;
		try {
			rc.write("");
		} catch (IOException ignorable) {
		}
		try {
			output = bos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		Assert.assertEquals(output, "HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\n"
				+ "Set-Cookie: korisnik=\"perica\"; Domain=127.0.0.1; Path=/; Max-Age=3600\r\n"+"\r\n");
		try {
			bos.close();
		} catch (IOException e) {
		}
		
		bos = new ByteArrayOutputStream();
		outputCookies = new ArrayList<RequestContext.RCCookie>();
		RCCookie cookie = new RCCookie("korisnik", "perica", "127.0.0.1", "/", 3600);
		cookie.setHttpOnly(true);
		outputCookies.add(cookie);
		rc =new RequestContext(bos, 
				new HashMap<String, String>(),
				new HashMap<String, String>(),
				outputCookies);
		output = null;
		try {
			rc.write("");
		} catch (IOException ignorable) {
		}
		try {
			output = bos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		Assert.assertEquals(output, "HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\n"
				+ "Set-Cookie: korisnik=\"perica\"; Domain=127.0.0.1; Path=/; Max-Age=3600; HttpOnly\r\n"+"\r\n");
		try {
			bos.close();
		} catch (IOException e) {
		}
	}
	
	@Test
	public void writeTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		RequestContext rc =new RequestContext(bos, 
				new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		String output = null;
		try {
			rc.write("Čevapčići i Šiščevapčići.");
		} catch (IOException ignorable) {
		}
		try {
			output = bos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		Assert.assertEquals(output, "HTTP/1.1 205 Idemo dalje\r\nContent-Type: text/plain; charset=UTF-8\r\n\r\n"
				+ "Čevapčići i Šiščevapčići.");
		try {
			bos.close();
		} catch (IOException e) {
		}
	}
	
	@Test
	public void setParametersAfterHeaderIsGeneratedErrorTest() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		RequestContext rc =new RequestContext(bos, 
				new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		String output = null;
		try {
			rc.write("Čevapčići i Šiščevapčići.");
		} catch (IOException ignorable) {
		}
		try {
			output = bos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		Assert.assertEquals(output, "HTTP/1.1 205 Idemo dalje\r\nContent-Type: text/plain; charset=UTF-8\r\n\r\n"
				+ "Čevapčići i Šiščevapčići.");
		
		int counter = 0;
		try {
			rc.setEncoding("UTF-8");
		} catch (RuntimeException e) {
			counter++;
		}	
		try {
			rc.setMimeType("text/html");
		} catch (RuntimeException e) {
			counter++;
		}
		try {
			rc.setStatusCode(300);
		} catch (RuntimeException e) {
			counter++;
		}
		try {
			rc.setStatusText("Nije OK");
		} catch (RuntimeException e) {
			counter++;
		}
		try {
			rc.setOutputCookies(null);
		} catch (RuntimeException e) {
			counter++;
		}
		
		Assert.assertEquals(5, counter);
		
		try {
			bos.close();
		} catch (IOException e) {
		}
	}
	
	@Test
	public void cookieConstructorAndGettersTest() {
		RCCookie cookie = new RCCookie("korisnik", "perica", "127.0.0.1", "/", 3600);
		Assert.assertEquals("korisnik", cookie.getName());
		Assert.assertEquals("perica", cookie.getValue());
		Assert.assertEquals("127.0.0.1", cookie.getDomain());
		Assert.assertEquals("/", cookie.getPath());
		Assert.assertEquals(3600, cookie.getMaxAge().intValue());
	}
	
}
