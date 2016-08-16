package hr.fer.zemris.java.student0036461026.hw15.web.servlets;

import hr.fer.zemris.java.student0036461026.hw15.dao.DAOProvider;
import hr.fer.zemris.java.student0036461026.hw15.model.BlogComment;
import hr.fer.zemris.java.student0036461026.hw15.model.BlogEntry;
import hr.fer.zemris.java.student0036461026.hw15.model.BlogEntryForm;
import hr.fer.zemris.java.student0036461026.hw15.model.BlogUser;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet responsible for dealing with blog users and users 
 * blog entries. It shows appropriate web pages based on url path, 
 * creates new or updates existing blog entries.
 * @author Tomislav
 *
 */

@WebServlet(urlPatterns={"/servleti/author/*"})
public class AuthorServlet extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Access denied error message.
	 */
	private static final String ACCESS_DENIED_MSG = 
			"You have no permission to enter this page. Access denied!";
	
	/**
	 * Error message if page can't be loaded from any reason.
	 */
	private static final String PAGE_ERROR_MSG = "Error loading page";

	/**
	 * Parses given url and redirects to appropriate web pages.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String nick = getNick(req.getPathInfo());	

		if (req.getRequestURI().endsWith("/"+nick)) {
			BlogUser blogUser =  DAOProvider.getDAO().getBlogUser(nick);
			List<BlogEntry> blogEntries = DAOProvider.getDAO().getUserBlogEntries(blogUser);
			req.setAttribute("blogEntries", blogEntries);
			req.setAttribute("nick", nick);
			req.getRequestDispatcher("/WEB-INF/pages/blog_entries.jsp").forward(req, resp);
		}

		else if (req.getRequestURI().endsWith("/new")) {
			if (checkUser(req, nick)) {
				req.setAttribute("action", "new");
				req.setAttribute("entry", new BlogEntryForm());
				req.getRequestDispatcher("/WEB-INF/pages/blog_editing.jsp").forward(req, resp);
			}
			else {
				req.setAttribute("error", ACCESS_DENIED_MSG);
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			}
		}
		
		else if (req.getRequestURI().endsWith("/edit")) {
			if (checkUser(req, nick)) {
				Long eid = Long.valueOf(req.getParameter("eid"));
				BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(eid);
				BlogEntryForm bef = new BlogEntryForm();
				bef.fillFromBlogEntry(blogEntry);
				req.setAttribute("action", "edit");
				req.setAttribute("entry", bef);
				req.getRequestDispatcher("/WEB-INF/pages/blog_editing.jsp").forward(req, resp);
			}
			else {
				req.setAttribute("error", ACCESS_DENIED_MSG);
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			}
		}
		
		else {
			try{			
				Long eid = Long.valueOf(req.getPathInfo().replace("/"+nick+"/", ""));
				BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(eid);
				if (blogEntry == null) {
					req.setAttribute("error", PAGE_ERROR_MSG);
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}
				List<BlogComment> blogComments = DAOProvider.getDAO().geBlogEntryComments(blogEntry);
				req.setAttribute("nick", nick);
				req.setAttribute("entry", blogEntry);
				req.setAttribute("comments", blogComments);
				req.getRequestDispatcher("/WEB-INF/pages/blog_entry.jsp").forward(req, resp);
			} catch (Exception ex) {
				req.setAttribute("error", PAGE_ERROR_MSG);
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			}
		}
	}

	/**
	 * Deals with post requests received if blog entry needs to be added or updated.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String nick = getNick(req.getPathInfo());
		
		if (req.getRequestURI().endsWith("/new")) {
			if (checkUser(req, nick)) {	
				BlogEntryForm bef = new BlogEntryForm();
				bef.fillFromHttpRequest(req);
				bef.validate();
				if (bef.hasErrors()) {
					req.setAttribute("entry", bef);
					req.getRequestDispatcher("/WEB-INF/pages/blog_editing.jsp").forward(req, resp);
					return;
				}		
				HttpSession session = req.getSession();
				Long userID = (Long)session.getAttribute("currentUserId");	
				BlogUser blogUser =  DAOProvider.getDAO().getBlogUser(userID);	
				BlogEntry newBlogEntry = new BlogEntry();
				newBlogEntry.setBlogUser(blogUser);
				newBlogEntry.setTitle(bef.getTitle());
				newBlogEntry.setText(bef.getText());
				newBlogEntry.setCreatedAt(new Date());
				newBlogEntry.setLastModifiedAt(newBlogEntry.getCreatedAt());
				DAOProvider.getDAO().addBlogEntry(newBlogEntry);
				resp.sendRedirect("/aplikacija5/servleti/author/"+nick);
			}
			else {
				req.setAttribute("error", ACCESS_DENIED_MSG);
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			}
		}
		
		else if (req.getRequestURI().endsWith("/edit")) {
			if (checkUser(req, nick)) {
				Long eid = Long.valueOf(req.getParameter("eid"));
				BlogEntryForm bef = new BlogEntryForm();
				bef.fillFromHttpRequest(req);
				bef.setId(eid);
				bef.validate();
				if (bef.hasErrors()) {
					req.setAttribute("entry", bef);
					req.getRequestDispatcher("/WEB-INF/pages/blog_editing.jsp").forward(req, resp);
					return;
				}
				BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(eid);		
				blogEntry.setTitle(bef.getTitle());
				blogEntry.setText(bef.getText());
				blogEntry.setLastModifiedAt(new Date());
				resp.sendRedirect("/aplikacija5/servleti/author/"+nick);
			}
			else {
				req.setAttribute("error", ACCESS_DENIED_MSG);
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			}
		}
	}

	/**
	 * Method returns users nickname from given url.
	 * @param servletPath Url.
	 * @return Users nickname.
	 */
	private String getNick(String servletPath) {
		String param = servletPath.substring(1, 
				servletPath.indexOf('/', 1) == -1 ? servletPath.length() :
					servletPath.indexOf('/', 1));
		return param;
	}

	/**
	 * Method checks if user is loged in and checks if user 
	 * has permission to send received request.
	 * @param req Http request.
	 * @param nick Users nickname.
	 * @return True or false value depending if user has permission to send request.
	 */
	private boolean checkUser(HttpServletRequest req, String nick) {
		HttpSession session = req.getSession();
		String sessionNick = (String)session.getAttribute("currentUserNick");
		if (nick.equals(sessionNick)) {
			return true;
		}
		else {
			return false;
		}
	}
}
