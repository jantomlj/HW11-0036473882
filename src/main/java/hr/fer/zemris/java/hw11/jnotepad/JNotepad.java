package hr.fer.zemris.java.hw11.jnotepad;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**Program that offers some basic notepad functionality. It is provided in two languages: english and croatian.
 * @author Jan Tomljanovoić
 */
public class JNotepad extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextArea editor;
	private Path openedFilePath;
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	private boolean stored;

	/**Creates a new notepad frame.
	 */
	public JNotepad() {

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);

		initGUI();
		stored = false;
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});
	}

	private void initGUI() {

		editor = new JTextArea();
		editor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				stored = false;
			}
		});

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(new JScrollPane(editor), BorderLayout.CENTER);

		createActions();
		createMenus();
		createToolbars();

	}

	private void createActions() {

		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));

		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));

		deleteSelectedPartAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));

		copySelectedPartAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		
		pasteSelectedPartAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		
		cutSelectedPartAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		
		toggleCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F3"));

		newAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
	}

	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		LJMenu fileMenu = new LJMenu("file", flp);
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(newAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));

		LJMenu editMenu = new LJMenu("edit", flp);
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(cutSelectedPartAction));
		editMenu.add(new JMenuItem(copySelectedPartAction));
		editMenu.add(new JMenuItem(pasteSelectedPartAction));
		editMenu.add(new JMenuItem(deleteSelectedPartAction));
		editMenu.add(new JMenuItem(toggleCaseAction));

		LJMenu meni = new LJMenu("languages", flp);

		JMenuItem eng = new JMenuItem(this.eng);
		JMenuItem hrv = new JMenuItem(this.hrv);

		meni.add(eng);
		meni.add(hrv);
		menuBar.add(meni);

		this.setJMenuBar(menuBar);
	}

	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(deleteSelectedPartAction));
		toolBar.add(new JButton(toggleCaseAction));

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	private Action eng = new LocalizableAction("eng", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		};
	};
	private Action hrv = new LocalizableAction("hrv", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};


	private Action openDocumentAction = new LocalizableAction("open", flp) {

		private static final long serialVersionUID = 1L;

		private String open_dialog_title;
		private String message11;
		private String message12;
		private String error;
		private String errorMessage;


		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(open_dialog_title);
			if (fc.showOpenDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JNotepad.this, message11 + fileName.getAbsolutePath() + message12, error,
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			byte[] okteti;
			try {
				okteti = Files.readAllBytes(filePath);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(JNotepad.this, errorMessage + fileName.getAbsolutePath() + ".", error,
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String tekst = new String(okteti, StandardCharsets.UTF_8);
			editor.setText(tekst);
			openedFilePath = filePath;
		}

		protected void updateMessages() {
			open_dialog_title = flp.getString("open_dialog_title");
			message11 = flp.getString("message11");
			message12 = flp.getString("message12");
			error = flp.getString("error");
			errorMessage = flp.getString("errorMessage");
		}

		@Override
		protected void update() {
			updateName();
			updateMessages();
		}
	};

	private Action saveDocumentAction = new LocalizableAction("save", flp) {

		private static final long serialVersionUID = 1L;

		private String save_doc;
		private String nothing_saved;
		private String warning;
		private String save_error_message;
		private String error;
		private String info;
		private String saved;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (openedFilePath == null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle(save_doc);
				if (jfc.showSaveDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JNotepad.this, nothing_saved, warning, JOptionPane.WARNING_MESSAGE);
					return;
				}
				openedFilePath = jfc.getSelectedFile().toPath();
			}
			byte[] podatci = editor.getText().getBytes(StandardCharsets.UTF_8);
			try {
				Files.write(openedFilePath, podatci);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JNotepad.this, save_error_message + " "
						+ openedFilePath.toFile().getAbsolutePath(), error, JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(JNotepad.this, saved, info, JOptionPane.INFORMATION_MESSAGE);
			stored = true;
		}

		protected void updateMessages() {
			save_doc = flp.getString("save_doc");
			nothing_saved = flp.getString("nothing_saved");
			warning = flp.getString("warning");
			save_error_message = flp.getString("save_error_message");
			error = flp.getString("error");
			info = flp.getString("info");
			saved = flp.getString("saved");
		}

		@Override
		protected void update() {
			updateName();
			updateMessages();
		}
	};

	private Action saveAsDocumentAction = new LocalizableAction("saveas", flp) {

		private static final long serialVersionUID = 1L;

		private String save_doc;
		private String nothing_saved;
		private String warning;
		private String save_error_message;
		private String error;
		private String info;
		private String saved;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle(save_doc);
			if (jfc.showSaveDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JNotepad.this, nothing_saved, warning, JOptionPane.WARNING_MESSAGE);
				return;
			}
			openedFilePath = jfc.getSelectedFile().toPath();
			byte[] podatci = editor.getText().getBytes(StandardCharsets.UTF_8);
			try {
				Files.write(openedFilePath, podatci);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JNotepad.this, save_error_message + " "
						+ openedFilePath.toFile().getAbsolutePath(), error, JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(JNotepad.this, saved, info, JOptionPane.INFORMATION_MESSAGE);
			stored = true;
		}

		protected void updateMessages() {
			save_doc = flp.getString("save_doc");
			nothing_saved = flp.getString("nothing_saved");
			warning = flp.getString("warning");
			save_error_message = flp.getString("save_error_message");
			error = flp.getString("error");
			info = flp.getString("info");
			saved = flp.getString("saved");
		}

		@Override
		protected void update() {
			updateName();
			updateMessages();
		}
	};

	private Action deleteSelectedPartAction = new LocalizableAction("delete", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			if (len == 0)
				return;
			int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			try {
				doc.remove(offset, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	private Action copySelectedPartAction = new LocalizableAction("copy", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			editor.copy();
		}
	};
	
	private Action pasteSelectedPartAction = new LocalizableAction("paste", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			editor.paste();
		}
	};
	
	private Action cutSelectedPartAction = new LocalizableAction("cut", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			editor.cut();
		}
	};


	private Action toggleCaseAction = new LocalizableAction("toggle_case", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			int offset = 0;
			if (len != 0) {
				offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			} else {
				len = doc.getLength();
			}
			try {
				String text = doc.getText(offset, len);
				text = changeCase(text);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}

		private String changeCase(String text) {
			char[] znakovi = text.toCharArray();
			for (int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				if (Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if (Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
			return new String(znakovi);
		}
	};
	
	private Action newAction = new LocalizableAction("new", flp) {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			editor.setText("");
			openedFilePath = null;
		}
	};

	private Action exitAction = new LocalizableAction("exit", flp) {

		private static final long serialVersionUID = 1L;

		private String exit_message;
		private String exit_title;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (stored) {
				JNotepad.this.dispose();
			} else {
				int res = JOptionPane.showConfirmDialog(JNotepad.this, exit_message, exit_title,
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					saveDocumentAction.actionPerformed(null);
					JNotepad.this.dispose();
				}
				else if (res == JOptionPane.NO_OPTION) {
					JNotepad.this.dispose();
				}
			}
		}
		
		protected void updateMessages () {
			exit_message = flp.getString("exit_message");
			exit_title = flp.getString("exit_title");
		}
		
		@Override
		protected void update() {
			updateName();
			updateMessages();
		}
	};

	/**Main method that starts the program
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new JNotepad().setVisible(true);
			}
		});
	}

}