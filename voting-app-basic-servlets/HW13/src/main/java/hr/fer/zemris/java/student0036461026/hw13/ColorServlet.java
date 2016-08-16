package hr.fer.zemris.java.student0036461026.hw13;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet class responsible for changing application background color 
 * based on user choice. 
 * @author Tomislav
 *
 */
@WebServlet(urlPatterns={"/setcolor/*"})
public class ColorServlet extends HttpServlet {

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

		String colorName = req.getRequestURI().
				replace(req.getContextPath()+"/setcolor/", "");

		Color color;
		try {
			Field field = Class.forName("java.awt.Color").getField(colorName);
			color = (Color)field.get(null);
		} catch (Exception e) {
			color = Color.WHITE;
		}
		
		String hexColor = String.format("#%06x", color.getRGB() & 0x00FFFFFF);
		
		HttpSession session = req.getSession();
		session.setAttribute("color", hexColor);
		
		resp.sendRedirect(req.getContextPath());
	}
}
