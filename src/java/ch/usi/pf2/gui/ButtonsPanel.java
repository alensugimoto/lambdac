package ch.usi.pf2.gui;

import ch.usi.pf2.model.LambdaTextEditor;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class ButtonsPanel extends JPanel {
    
    private final LambdaTextEditor textEditor;

    public ButtonsPanel(final LambdaTextEditor textEditor) {
        super();
        this.textEditor = textEditor;
        
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
            textEditor.getFile().setName(chooser.getSelectedFile().getName());
            open();
        });
        save.addActionListener(ev -> {
            if (textEditor.getFile() == null) {
                final JFileChooser chooser = new JFileChooser();
                chooser.showSaveDialog(null);
                textEditor.getFile().setName(chooser.getSelectedFile().getName());
            }
            save();
        });
        saveAs.addActionListener(ev -> {
            final JFileChooser chooser = new JFileChooser();
            chooser.showSaveDialog(null);
            textEditor.getFile().setName(chooser.getSelectedFile().getName());
            save();
        });
        run.addActionListener(ev -> run());
        quit.addActionListener(ev -> quit());
    }

    private void open() {
        try {
            textEditor.getFile().open();
        } catch (IOException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }

    private void save() {
        try {
            textEditor.getFile().save();
        } catch (IOException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }

    private void run() {
        textEditor.getFile().getText().interpret();
    }

    private void quit() {
        System.exit(0); // TODO 
    }

}
