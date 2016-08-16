package hr.fer.zemris.java.student0036461026.hw14.glasanje;

import hr.fer.zemris.java.student0036461026.hw14.dao.DAOProvider;
import hr.fer.zemris.java.student0036461026.hw14.model.Anketa;
import hr.fer.zemris.java.student0036461026.hw14.model.AnketaOpcija;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet čija je zadaća dohvaćanje podataka o opcijama ankete preko 
 * poslužitelja usluge pristupa podsustavu za perzistenciju podataka.
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
	 * Metoda dohvaća podatke o opcijama ankete i proslijeđuje ih na 
	 * prikaz odgovarajućij stranici za prikaz opcija ankete.
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
		
		Anketa anketa = DAOProvider.getDao().dohvatiAnketu(pollID);
		List<AnketaOpcija> opcije = DAOProvider.getDao().dohvatiSveOpcijeAnkete(pollID, "id");
		req.setAttribute("anketa", anketa);
		req.setAttribute("opcije", opcije);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
