package hr.fer.zemris.java.student0036461026.hw13;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class od main servlet responsible for dealing with main webpage url-s.
 * @author Tomislav
 *
 */
@WebServlet(urlPatterns={"/index.html", "/index", "/colorpick", 
		"/stories/*", "/appinfo", "/reportImage" })
public class MainServlet extends HttpServlet{

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
		
		String pathInfo = req.getRequestURI();
		String root = req.getContextPath();
		
		if (pathInfo.equals(root+"/index.html") || pathInfo.equals(root+"/index")
				|| pathInfo.equals(root+"/")) {
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
		}
		else if (pathInfo.equals(root+"/colorpick")) {
			req.getRequestDispatcher("/WEB-INF/pages/colors.jsp").forward(req, resp);
		}
		else if (pathInfo.contains("/stories/")) {
			String storyName = pathInfo.substring(pathInfo.lastIndexOf("/") + 1);
			String resource = "/WEB-INF/stories/"+storyName+".jsp";	
			File file = new File(req.getServletContext().getRealPath(resource));
			if(file.exists()){
				req.getRequestDispatcher(resource).forward(req, resp);
			}
		}
		else if (pathInfo.equals(root+"/appinfo")){
			req.getRequestDispatcher("/WEB-INF/pages/appinfo.jsp").forward(req, resp);
		}
		else if (pathInfo.equals(root+"/reportImage")) {
			req.getRequestDispatcher("/WEB-INF/pages/report.jsp").forward(req, resp);
		}
	}
}

