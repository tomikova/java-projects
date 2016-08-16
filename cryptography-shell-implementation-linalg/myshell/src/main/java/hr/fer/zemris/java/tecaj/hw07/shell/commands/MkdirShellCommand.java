package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command mkdir takes a single argument: directory name, 
 * and creates the appropriate directory structure.
 * @author Tomislav
 *
 */
public class MkdirShellCommand implements ShellCommand {

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
	public MkdirShellCommand() {
		this.name = "mkdir";
		this.description = new ArrayList<>();
		this.description.add("Command mkdir takes a single argument: directory name, "
				+ "and creates the appropriate directory structure.");
		this.description.add("USAGE EXAMPLE:");
		this.description.add("mkdir directory");
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

			File directory = new File(params[0]);

			if (!directory.exists()) {
				directory.mkdirs();
			}
			
		}catch(Exception ex){
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
