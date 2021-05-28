package ch.usi.pf2.gui;

import ch.usi.pf2.model.LambdaFile;
import ch.usi.pf2.model.LambdaFileListener;

import javax.swing.JLabel;

public final class FileInfoLabel extends JLabel {

    public FileInfoLabel(final LambdaFile file) {
        super();
        setText(file.getName());

        // register listeners
        file.addLambdaFileListener(new LambdaFileListener() {
            @Override
            public void fileNameChanged(final String fileName) {
                setText(fileName);
            }
        });
    }
    
}
