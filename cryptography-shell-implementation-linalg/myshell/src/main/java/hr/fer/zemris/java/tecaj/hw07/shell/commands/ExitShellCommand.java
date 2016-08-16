package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command exit terminates shell.
 * @author Tomislav
 *
 */
public class ExitShellCommand implements  ShellCommand {

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
	public ExitShellCommand() {
		this.name = "exit";
		this.description = new ArrayList<>();
		this.description.add("Command exit terminates shell.");
		this.description.add("USAGE EXAMPLE:");
		this.description.add("exit");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
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
