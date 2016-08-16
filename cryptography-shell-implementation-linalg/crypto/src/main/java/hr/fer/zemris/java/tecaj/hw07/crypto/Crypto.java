package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Program for file encryption/decryption using AES cypto-algorithm, 
 * and calculating or checking file digset using SHA-256.
 * Program arguments are operation that needs to be performed.
 * If operation is "encrypt" or "decrypt" then program expects to get source file
 * and destination file paths.
 * If operation is "checksha" then program expects to get path of the source file.
 * @author Tomislav
 *
 */

public class Crypto {
	
	/**
	 * Method called at program start.
	 * @param args Command line arguments.
	 */
	public static void main( String[] args ) {

		if(args.length == 0) {
			System.out.println("No arguments given!");
			System.exit(0);
		}

		String action = args[0].toLowerCase();

		if (action.equals("encrypt")) {
			checkInput(args, 3);
			encryptOrDecrypt(args[1], args[2], "Encrypt", true);
		}		
		else if (action.equals("decrypt")) {
			checkInput(args, 3);
			encryptOrDecrypt(args[1], args[2], "Decrypt", false);
		}

		else if (action.equals("checksha")) {	
			checkInput(args, 2);
			computeHash(args[1],"SHA-256");
		}
		else {
			System.out.println("Operation '"+action+"' not supported.");
			System.exit(2);
		}
	}

	/**
	 * Method for checking user input.
	 * @param args Command line arguments.
	 * @param numArgs Expected number of arguments.
	 */
	private static void checkInput (String[] args, int numArgs) {
		if (args.length != numArgs) {
			System.out.println("Expected number of arguments is "+numArgs+", "+args.length+" given.");
			System.exit(1);
		}
	}

	/**
	 * Method for converting hex digits to bytes.
	 * @param text Text representation of hex digits.
	 * @return Byte array.
	 */
	public static byte[] hexToBytes(String text) {
		int length = text.length();
		if (length % 2 != 0) {
			throw new IllegalArgumentException("Uneven number of characters.");
		}
		byte[] data = new byte[length / 2];
		for (int i = 0; i < length; i += 2) {
			int firstDigit = Character.digit(text.charAt(i), 16);
			int secondDigit = Character.digit(text.charAt(i+1), 16);
			if (firstDigit == -1 || secondDigit == -1) {
				throw new IllegalArgumentException("Unable to convert to byte, text contains invalid characters.");
			}
			data[i / 2] = (byte) ((firstDigit<<4)+secondDigit);
		}
		return data;
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

	/**
	 * Method for encryption or decryption of given file.
	 * @param inputFile Source file path on which operation will be performed.
	 * @param outputFile  Destination file path which will be created as operation result.
	 * @param action Operation name.
	 * @param mode True/false value indicating which operation will be performed.
	 */
	private static void encryptOrDecrypt (String inputFile, String outputFile, String action, boolean mode)  {

		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(System.in)));

			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");

			String keyText = reader.readLine().trim();

			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");

			String ivText = reader.readLine().trim();

			if (keyText.length() != 32 || ivText.length() != 32) {
				throw new IllegalArgumentException("Password and initialization vector must contain 32 hex-digits.");
			}

			SecretKeySpec keySpec = new SecretKeySpec(hexToBytes(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(hexToBytes(ivText));

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(mode ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

			BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(
					Paths.get(inputFile), StandardOpenOption.READ), 4096);
			BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(
					Paths.get(outputFile), StandardOpenOption.CREATE, StandardOpenOption.WRITE), 4096);

			byte[] buffer = new byte[4096];

			while (true) {
				int read = bis.read(buffer);
				if (read < 1) {
					bis.close();
					bos.close();
					break;
				}
				else if (read == buffer.length) {
					bos.write(cipher.update(buffer));
				}
				else {
					bos.write(cipher.doFinal(buffer, 0, read));
				}
			}

			System.out.println(action+"ion completed. Generated file "+outputFile+" based on file "+inputFile+".");
		}catch (Exception ex) {
			System.out.println(ex.getStackTrace()[0]);
			System.out.println(ex.getMessage());
			System.exit(3);
		}
	}

	/**
	 * Method for computing digset using SHA-256.
	 * @param inputFile Source file path.
	 * @param algorithm Algorithm name.
	 */
	private static void computeHash(String inputFile, String algorithm) {

		try {
			MessageDigest sha = MessageDigest.getInstance(algorithm);

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(System.in)));

			System.out.println("Please provide expected "+algorithm.toLowerCase()+" digest for "+inputFile+":");

			String digset = reader.readLine().trim().toLowerCase();

			BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(
					Paths.get(inputFile), StandardOpenOption.READ), 4096);

			byte[] buffer = new byte[4096];

			while (true) {
				int read = bis.read(buffer);
				if (read < 1) {
					bis.close();
					break;
				}
				sha.update(buffer, 0, read);
			}

			byte[] hash = sha.digest();	

			String originalDigset = bytesToHex(hash).toLowerCase();

			if (originalDigset.equals(digset)) {
				System.out.println("Digesting completed. Digest of hw07test.bin matches expected digest.");
			}
			else {
				System.out.println("Digesting completed. Digest of "+inputFile+" does not match the "
						+ "expected digest. Digest was: "+originalDigset);
			}
		} catch (Exception ex) {
			System.out.println(ex.getStackTrace()[0]);
			System.out.println(ex.getMessage());
			System.exit(4);
		}
	}
}
