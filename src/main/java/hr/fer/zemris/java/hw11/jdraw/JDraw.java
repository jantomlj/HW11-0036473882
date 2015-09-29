package hr.fer.zemris.java.hw11.jdraw;

import hr.fer.zemris.java.hw11.jdraw.colors.JColorArea;
import hr.fer.zemris.java.hw11.jdraw.colors.JColorInfo;
import hr.fer.zemris.java.hw11.jdraw.drawing.ConcreteDrawingModel;
import hr.fer.zemris.java.hw11.jdraw.drawing.DrawingObjectListModel;
import hr.fer.zemris.java.hw11.jdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw11.jdraw.geoobj.GeometricalObject;
import hr.fer.zemris.java.hw11.jdraw.toggles.JDrawButtonGroup;
import hr.fer.zemris.java.hw11.jdraw.Actions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**Program that offers a mini version of paint program. It allows user to draw lines, circles and filled circles
 * in different colors, and modify them. Drawings can be saved and exported.
 * @author Jan Tomljanović
 */
public class JDraw extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Path openedFilePath = null;
	
	private JColorInfo colorinfo;
	private JDrawingCanvas canvas;
	private ConcreteDrawingModel model;
	@SuppressWarnings("unused")
	private JDrawButtonGroup buttons;
	private DrawingObjectListModel listmodel;
	private JList<GeometricalObject> list;
	
	//actions
	private Action open;
	private Action save;
	private Action saveAs;
	private Action exit;
	private Action export;
	
	/**Constructor of the program frame.
	 */
	public JDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);  
		setLocation(100, 100);
		this.setTitle("JDraw");
		
		initGUI();
		pack();
	}

	/**Initializes the frame look and feel
	 */
	public void initGUI () {
		getContentPane().setLayout(new BorderLayout());
		
		colorinfo = new JColorInfo();
		getContentPane().add(colorinfo, BorderLayout.PAGE_END);
		
		model = new ConcreteDrawingModel(); 
		canvas = new JDrawingCanvas(model);
		getContentPane().add(canvas, BorderLayout.CENTER);
		
		listmodel = new DrawingObjectListModel(model);   
		list = new JList<>(listmodel);
		list.setBackground(Color.gray);
		list.setForeground(Color.white);
		ListMouseListener llistener = new ListMouseListener(this);
		list.addMouseListener(llistener);
		JScrollPane l1 = new JScrollPane(list);
		l1.setPreferredSize(new Dimension(125, canvas.getPreferredSize().height));
		getContentPane().add(l1, BorderLayout.EAST);
		
		createActions ();
		
		createMenu();
		
		createToolbar();	
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit.actionPerformed(null);
			}
		});
	}

	private void createActions () {
		open = new Actions.OpenDrawingAction(model, this);
		open.putValue(Action.NAME, "Open");
		open.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O")); 
		open.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O); 
		open.putValue(Action.SHORT_DESCRIPTION, "Used to open existing file from disk.");
		
		save = new Actions.SaveDrawingAction(model, this);
		save.putValue(Action.NAME, "Save");
		save.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		save.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		save.putValue(Action.SHORT_DESCRIPTION, "Save existing file");
		
		
		saveAs = new Actions.SaveAsDrawingAction(this, model);
		saveAs.putValue(Action.NAME, "Save as");
		saveAs.putValue(Action.SHORT_DESCRIPTION, "Save file as");
		
		exit = new Actions.ExitDrawingAction(this, model);
		exit.putValue(Action.NAME, "Exit");
		exit.putValue(Action.SHORT_DESCRIPTION, "Exit program");
		
		export = new Actions.ExportDrawingAction(this, model);
		export.putValue(Action.NAME, "Export");
		export.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		export.putValue(Action.SHORT_DESCRIPTION, "Exports drawing to image");
	}
	
	private void createToolbar() {
		JToolBar bar = new JToolBar("Tools");
		bar.setPreferredSize(new Dimension(100, 30));
		bar.setFloatable(true);
		bar.add(new JColorArea("foreground", Color.BLACK, colorinfo, canvas)); 
		bar.addSeparator();
		bar.add(new JColorArea("background", Color.WHITE, colorinfo, canvas)); 
		
		JToggleButton b1 = new JToggleButton("Line");
		b1.setSelected(true);
		b1.setName("Line");
		JToggleButton b2 = new JToggleButton("Circle");
		b2.setName("Circle");
		JToggleButton b3 = new JToggleButton("Filled circle");
		b3.setName("Filled circle");

		List<JToggleButton> toggles = new LinkedList<>(Arrays.asList(b1, b2,b3) );
		buttons = new JDrawButtonGroup(toggles, canvas);
		
		bar.addSeparator();
		bar.add(b1);
		bar.add(b2);
		bar.add(b3);
		
		this.getContentPane().add(bar, BorderLayout.PAGE_START);
	}

	private void createMenu() {
		JMenuBar bar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		bar.add(fileMenu);
		JMenuItem open = new JMenuItem(this.open);
		JMenuItem save = new JMenuItem(this.save);
		JMenuItem saveAs = new JMenuItem(this.saveAs);
		JMenuItem exit = new JMenuItem(this.exit);
		JMenuItem export = new JMenuItem(this.export);
		
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(saveAs);
		fileMenu.add(export);
		fileMenu.add(exit);
		this.setJMenuBar(bar);
	}
	
	/**Sets path that is considered to be the path of the drawing that is currently dispayed
	 * @param path given path that will be set
	 */
	public void setOpenedFilePath (Path path) {
		this.openedFilePath = path;
	}
	
	/**Gets the path that is considered to be the path of the drawing that is currently dispayed
	 * @return path that is considered to be the path of the drawing that is currently dispayed
	 */
	public Path getOpenedFilePath () {
		return openedFilePath;
	}

	
	/**Main method that starts the program.
	 */
	public static void main (String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new JDraw().setVisible(true);
			}
		});
	}
}
