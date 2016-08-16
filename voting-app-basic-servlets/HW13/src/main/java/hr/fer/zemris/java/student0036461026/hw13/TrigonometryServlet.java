package hr.fer.zemris.java.student0036461026.hw13;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet class resposnible for calculating sine and cosine od numbers in range
 * specified by url parameters. Two range number parameters are expected.
 * Numbers represents angles.
 * @author Tomislav
 *
 */
@WebServlet(urlPatterns={"/trigonometric"})
public class TrigonometryServlet extends HttpServlet {

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

		Integer startFrom = null;
		Integer endAt = null;

		try{
			startFrom = Integer.valueOf(req.getParameter("a"));
		}catch (Exception ex) {
			startFrom = 0;
		}

		try{
			endAt = Integer.valueOf(req.getParameter("b"));
		}catch (Exception ex) {
			endAt = 360;
		}

		if (startFrom > endAt) {
			Integer tmp = startFrom;
			startFrom = endAt;
			endAt = tmp;
		}

		if (endAt > startFrom+720) {
			endAt = startFrom+720;
		}

		List<Trigonometry> results = new ArrayList<>();

		for(int i = startFrom, n = endAt.intValue(); i <= n; i++ ) {
			results.add(new Trigonometry(i, Math.sin(i*Math.PI/180), Math.cos(i*Math.PI/180)));
		}

		req.setAttribute("results", results);

		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

	/**
	 * Class used for modeling response, it consists of number and its sine and cosine.
	 * @author Tomislav
	 *
	 */
	public static class Trigonometry {
		/**
		 * Number used for calculation.
		 */
		private int number;
		/**
		 * Sine of number.
		 */
		private double sine;
		/**
		 * Cosine of number.
		 */
		private double cosine;
		/**
		 * Default constructor with three parameters.
		 * @param number Number used for calculation.
		 * @param sine Sine of number.
		 * @param cosine Cosine of number.
		 */
		public Trigonometry(int number, double sine, double cosine) {
			super();
			this.number = number;
			this.sine = sine;
			this.cosine = cosine;
		}
		/**
		 * Method return number used for calculation.
		 * @return Number used for calculation.
		 */
		public int getNumber() {
			return number;
		}
		/**
		 * Method returns sine of number.
		 * @return Sine of number.
		 */
		public double getSine() {
			return sine;
		}
		/**
		 * Method return cosine of number.
		 * @return Cosine of number.
		 */
		public double getCosine() {
			return cosine;
		}
	}
}
