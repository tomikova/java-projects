package hr.fer.zemris.java.student0036461026.hw14.glasanje;

import hr.fer.zemris.java.student0036461026.hw14.dao.DAOProvider;
import hr.fer.zemris.java.student0036461026.hw14.model.AnketaOpcija;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet čija je zadaća stvaranje xls dokumenta s rezultatima glasanja za opcije ankete.
 * @author Tomislav
 *
 */
@WebServlet(urlPatterns={"/glasanje-xls"})
public class GlasanjeXLSServlet extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Metoda stvara xls dokument s rezultatima glasanja za opcije ankete.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();

		@SuppressWarnings("unchecked")
		List<AnketaOpcija> list = (List<AnketaOpcija>) session.getAttribute("opcije");
		if (list == null) {
			Long pollID = null;
			try{
				pollID = Long.valueOf(req.getParameter("pollID"));
			}catch (Exception ex) {
				return;
			}
			list = DAOProvider.getDao().dohvatiSveOpcijeAnkete(pollID, "votesCount");
			session.setAttribute("opcije", list);
		}


		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("rezultati");
		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("ID");
		rowhead.createCell(1).setCellValue("Naziv");
		rowhead.createCell(2).setCellValue("Glasovi");
		for (int i = 0; i < list.size(); i++) {
			HSSFRow row =   sheet.createRow(i+1);
			row.createCell(0).setCellValue(list.get(i).getId());
			row.createCell(1).setCellValue(list.get(i).getName());
			row.createCell(2).setCellValue(list.get(i).getBrGlasova());
		}

		resp.setContentType("application/vnd.ms-excel");
		OutputStream os = resp.getOutputStream();
		hwb.write(os);
		os.flush();
		os.close();
		hwb.close();
	}
}
