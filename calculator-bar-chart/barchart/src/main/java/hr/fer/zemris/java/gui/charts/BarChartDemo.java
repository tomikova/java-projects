package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Program draws bar chart.
 * Program expects single argument - path to the file with bar chart definition.
 * @author Tomislav
 *
 */

public class BarChartDemo {

	/**
	 * Method called at program start.
	 * @param args Command line arguments.
	 * @throws IOException In case of error while reading file.
	 * @throws IllegalArgumentException In case number of arguments is not one.
	 * In case file is not well formatted.
	 */
	public static void main(String[] args) throws IOException {
		
		if(args.length != 1) {
			throw new IllegalArgumentException("Expected one argument: file path");
		}
		
		String path = args[0];
		
		List<XYValue> values = new ArrayList<>();
		
		String[] lines = Files.readAllLines(Paths.get(path),
				StandardCharsets. UTF_8).toArray(new String[0]);
		
		if(lines.length < 6) {
			throw new IllegalArgumentException("File not well formatted");
		}
		
		String xDexcription = lines[0].trim();
		String yDescription = lines[1].trim();
		
		String[] xyValues = lines[2].split("\\s++");
		for (String value : xyValues) {
			String[] xy = value.split(",");
			values.add(new XYValue(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
		}
		int minY = Integer.parseInt(lines[3].trim());
		int maxY = Integer.parseInt(lines[4].trim());
		int yGap = Integer.parseInt(lines[5].trim());

		JFrame frame = new JFrame();

		final int FRAME_WIDTH = 400;
		final int FRAME_HEIGHT = 400;

		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("BarChart");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel label = new JLabel(path);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(label, BorderLayout.PAGE_START);

		BarChart model = new BarChart(values, xDexcription, yDescription, minY, maxY, yGap);

		BarChartComponent component = new BarChartComponent(model);
		frame.add(component, BorderLayout.CENTER);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
