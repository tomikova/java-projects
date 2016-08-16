package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * Custom component that is used for adding in application tabs.
 * It consists of image indicating if document is modified, label displaying
 * document name and button component used to close tab when clicked.
 * @author Tomislav
 *
 */

public class TabComponent extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Application pane which holds application tabs.
	 */
	private final JTabbedPane pane;
	/**
	 * Label used for this component which will display document name.
	 */
	private final JLabel label;
	/**
	 * Reference to main application that will close this component tab when clicked.
	 */
	private final JNotepadPP notepad;
	
	/**
	 * Constructor with two parameters.
	 * @param pane Application pane which holds application tabs.
	 * @param notepad Reference to main application.
	 */
	public TabComponent(JTabbedPane pane, JNotepadPP notepad) {
		
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        this.notepad = notepad;
		this.pane = pane;
		setOpaque(false);
		
		
		this.label = new JLabel() {
			private static final long serialVersionUID = 1L;
			public String getText() {
                int i = pane.indexOfTabComponent(TabComponent.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };
        add(label);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        JButton button = new TabButton();
        add(button);
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	}
	
	/**
	 * Sets image to component label.
	 * @param icon Desired label image.
	 */
	public void setIcon(ImageIcon icon) {
		label.setIcon(icon);
	}
	
	@Override
	public void updateUI() {
	}
	
	/**
	 * Button component used to close this component tab.
	 * @author Tomislav
	 *
	 */
	private class TabButton extends JButton implements ActionListener {
		
		private static final long serialVersionUID = 1L;

		/**
		 * Default constructor.
		 */
		public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("close this tab");
            setUI(new BasicButtonUI());
            setContentAreaFilled(false);
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            addActionListener(this);
        }
		
		/**
		 * Closes this tab when clicked.
		 */
        public void actionPerformed(ActionEvent e) {
            int index = pane.indexOfTabComponent(TabComponent.this);
            notepad.closeDocument(index);
        }
        
        @Override
        public void updateUI() {
        }
        
        /**
         * Method for drawing button image.
         */
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
			ImageIcon icon_close = new ImageIcon("images/icon_close.png");
            g2.drawImage(icon_close.getImage(),0,0,this);
            g2.finalize();
        }
    }
	
	/**
	 * Mouse listener used to indicate when mouse is over this button component.
	 */
	private final static MouseListener buttonMouseListener = new MouseAdapter() {
		/**
		 * Sets button border when mouse is over button.
		 */
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        /**
         * Removes button border when mouse is not over button.
         */
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }        
    };
}
