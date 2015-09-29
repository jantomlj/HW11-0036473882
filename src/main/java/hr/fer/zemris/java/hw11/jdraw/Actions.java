package hr.fer.zemris.java.hw11.jdraw;

import hr.fer.zemris.java.hw11.jdraw.drawing.ConcreteDrawingModel;
import hr.fer.zemris.java.hw11.jdraw.geoobj.Circle;
import hr.fer.zemris.java.hw11.jdraw.geoobj.FilledCircle;
import hr.fer.zemris.java.hw11.jdraw.geoobj.GeoObjectFactory;
import hr.fer.zemris.java.hw11.jdraw.geoobj.GeometricalObject;
import hr.fer.zemris.java.hw11.jdraw.geoobj.Line;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;


/**Class containing actions that are listed in file menu
 * 
 * @author Jan Tomljanović
 */
public class Actions {

	/**Class that implements action that opens document from disk
	 */
	public static class OpenDrawingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		private ConcreteDrawingModel model;
		private JDraw source;

		public OpenDrawingAction(ConcreteDrawingModel model, JDraw source) {
			if (model == null || source == null) {
				throw new IllegalArgumentException();
			}
			this.model = model;
			this.source = source;
		}


		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if (fc.showOpenDialog(source) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(source, "File " + fileName.getAbsolutePath() + " doesn't exist", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			byte[] okteti;
			try {
				okteti = Files.readAllBytes(filePath);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(source, "Error while reading file " + fileName.getAbsolutePath() + ".",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String text = new String(okteti, StandardCharsets.UTF_8);
			String[] lines = text.trim().split("\n");
			List<GeometricalObject> foundObjects = new LinkedList<>();
			try {
				foundObjects = parseIntoObjects(lines);
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(source, "File " + fileName.getAbsolutePath()
						+ " is not readable in this program", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			source.setOpenedFilePath(filePath);
			model.clear();
			int i = 0;
			for (GeometricalObject obj : foundObjects) {
				obj.setName(obj.getName() + " #" + i);
				model.add(obj);
				i++;
			}
		}


		private List<GeometricalObject> parseIntoObjects(String[] lines) {
			if (lines == null) {
				throw new IllegalArgumentException();
			}
			List<GeometricalObject> obj = new LinkedList<>();
			for (String line : lines) {
				int index = line.indexOf(" ");
				if (index == -1) {
					throw new IllegalArgumentException();
				}
				String name = line.substring(0, index);
				try {
					obj.add(GeoObjectFactory.fromText(name, line.substring(index + 1)));
				} catch (IllegalArgumentException ex) {
					throw new IllegalArgumentException();
				}
			}
			return obj;
		}
	}

	/**Action that saves current state of the drawing to disk
	 */
	public static class SaveDrawingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private ConcreteDrawingModel model;
		private JDraw source;

		public SaveDrawingAction(ConcreteDrawingModel model, JDraw source) {
			if (model == null || source == null) {
				throw new IllegalArgumentException();
			}
			this.model = model;
			this.source = source;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Path opened = source.getOpenedFilePath();
			if (opened != null) {
				carrieOutSave(source, model);
			} else {
				new SaveAsDrawingAction(source, model).actionPerformed(e);
			}
		}


	}

	/**Action that saves current state of the drawing to disk. Always asks where to save.
	 */
	public static class SaveAsDrawingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private JDraw source;
		private ConcreteDrawingModel model;

		public SaveAsDrawingAction(JDraw source, ConcreteDrawingModel model) {
			if (source == null || model == null) {
				throw new IllegalArgumentException();
			}
			this.source = source;
			this.model = model;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Path savePath = source.getOpenedFilePath();
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document");
			if (jfc.showSaveDialog(source) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(source, "Saving stopped", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			Path openedFilePath = jfc.getSelectedFile().toPath();
			source.setOpenedFilePath(openedFilePath);
			if (!carrieOutSave(source, model)) {
				source.setOpenedFilePath(savePath);
			}
		}

	}


	private static boolean carrieOutSave(JDraw source, ConcreteDrawingModel model) {
		Path opened = source.getOpenedFilePath();
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(
					new FileOutputStream(opened.toFile())), StandardCharsets.UTF_8));
			for (int i = 0, end = model.getSize(); i < end; i++) {
				writer.write(model.getObject(i).toText() + "\n");
			}
			writer.close();
			model.setSaved(true);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(source, "Error while saving file " + opened.toFile().getAbsolutePath() + ".",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}


	/**Action that exits {@link JDraw} program.
	 */
	public static class ExitDrawingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private JDraw source;
		private ConcreteDrawingModel model;

		public ExitDrawingAction(JDraw source, ConcreteDrawingModel model) {
			if (source == null || model == null) {
				throw new IllegalArgumentException();
			}
			this.source = source;
			this.model = model;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model.isSaved()) {
				source.dispose();
				return;
			}
			int ans = JOptionPane.showConfirmDialog(source, "Do you want to save your drawing?", "File not saved",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (ans == JOptionPane.NO_OPTION) {
				source.dispose();
				return;
			}
			if (ans == JOptionPane.YES_OPTION) {
				new SaveDrawingAction(model, source).actionPerformed(e);
				;
				source.dispose();
				return;
			}
		}
	}

	/**Action that exports current drawing as an jpg, png or gif image
	 */
	public static class ExportDrawingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private JDraw source;
		private ConcreteDrawingModel model;

		public ExportDrawingAction(JDraw source, ConcreteDrawingModel model) {
			if (source == null || model == null) {
				throw new IllegalArgumentException();
			}
			this.source = source;
			this.model = model;
		}


		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfk = new JFileChooser();
			jfk.addChoosableFileFilter(new JpgFilter());
			jfk.addChoosableFileFilter(new PngFilter());
			jfk.addChoosableFileFilter(new GifFilter());
			
				
			if (jfk.showSaveDialog(source) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						source,
						"Exporting stopped", 
						"Warning", 
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			File f = jfk.getSelectedFile();
			
			
			int[] dimensions = getBoundingBox(model); // x1 y1 x2 y2;
			int box_width = dimensions[2] - dimensions[0];
			int box_height = dimensions[3] - dimensions[1];
			BufferedImage image = new BufferedImage(box_width, box_height, BufferedImage.TYPE_3BYTE_BGR	);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, box_width, box_height); // setting white background
			
			ConcreteDrawingModel newModel = makeNewModel(dimensions[0], dimensions[1], model);
			for (int i = 0, end = newModel.getSize(); i < end; i++) {
				newModel.getObject(i).paintYourself(g);
			}
			g.dispose();
			
			String path = f.toString();
			String ext = path.substring(path.lastIndexOf('.') + 1);
			
			if (!ext.equals("jpg") && !ext.equals("png") && !ext.equals("gif")) {
				JOptionPane.showMessageDialog(
						source,
						"Invalid extension selected - jpg, png and gif allowed", 
						"Error", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				ImageIO.write(image, ext, f);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(
						source,
						"Error occured", 
						"Error", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(source, "Drawing has been exported");
		}

		private ConcreteDrawingModel makeNewModel(int x, int y, ConcreteDrawingModel model) {
			ConcreteDrawingModel ret = new ConcreteDrawingModel();
			for (int i = 0, end = model.getSize(); i < end; i++) {
				GeometricalObject obj = model.getObject(i);
				GeometricalObject movedObject = null;
				if (obj instanceof Line) {
					Line l = (Line) obj;
					movedObject = new Line("", l.getX1() - x, l.getY1() - y, l.getX2() - x,
							l.getY2() - y, l.getColor(), null);
				}
				else if (obj instanceof FilledCircle) {
					FilledCircle c = (FilledCircle) obj;
					movedObject = new FilledCircle("", c.getX() - x, c.getY() - y, c.getR(), c.getColor(), c.getBGColor());
				}
				else if (obj instanceof Circle) {
					Circle c = (Circle) obj;
					movedObject = new Circle("", c.getX() - x, c.getY() - y, c.getR(), c.getColor());
				}
				ret.add(movedObject);
			}
			return ret;
		}


		private int[] getBoundingBox(ConcreteDrawingModel model) {
			int[] dimensions = new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE };
			for (int i = 0, end = model.getSize(); i < end; i++) {
				GeometricalObject obj = model.getObject(i);
				if (obj instanceof Line) {
					adjustDimensions(dimensions, (Line) obj);
				}
				else if (obj instanceof Circle) {
					ajustDimensions(dimensions, (Circle) obj);
				}
			}
			return dimensions;
		}


		private void ajustDimensions(int[] dimensions, Circle circle) {
			int x1 = circle.getX() - (int)Math.ceil(circle.getR());
			int y1 = circle.getY() - (int)Math.ceil(circle.getR());
			int x2 = circle.getX() + (int)Math.ceil(circle.getR());
			int y2 = circle.getY() + (int)Math.ceil(circle.getR());
			adjustDimensions(dimensions, new Line("", x1, y1, x2, y2, Color.black, null));
		}


		private void adjustDimensions(int[] dimensions, Line line) {
			if (line.getX1() < dimensions[0]) {
				dimensions[0] = line.getX1();
			}
			if (line.getX2() < dimensions[0]) {
				dimensions[0] = line.getX2();
			}
			if (line.getX1() > dimensions[2]) {
				dimensions[2] = line.getX1();
			}
			if (line.getX2() > dimensions[2]) {
				dimensions[2] = line.getX2();
			}
			if (line.getY1() < dimensions[1]) {
				dimensions[1] = line.getY1();
			}
			if (line.getY2() < dimensions[1]) {
				dimensions[1] = line.getY2();
			}
			if (line.getY1() > dimensions[3]) {
				dimensions[3] = line.getY1();
			}
			if (line.getY2() > dimensions[3]) {
				dimensions[3] = line.getY2();
			}
		}
		
		private static class JpgFilter extends FileFilter {
			@Override
			public String getDescription() {
				return "jpg";
			}
			
			@Override
			public boolean accept(File f) {
				String path = f.toString();
				String ext = path.substring(path.lastIndexOf('.') +1);
				return ext.equals("jpg");
			}
		}
		
		private static class PngFilter extends FileFilter {
			@Override
			public String getDescription() {
				return "png";
			}
			
			@Override
			public boolean accept(File f) {
				String path = f.toString();
				String ext = path.substring(path.lastIndexOf('.') +1);
				return ext.equals("png");
			}
		}
		
		private static class GifFilter extends FileFilter {
			@Override
			public String getDescription() {
				return "gif";
			}
			
			@Override
			public boolean accept(File f) {
				String path = f.toString();
				String ext = path.substring(path.lastIndexOf('.') +1);
				return ext.equals("gif");
			}
		}
	}
}
