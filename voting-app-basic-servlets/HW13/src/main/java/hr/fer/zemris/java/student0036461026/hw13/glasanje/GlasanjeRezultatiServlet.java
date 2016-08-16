package hr.fer.zemris.java.student0036461026.hw13.glasanje;

import hr.fer.zemris.java.student0036461026.hw13.glasanje.Utility.Bend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet čija je zadaća dohvaćanje podataka o bendovima 
 * i rezultatima glasanja za prikaz na stranici.
 * @author Tomislav
 *
 */
@WebServlet(urlPatterns={"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {

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
		
		String datotekaGlasovi = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-rezultati.txt");
		
		Map<Integer, Bend> bendovi = Utility.ucitajBendoveIGlasove(datotekaBendovi, datotekaGlasovi);
		
		List<Bend> list = new ArrayList<>(bendovi.values());
		
		Collections.sort(list, new Comparator<Bend>() {
	        @Override
	        public int compare(Bend o1, Bend o2) {
	            return -Double.compare(o1.getBrGlasova(), o2.getBrGlasova());
	        }
	    });
		
		//na raspolaganju i drugim servletima bez ponovog učitavanja
		HttpSession session = req.getSession();
		session.setAttribute("bendovi", list);
		
		req.setAttribute("bendovi", list);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
