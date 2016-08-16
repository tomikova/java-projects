package hr.fer.zemris.java.student0036461026.hw14.glasanje;

import hr.fer.zemris.java.student0036461026.hw14.dao.DAOProvider;
import hr.fer.zemris.java.student0036461026.hw14.model.AnketaOpcija;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet čija je zadaća dohvaćanje podataka o opcijama ankete 
 * i rezultata glasanja za prikaz na stranici.
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
	 * Metoda dohvaća opcije ankete i prikazuje rezultate na stranici za prikaz rezultata.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Long pollID = null;

		try{
			pollID = Long.valueOf(req.getParameter("pollID"));
		}catch (Exception ex) {
			resp.sendRedirect(req.getContextPath()+"/index.html");
		}
		
		List<AnketaOpcija> opcije = DAOProvider.getDao().dohvatiSveOpcijeAnkete(pollID, "votesCount DESC");
		
		//na raspolaganju i drugim servletima bez ponovog učitavanja
		HttpSession session = req.getSession();
		session.setAttribute("opcije", opcije);

		req.setAttribute("opcije", opcije);
		req.setAttribute("pollID", pollID);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
