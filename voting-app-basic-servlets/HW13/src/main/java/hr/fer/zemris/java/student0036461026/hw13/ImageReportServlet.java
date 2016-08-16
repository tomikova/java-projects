package hr.fer.zemris.java.student0036461026.hw13;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Servlet is generating image of pie chart based on os usage data.
 * @author Tomislav
 *
 */
@WebServlet(urlPatterns={"/generateImage"})
public class ImageReportServlet extends HttpServlet {

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
		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset, "");
		BufferedImage bufferedImage = chart.createBufferedImage(600,600);
		resp.setContentType("image/png");
		OutputStream out = resp.getOutputStream();
		ImageIO.write(bufferedImage, "png", out);
		out.flush();
		out.close();
	}

	/**
	 * Method is creating pie chart dataset.
	 * @return Pie chart dataset.
	 */
	private  PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 27);
		result.setValue("Mac", 18);
		result.setValue("Windows", 50);
		result.setValue("Other", 5);
		return result;   
	}

	/**
	 * Method is creating pie chart.
	 * @param dataset Dataset used for pie chart.
	 * @param title Title of pie chart.
	 * @return Created pie chart.
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title,
				dataset,                
				true,                   
				true,
				false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}
}

