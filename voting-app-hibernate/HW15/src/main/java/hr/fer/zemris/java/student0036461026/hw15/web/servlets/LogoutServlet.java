package hr.fer.zemris.java.student0036461026.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet is doing user logout by invalidating user session.
 * @author Tomislav
 *
 */

@WebServlet(urlPatterns={"/servleti/logout"})
public class LogoutServlet extends HttpServlet {

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
		
		req.getSession().invalidate();
		resp.sendRedirect("main");
	}
	
}
