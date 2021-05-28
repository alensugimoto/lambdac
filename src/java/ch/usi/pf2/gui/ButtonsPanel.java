package ch.usi.pf2.gui;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import java.io.IOException;

import ch.usi.pf2.model.LambdaTextEditor;
import ch.usi.pf2.model.command.EditHistory;
import ch.usi.pf2.model.command.InterpretCommand;
import ch.usi.pf2.model.command.OpenFileCommand;
import ch.usi.pf2.model.command.SaveFileCommand;

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
            new OpenFileCommand(textEditor.getFile()).execute();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void save() {
        try {
            new SaveFileCommand(textEditor.getFile()).execute();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void run() {
        new InterpretCommand(textEditor.getFile().getText()).execute();
    }

    private void quit() {
        System.exit(0); // TODO 
    }

}
