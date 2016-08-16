package hr.fer.zemris.java.custom.scripting.exec.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Program demonstrates execution of brojPoziva.smscr smart script using SmartScriptEngine.
 * @author Tomislav
 *
 */

public class SmartScriptEngineDemo3 {
	
	/**
	 * Method called at program start.
	 * @param args Command line arguments, not used.
	 * @throws IOException If error occurs while reading file path.
	 */
	public static void main(String[] args) throws IOException {

		String filepath = "lib/SmartScriptEngineTests/brojPoziva.smscr";
		String documentBody = new String(
				Files.readAllBytes(Paths.get(filepath)),StandardCharsets.UTF_8);
		
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(), rc
		).execute();
		System.out.println("Vrijednost u mapi: "+rc.getPersistentParameter("brojPoziva"));
	}
}
