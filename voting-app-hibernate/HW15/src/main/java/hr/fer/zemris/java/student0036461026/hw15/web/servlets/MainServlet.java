package hr.fer.zemris.java.student0036461026.hw15.web.servlets;

import hr.fer.zemris.java.student0036461026.hw15.dao.DAOException;
import hr.fer.zemris.java.student0036461026.hw15.dao.DAOProvider;
import hr.fer.zemris.java.student0036461026.hw15.model.BlogUser;
import hr.fer.zemris.java.student0036461026.hw15.model.UserLoginForm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Main application servlet. For not logged users it displayes login form 
 * and link to the new user registration form. Provides logout option for logged 
 * users and displays list of blog users.
 * @author Tomislav
 *
 */

@WebServlet(urlPatterns={"/servleti/main"})
public class MainServlet  extends HttpServlet {
	
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setAttribute("entry", new UserLoginForm());
		req.setAttribute("authors", getBlogUsers());
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");

		UserLoginForm ulf = new UserLoginForm();
		ulf.fillFromHttpRequest(req);
		ulf.validate();

		if (ulf.hasErrors()) {
			ulf.setPassword("");
			req.setAttribute("entry", ulf);
			req.setAttribute("authors", getBlogUsers());
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
			return;
		}

		BlogUser blogUser =  DAOProvider.getDAO().getBlogUser(ulf.getNick());

		HttpSession session = req.getSession();
		session.setAttribute("currentUserId", blogUser.getId());
		session.setAttribute("currentUserFn", blogUser.getFirstName());
		session.setAttribute("currentUserLn", blogUser.getLastName());
		session.setAttribute("currentUserNick", blogUser.getNick());

		resp.sendRedirect(req.getServletContext().getContextPath());
	}
	
	/**
	 * Method returns list of blog users or empty list if error occured.
	 * @return List of blog users or empty list if error occured.
	 */
	private List<BlogUser> getBlogUsers() {
		List<BlogUser> blogUsers;
		try {
			blogUsers = DAOProvider.getDAO().getAllBlogUsers();
		} catch (DAOException ex) {
			blogUsers = new ArrayList<>();
		}
		return blogUsers;
	}
}
