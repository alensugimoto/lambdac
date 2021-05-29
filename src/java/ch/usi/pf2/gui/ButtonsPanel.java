package ch.usi.pf2.gui;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import ch.usi.pf2.model.LambdacModel;
import ch.usi.pf2.model.parser.ParseException;

public class ButtonsPanel extends JPanel {
    
    private final LambdacModel model;

    public ButtonsPanel(final LambdacModel model) {
        super();
        this.model = model;
        
        final JButton open = new JButton("Open...");
        final JButton save = new JButton("Save");
        final JButton saveAs = new JButton("Save As...");
        final JButton run = new JButton("Run");
        final JButton quit = new JButton("Quit");
        add(open);
        add(save);
        add(saveAs);
        add(run);
        add(quit);

        // register listeners
        open.addActionListener(ev -> {
            final JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            model.setFileName(chooser.getSelectedFile().getName());
            open();
        });
        save.addActionListener(ev -> {
            if (model.getFileName() == null) {
                final JFileChooser chooser = new JFileChooser();
                chooser.showSaveDialog(null);
                model.setFileName(chooser.getSelectedFile().getName());
            }
            save();
        });
        saveAs.addActionListener(ev -> {
            final JFileChooser chooser = new JFileChooser();
            chooser.showSaveDialog(null);
            model.setFileName(chooser.getSelectedFile().getName());
            save();
        });
        run.addActionListener(ev -> run());
        quit.addActionListener(ev -> quit());
    }

    private void open() {
        try {
            model.open();
        } catch (IOException ex) {
            System.err.println("There was a problem when opening " + model.getFileName());
        }
    }

    private void save() {
        try {
            model.save();
        } catch (IOException ex) {
            System.err.println("There was a problem when saving " + model.getFileName());
        }
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

}
