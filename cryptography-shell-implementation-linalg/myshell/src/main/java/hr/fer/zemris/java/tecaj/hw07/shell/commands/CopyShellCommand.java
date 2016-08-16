package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The copy command expects two arguments:
 * source file name and destination file name (i.e. paths and names).
 * If destination file exists, user is asked is it allowed to overwrite it.
 * Command copy works only with files.
 * If the second argument is directory, original file 
 * is copied in that directory using the original file name.
 * @author Tomislav
 *
 */
public class CopyShellCommand implements  ShellCommand {

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
	public CopyShellCommand() {
		this.name = "copy";
		this.description = new ArrayList<>();
		this.description.add("The copy command expects two arguments: ");
		this.description.add("source file name and destination file name (i.e. paths and names).");
		this.description.add("If destination file exists, user is asked is it allowed to overwrite it.");
		this.description.add("Command copy works only with files.");
		this.description.add("If the second argument is directory, original file is "
				+ "copied in that directory using the original file name.");
		this.description.add("USAGE EXAMPLE:");
		this.description.add("copy sourceFile destFile");
		this.description.add("copy sourceFile destDirectory");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			String[] params = CommandUtility.parseString(arguments);
			if (params.length != 2) {
				throw new IllegalArgumentException("Invalid number of arguments for command "+getCommandName());
			}
				
			File source = new File(params[0]);
			File destination = new File(params[1]);
				
			if (destination.isDirectory()) {
				destination = new File(destination.getAbsolutePath()+"\\"+source.getName());
			}	
			
			if (destination.isFile()) {
				String choice;
				while(true) {
					env.writeln("File "+params[1]+" exist.\nOverwrite? (y/n)");
					choice = env.readLine().toLowerCase();
					if (choice.equals("y") || choice.equals("n")) {
						break;
					}
				}
				if (choice.equals("y")) {
					copy(source, destination);
				}
			}
			else {
				copy(source, destination);
			}
				
				
		} catch (Exception ex) {
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
	
	/**
	 * Method for copying file.
	 * @param source Source file path.
	 * @param destination Destination file path.
	 * @throws IOException If error occurs while reading or writting.
	 */
	private void copy(File source, File destination) throws IOException {
		FileInputStream input = new FileInputStream(source);
		FileOutputStream output = new FileOutputStream(destination, false);
		byte[] buffer = new byte[1024];
		while(true) {
			int readBytes = input.read(buffer);
			if (readBytes < 1) {
				input.close();
				output.close();
				break;
			}
			output.write(buffer, 0, readBytes);
		}
		
		
	}
}
