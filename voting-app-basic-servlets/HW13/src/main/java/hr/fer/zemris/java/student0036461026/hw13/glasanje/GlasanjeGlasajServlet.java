package hr.fer.zemris.java.student0036461026.hw13.glasanje;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet čija je zadaća registriranje glasa pri glasanju.
 * U datoteci se pronalazi id benda te se ažurira vrijednost glasova.
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
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Integer id = null;
		try{
			id = Integer.valueOf(req.getParameter("id"));
		}catch (Exception ex) {
			return;
		}

		List<String> noviGlasovi = new ArrayList<>();

		String datotekaGlasovi = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-rezultati.txt");

		String[] definicije = Files.readAllLines(Paths.get(datotekaGlasovi),
				StandardCharsets. UTF_8).toArray(new String[0]);

		for (String bend : definicije) {
			String[] params = bend.split("\t");
			Integer bendId = Integer.valueOf(params[0]);
			Integer glasovi = Integer.valueOf(params[1]);
			if (bendId == id) {
				noviGlasovi.add(bendId+"\t"+(glasovi+1));
			}
			else {
				noviGlasovi.add(bendId+"\t"+glasovi);
			}
		}

		Path path = Paths.get(datotekaGlasovi);
		try (OutputStream out = new BufferedOutputStream(
				Files.newOutputStream(path, StandardOpenOption.CREATE))) {
			for (String line : noviGlasovi) {
				byte data[] = (line+"\n").getBytes();
				out.write(data, 0, data.length);
			}
		} catch (IOException e) {
			System.err.println(e);
		}

		resp.sendRedirect(req.getContextPath()+"/glasanje-rezultati");
	}

}
