package ch.usi.pf2.gui;

import ch.usi.pf2.model.LambdacModel;
import ch.usi.pf2.model.parser.ParseException;

import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class ButtonsPanel extends JPanel {

    private final LambdacModel model;

    public ButtonsPanel(final LambdacModel model) {
        super();
        this.model = model;
        setLayout(new FlowLayout(FlowLayout.TRAILING));

        final JButton open = new JButton("Open...");
        final JButton save = new JButton("Save");
        final JButton saveAs = new JButton("Save As...");
        final JButton run = new JButton("Run");
        final JButton quit = new JButton("Quit");
        open.addActionListener(ev -> open());
        save.addActionListener(ev -> save());
        saveAs.addActionListener(ev -> saveAs());
        run.addActionListener(ev -> run());
        quit.addActionListener(ev -> quit());
        add(open);
        add(save);
        add(saveAs);
        add(run);
        add(quit);
    }

    private void open() {
        final JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        model.setFileName(chooser.getSelectedFile().getName());
        openFile();
    }

    private void save() {
        if (model.getFileName() == null) {
            final JFileChooser chooser = new JFileChooser();
            chooser.showSaveDialog(null);
            model.setFileName(chooser.getSelectedFile().getName());
        }
        saveFile();
    }

    private void saveAs() {
        final JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(null);
        model.setFileName(chooser.getSelectedFile().getName());
        saveFile();
    }

    private void run() {
        try {
            model.interpret();
        } catch (ParseException ex) {
            model.setInterpretedText(ex.getMessage());
        }
    }

    private void quit() {
        System.exit(0); // TODO 
    }

    private void openFile() {
        try {
            model.open();
        } catch (IOException ex) {
            System.err.println("There was a problem when opening " + model.getFileName());
        }
    }

    private void saveFile() {
        try {
            model.save();
        } catch (IOException ex) {
            System.err.println("There was a problem when saving " + model.getFileName());
        }
    }
    
}
