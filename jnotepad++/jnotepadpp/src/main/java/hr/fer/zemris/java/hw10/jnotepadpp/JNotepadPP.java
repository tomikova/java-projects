package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Program is a basic text-editing program that you can use to create 
 * new or edit existing documents.
 * Following funcionalities are built in:
 * 		creating a new blank document
 * 		opening existing document
 * 		saving document
 * 		saving-as document
 * 		close document shown it a tab
 * 		cut/copy/paste text
 * 		statistical info
 * 		included status bar
 * 		language change
 * 		change case tools
 * 		line sorting
 * Supported languages are croatian, english and german.
 * @author Tomislav
 *
 */

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Object providing localization parameters.
	 */
	private FormLocalizationProvider flp;
	
	/**
	 * Creates new document.
	 */
	private Action newDocumentAction;
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
	 * Closes tab.
	 */
	private Action closeDocumentAction;
	/**
	 * Exits application.
	 */
	private Action exitAction;
	/**
	 * Cuts selected text.
	 */
	private Action cutAction;
	/**
	 * Copies selected text.
	 */
	private Action copyAction;
	/**
	 * Pastes selected text.
	 */
	private Action pasteAction;
	/**
	 * Provides statictical information of selected document.
	 */
	private Action infoAction;
	/**
	 * Changes case to upper case.
	 */
	private Action toUpperCaseAction;
	/**
	 * Change case to lower case.
	 */
	private Action toLowerCaseAction;
	/**
	 * Inverts case.
	 */
	private Action invertCaseAction;
	/**
	 * Sorts selected lines ascending order.
	 */
	private Action ascendingSortAction;
	/**
	 * Sorts selected lines descending order.
	 */
	private Action descendingSortAction;
	/**
	 * Leaves only unique lines in selected lines.
	 */
	private Action uniqueLineAction;	

	/**
	 * Icon used to indicate that document is not modified.
	 */
	private ImageIcon icon_green = new ImageIcon("images/icon_green.png");
	/**
	 * Icon used to indicate that document is modified.
	 */
	private ImageIcon icon_red = new ImageIcon("images/icon_red.png");

	/**
	 * Pane holding application tabs.
	 */
	private JTabbedPane tabbedPane;
	/**
	 * List of objects with additional information related to opened tabs.
	 */
	private List<TabEntry> entries = new ArrayList<>();
	/**
	 * List of objects linked to modified tabs.
	 */
	private List<TabEntry> modifications = new ArrayList<>();

	/**
	 * Counter for new documents.
	 */
	private int newTabCount = 1;

	/**
	 * Label displaying total number of characters.
	 */
	private JLabel statusLabel;
	/**
	 * Label displaying current line.
	 */
	private JLabel lnLabel;
	/**
	 * Label displaying current column.
	 */
	private JLabel colLabel;
	/**
	 * Label displaying number of selected characters.
	 */
	private JLabel selLabel;

	/**
	 * Length label text.
	 */
	private final String statusLabelText = "length";
	/**
	 * Line label text.
	 */
	private final String lnLabelText = "Ln";
	/**
	 * Column label text.
	 */
	private final String colLabelText = "Col";
	/**
	 * Selection label text.
	 */
	private final String selLabelText = "Sel";

	/**
	 * List of MenuItem objects which are enabled or disabled depending if there is text selection.
	 */
	private List<JMenuItem> menuItems = new ArrayList<>();
	
	/**
	 * Component displaying clock.
	 */
	private ClockComponent clock;

	/**
	 * Default constructor without parameters.
	 */
	public JNotepadPP() {
		this.flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(800, 600);
		setTitle("JNotepad++");
		setLocationRelativeTo(null);
		initGUI();
	}

	/**
	 * Method initalizes program GUI. 
	 * Sets program title, creates actions, menus, toolbars and status bar.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();
		tabbedPane.addChangeListener(new ChangeListener() {
			/**
			 * Set program title to currently selected document path.
			 */
			public void stateChanged(ChangeEvent e) {
				int selectedIndex = tabbedPane.getSelectedIndex();
				if (selectedIndex != -1) {				
					JViewport viewport = ((JScrollPane)(((JTabbedPane)e.getSource()).getSelectedComponent())).getViewport();
					JTextArea area = (JTextArea)viewport.getView();
					updateStatus(area);				
					String path = entries.get(selectedIndex).path == null ? 
							entries.get(selectedIndex).fileName : entries.get(selectedIndex).path.toString(); 
							setTitle(path+"  JNotepad++");
				}
				else {
					setTitle("JNotepad++");
				}
			}
		});
		getContentPane().add(tabbedPane);
		createActions();
		setActions();
		createMenus();
		createToolBars();
		createStatusBar();
	}

	/**
	 * Method sets action descriptions, and binds keyboard keys to execute actions.
	 */
	private void setActions() {

		newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);

		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F4);

		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);

		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);

		infoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		infoAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
	}

	/**
	 * Method creates program File, Edit, Tools and Language menus and its
	 * submenues and actions.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new LJMenu("file", flp);
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.add(new JMenuItem(infoAction));
		fileMenu.add(new JMenuItem(exitAction));

		JMenu editMenu = new LJMenu("edit", flp);
		menuBar.add(editMenu);
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));

		JMenu toolsMenu = new LJMenu("tools", flp);
		menuBar.add(toolsMenu);
		JMenuItem changeCase = new LJMenu("change_case", flp);
		JMenuItem toUpperCase = new JMenuItem(toUpperCaseAction);
		JMenuItem toLowerCase = new JMenuItem(toLowerCaseAction);
		JMenuItem invertCase = new JMenuItem(invertCaseAction);
		changeCase.add(toUpperCase);		
		changeCase.add(toLowerCase);
		changeCase.add(invertCase);

		JMenuItem sort = new LJMenu("sort", flp);
		JMenuItem ascendingSort = new JMenuItem(ascendingSortAction);
		JMenuItem descendingSort = new JMenuItem(descendingSortAction);
		JMenuItem unique = new JMenuItem(uniqueLineAction);
		sort.add(ascendingSort);
		sort.add(descendingSort);
		sort.add(unique);

		toolsMenu.add(changeCase);
		toolsMenu.add(sort);
		
		menuItems.add(toUpperCase);
		menuItems.add(toLowerCase);
		menuItems.add(invertCase);
		menuItems.add(ascendingSort);
		menuItems.add(descendingSort);
		menuItems.add(unique);
		
		for (JMenuItem item : menuItems) {
			item.setEnabled(false);
		}

		JMenu languageMenu = new LJMenu("language", flp);
		menuBar.add(languageMenu);
		JMenuItem itemEn = new LJMenuItem("english", flp);
		itemEn.addActionListener(new ActionListener() {	
			/**
			 * Sets language to english.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		});

		JMenuItem itemHr = new LJMenuItem("croatian", flp);
		itemHr.addActionListener(new ActionListener() {	
			/**
			 * Sets language to croatian.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");		
			}
		});

		JMenuItem itemDe = new LJMenuItem("german", flp);
		itemDe.addActionListener(new ActionListener() {	
			/**
			 * Sets language to german.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");			
			}
		});

		languageMenu.add(itemEn);
		languageMenu.add(itemHr);
		languageMenu.add(itemDe);

		this.setJMenuBar(menuBar);
	}

	/**
	 * Method creates programs floatable toolbar and registers actions to its components.
	 */
	private void createToolBars() {
		JToolBar toolbar = new JToolBar("Tools");
		toolbar.setFloatable(true);
		toolbar.add(new JButton(newDocumentAction));
		toolbar.add(new JButton(openDocumentAction));
		toolbar.add(new JButton(saveDocumentAction));
		toolbar.add(new JButton(saveAsDocumentAction));
		toolbar.add(new JButton(closeDocumentAction));
		toolbar.addSeparator();
		toolbar.add(new JButton(cutAction));
		toolbar.add(new JButton(copyAction));
		toolbar.add(new JButton(pasteAction));
		toolbar.addSeparator();
		toolbar.add(new JButton(infoAction));

		getContentPane().add(toolbar, BorderLayout.PAGE_START);
	}

	/**
	 * Method creates status bar for displaying number of document characters, 
	 * current line and column, number of selected characters and a clock.
	 */
	private void createStatusBar() {

		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(getWidth(), 20));
		statusPanel.setLayout(new GridLayout(1,2));

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
		statusLabel = new JLabel(statusLabelText+" : 0");
		statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));
		lnLabel = new JLabel(lnLabelText+" : 1");
		lnLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
		colLabel = new JLabel(colLabelText+" : 1");
		colLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
		selLabel = new JLabel(selLabelText+" : 0");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lnLabel.setHorizontalAlignment(SwingConstants.LEFT);
		colLabel.setHorizontalAlignment(SwingConstants.LEFT);
		selLabel.setHorizontalAlignment(SwingConstants.LEFT);
		labelPanel.add(statusLabel);
		labelPanel.add(lnLabel);
		labelPanel.add(colLabel);
		labelPanel.add(selLabel);
		statusPanel.add(labelPanel);
		labelPanel.setPreferredSize(new Dimension(getWidth(), 20));

		JLabel timeLabel = new JLabel();
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		clock = new ClockComponent(timeLabel);
		statusPanel.add(timeLabel);	

		this.addWindowListener(new WindowAdapter() {
			/**
			 * Close all opened tabs. If there are modified documents ask
			 * to save file and perform chosen action.
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				closeDocument();
			}
			/**
			 * Stops the clock when window is closed.
			 */
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				clock.stop();
			}
		});
	}

	/**
	 * Method creates program actions. All actions are derived from LocalizableAction
	 * class which enables them to dynamically update its parameters on language change.
	 */
	private void createActions() {

		newDocumentAction = new LocalizableAction("new", flp) {

			private static final long serialVersionUID = 1L;
			
			/**
			 * Creates new document in new tab.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea editor = new JTextArea();
				editor.addCaretListener(getCaretListener());
				editor.addMouseMotionListener(getMouseMotionListener());
				editor.getDocument().addDocumentListener(getDocumentListener());
				entries.add(new TabEntry(null, "new "+ newTabCount));
				tabbedPane.addTab("new "+ newTabCount, icon_green, new JScrollPane(editor));
				TabComponent tabComponent = new TabComponent(tabbedPane, JNotepadPP.this);
				tabComponent.setIcon(icon_green);
				tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, tabComponent);
				newTabCount++;
			}
		};

		openDocumentAction = new LocalizableAction("open", flp) {

			private static final long serialVersionUID = 1L;

			/**
			 * Opens existing document in new tab if document is readable.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Open file");
				if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				Path file = fc.getSelectedFile().toPath();
				if (!Files.isReadable(file)) {
					JOptionPane.showMessageDialog(JNotepadPP.this,
							"Chosen file (" + file + ") is not readable",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					byte[] bytes = Files.readAllBytes(file);
					JTextArea editor = new JTextArea();
					editor.addCaretListener(getCaretListener());
					editor.addMouseMotionListener(getMouseMotionListener());
					editor.setText(new String(bytes, StandardCharsets.UTF_8));
					editor.getDocument().addDocumentListener(getDocumentListener());
					entries.add(new TabEntry(file, file.getFileName().toString()));
					tabbedPane.addTab(file.getFileName().toString(), icon_green, new JScrollPane(editor));
					TabComponent tabComponent = new TabComponent(tabbedPane, JNotepadPP.this);
					tabComponent.setIcon(icon_green);
					tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, tabComponent);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(
							JNotepadPP.this,
							"Error while reading file (" + file + "):"
									+ e1.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		};		

		saveDocumentAction = new LocalizableAction("save", flp) {

			private static final long serialVersionUID = 1L;

			/**
			 * Save currently selected document.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = tabbedPane.getSelectedIndex();
				if (selectedIndex != -1) {
					Path openedFilePath = entries.get(selectedIndex).path;
					saveDocument(openedFilePath);
				}
			}
		};

		/**
		 * Save currently selected docuent under new name.
		 */
		saveAsDocumentAction = new LocalizableAction("save_as", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				saveDocument(null);
			}
		};

		/**
		 * Closes selected tab. If document is modified save option is 
		 * provided before close.
		 */
		closeDocumentAction = new LocalizableAction("close", flp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = tabbedPane.getSelectedIndex();
				closeDocument(selectedIndex);
				if (entries.isEmpty()) {
					for (JMenuItem item : menuItems) {
						item.setEnabled(false);
					}
				}
			}
		};

		exitAction = new LocalizableAction("exit", flp) {

			private static final long serialVersionUID = 1L;
			
			/**
			 * Close all opened tabs and exits application.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				closeDocument();
			}
		};

		cutAction = new LocalizableAction("cut", flp) {

			private static final long serialVersionUID = 1L;

			/**
			 * Cuts selected text in document.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				cutOrCopy(true);
			}
		};

		copyAction = new LocalizableAction("copy", flp) {

			private static final long serialVersionUID = 1L;

			/**
			 * Copies selected text in document.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				cutOrCopy(false);
			}
		};

		pasteAction = new LocalizableAction("paste", flp) {

			private static final long serialVersionUID = 1L;

			/**
			 * Pastes selected text into document.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				int componentIndex = tabbedPane.getSelectedIndex()+1;
				if (componentIndex == 0) {
					return;
				}
				if (!(tabbedPane.getComponent(componentIndex) instanceof JScrollPane)) {
					componentIndex--;
				}		
				JViewport viewport = ((JScrollPane)tabbedPane.getComponent(componentIndex)).getViewport();			
				JTextArea editor = (JTextArea)viewport.getView();
				Document doc = editor.getDocument();
				int insertPosition = editor.getCaret().getDot();
				try {
					String data = (String) Toolkit.getDefaultToolkit()
							.getSystemClipboard().getData(DataFlavor.stringFlavor);
					doc.insertString(insertPosition, data, null);
				} catch (HeadlessException | UnsupportedFlavorException
						| IOException | BadLocationException ignorable) {
				} 		
			}
		};

		infoAction = new LocalizableAction("info", flp) {

			private static final long serialVersionUID = 1L;

			/**
			 * Displays statisctcal information of currently selected document.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = tabbedPane.getSelectedIndex();
				int componentIndex = selectedIndex+1;
				if (componentIndex == 0) {
					return;
				}
				if (!(tabbedPane.getComponent(componentIndex) instanceof JScrollPane)) {
					componentIndex--;
				}
				TabEntry entry = entries.get(selectedIndex);
				JViewport viewport = ((JScrollPane)tabbedPane.getComponent(componentIndex)).getViewport();			
				JTextArea editor = (JTextArea)viewport.getView();
				Document doc = editor.getDocument();
				try {
					String documentString = doc.getText(0, doc.getLength());
					int total = documentString.length();
					int lines = editor.getLineCount();
					Matcher m = Pattern.compile("\\s").matcher(documentString);
					int blank = 0;
					while (m.find()) {
						blank++;
					}
					JOptionPane.showMessageDialog(JNotepadPP.this,
							"Document: "+entry.fileName
							+"\nCharacters: "+total
							+"\nNon-blank characters: "+(total-blank)
							+"\nLines: "+lines, "Statistics",
							JOptionPane.INFORMATION_MESSAGE);			
				} catch (BadLocationException e1) {
					JOptionPane.showMessageDialog(JNotepadPP.this,
							"Error occured):" + e1.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};


		toUpperCaseAction = new LocalizableAction("toUpperCase", flp) {

			private static final long serialVersionUID = 1L;

			/**
			 * Converts selected text to upper case.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				super.actionPerformed(e);
				changeCase(CaseType.UPPER);
			}
		};

		toLowerCaseAction = new LocalizableAction("toLowerCase", flp) {

			private static final long serialVersionUID = 1L;

			/**
			 * Converts selected text to lower case.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				super.actionPerformed(e);
				changeCase(CaseType.LOWER);
			}
		};

		invertCaseAction = new LocalizableAction("invertCase", flp) {

			private static final long serialVersionUID = 1L;

			/**
			 * Inverts case on selected text.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				super.actionPerformed(e);
				changeCase(CaseType.INVERT);
			}
		};
		
		ascendingSortAction = new LocalizableAction("ascending", flp) {

			private static final long serialVersionUID = 1L;

			/**
			 * Sorts selected document lines ascending order.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				super.actionPerformed(e);
				sortLines(SortType.ASCENDING);
			}
		};
		
		
		descendingSortAction = new LocalizableAction("descending", flp) {

			private static final long serialVersionUID = 1L;

			/**
			 * Sorts selected document lines descending order.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				super.actionPerformed(e);
				sortLines(SortType.DESCENDING);
			}
		};
		
		uniqueLineAction = new LocalizableAction("unique", flp) {

			private static final long serialVersionUID = 1L;

			/**
			 * Leaves unique lines in selected text lines.
			 * Duplicate lines are deleted.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				super.actionPerformed(e);
				sortLines(SortType.UNIQUE);
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
		int selectedIndex = tabbedPane.getSelectedIndex();
		int componentIndex = selectedIndex+1;
		if (!(tabbedPane.getComponent(componentIndex) instanceof JScrollPane)) {
			componentIndex--;
		}	
		if (selectedIndex < 0) {
			JOptionPane.showMessageDialog(JNotepadPP.this,
					"No selected documents", "Message",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}		
		if (openedFilePath == null) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Save document");
			if (fc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						"Nothing was saved", "Message",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			Path file = fc.getSelectedFile().toPath();
			if (Files.exists(file)) {
				int rez = JOptionPane
						.showConfirmDialog(
								JNotepadPP.this,
								"Chosen file ("
										+ file
										+ ") already exist. Overwrite?",
										"Warning", JOptionPane.YES_NO_OPTION,
										JOptionPane.WARNING_MESSAGE);
				if (rez != JOptionPane.YES_OPTION) {
					return;
				}
			}			
			openedFilePath = file;
		}
		try {
			JViewport viewport = ((JScrollPane)tabbedPane.getComponent(componentIndex)).getViewport();			
			JTextArea editor = (JTextArea)viewport.getView();
			Files.write(openedFilePath,
					editor.getText().getBytes(StandardCharsets.UTF_8));

			entries.get(selectedIndex).path = openedFilePath;
			tabbedPane.setTitleAt(selectedIndex, openedFilePath.getFileName().toString());
			if (modifications.contains(entries.get(selectedIndex))) {
				modifications.remove(entries.get(selectedIndex));
				((TabComponent)tabbedPane.getTabComponentAt(selectedIndex)).setIcon(icon_green);
			}

		} catch (IOException e1) {
			JOptionPane.showMessageDialog(JNotepadPP.this,
					"Error while saving file (" + openedFilePath
					+ "):" + e1.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Method used to close all opened documents and exit application.
	 * Used by Exit action and other components that can close application.
	 */
	private void closeDocument() {
		while(!modifications.isEmpty()) {
			int sizeBefore = modifications.size();
			TabEntry entry = modifications.get(0);
			tabbedPane.setSelectedIndex(entries.indexOf(entry));
			closeDocument(entry);
			if (sizeBefore == modifications.size()) {
				return;
			}
		}
		if (modifications.isEmpty()) {
			JNotepadPP.this.dispose();
		}
	}

	/**
	 * Method closes tab/document of provided index.
	 * @param index Index of tab/document.
	 */
	public void closeDocument(int index) {
		if (index != -1) {
			closeDocument(entries.get(index));
		}
	}

	/**
	 * Method closes tab/document determinated by TabEntry object.
	 * @param entry TabEntry object linked to opened tabs.
	 */
	private  void closeDocument(TabEntry entry) {
		int index = entries.indexOf(entry);
		if (modifications.contains(entry)){
			int option = JOptionPane.showConfirmDialog(JNotepadPP.this,
					"File ("+entry.fileName+") was modified. Save? ",
					"Warning",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				saveDocument(entry.path);
			}
			else if (option == JOptionPane.CANCEL_OPTION) {
				return;
			}
			else if (option == JOptionPane.NO_OPTION) {
				tabbedPane.remove(index);
				modifications.remove(entry);
				entries.remove(entry);
				return;
			}
			else {
				return;
			}
			if (modifications.contains(entry)) {
				return;
			}	 
		}
		tabbedPane.remove(index);
		entries.remove(entry);
	}

	/**
	 * Method used for cutting or copying selected document text.
	 * Used by Cut and Copy actions.
	 * @param action Flag true if action is cut, false if action is copy.
	 */
	private void cutOrCopy(boolean action) {
		int componentIndex = tabbedPane.getSelectedIndex()+1;
		if (componentIndex == 0) {
			return;
		}
		if (!(tabbedPane.getComponent(componentIndex) instanceof JScrollPane)) {
			componentIndex--;
		}		
		JViewport viewport = ((JScrollPane)tabbedPane.getComponent(componentIndex)).getViewport();			
		JTextArea editor = (JTextArea)viewport.getView();
		Document doc = editor.getDocument();
		int begin = Math.min(editor.getCaret().getDot(), editor
				.getCaret().getMark());
		int length = Math.max(editor.getCaret().getDot(), editor
				.getCaret().getMark()) - begin;				    	
		try {
			StringSelection selection = new StringSelection(doc.getText(begin, length));
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);
			if (action) {
				doc.remove(begin, length);
			}
		} catch (BadLocationException ignorable) {
		}
	}

	/**
	 * Method updates status bar depending on caret position
	 * and enables/disables menu items depending if some text is selected or not.
	 * @param area
	 */
	private void updateStatus(JTextArea area) {
		Document doc = area.getDocument();
		String documentString;
		try {
			documentString = doc.getText(0, doc.getLength());
			int total = documentString.length();
			int caretLine = area.getLineOfOffset(area.getCaret().getDot());
			int caretColumn = area.getCaret().getDot() - area.getLineStartOffset(caretLine);
			int begin = Math.min(area.getCaret().getDot(), area.getCaret().getMark());
			int length = Math.max(area.getCaret().getDot(), area.getCaret().getMark()) - begin;
			statusLabel.setText(statusLabelText+" : "+total);
			lnLabel.setText(lnLabelText+" : "+(caretLine+1));
			colLabel.setText(colLabelText+" : "+(caretColumn+1));
			selLabel.setText(selLabelText+" : "+length);
			if (length == 0) {
				for (JMenuItem item : menuItems) {
					item.setEnabled(false);
				}
			}
			else {
				for (JMenuItem item : menuItems) {
					item.setEnabled(true);
				}
			}
		} catch (BadLocationException ignorable) {
		}
	}

	/**
	 * Method used to change case of selected text. Used by toLowerCase, toUpperCase
	 * and invertCase actions.
	 * @param caseType Enumeration indicating which action will be executed.
	 */
	private void changeCase(CaseType caseType) {
		int componentIndex = tabbedPane.getSelectedIndex()+1;
		if (componentIndex == 0) {
			return;
		}
		if (!(tabbedPane.getComponent(componentIndex) instanceof JScrollPane)) {
			componentIndex--;
		}		
		JViewport viewport = ((JScrollPane)tabbedPane.getComponent(componentIndex)).getViewport();			
		JTextArea editor = (JTextArea)viewport.getView();
		Document doc = editor.getDocument();
		int begin = Math.min(editor.getCaret().getDot(), editor
				.getCaret().getMark());
		int length = Math.max(editor.getCaret().getDot(), editor
				.getCaret().getMark())
				- begin;

		try {
			String text = doc.getText(begin, length);

			char[] characters = text.toCharArray();

			if (caseType == CaseType.LOWER) {
				for (int i = 0; i < characters.length; i++) {
					char c = characters[i];
					characters[i] = Character.toLowerCase(c);
				}
			}
			else if (caseType == CaseType.UPPER) {
				for (int i = 0; i < characters.length; i++) {
					char c = characters[i];
					characters[i] = Character.toUpperCase(c);
				}
			}
			else {
				for (int i = 0; i < characters.length; i++) {
					char c = characters[i];
					if (Character.isLowerCase(c)) {
						characters[i] = Character.toUpperCase(c);
					} 
					else if (Character.isUpperCase(c)) {
						characters[i] = Character.toLowerCase(c);
					}
				}
			}
			text = new String(characters);	
			doc.remove(begin, length);
			doc.insertString(begin, text, null);
		} catch (BadLocationException ignorable) {
		}
	}
	
	/**
	 * Method used to sort selected text. Used by ascendingSort, descendingSort
	 * and unique actions.
	 * @param sortType Enumeration indicating which operation will be executed.
	 */
	private void sortLines(SortType sortType) {
		int componentIndex = tabbedPane.getSelectedIndex()+1;
		if (componentIndex == 0) {
			return;
		}
		if (!(tabbedPane.getComponent(componentIndex) instanceof JScrollPane)) {
			componentIndex--;
		}		
		JViewport viewport = ((JScrollPane)tabbedPane.getComponent(componentIndex)).getViewport();			
		JTextArea editor = (JTextArea)viewport.getView();
		Document doc = editor.getDocument();
		int begin = Math.min(editor.getCaret().getDot(), editor
				.getCaret().getMark());
		int length = Math.max(editor.getCaret().getDot(), editor
				.getCaret().getMark())
				- begin;
		try {
			int startLine = editor.getLineOfOffset(begin);
			int endLine = editor.getLineOfOffset(begin+length);
			
			String[] lineStrings = new String[endLine-startLine+1];		
			boolean addNewLine = false;
			for (int i = startLine, count = 0; i <= endLine; i++, count++) {
				int beginOffset = editor.getLineStartOffset(i);
				int offset = editor.getLineEndOffset(i) - beginOffset;
				String lineString = doc.getText(beginOffset, offset);
				if (i == endLine && !lineString.contains("\n")) {
					lineString += "\n";
					addNewLine = true;
				}
				lineStrings[count] = lineString;
			}			
			if (sortType == SortType.ASCENDING) {
				Arrays.sort(lineStrings);
			}
			else if (sortType == SortType.DESCENDING) {
				Arrays.sort(lineStrings, Collections.reverseOrder());
			}
			else {
				String[] unique = new LinkedHashSet<String>(Arrays.asList(lineStrings)).toArray(new String[0]);
				doc.remove(begin, length);
				String uniqueLines = "";
				for(int i = 0; i < unique.length; i++) {
					String toAppend = unique[i];
					if (i == unique.length-1 && addNewLine) {
						toAppend = toAppend.substring(0, toAppend.length()-1);
					}
					uniqueLines += toAppend;
				}
				doc.insertString(begin, uniqueLines, null);
				return;
			}
			for (int i = startLine, count = 0; i <= endLine; i++, count++) {
				int beginOffset = editor.getLineStartOffset(i);
				int offset = editor.getLineEndOffset(i) - beginOffset;
				doc.remove(beginOffset, offset);
				String lineString = lineStrings[count];
				if (i == endLine && addNewLine) {
					lineString = lineString.substring(0, lineString.length()-1);
				}
				doc.insertString(beginOffset, lineString, null);
			}		
		} catch (BadLocationException ignorable) {
		}
		
	}

	/**
	 * Method return document listener which are asigned to JTextArea components
	 * indicating when document text has changed.
	 * @return New document listener.
	 */
	private DocumentListener getDocumentListener() {
		return new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				registerModification();	
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				registerModification();				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				registerModification();			
			}

			/**
			 * When document is changed method adds TabEntry component which
			 * are linked to modified document to list of modified documents.
			 */
			private void registerModification() {
				int selectedIndex = tabbedPane.getSelectedIndex();
				if (!modifications.contains(entries.get(selectedIndex))) {
					modifications.add(entries.get(selectedIndex));
					((TabComponent)tabbedPane.getTabComponentAt(selectedIndex)).setIcon(icon_red);
				}
			}
		};
	}
	
	/**
	 * Method returns new caret listener.
	 * @return New caret listener.
	 */
	private CaretListener getCaretListener() {
		return new CaretListener() {	
			/**
			 * When caret is moved update status bar to new caret position.
			 */
			@Override
			public void caretUpdate(CaretEvent e) {
				JTextArea area = (JTextArea)e.getSource();
				updateStatus(area);
			}
		};
	}

	/**
	 * Method returns new mouse listener.
	 * @return New mouse listener.
	 */
	private MouseMotionListener getMouseMotionListener() { 
		return new MouseMotionAdapter() {
			/**
			 * Method updating status bar when mouse is pressed down
			 * and dragged.
			 */
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				JTextArea area = (JTextArea)e.getSource();
				updateStatus(area);
			}
		};
	}

	/**
	 * Class which holds document path and file name. Related to 
	 * documents opened in tabs and used instead of indexes which can be modified in time.
	 * @author Tomislav
	 *
	 */
	private static class TabEntry{

		/**
		 * Document path. Null id its new file.
		 */
		private Path path;
		/**
		 * Document name.
		 */
		private String fileName;

		/**
		 * Default constructor with two parameters.
		 * @param path Doument path.
		 * @param fileName Document name.
		 */
		public TabEntry(Path path, String fileName) {
			this.path = path;
			this.fileName = fileName;
		}
	}

	/**
	 * Method called at start of program.
	 * @param args Command line arguments, not used.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
}
