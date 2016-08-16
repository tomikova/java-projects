package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Help class for shell commands providing them oftenly used methods.
 * @author Tomislav
 *
 */
public class CommandUtility{

	/**
	 * Method for coverting bytes to hex digits.
	 * @param data Data byte array.
	 * @param offset Offset of byte array start index.
	 * @param len Length of the data.
	 * @return Hex digits.
	 */
	public static String bytesToHex(byte[] data, int offset, int len) {
		StringBuilder sb = new StringBuilder();
		int endIndex = offset+len;
		int size = endIndex < data.length ? data.length : endIndex;
		for (int i = offset; i < size; i++ ) {
			String separator = (i+1) % 8 == 0 ? "|" : " ";
			separator = (i+1) < data.length ? separator : "";
			if (i < data.length && i < endIndex) {
				sb.append(String.format("%02x"+separator, data[i]));
			}
			else {
				sb.append("  "+separator);
			}
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * Method for coverting bytes to ascii characters.
	 * @param data Data byte array.
	 * @param offset Offset of byte array start index.
	 * @param len Length of the data.
	 * @return Ascii characters.
	 */
	public static String bytesToAscii(byte[] data, int offset, int len) {
		StringBuilder sb = new StringBuilder();
		int endIndex = offset+len;
		for (int i = offset; i < endIndex; i++ ) {
			if (i < data.length) {
				if ((32 <= data[i]) && (127 >= data[i])) {
					sb.append((char) data[i]);
				} else {
					sb.append(".");
				}
			}
			else {
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Method for parsing string. String is split on whitespace if
	 * whitespace is not inside quotes. Inside quotes escape sequence is recognized. 
	 * @param arguments String for parsing.
	 * @return String split.
	 */
	public static String[] parseString(String arguments) {
		
		List<String> params = new ArrayList<>();
		
		String param = "";
		
		boolean quoteFound = false;
		for (int i = 0, length = arguments.length(); i < length; i++) {
			char character = arguments.charAt(i);
			if (character == ' ' || character == '\t'){
				if (quoteFound){
					param += character;
				}
				else if (!param.equals("")) {
					params.add(param);
					param = "";
				}
			}
			else if (character == '\\') {
				if (i+1 < arguments.length()) {
					char next = arguments.charAt(i+1);
					if (next == '\\') {
						param += next;
						i++;
					}
					else if (next == '"') {
						param += next;
						i++;
					}
					else {
						param += character;
					}
				}
				else {
					param += character;
				}
			}
			else if (character == '"') {
				if (!param.equals("")) {
					params.add(param);
					param = "";
				}
				quoteFound = !quoteFound;
			}
			else {
				param += character;
			}
		}
		
		if (quoteFound) {
			throw new IllegalArgumentException("Closing '\"' missing");
		}
		else if (!param.equals("")) {
			params.add(param);
		}
		
		return params.toArray(new String[params.size()]);
	}
}
