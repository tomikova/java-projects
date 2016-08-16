package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command ls takes a single argument – directory 
 * and writes a directory listing (not recursive).
 * @author Tomislav
 *
 */
public class LsShellCommand implements  ShellCommand {

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
	public LsShellCommand() {
		this.name = "ls";
		this.description = new ArrayList<>();
		this.description.add("Command ls takes a single argument – "
				+ "directory and writes a directory listing (not recursive).");
		this.description.add("USAGE EXAMPLE:");
		this.description.add("ls directory");
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
			
			File root = new File(params[0]);

			if (!root.exists()) {
				throw new IllegalArgumentException(root+" does not exist");
			}

			if (!root.isDirectory()) {
				throw new IllegalArgumentException(root+" is not a directory");
			}

			File[] children = root.listFiles();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			if (children!= null) {
				for(File file : children) {
					Path path = Paths.get(file.getPath());
					BasicFileAttributeView faView = Files.getFileAttributeView(
							path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
					BasicFileAttributes attributes = faView.readAttributes();
					FileTime fileTime = attributes.creationTime();
					String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
					String info = "";
					info += file.isDirectory() ? "d" : "-";
					info += file.canRead() ? "r" : "-";
					info += file.canWrite() ? "w" : "-";
					info += file.canExecute() ? "x" : "-";
					String fileSize = String.valueOf(file.length());
					String output = String.format("%s%"+(15-fileSize.length())+"s %s %s %s", info, " ", 
							fileSize, formattedDateTime, file.getName());
					env.writeln(output);
				}
			}
		} catch (Exception ex) {
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
