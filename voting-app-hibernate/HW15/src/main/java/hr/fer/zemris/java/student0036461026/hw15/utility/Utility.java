package hr.fer.zemris.java.student0036461026.hw15.utility;

import java.security.MessageDigest;

/**
 * Utility class providing methods for calculating hashes.
 * @author Tomislav
 *
 */

public class Utility {
	
	/**
	 * Method for computing hash.
	 * @param string String for computing hash.
	 * @param algorithm Algorithm name.
	 * @return Hash.
	 */
	public static String computeHash(String string, String algorithm) {
		String hashString = null;
		try {
			MessageDigest sha = MessageDigest.getInstance(algorithm);
			sha.update(string.getBytes());
			byte[] hash = sha.digest();	
			hashString = bytesToHex(hash).toLowerCase();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return hashString;
	}
	
	/**
	 * Method for coverting bytes to hex digits.
	 * @param data Byte array.
	 * @return Hex digits.
	 */
	public static String bytesToHex(byte[] data) {
		final StringBuilder sb = new StringBuilder();
		for(byte oneByte : data) {
			sb.append(String.format("%02x", oneByte));
		}
		return sb.toString();
	}
}
