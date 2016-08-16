package hr.fer.zemris.java.student0036461026.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.student0036461026.hw15.model.BlogComment;
import hr.fer.zemris.java.student0036461026.hw15.model.BlogEntry;
import hr.fer.zemris.java.student0036461026.hw15.model.BlogUser;

/**
 * Interface defines methods that need to be implemented by class that
 * will model data access layer and access to subsystem for data persistence.
 * @author Tomislav
 *
 */
public interface DAO {

	/**
	 * Method returns blog entry with given id. If such entry does not 
	 * exist null is returned.
	 * @param id Blog entry key.
	 * @return  Blog entry with given id or null if such entry does not exist
	 * @throws DAOException If error occurs while retrieving data.
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Method returns blog user with given id. If such entry does not 
	 * exist null is returned.
	 * @param id Blog user key.
	 * @return Blog user with given id or null if such entry does not exist.
	 * @throws DAOException If error occurs while retrieving data.
	 */
	public BlogUser getBlogUser(Long id) throws DAOException;
	
	/**
	 * Method returns blog user with given nick. If such entry does not 
	 * exist null is returned.
	 * @param nick Blog user nick
	 * @return Blog user with given nick or null if such entry does not exist.
	 * @throws DAOException If error occurs while retrieving data.
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;
	
	/**
	 * Method adds new blog user.
	 * @param user User that will be added.
	 * @throws DAOException If error occurs while adding data.
	 */
	public void addNewBlogUser(BlogUser user) throws DAOException;
	
	/**
	 * Method returns all blog users.
	 * @return List of blog users.
	 * @throws DAOException If error occurs while retrieving data.
	 */
	public List<BlogUser> getAllBlogUsers() throws DAOException;
	
	/**
	 * Method returns all blog entries of given blog user.
	 * @param blogUser Blog user.
	 * @return All blog entries of given blog user.
	 * @throws DAOException If error occurs while retrieving data.
	 */
	public List<BlogEntry> getUserBlogEntries(BlogUser blogUser) throws DAOException;
	
	/**
	 * Method adds new blog entry.
	 * @param entry Blog entry that will be added.
	 * @throws DAOException If error occurs while adding data.
	 */
	public void addBlogEntry(BlogEntry entry) throws DAOException;
	
	/**
	 * Method returns all blog comments of given blog entry.
	 * @param blogEntry Blog entry.
	 * @return All blog comments of given blog entry.
	 * @throws DAOException If error occurs while retrieving data.
	 */
	public List<BlogComment> geBlogEntryComments(BlogEntry blogEntry) throws DAOException;
	
	/**
	 * Method adds new blog comment.
	 * @param comment Blog comment that will be added.
	 * @throws DAOException If error occurs while adding data.
	 */
	public void addBlogComment(BlogComment comment) throws DAOException;
	
}
