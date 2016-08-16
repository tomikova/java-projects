package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command help if started with no arguments, it lists names of all supported commands.
 * If started with single argument, it prints name and the description of selected command.
 * @author Tomislav
 *
 */
public class HelpShellCommand implements  ShellCommand {

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
	public HelpShellCommand() {
		this.name ="help";
		this.description = new ArrayList<>();
		this.description.add("Command help if started with no arguments, it lists names of all supported commands.");
		this.description.add("If started with single argument, it prints name and the description of selected command.");
		this.description.add("USAGE EXAMPLE:");
		this.description.add("help");
		this.description.add("help commandName");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			String[] params = CommandUtility.parseString(arguments);
			
			if (params.length > 1) {
				throw new IllegalArgumentException("Invalid number of arguments for command "+getCommandName());
			}
			
			if (params.length == 0) {
				env.writeln("Supported commands:");
				for (ShellCommand command : env.commands()) {
					env.writeln(command.getCommandName());
				}
			}
			
			else {
				boolean commandExist = false;
				for (ShellCommand command : env.commands()) {
					if (command.getCommandName().equals(params[0])) {
						commandExist = true;
						env.writeln("COMMAND:\n"+command.getCommandName());
						env.writeln("DESCRIPTION:");
						List<String> desc = command.getCommandDescription();
						for (String str : desc) {
							env.writeln(str);
						}
						break;
					}
				}
				if (!commandExist) {
					env.writeln("Command '"+params[0]+"' does not exist.");
				}
			}
			
		}catch(Exception ex) {
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
