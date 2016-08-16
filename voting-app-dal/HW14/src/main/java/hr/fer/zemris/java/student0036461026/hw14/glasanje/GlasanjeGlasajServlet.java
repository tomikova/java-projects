package hr.fer.zemris.java.student0036461026.hw14.glasanje;

import hr.fer.zemris.java.student0036461026.hw14.dao.DAOProvider;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet čija je zadaća registriranje glasa pri glasanju.
 * Ažurira se odabrana opcija ankete.
 * @author Tomislav
 *
 */
@WebServlet(urlPatterns={"/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Metoda ažurira opciju ankete te proslijeđuje na pregled rezultata ankete.
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

		Integer id = null;
		try{
			id = Integer.valueOf(req.getParameter("id"));
		}catch (Exception ex) {
			resp.sendRedirect(req.getContextPath()+"/index.html");
		}

		DAOProvider.getDao().azurirajOpciju(id, 1);
		
		resp.sendRedirect(req.getContextPath()+"/glasanje-rezultati?pollID="+pollID);
	}

}
