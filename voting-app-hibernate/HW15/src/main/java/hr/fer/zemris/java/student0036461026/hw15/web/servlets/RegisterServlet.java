package hr.fer.zemris.java.student0036461026.hw15.web.servlets;

import hr.fer.zemris.java.student0036461026.hw15.dao.DAOProvider;
import hr.fer.zemris.java.student0036461026.hw15.model.BlogUser;
import hr.fer.zemris.java.student0036461026.hw15.model.UserRegisterForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet registers new blog user.
 * @author Tomislav
 *
 */

@WebServlet(urlPatterns={"/servleti/register"})
public class RegisterServlet extends HttpServlet {

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
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		UserRegisterForm urf = new UserRegisterForm();
		urf.fillFromHttpRequest(req);
		urf.validate();
		
		if (urf.hasErrors()) {
			urf.setPassword("");
			req.setAttribute("entry", urf);
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}
		
		BlogUser newBlogUser = new BlogUser();
		urf.fillBlogUser(newBlogUser);
		DAOProvider.getDAO().addNewBlogUser(newBlogUser);
		resp.sendRedirect(req.getServletContext().getContextPath());
	}
}
