package hr.fer.zemris.java.hw12.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import hr.fer.zemris.java.hw12.geometry.Circle;
import hr.fer.zemris.java.hw12.geometry.FilledCircle;
import hr.fer.zemris.java.hw12.geometry.GeometricalObject;
import hr.fer.zemris.java.hw12.geometry.Line;
import hr.fer.zemris.java.hw12.geometry.ObjectsGroup;

/**
 * Program for drawing geometrical objects Lines, Circles and Filled Circles.
 * Following functionalities are supported:
 * 		Drawing geometrical objects
 * 		Changing drawn geometrical object properties
 * 		Saving drawing in .jvd file format
 * 		Opening existing drawing in .jvd file format
 * 		Exporting drawing as image in .jpg, .png, and .gif image formats
 * @author Tomislav
 *
 */
public class JVDraw extends JFrame {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Component that provides background color.
	 */
	private JColorArea bgColorArea = new JColorArea(Color.RED);
	/**
	 * Component that provides foreground color.
	 */
	private JColorArea fgColorArea = new JColorArea(Color.BLUE);
	/**
	 * Mutually exclusive buttons group.
	 */
	private ButtonGroup buttonGroup;
	/**
	 * Drawing model for storing geometrical objects.
	 */
	private DrawingModel drawingModel;
	/**
	 * List displaying programs geometrical objects.
	 */
	private JList<GeometricalObject> objectList;
	/**
	 * Path to the currentl opened file.
	 */
	private Path openedFilePath = null;
	/**
	 * Indicator if current file is modified.
	 */
	private boolean modified = false;

	/**
	 * Opens existing document.
	 */
	private Action openDocumentAction;
	/**
	 * Saves opened document using existing file name.
	 */
	private Action saveDocumentAction;
	/**
	 * Saves opened document under new name.
	 */
	private Action saveAsDocumentAction;

	/**
	 * Exports drawing in image  file formats.
	 */
	private Action exportAction;
	
	/**
	 * Closes application.
	 */
	private Action exitAction;

	/**
	 * Default constructor without parameters.
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(1000, 600);
		setTitle("JVDraw");
		setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {
			
			/**
			 * Closes application. If there are modified documents ask
			 * to save file and perform chosen action.
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				exitApplication();
			}
		});
		initGUI();
	}

	/**
	 * Method initalizes program GUI. 
	 * Method creates program drawing model, menus, toolbars, status bar and actions.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());

		drawingModel = new DrawingModel() {

			/**
			 * Drawing model listeners.
			 */
			List<DrawingModelListener> drawingModelListeners = new ArrayList<>();
			/**
			 * List of geometrical objects in drawing model.
			 */
			List<GeometricalObject> geoObjects = new ArrayList<>();
			/**
			 * Geometrical object taht is object group, grouping geometrical objects
			 * in drawing model. Used to determine minimal bounding rectangle of
			 * drawing model geometrical objects.
			 */
			ObjectsGroup objGroup = new ObjectsGroup();

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void removeDrawingModelListener(DrawingModelListener l) {
				if (!drawingModelListeners.isEmpty()) {
					drawingModelListeners.remove(l);
				}
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void addDrawingModelListener(DrawingModelListener l) {
				if (!drawingModelListeners.contains(l) && l != null) {
					drawingModelListeners.add(l);
				}
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public int getSize() {
				return geoObjects.size();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public GeometricalObject getObject(int index) {
				if (index >= 0 && index < geoObjects.size()) {
					return geoObjects.get(index);
				}
				return null;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void add(GeometricalObject object) {
				if (!geoObjects.contains(object) && object != null) {
					geoObjects.add(object);
					objGroup.add(object);
					for (DrawingModelListener listener : drawingModelListeners) {
						listener.objectsAdded(this, geoObjects.size()-1, geoObjects.size()-1);
					}
					modified = true;
				}
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void change(GeometricalObject object) {
				for (DrawingModelListener listener : drawingModelListeners) {
					listener.objectsChanged(this, geoObjects.indexOf(object), geoObjects.indexOf(object));
				}
				modified = true;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void removeAllObjects() {
				geoObjects.clear();
				objGroup.removeAll();
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void add(List<GeometricalObject> objects) {
				for (GeometricalObject object : objects) {
					this.add(object);
				}
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Rectangle getBoundingRectangle() {
				return objGroup.getBoundingRectangle();
			}
		};

		createActions();
		setActions();
		createMenus();
		createToolBars();
		createStatusBar();
		createDrawnObjectsList();
		createDrawCanvas();
	}

	/**
	 * Method creates all program actions.
	 */
	private void createActions() {

		openDocumentAction = new AbstractAction() {

			/**
			 * Serial version.
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * Opens and parses existing .jvd document if document is readable.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if(modified) {
					int rez = JOptionPane
							.showConfirmDialog(
									JVDraw.this,
									"Current document is modified. Save?",
									"Warning", JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE);
					if (rez == JOptionPane.YES_OPTION) {
						saveDocument(openedFilePath);
						if(modified) {
							return;
						}
					}
				}
				JFileChooser fc = new JFileChooser();
				fc.setAcceptAllFileFilterUsed(false);
				fc.addChoosableFileFilter(new JVDrawFileFilter("jvd","JVD File Format"));
				fc.setDialogTitle("Open document");
				if (fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				Path file = fc.getSelectedFile().toPath();
				if (!Files.isReadable(file)) {
					JOptionPane.showMessageDialog(JVDraw.this,
							"Chosen file (" + file + ") is not readable",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					byte[] bytes = Files.readAllBytes(file);
					String[] objects = (new String(bytes, StandardCharsets.UTF_8)).split("\r\n|\n");
					List<GeometricalObject> geoObjects = new ArrayList<>();
					int lineNumber = 1;
					for (String obj : objects) {
						String[] params = obj.split("\\s+");
						if (params[0].equals("LINE")) {
							if (params.length != 8) {
								throw new IllegalArgumentException("Invalid number of LINE "
										+ "parameters. Line: "+lineNumber);
							}
							int startPointX = Integer.parseInt(params[1]);
							int startPointY = Integer.parseInt(params[2]);
							int endPointX = Integer.parseInt(params[3]);
							int endPointY = Integer.parseInt(params[4]);
							if (startPointX < 0 || startPointY < 0 || endPointX < 0 || endPointY < 0) {
								throw new IllegalArgumentException("LINE parameter can't "
										+ "be negative. Line: "+lineNumber);
							}
							Point startPoint = new Point(startPointX, startPointY);
							Point endPoint = new Point(endPointX, endPointY);
							Color color = new Color(Integer.parseInt(params[5]), Integer.parseInt(params[6]),
									Integer.parseInt(params[7]));
							Line line = new Line(startPoint, endPoint, color);
							geoObjects.add(line);
						}
						else if (params[0].equals("CIRCLE")) {
							if (params.length != 7) {
								throw new IllegalArgumentException("Invalid number of CIRCLE "
										+ "parameters. Line: "+lineNumber);
							}
							
							int centerX = Integer.parseInt(params[1]);
							int centerY = Integer.parseInt(params[2]);
							int radius = Integer.parseInt(params[3]);
							if (centerX < 0 || centerY < 0 || radius < 0) {
								throw new IllegalArgumentException("CIRCLE parameter can't "
										+ "be negative. Line: "+lineNumber);
							}
							Point center = new Point(centerX, centerY);
							Color color = new Color(Integer.parseInt(params[4]), Integer.parseInt(params[5]),
									Integer.parseInt(params[6]));
							Circle circle = new Circle(center, radius, color);
							geoObjects.add(circle);
						}
						else if (params[0].equals("FCIRCLE")) {
							if (params.length != 10) {
								throw new IllegalArgumentException("Invalid number of FCIRCLE parameters");
							}
							int centerX = Integer.parseInt(params[1]);
							int centerY = Integer.parseInt(params[2]);
							int radius = Integer.parseInt(params[3]);
							if (centerX < 0 || centerY < 0 || radius < 0) {
								throw new IllegalArgumentException("FCIRCLE parameter can't "
										+ "be negative. Line: "+lineNumber);
							}
							Point center = new Point(centerX, centerY);
							Color fgColor = new Color(Integer.parseInt(params[4]), Integer.parseInt(params[5]),
									Integer.parseInt(params[6]));
							Color bgColor = new Color(Integer.parseInt(params[7]), Integer.parseInt(params[8]),
									Integer.parseInt(params[9]));
							FilledCircle filledCircle = new FilledCircle(center, radius, fgColor, bgColor);
							geoObjects.add(filledCircle);
						}
						lineNumber++;
					}
					objectList.removeAll();
					drawingModel.removeAllObjects();
					drawingModel.add(geoObjects);
					openedFilePath = file;
					modified = false;

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(
							JVDraw.this,
							"Error while reading file (" + file + "): "
									+ e1.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		};

		saveDocumentAction = new AbstractAction() {

			/**
			 * Serial version.
			 */
			private static final long serialVersionUID = 1L;
			
			/**
			 * Save currently opened document.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				saveDocument(openedFilePath);
			}
		};

		saveAsDocumentAction = new AbstractAction() {

			/**
			 * Serial version.
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * Save currently opened document under new name.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				saveDocument(null);
			}
		};

		exportAction = new AbstractAction() {

			/**
			 * Serial version.
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * Export drawing as image in supported image formats.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (drawingModel.getSize() == 0) {
					JOptionPane.showMessageDialog(JVDraw.this,
							"Nothing to export", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Export image");
				fc.setAcceptAllFileFilterUsed(false);
				fc.addChoosableFileFilter(new JVDrawFileFilter("jpg","JPG Image Format"));
				fc.addChoosableFileFilter(new JVDrawFileFilter("png","PNG Image Format"));
				fc.addChoosableFileFilter(new JVDrawFileFilter("gif","GIF Image Format"));
				if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JVDraw.this,
							"Nothing was saved", "Message",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				Path imageFilePath;
				String extension = ((JVDrawFileFilter)fc.getFileFilter()).getExtension();
				if (!fc.getSelectedFile().toString().endsWith("."+extension)) {
					imageFilePath = Paths.get(fc.getSelectedFile()+"."+extension); 
				}
				else {
					imageFilePath = fc.getSelectedFile().toPath();
				}
				if (Files.exists(imageFilePath)) {
					int rez = JOptionPane
							.showConfirmDialog(
									JVDraw.this,
									"Chosen file ("
											+ fc.getSelectedFile().toPath()
											+ ") already exist. Overwrite?",
											"Warning", JOptionPane.YES_NO_OPTION,
											JOptionPane.WARNING_MESSAGE);
					if (rez != JOptionPane.YES_OPTION) {
						return;
					}
				}
				try {
					Rectangle boundRectangle = drawingModel.getBoundingRectangle();
					int leftX = (int)boundRectangle.getMinX();
					int leftY = (int)boundRectangle.getMinY();
					int shiftX = -leftX;
					int shiftY = -leftY;
					BufferedImage image = new BufferedImage(
							boundRectangle.width, boundRectangle.height, BufferedImage.TYPE_3BYTE_BGR);
					Graphics2D g2d = image.createGraphics();
					g2d.setBackground(Color.WHITE);
					g2d.fillRect(0, 0, boundRectangle.width, boundRectangle.height);
					for (int i = 0; i < drawingModel.getSize(); i++) {
						drawingModel.getObject(i).draw(g2d, shiftX, shiftY);
					}
					g2d.dispose();
					ImageIO.write(image, extension, imageFilePath.toFile());
					JOptionPane.showMessageDialog(JVDraw.this,
							"Export successful", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(JVDraw.this,
							"Error while exporting image (" + imageFilePath
							+ "):" + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		
		exitAction = new AbstractAction() {
			
			/**
			 * Serial version.
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * Exit application.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				exitApplication();
			}
		};

	}

	/**
	 * Method used by Save and SaveAs actions. Saves selected document.
	 * If file name exists user is asked to approve overwrite.
	 * When file is saved modification indicator is set to not modified.
	 * @param openedFilePath Path of the file that needs to be saved. Null if 
	 * document is new document and not opened existing one.
	 */
	private void saveDocument(Path openedFilePath) {
		if (openedFilePath == null) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Save document");
			fc.setAcceptAllFileFilterUsed(false);
			fc.addChoosableFileFilter(new JVDrawFileFilter("jvd","JVD File Format"));
			if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JVDraw.this,
						"Nothing was saved", "Message",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (Files.exists(fc.getSelectedFile().toPath())) {
				int rez = JOptionPane
						.showConfirmDialog(
								JVDraw.this,
								"Chosen file ("
										+ fc.getSelectedFile().toPath()
										+ ") already exist. Overwrite?",
										"Warning", JOptionPane.YES_NO_OPTION,
										JOptionPane.WARNING_MESSAGE);
				if (rez != JOptionPane.YES_OPTION) {
					return;
				}
			}
			if(!fc.getSelectedFile().toString().endsWith(".jvd")) {
				openedFilePath = Paths.get(fc.getSelectedFile()+".jvd"); 
			}
			else {
				openedFilePath = fc.getSelectedFile().toPath();
			}
		}
		try {
			Files.write(openedFilePath, new byte[0]);
			for(int i = 0, n = drawingModel.getSize(); i < n; i++) {
				Files.write(openedFilePath,
						(drawingModel.getObject(i).toString()+"\n").getBytes(StandardCharsets.UTF_8), 
						StandardOpenOption.APPEND);
			}
			this.openedFilePath = openedFilePath;
			modified = false;
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(JVDraw.this,
					"Error while saving file (" + openedFilePath
					+ "):" + e1.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Method used to exit application. If there is modified document user is asked
	 * to save document or continue with application closing.
	 */
	private void exitApplication() {
		if (modified) {
			int rez = JOptionPane
					.showConfirmDialog(
							JVDraw.this,
							"Current document is modified. Save?",
							"Warning", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);
			if (rez == JOptionPane.YES_OPTION) {
				saveDocument(openedFilePath);
				if(modified) {
					return;
				}
			}			
		}
		JVDraw.this.dispose();
	}

	/**
	 * Method sets action descriptions, and binds keyboard keys to execute actions.
	 */
	private void setActions() {
		openDocumentAction.putValue(Action.NAME, "Open");
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		saveDocumentAction.putValue(Action.NAME, "Save");
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		saveAsDocumentAction.putValue(Action.NAME, "Save as");
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		exportAction.putValue(Action.NAME, "Export as image");
		exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exportAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		
		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F4);
	}

	/**
	 * Method creates program File and Export menus and its
	 * submenues and actions.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(exitAction));

		JMenu exportMenu = new JMenu("Export");
		menuBar.add(exportMenu);
		exportMenu.add(new JMenuItem(exportAction));

		this.setJMenuBar(menuBar);
	}

	/**
	 * Method creates programs floatable toolbar and registers actions to its components.
	 */
	private void createToolBars() {
		JToolBar toolbar = new JToolBar("Tools");
		toolbar.setFloatable(true);
		bgColorArea.setMinimumSize(new Dimension(15,15));
		bgColorArea.setMaximumSize(new Dimension(15,15));
		fgColorArea.setMinimumSize(new Dimension(15,15));
		fgColorArea.setMaximumSize(new Dimension(15,15));
		bgColorArea.setToolTipText("Background color");
		fgColorArea.setToolTipText("Foreground color");	
		buttonGroup = new ButtonGroup();
		JToggleButton lineButton = new JToggleButton("Line");
		lineButton.setActionCommand("Line");
		JToggleButton circleButton = new JToggleButton("Circle");
		circleButton.setActionCommand("Circle");
		JToggleButton filledCircleButton = new JToggleButton("Filled circle");
		filledCircleButton.setActionCommand("FilledCircle");
		buttonGroup.add(lineButton);
		buttonGroup.add(circleButton);
		buttonGroup.add(filledCircleButton);
		toolbar.add(bgColorArea);
		toolbar.addSeparator();
		toolbar.add(fgColorArea);
		toolbar.addSeparator();
		toolbar.add(lineButton);
		toolbar.add(circleButton);
		toolbar.add(filledCircleButton);
		getContentPane().add(toolbar, BorderLayout.PAGE_START);
	}

	/**
	 * Method creates status bar for displaying currently selected
	 * background and foregroun colors.
	 */
	private void createStatusBar() {
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(getWidth(), 25));
		JColorLabel colorLabel = new JColorLabel(bgColorArea, fgColorArea);
		bgColorArea.addColorChangeListener(colorLabel);
		fgColorArea.addColorChangeListener(colorLabel);
		statusPanel.add(colorLabel);
	}

	/**
	 * Method creates list that displays all stored geometrical objects from drawing model.
	 */
	private void createDrawnObjectsList() {
		DrawingObjectListModel model = new DrawingObjectListModel(drawingModel);
		objectList = new JList<GeometricalObject>(model);
		objectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		objectList.setCellRenderer(new ListCellRenderer<GeometricalObject>() {

			/**
			 * Default list cell renderer.
			 */
			protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

			@Override
			public Component getListCellRendererComponent(
					JList<? extends GeometricalObject> list,
					GeometricalObject value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				renderer.setText(value.toString());
				return renderer;
			}
		});

		objectList.addMouseListener(new MouseAdapter() {

			/**
			 * When user double clicks on list component it is determinated
			 * which geometrical object was clicked and user is offered to change
			 * properties of that object.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JList<?> list = (JList<?>)e.getSource();
					GeometricalObject obj = (GeometricalObject) list.getSelectedValue();
					JPanel panel = new JPanel();
					if (obj instanceof Line) {
						Line line = (Line)obj;
						panel.setLayout(new GridLayout(5, 2));
						JTextField startPointX = new JTextField(String.valueOf(line.getStartPoint().x));
						JTextField startPointY = new JTextField(String.valueOf(line.getStartPoint().y));
						JTextField endPointX = new JTextField(String.valueOf(line.getEndPoint().x));
						JTextField endPointY = new JTextField(String.valueOf(line.getEndPoint().y));
						panel.add(new JLabel("Start point x: "));
						panel.add(startPointX);
						panel.add(new JLabel("Start point y: "));
						panel.add(startPointY);
						panel.add(new JLabel("End point x: "));
						panel.add(endPointX);
						panel.add(new JLabel("End point y: "));
						panel.add(endPointY);
						panel.add(new JLabel("Line color: "));
						JColorArea colorArea = new JColorArea(line.getColor());
						panel.add(colorArea);
						int res = JOptionPane.showConfirmDialog(JVDraw.this, panel, "Change line properties", 
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
						if (res == JOptionPane.OK_OPTION) {
							try {
								int startPointXInt = Integer.valueOf(startPointX.getText());
								int startPointYInt =  Integer.valueOf(startPointY.getText());
								int endPointXInt = Integer.valueOf(endPointX.getText());
								int endPointYInt =  Integer.valueOf(endPointY.getText());
								Color color = colorArea.getCurrentColor();
								if (startPointXInt < 0 || startPointYInt < 0 
										|| endPointXInt < 0 || endPointYInt < 0) {
									throw new IllegalArgumentException();
								}
								line.setStartPoint(new Point(startPointXInt, startPointYInt));
								line.setEndPoint(new Point(endPointXInt, endPointYInt));
								line.setColor(color);
								drawingModel.change(line);
							}catch (Exception ex) {
								JOptionPane.showMessageDialog(JVDraw.this,
										"Illegal point argument", "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						}			
					}
					else if (obj instanceof Circle) {
						Circle circle = (Circle)obj;
						panel.setLayout(new GridLayout(5, 2));
						JTextField centerX = new JTextField(String.valueOf(circle.getCenter().x));
						JTextField centerY = new JTextField(String.valueOf(circle.getCenter().y));
						JTextField radius = new JTextField(String.valueOf(circle.getRadius()));
						panel.add(new JLabel("Center point x: "));
						panel.add(centerX);
						panel.add(new JLabel("Center point y: "));
						panel.add(centerY);
						panel.add(new JLabel("Radius: "));
						panel.add(radius);
						panel.add(new JLabel("Outline color: "));
						JColorArea fgColorArea = new JColorArea(circle.getOutlineColor());
						panel.add(fgColorArea);
						JColorArea bgColorArea = null;
						if (circle instanceof FilledCircle) {
							panel.add(new JLabel("Fill color: "));
							bgColorArea = new JColorArea(((FilledCircle)circle).getFillColor());
							panel.add(bgColorArea);
						}
						int res = JOptionPane.showConfirmDialog(JVDraw.this, panel, 
								"Change filled circle properties", 
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
						if (res == JOptionPane.OK_OPTION) {
							try{
								int centerXInt = Integer.valueOf(centerX.getText());
								int centerYInt = Integer.valueOf(centerY.getText());
								int radiusInt = Integer.valueOf(radius.getText());
								Color fgColor = fgColorArea.getCurrentColor();
								if (centerXInt < 0 || centerYInt < 0 || radiusInt < 0) {
									throw new IllegalArgumentException();
								}
								circle.setCenter(new Point(centerXInt, centerYInt));
								circle.setRadius(radiusInt);
								circle.setOutlineColor(fgColor);
								if (circle instanceof FilledCircle) {
									Color bgColor = bgColorArea.getCurrentColor();
									((FilledCircle)circle).setFillColor(bgColor);
								}
								drawingModel.change(circle);
							} catch(Exception ex) {
								JOptionPane.showMessageDialog(JVDraw.this,
										"Illegal circle argument", "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(objectList);
		JPanel panel = new JPanel(new GridLayout());
		panel.add(scrollPane);
		add(panel, BorderLayout.EAST);
		drawingModel.addDrawingModelListener(model);
	}

	/**
	 * Method is creating main drawing component on which all geometrical objects
	 * from drawing model are drawned.
	 */
	private void createDrawCanvas() {
		JDrawingCanvas drawingCanvas = new JDrawingCanvas(drawingModel, buttonGroup, bgColorArea, fgColorArea);
		drawingCanvas.setBackground(Color.WHITE);
		add(drawingCanvas, BorderLayout.CENTER);
		drawingModel.addDrawingModelListener(drawingCanvas);
	}

	/**
	 * Method called at program start.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});

	}
}
