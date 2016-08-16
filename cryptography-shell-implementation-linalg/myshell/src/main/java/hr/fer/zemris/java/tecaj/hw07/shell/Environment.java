package hr.fer.zemris.java.tecaj.hw07.shell;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;
import java.io.IOException;

/**
 * Interface defining environment that will be passed to each shell command.
 * Each command communicating with user using this interface.
 * @author Tomislav
 *
 */

public interface Environment {

	/**
	 * Method for reading user input.
	 * @return User input.
	 * @throws IOException If any error occurs while reading user input.
	 */
	String readLine() throws IOException;
	
	/**
	 * Method for writing on output stream.
	 * @param text Text for writting.
	 * @throws IOException If error occurs while writing.
	 */
	void write(String text) throws IOException;
	
	/**
	 * Method for writting on output stream which adds new line at the end.
	 * @param text Text for writting.
	 * @throws IOException If error occurs while writing.
	 */
	void writeln(String text) throws IOException;
	
	/**
	 * Method returns interface for iterating over shell commands.
	 * @return  Interface for iterating over shell commands.
	 */
	Iterable<ShellCommand> commands();
	
	/**
	 * Method returns multiline symbol.
	 * @return Multiline symbol.
	 */
	Character getMultilineSymbol();
	
	/**
	 * Method for setting multiline symbol.
	 * @param symbol New multiline symbol.
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Method returns prompt symbol.
	 * @return Prompt symbol.
	 */
	Character getPromptSymbol();
	
	/**
	 * Method for setting prompt symbol.
	 * @param symbol New prompt symbol.
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Method returns morelines symbol.
	 * @return Morelines symbol.
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Method for setting morelines symbol.
	 * @param symbol New morelines symbol.
	 */
	void setMorelinesSymbol(Character symbol);
	
}
