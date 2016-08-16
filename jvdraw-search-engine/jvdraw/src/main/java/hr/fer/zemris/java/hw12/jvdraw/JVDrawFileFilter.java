package hr.fer.zemris.java.hw12.jvdraw;

import java.io.File;

import javax.swing.filechooser.*;

/**
 * Class used to model filter for files that will be saved or opened
 * based on provided extension used as file filter.
 * @author Tomislav
 *
 */
public class JVDrawFileFilter extends FileFilter {
	
	/**
	 * Extension description.
	 */
	String description = "";
	/**
	 * File extension.
	 */
    String fileExt = "";

    /**
     * Default constructor with one parameter.
     * @param extension File extension.
     */
    public JVDrawFileFilter(String extension) {
        this.fileExt = extension;
    }

    /**
     * Constructor with twho parameters.
     * @param extension File extension.
     * @param description Extension description.
     */
    public JVDrawFileFilter(String extension, String description) {
        this.fileExt = extension;
        this.description = description;
    }
    
    /**
     * Method returns file extension.
     * @return File extension.
     */
    public String getExtension() {
    	return fileExt;
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
        	 return true;
        }
        return (f.getName().toLowerCase().endsWith(fileExt));
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public String getDescription() {
        return description;
    }
}
