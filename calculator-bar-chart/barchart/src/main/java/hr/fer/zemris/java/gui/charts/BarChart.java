package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.List;

/**
 * Class represents and draws barchart.
 * @author Tomislav
 *
 */

public class BarChart {

	/**
	 * Distance between two bars.
	 */
	private static final int XGAP = 2;
	/**
	 * Distance from border on x axis.
	 */
	private static final int XBORDER = 180;
	/**
	 * Distance from border on y axis.
	 */
	private static final int YBORDER = 150;
	/**
	 * List of values to be drawn on chart.
	 */
	private List<XYValue> values;
	/**
	 * Label text on x axis.
	 */
	private String xDescription;
	/**
	 * Label text on y axis.
	 */
	private String yDescription;
	/**
	 * Minimum displayed value on y axis.
	 */
	private int minY;
	/**
	 * Maximum displayed value on y axis.
	 */
	private int maxY;
	/**
	 * Distance between two y axis values drawn on chart.
	 */
	private int yGap;

	/**
	 * Default BartChart constructor.
	 * @param values List of values to be drawn on chart.
	 * @param xDescription Label text on x axis.
	 * @param yDescription Label text on y axis.
	 * @param minY Minimum displayed value on y axis.
	 * @param maxY Maximum displayed value on y axis.
	 * @param yGap Distance between two y axis values drawn on chart.
	 */
	public BarChart(List<XYValue> values, String xDescription, String yDescription, int minY, int maxY, int yGap) {
		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.minY = minY;
		this.maxY = maxY;
		if ((maxY-minY) % yGap != 0) {
			yGap++;
		}
		this.yGap = yGap;
	}

	/**
	 * Method draws bar chart.
	 * @param g2 Object used for drawing.
	 * @param width Draw component width.
	 * @param height Draw component height.
	 * @param vDiff Difference between frame window and draw component height.
	 */
	public void draw(Graphics2D g2, int width, int height, int vDiff)
	{
		double maxHeight = 0;
		double maxWidth = 0;

		for (XYValue value : values) {
			if(maxHeight < value.getY() && value.getY() <= maxY) {
				maxHeight = value.getY(); 
			}
			
			if (maxWidth < value.getX()) {
				maxWidth = value.getX();
			}
		}	
		maxHeight -= minY;

		int xwidth = width - (XBORDER+1);
		int yheight = height - (YBORDER+1);
		
		drawGrid(g2,width,height,(int)maxHeight,(int)maxWidth);
		drawDescription(g2,width,height);
		
		g2.setColor(new Color(244, 119, 72, 255));
		for (XYValue value : values) {
			int xleft = (int)(XBORDER/2+(xwidth) * (value.getX()-1) / maxWidth);
			int barWidth = (int)(xwidth / maxWidth);
			int barHeight = (int) Math.round(yheight *  (value.getY() < maxY ? value.getY()-minY : maxY-minY) / maxHeight);
			Rectangle bar = new Rectangle(xleft, yheight - barHeight + YBORDER/2 + vDiff, barWidth-XGAP, barHeight);
			g2.fill(bar);
			g2.draw(bar);
		}
	}
	
	
	/**
	 * Method draws bar chart grid and its labels.
	 * @param g2 Object used for drawing.
	 * @param width Draw component width.
	 * @param height Draw component height.
	 * @param maxHeight Maximum y axis bar value.
	 * @param maxWidth Maximum x axis bar value.
	 */
	public void drawGrid(Graphics2D g2, int width, int height, int maxHeight, int maxWidth) {
		
		int xwidth = width - (XBORDER+1);
		int yheight = height - (YBORDER+1);
		
		//draw arrows
		g2.setColor(Color.GRAY);
		g2.setStroke(new BasicStroke(4));
		g2.draw(new Line2D.Double(xwidth+XBORDER/1.5f, yheight+YBORDER/2+2, xwidth+XBORDER/1.5f-10, yheight+YBORDER/2-3));
		g2.draw(new Line2D.Double(xwidth+XBORDER/1.5f, yheight+YBORDER/2+2, xwidth+XBORDER/1.5f-10, yheight+YBORDER/2+7));	
		g2.draw(new Line2D.Double(XBORDER/2-2, YBORDER/3, XBORDER/2-7, YBORDER/3+10));
		g2.draw(new Line2D.Double(XBORDER/2-2, YBORDER/3, XBORDER/2+3, YBORDER/3+10));
		
		//draw vertical lines and labels
		for (int i = 0; i <= maxWidth; i++) {
			int xleft = (int)(XBORDER/2+(xwidth) * i / maxWidth) - (i== 0 ? 2 : XGAP);
			g2.setColor(Color.GRAY);
			int stroke = (i == 0 ? 4 : 1);
			g2.setStroke(new BasicStroke(stroke));
            g2.draw(new Line2D.Double(xleft, YBORDER/3, xleft, yheight + YBORDER/2));
            if (i < maxWidth) {
            	g2.setColor(Color.BLACK);
            	g2.drawString(String.valueOf(i+1), xleft + xwidth/maxWidth/2, yheight + 3*YBORDER/4);
            }
		}
		
		//draw horizontal lines and labels
		int num = (int)Math.ceil(maxHeight / (double)yGap);			
		double piece = yheight / (double)maxHeight*yGap;
		
		for(int i = 0; i <= num; i ++) {
			g2.setColor(Color.GRAY);
			int stroke = (i == 0 ? 4 : 1);
			g2.setStroke(new BasicStroke(stroke));
			g2.draw(new Line2D.Double(3*XBORDER/8, yheight+YBORDER/2+2 - i*piece, xwidth+XBORDER/1.5,  yheight+YBORDER/2+2 - i*piece));
			g2.setColor(Color.BLACK);
			g2.drawString(String.valueOf(minY + i*yGap), XBORDER/4, (int)(yheight+YBORDER/2 - i*piece));
		}
	}
	
	/**
	 * Method draws descrption labels on x and y axis.
	 * @param g2 Object used for drawing.
	 * @param width Draw component width.
	 * @param height Draw component height.
	 */
	private void drawDescription(Graphics2D g2, int width, int height) {
		
		int yheight = height - (YBORDER+1);
		
		FontMetrics fm = g2.getFontMetrics();
		int stringHorizontalWidth = fm.stringWidth(xDescription);
		g2.setColor(Color.BLACK);
		g2.drawString(String.valueOf(xDescription), width/2 - stringHorizontalWidth/2, yheight+7*YBORDER/8);
		
		int stringVerticalWidth = fm.stringWidth(yDescription);
		AffineTransform at = new AffineTransform();
		at.rotate(- Math.PI / 2);
        g2.setTransform(at);
        g2.drawString(yDescription, -height/2-stringVerticalWidth/2, XBORDER/8);
        at.rotate(Math.PI / 2);
        g2.setTransform(at);    
	}
}
