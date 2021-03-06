package ch.usi.pf2.gui;

import ch.usi.pf2.model.LambdacModel;
import ch.usi.pf2.model.parser.ParseException;

import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * The ButtonsPane is part of the graphical user interface.
 * It is a custom panel that contains buttons.
 * These buttons act on files and text.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class ButtonsPane extends JPanel {

    private static final JScrollPane HELP_PANE;

    private final LambdacModel model;

    static {
        final JTextArea helpArea = new JTextArea(LambdacModel.getHelp(), 22, 62);
        helpArea.setEditable(false);
        helpArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        HELP_PANE = new JScrollPane(helpArea);
    }

    /**
     * Constructs a new ButtonsPane for the specified model.
     * @param model the model to use
     */
    public ButtonsPane(final LambdacModel model) {
        super();
        this.model = model;

        setLayout(new FlowLayout(FlowLayout.LEADING));

        final JButton open = new JButton("Open...");
        final JButton save = new JButton("Save");
        final JButton saveAs = new JButton("Save As...");
        final JButton run = new JButton("Run");
        final JButton help = new JButton("Help");
        add(open);
        add(save);
        add(saveAs);
        add(run);
        add(help);

        open.addActionListener(ev -> open());
        save.addActionListener(ev -> save());
        saveAs.addActionListener(ev -> saveAs());
        run.addActionListener(ev -> run());
        help.addActionListener(ev -> help());
    }

    private final void open() {
        selectFile("Open", this::openFile);
    }

    private final void save() {
        if (model.getFilePath() == null) {
            saveAs();
        } else {
            saveFile();
        }
    }

    private final void saveAs() {
        selectFile("Save", this::saveFile);
    }

    private final void selectFile(final String approveButtonText, final FileCallback callback) {
        final JFileChooser chooser = new JFileChooser(model.getFilePath());
        final int returnVal = chooser.showDialog(this, approveButtonText);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            model.setFilePath(chooser.getSelectedFile().getPath());
            callback.call();
        }
    }

    private final void run() {
        try {
            model.interpret();
        } catch (ParseException ex) {
            model.setInterpretedText(ex.getMessage());
        }
    }

    private final void help() {
        JOptionPane.showMessageDialog(this, HELP_PANE, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    private final void openFile() {
        try {
            model.open();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "A problem occurred opening the file.",
                                          "Failed to Open", JOptionPane.ERROR_MESSAGE);
        }
    }

    private final void saveFile() {
        try {
            model.save();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "A problem occurred saving the file.",
                                          "Failed to Save", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FunctionalInterface
    private interface FileCallback {

        public abstract void call();

    }
    
}
