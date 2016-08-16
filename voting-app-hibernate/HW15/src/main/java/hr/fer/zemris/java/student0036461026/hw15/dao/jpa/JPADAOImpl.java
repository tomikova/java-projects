package hr.fer.zemris.java.student0036461026.hw15.dao.jpa;

import java.util.List;

import hr.fer.zemris.java.student0036461026.hw15.dao.DAO;
import hr.fer.zemris.java.student0036461026.hw15.dao.DAOException;
import hr.fer.zemris.java.student0036461026.hw15.model.BlogComment;
import hr.fer.zemris.java.student0036461026.hw15.model.BlogEntry;
import hr.fer.zemris.java.student0036461026.hw15.model.BlogUser;

/**
 * Implementation of DAO subsystem using ORM technology. This concrete
 * implementation expects to have available entity manager that is 
 * supplied by {@link JPAEMProvider} class.
 * @author Tomislav
 *
 */
public class JPADAOImpl implements DAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		try {
			BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
			return blogEntry;
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogUser getBlogUser(Long id) throws DAOException {
		try {
			BlogUser blogUser = JPAEMProvider.getEntityManager().find(BlogUser.class, id);
			return blogUser;
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		try {
			BlogUser blogUser = 
					(BlogUser)JPAEMProvider.getEntityManager().
					createQuery("select b from BlogUser as b where b.nick=:nick")
					.setParameter("nick", nick)
					.setHint("org.hibernate.cacheable", Boolean.TRUE)
					.getSingleResult();
			return blogUser;
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addNewBlogUser(BlogUser user) throws DAOException {
		try {
			JPAEMProvider.getEntityManager().persist(user);
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BlogUser> getAllBlogUsers() throws DAOException {
		try {
			@SuppressWarnings("unchecked")
			List<BlogUser> blogUsers = 
			(List<BlogUser>)JPAEMProvider.getEntityManager()
			.createQuery("from BlogUser")
			.getResultList();
			return blogUsers;
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BlogEntry> getUserBlogEntries(BlogUser blogUser) throws DAOException {
		try {
			@SuppressWarnings("unchecked")
			List<BlogEntry> entries = 
			(List<BlogEntry>)JPAEMProvider.getEntityManager()
			.createQuery("select b from BlogEntry as b where b.blogUser=:bu")
			.setParameter("bu", blogUser)
			.setHint("org.hibernate.cacheable", Boolean.TRUE)
			.getResultList();
			return entries;
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage());
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addBlogEntry(BlogEntry entry) throws DAOException {
		try {
			JPAEMProvider.getEntityManager().persist(entry);
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BlogComment> geBlogEntryComments(BlogEntry blogEntry)
			throws DAOException {
		try {
			@SuppressWarnings("unchecked")
			List<BlogComment> comments = 
			(List<BlogComment>)JPAEMProvider.getEntityManager()
			.createQuery("select b from BlogComment as b where b.blogEntry=:be")
			.setParameter("be", blogEntry)
			.setHint("org.hibernate.cacheable", Boolean.TRUE)
			.getResultList();
			return comments;
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage());
		}	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addBlogComment(BlogComment comment) throws DAOException {
		try {
			JPAEMProvider.getEntityManager().persist(comment);
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage());
		}
	}
}
