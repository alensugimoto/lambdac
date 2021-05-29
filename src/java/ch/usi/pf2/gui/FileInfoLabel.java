package ch.usi.pf2.gui;

import ch.usi.pf2.model.LambdacModel;
import ch.usi.pf2.model.LambdacModelListener;

import java.beans.PropertyChangeEvent;

import javax.swing.JLabel;

public final class FileInfoLabel extends JLabel {

    public FileInfoLabel(final LambdacModel model) {
        super();
        setText(model.getFileName());

        // register listeners
        model.addPropertyChangeListener(new LambdacModelListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(LambdacModel.FILE_NAME_PROPERTY_NAME)) {
                    setText(evt.getNewValue().toString());
                }
            }
            
        });
    }
    
}
