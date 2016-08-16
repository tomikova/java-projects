package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.geometry.Circle;
import hr.fer.zemris.java.hw12.geometry.FilledCircle;
import hr.fer.zemris.java.hw12.geometry.Line;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;

/**
 * Class used for drawing geometrical objects on screen.
 * @author Tomislav
 *
 */
public class JDrawingCanvas extends JPanel implements DrawingModelListener {
	
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Drawing model used to store geometrical objects.
	 */
	private DrawingModel model;
	/**
	 * ButtonGroup for determining which geometrical object will be drawn.
	 */
	private ButtonGroup buttonGroup;
	/**
	 * Background color provider.
	 */
	private IColorProvider bgColorProvider;
	/**
	 * Foreground color provider.
	 */
	private IColorProvider fgColorProvider;
	/**
	 * Point where first mouse click occured.
	 */
	private Point firstClickPoint = null;
	/**
	 * Point where second mouse click occured.
	 */
	private Point secondClickPoint = null;
	/**
	 * Used to identify which geometrical object will be drawn.
	 */
	private String action = null;
	
	/**
	 * Default component constructor.
	 * @param model Drawing model used to store geometrical objects.
	 * @param buttonGroup ButtonGroup for determining which geometrical object will be drawn.
	 * @param bgColorProvider Background color provider.
	 * @param fgColorProvider Foreground color provider.
	 */
	public JDrawingCanvas(DrawingModel model, ButtonGroup buttonGroup, 
			IColorProvider bgColorProvider, IColorProvider fgColorProvider) {
		this.model = model;
		this.buttonGroup = buttonGroup;
		this.bgColorProvider = bgColorProvider;
		this.fgColorProvider = fgColorProvider;
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (firstClickPoint == null) {
					ButtonModel buttonModel = JDrawingCanvas.this.buttonGroup.getSelection();
					if (buttonModel != null) {
						JDrawingCanvas.this.action = buttonModel.getActionCommand();
						firstClickPoint = e.getPoint();
					}
				}
				else {
					if (action.equals("Line")) {
						Line line = new Line(firstClickPoint, e.getPoint(), 
								JDrawingCanvas.this.fgColorProvider.getCurrentColor());
						JDrawingCanvas.this.model.add(line);
					}
					else if (action.equals("Circle")) {
						int radius = (int)Math.sqrt(Math.pow(firstClickPoint.x-e.getX(),2)+
								Math.pow(firstClickPoint.y-e.getY(),2));
						Circle circle = new Circle(firstClickPoint, 
								radius, JDrawingCanvas.this.fgColorProvider.getCurrentColor());
						JDrawingCanvas.this.model.add(circle);
					}
					else if (action.equals("FilledCircle")) {
						int radius = (int)Math.sqrt(Math.pow(firstClickPoint.x-e.getX(),2)+
								Math.pow(firstClickPoint.y-e.getY(),2));
						FilledCircle filledCircle = new FilledCircle(firstClickPoint, radius,
								JDrawingCanvas.this.fgColorProvider.getCurrentColor(),
								JDrawingCanvas.this.bgColorProvider.getCurrentColor());
						JDrawingCanvas.this.model.add(filledCircle);
					}
					JDrawingCanvas.this.firstClickPoint = null;
					JDrawingCanvas.this.secondClickPoint = null;
					JDrawingCanvas.this.action = null;
				}
			}
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				if (firstClickPoint != null) {
					secondClickPoint = e.getPoint();
					JDrawingCanvas.this.repaint();
				}
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		model = source;
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		model = source;
		repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		model = source;
		repaint();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).draw(g2d);
		}
		
		if (action != null) {
			if (action.equals("Line")) {
				g2d.setColor(fgColorProvider.getCurrentColor());
				g2d.drawLine(firstClickPoint.x, firstClickPoint.y, secondClickPoint.x, secondClickPoint.y);
			}
			else if (action.equals("Circle")) {
				int radius = (int)Math.sqrt(Math.pow(firstClickPoint.x-secondClickPoint.x,2)+
						Math.pow(firstClickPoint.y-secondClickPoint.y,2));
				g2d.setColor(fgColorProvider.getCurrentColor());
				g2d.drawOval(firstClickPoint.x-radius, firstClickPoint.y-radius, 
						2*radius, 2*radius);
			}
			else if (action.equals("FilledCircle")) {
				int radius = (int)Math.sqrt(Math.pow(firstClickPoint.x-secondClickPoint.x,2)+
						Math.pow(firstClickPoint.y-secondClickPoint.y,2));
				g2d.setColor(bgColorProvider.getCurrentColor());
				g2d.fillOval(firstClickPoint.x-radius, firstClickPoint.y-radius, 
						2*radius, 2*radius);
				g2d.setColor(fgColorProvider.getCurrentColor());
				g2d.drawOval(firstClickPoint.x-radius, firstClickPoint.y-radius, 
						2*radius, 2*radius);
			}
		}
	}
}
