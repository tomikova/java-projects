package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command charsets takes no arguments and lists names 
 * of supported charsets for your Java platform.
 * A single charset name is written per line.
 * 
 * @author Tomislav
 *
 */
public class CharsetsShellCommand implements ShellCommand {

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
	public CharsetsShellCommand() {
		this.name = "charsets";
		this.description = new ArrayList<>();
		this.description.add("Command charsets takes no arguments and lists names of supported charsets for your Java platform.");
		this.description.add("A single charset name is written per line.");
		this.description.add("USAGE EXAMPLE:");
		this.description.add("charsets");
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			String[] params = CommandUtility.parseString(arguments);
			if (params.length != 0) {
				throw new IllegalArgumentException("Invalid number of arguments for command "+getCommandName());
			}
			Map charSets = Charset.availableCharsets();
			Iterator it = charSets.keySet().iterator();
			while(it.hasNext()) {
				String csName = (String)it.next();
				env.write(csName);
				Iterator aliases = ((Charset)charSets.get(csName))
						.aliases().iterator();
				if(aliases.hasNext())
					env.write(": ");
				while(aliases.hasNext()) {
					env.write(aliases.next().toString());
					if(aliases.hasNext())
						env.write(", ");
				}
				env.writeln("");
			}
		}catch (Exception ex){
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
