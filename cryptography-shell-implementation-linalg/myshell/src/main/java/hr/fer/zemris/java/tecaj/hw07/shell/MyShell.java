package hr.fer.zemris.java.tecaj.hw07.shell;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Program simulates work of a shell.
 * @author Tomislav
 *
 */

public class MyShell
{	
	private static final Character PROMPT_DEFAULT = '>';
	private static final Character MORELINES_DEFAULT = '\\';
	private static final Character MULTILINE_DEFAULT = '|';

	/**
	 * Method called on program start.
	 * @param args Command line arguments.
	 * @throws IOException If any error occurs while reading or writting.
	 */
	public static void main( String[] args ) throws IOException
	{
		Map<String, ShellCommand> commands = new HashMap<>();
		commands.put("exit", new ExitShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("symbol", new SymbolShellCommand());

		Map<String, Character> symbols = new HashMap<>();
		symbols.put("PROMPT", PROMPT_DEFAULT);
		symbols.put("MORELINES", MORELINES_DEFAULT);
		symbols.put("MULTILINE", MULTILINE_DEFAULT);

		Environment env = new MyShellEnvironment(commands, symbols);
		
		env.writeln("Welcome to MyShell v 1.0");
		
		String line = "";
		String multiline = "";
		String action = "";
		boolean multilineFlag = false;
		ShellStatus status = ShellStatus.CONTINUE;
		
		do {
			if (multilineFlag) {
				env.write(symbols.get("MULTILINE")+" ");
			}
			else {
				env.write(symbols.get("PROMPT")+" ");
			}
			line = env.readLine();
			String[] params = line.split("\\s+");
			int len = params.length;
			Character lastSymbol = params[len-1].charAt(0);
			int counter;
			if (multiline.equals("") && !multilineFlag) {
				action = params[0];
				counter = 1;
			}
			else {
				counter = 0;
			}		
			for (int i = counter; i < (lastSymbol == symbols.get("MORELINES") ? len-1 : len)  ; i++) {
				multiline += " "+params[i];
			}
			if (lastSymbol == symbols.get("MORELINES")) {
				multilineFlag = true;
			}
			else {
				String commandName = action;
				String arguments = multiline.trim();
				ShellCommand command = commands.get(commandName);
				if (command != null) {
					status = command.executeCommand(env, arguments);
				}
				else {
					env.writeln("Command '"+commandName+"' does not exist.");
				}
				multiline = "";
				multilineFlag = false;
			}
		} while(status != ShellStatus.TERMINATE);

	}

	/**
	 * Class implements Environment interface for commands communication with user.
	 * @author Tomislav
	 *
	 */
	private static class MyShellEnvironment implements Environment {

		/**
		 * Supported shell commands.
		 */
		private Map<String, ShellCommand> commands;
		/**
		 * Dupported shell symbols.
		 */
		private Map<String, Character> symbols;
		
		BufferedReader reader;

		/**
		 * Constructor with two parameters.
		 * @param commands Map of commands supported by shell.
		 * @param symbols Map of symbols supported by shell.
		 */
		public MyShellEnvironment(Map<String, ShellCommand> commands, Map<String, Character> symbols) {
			this.commands = commands;
			this.symbols = symbols;
			this.reader = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(System.in)));
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Iterable<ShellCommand> commands() {
			return commands.values();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Character getMorelinesSymbol() {
			return symbols.get("MORELINES");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Character getMultilineSymbol() {
			return symbols.get("MULTILINE");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Character getPromptSymbol() {
			return symbols.get("PROMPT");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String readLine() throws IOException {
			return reader.readLine();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setMorelinesSymbol(Character symbol) {
			symbols.put("MORELINES", symbol);

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setMultilineSymbol(Character symbol) {
			symbols.put("MULTILINE", symbol);

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setPromptSymbol(Character symbol) {
			symbols.put("PROMPT", symbol);

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void write(String text) throws IOException {
			System.out.print(text);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void writeln(String text) throws IOException {
			System.out.println(text);
		}
	}
}
