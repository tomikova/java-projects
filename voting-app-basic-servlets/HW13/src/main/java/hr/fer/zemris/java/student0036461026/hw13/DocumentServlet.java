package hr.fer.zemris.java.student0036461026.hw13;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import  java.io.*;  

import  org.apache.poi.hssf.usermodel.HSSFSheet;  
import  org.apache.poi.hssf.usermodel.HSSFWorkbook; 
import  org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Servlet class that accepts two range numbers and number of document sheets.
 * Creates xls file with powers table of numbers between given two range numbers
 * where sheet number is power that is used.
 * First parametar is a (must be between -100 and 100)
 * Second parameter is b (must be between -100 and 100)
 * Third parameter is n - number of sheets (must be between 1 and 5) 
 * @author Tomislav
 *
 */
@WebServlet(urlPatterns={"/powers"})
public class DocumentServlet extends HttpServlet {

	/**
	 * Serial version
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
		Integer n = null;
		String message = null;
		
		try{
			startFrom = Integer.valueOf(req.getParameter("a"));
		}catch (Exception ex) {
			message = "Parametar \"a\" must be integer";
			req.setAttribute("message", message);
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		try{
			endAt = Integer.valueOf(req.getParameter("b"));
		}catch (Exception ex) {
			message = "Parametar \"b\" must be integer";
			req.setAttribute("message", message);
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		try{
			n = Integer.valueOf(req.getParameter("n"));
		}catch (Exception ex) {
			message = "Parametar \"n\" must be integer";
			req.setAttribute("message", message);
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		if (startFrom < -100 || startFrom > 100) {
			message = "Parametar \"a\" must be between -100 and 100";
			req.setAttribute("message", message);
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		if (endAt < -100 || endAt > 100) {
			message = "Parametar \"b\" must be between -100 and 100";
			req.setAttribute("message", message);
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		if (n < 1 || n > 5) {
			message = "Parametar \"n\" must be between 1 and 5";
			req.setAttribute("message", message);
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		if (startFrom > endAt) {
			Integer tmp = startFrom;
			startFrom = endAt;
			endAt = tmp;
		}
		
		HSSFWorkbook hwb=new HSSFWorkbook();
		for (int i = 1; i<= n; i++) {
			HSSFSheet sheet =  hwb.createSheet("sheet"+i);
			HSSFRow rowhead = sheet.createRow(0);
			rowhead.createCell(0).setCellValue("Number");
			rowhead.createCell(1).setCellValue("Power");
			int rowCount = 1;
			for (int j = startFrom; j <= endAt; j++ ) {
				HSSFRow row=   sheet.createRow(rowCount);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
				rowCount++;
			}
		}
		
		resp.setContentType("application/vnd.ms-excel");
		OutputStream os = resp.getOutputStream();
		hwb.write(os);
		os.flush();
		os.close();
		hwb.close();
	}
}