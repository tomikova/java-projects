package hr.fer.zemris.java.student0036461026.hw15.web.servlets;

import hr.fer.zemris.java.student0036461026.hw15.dao.DAOProvider;
import hr.fer.zemris.java.student0036461026.hw15.model.BlogComment;
import hr.fer.zemris.java.student0036461026.hw15.model.BlogEntry;
import hr.fer.zemris.java.student0036461026.hw15.model.BlogUser;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet responsible for adding new blog entry comment.
 * @author Tomislav
 *
 */

@WebServlet(urlPatterns={"/addComment"})
public class CommentServlet extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Adds new blog entry comment.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Long eid = Long.valueOf(req.getParameter("eid"));
		String nick = req.getParameter("nick");
		String text = req.getParameter("text").trim();
		if (text.isEmpty()) {
			resp.sendRedirect("servleti/author/"+nick+"/"+eid);
			return;
		}	
		HttpSession session = req.getSession();	
		Long userID = (Long)session.getAttribute("currentUserId");	
		String email = null;
		if (userID != null) {
			BlogUser blogUser =  DAOProvider.getDAO().getBlogUser(userID);
			email = blogUser.getEmail();
		}
		else {
			email = "user@anonymous";
		}	
		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(eid);
		BlogComment comment = new BlogComment();
		comment.setBlogEntry(blogEntry);
		comment.setMessage(text);
		comment.setPostedOn(new Date());
		comment.setEmail(email);
		DAOProvider.getDAO().addBlogComment(comment);
		resp.sendRedirect("servleti/author/"+nick+"/"+eid);
	}

}
