package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command symbol prints symbol if given one argument - symbol name.
 * If second argument is provided, symbol is changed to that argument value.
 * @author Tomislav
 *
 */
public class SymbolShellCommand implements ShellCommand {

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
	public SymbolShellCommand() {
		this.name = "symbol";
		this.description = new ArrayList<>();
		this.description.add("Command symbol prints symbol if given one argument - symbol name.");
		this.description.add("If second argument is provided, symbol is changed to that argument value.");
		this.description.add("SUPPORTED SYMBOLS:");
		this.description.add("PROMPT, MORELINES, MULTILINE");
		this.description.add("USAGE EXAMPLE:");
		this.description.add("symbol PROMPT");
		this.description.add("symbol PROMPT newSymbolValue");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			String[] params = CommandUtility.parseString(arguments);
			
			if (params.length > 2 || params.length < 1) {
				throw new IllegalArgumentException("Invalid number of arguments for command "+getCommandName());
			}
			
			String message = null;
			Character symbol;
			if (params[0].equals("PROMPT")) {
				symbol = env.getPromptSymbol();
				if (params.length == 1) {
					message = "Symbol for PROMPT is '"+symbol+"'";
				}
				else if (params.length == 2) {
					env.setPromptSymbol(params[1].charAt(0));
					message = "Symbol for PROMPT changed from '"+symbol+"' to '"+params[1]+"'";
				}
			}
			else if (params[0].equals("MORELINES")) {
				symbol = env.getMorelinesSymbol();
				if (params.length == 1) {
					message = "Symbol for MORELINES is '"+symbol+"'";
				}
				else if (params.length == 2) {
					env.setMorelinesSymbol(params[1].charAt(0));
					message = "Symbol for MORELINES changed from '"+symbol+"' to '"+params[1]+"'";
				}
			}

			else if (params[0].equals("MULTILINE")) {
				symbol = env.getMultilineSymbol();
				if (params.length == 1) {
					message = "Symbol for MULTILINE is '"+symbol+"'";
				}
				else if (params.length == 2) {
					env.setMultilineSymbol(params[1].charAt(0));
					message = "Symbol for MULTILINE changed from '"+symbol+"' to '"+params[1]+"'";
				}
			}
			else {
				message = "Not recognized command";
			}

			env.writeln(message);
			
		}catch (Exception ex) {
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
