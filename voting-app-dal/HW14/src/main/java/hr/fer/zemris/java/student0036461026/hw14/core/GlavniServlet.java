package hr.fer.zemris.java.student0036461026.hw14.core;

import hr.fer.zemris.java.student0036461026.hw14.dao.DAOProvider;
import hr.fer.zemris.java.student0036461026.hw14.model.Anketa;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji je zadužen za obradu zahtjeva za index.html url-om.
 * @author Tomislav
 *
 */
@WebServlet(urlPatterns={"/index.html"})
public class GlavniServlet extends HttpServlet{

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Metoda dohvaća listu dostupnih anketa iz baze podataka te je
	 * proslijeđuje početnoj stranici za prikaz.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<Anketa> ankete = DAOProvider.getDao().dohvatiSveAnkete();
		req.setAttribute("ankete", ankete);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
		
	}
}

