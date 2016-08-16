package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The cat command opens given file and writes its content to console.
 * Command cat takes one or two arguments.
 * The first argument is path to some file and is mandatory.
 * The second argument is charset name that should be used to interpret chars from bytes.
 * If not provided, a default platform charset will be used.
 * 
 * @author Tomislav
 *
 */
public class CatShellCommand implements ShellCommand {

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
	public CatShellCommand() {
		this.name = "cat";
		this.description = new ArrayList<>();
		this.description.add("The cat command opens given file and writes its content to console.");
		this.description.add("Command cat takes one or two arguments.");
		this.description.add("The first argument is path to some file and is mandatory.");
		this.description.add("The second argument is charset name that should be used to interpret chars from bytes.");
		this.description.add("If not provided, a default platform charset will be used.");
		this.description.add("USAGE EXAMPLE:");
		this.description.add("cat file");
		this.description.add("cat file UTF-16");
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
			String filePath = params[0];
			Charset charset = Charset.defaultCharset();
			if (params.length == 2) {
				charset = Charset.forName(params[1]);
			}
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(new FileInputStream(filePath)), charset));
			String line;
			while ((line = reader.readLine()) != null) {
				env.writeln(line);
			}
			reader.close();
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
