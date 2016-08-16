package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The tree command expects a single argument: directory name and prints a file tree.
 * @author Tomislav
 *
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * Command name.
	 */
	private final String name; 
	
	/**
	 * Indent level used for output format.
	 */
	private int indentLevel;
	
	/**
	 * Command descriptions.
	 */
	private final List<String> description;
	
	/**
	 * Constructor without parameters.
	 */
	public TreeShellCommand() {
		this.name = "tree";
		this.description = new ArrayList<>();
		this.description.add("The tree command expects a single argument: directory name and prints a file tree.");
		this.description.add("USAGE EXAMPLE:");
		this.description.add("tree directory");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {		
			indentLevel = 0;
			String[] params = CommandUtility.parseString(arguments);
			
			if (params.length != 1) {
				throw new IllegalArgumentException("Invalid number of arguments for command "+getCommandName());
			}
			
			File root = new File(params[0]);
			
			if (!root.isDirectory()) {
				throw new IllegalArgumentException(root+" is not a directory");
			}
			
			recursivePass(root, env);
			
		} catch(Exception ex) {
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
	 * Method for recursive pass through files and directories.
	 * @param dir Root directory.
	 * @param env Environment command will use for communication.
	 * @throws IOException If error occurs while reading or writting.
	 */
	private void recursivePass(File dir, Environment env) throws IOException{
		File[] children = dir.listFiles();
		if (children != null){
			for(File child : children){
				if(child.isFile()){
					onFile(child, env);
				}
				else if (child.isDirectory()){
					beforeDirectory(child, env);
					recursivePass(child,env);
					afterDirectory(child, env);
				}
			}
		}
	}
	
	/**
	 * Operation that is performed before recursive pass of current directory.
	 * @param dir Current directory.
	 * @param env Environment command will use for communication.
	 * @throws IOException If error occurs while reading or writting.
	 */
	private void beforeDirectory(File dir, Environment env) throws IOException {
		if(indentLevel == 0){
			env.writeln(dir.getName());
		} else{
			env.writeln(String.format("%"+indentLevel+"s%s"," ",dir.getName()));
		}
		indentLevel += 2;	
	}
	
	/**
	 * Operation that is performed after recursive pass of current directory.
	 * @param dir Current directory.
	 * @param env Environment command will use for communication.
	 */
	private void afterDirectory(File dir, Environment env) {
		indentLevel -= 2;	
	}
	
	/**
	 * Method that is performed when file is found.
	 * @param f Current file.
	 * @param env Environment command will use for communication.
	 * @throws IOException If error occurs while reading or writting.
	 */
	private void onFile(File f, Environment env) throws IOException {
		if(indentLevel == 0){
			env.writeln(f.getName());
		} 
		else{
			env.writeln(String.format("%"+indentLevel+"s%s"," ",f.getName()));
		}
	}
}
