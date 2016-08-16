package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command hexdump expects a single argument: file name, and produces hex-output.
 * @author Tomislav
 *
 */
public class HexdumpShellCommand implements  ShellCommand {

	/**
	 * Command name.
	 */
	private final String name;
	
	/**
	 * Command description.
	 */
	private final List<String> description;

	/**
	 * Constructor without parameters.
	 */
	public HexdumpShellCommand() {
		this.name = "hexdump";
		this.description = new ArrayList<>();
		this.description.add("Command hexdump expects a single argument: file name, and produces hex-output.");
		this.description.add("USAGE EXAMPLE:");
		this.description.add("hexdump file");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			String[] params = CommandUtility.parseString(arguments);
			
			if (params.length != 1) {
				throw new IllegalArgumentException("Invalid number of arguments for command "+getCommandName());	
			}
			
			File file = new File(params[0]);
			
			if (!file.exists()) {
				throw new IllegalArgumentException("File "+file.getAbsolutePath()+" does not exist");
			}
			
			if (file.isDirectory()) {
				throw new IllegalArgumentException("File "+file.getAbsolutePath()+" is directory");
			}
			
			FileInputStream input = new FileInputStream(file);
			byte[] buffer = new byte[16];
			
			int count = 0;
		
			while(true) {
				String hexString = Integer.toHexString(count);
				String offset = "";
				for(int i = 0, diff = 8-hexString.length(); i < diff; i++ ) {
					offset += "0";
				}
				offset += hexString;
				int readBytes = input.read(buffer);
				if (readBytes < 1) {
					input.close();
					break;
				}
				String output = offset.toUpperCase() + ": " + CommandUtility.bytesToHex(buffer, 0, readBytes) + 
						" | " + CommandUtility.bytesToAscii(buffer, 0, readBytes);
				env.writeln(output);
				count += readBytes;
			}

		} catch(Exception ex) {
			try {
				env.writeln("Error: "+ex.getMessage());
			} catch (Exception ignorable){}
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}
}
