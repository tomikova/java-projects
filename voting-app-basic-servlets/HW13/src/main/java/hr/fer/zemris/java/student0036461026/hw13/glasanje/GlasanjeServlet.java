package hr.fer.zemris.java.student0036461026.hw13.glasanje;

import hr.fer.zemris.java.student0036461026.hw13.glasanje.Utility.Bend;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet čija je zadaća dohvaćanje podataka o bendovima iz tekstovne datoteke.
 * @author Tomislav
 *
 */
@WebServlet(urlPatterns={"/glasanje"})
public class GlasanjeServlet extends HttpServlet {

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
		
		String datotekaBendovi = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-definicija.txt");
		
		Map<Integer, Bend> bendovi = Utility.ucitajBendove(datotekaBendovi);
		
		req.setAttribute("bendovi", bendovi);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
