package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import java.util.List;

/**
 * Interface with methods supported by shell commands.
 * @author Tomislav
 *
 */

public interface ShellCommand {
	
	/**
	 * Method executes command.
	 * @param env Environment which commmand will use for communication.
	 * @param arguments Command arguments.
	 * @return Returns shell status indicating should shell work be terminated or not.
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Method returns command name.
	 * @return Command name.
	 */
	String getCommandName();
	
	/**
	 * Method return command description.
	 * @return Command description.
	 */
	List<String> getCommandDescription();
	
}
