package ch.usi.pf2.gui;

import ch.usi.pf2.model.LambdacModel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFrame;

/**
 * The main frame of the graphical user interface of this application.
 * 
 * @author Alen Sugimoto
 * @version 03.06.2021
 */
public final class LambdacGraphicView extends JFrame {

    /**
     * Constructs a new LambdacGraphicView for the specified model.
     * @param model the model to use
     */
    public LambdacGraphicView(final LambdacModel model) {
        super();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitleWithPath(model.getFilePath());
        setLayout(new BorderLayout());
        add(new ButtonsPane(model), BorderLayout.NORTH);
        add(new TextAreasPane(model), BorderLayout.CENTER);

        model.addPropertyChangeListener(new LambdacModelListener());

        pack();
    }

    /**
     * Asynchronously sets this frame to be visible
     * in the event dispatch thread of the system EventQueue.
     */
    public final void display() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
            }
        });
    }

    private final void setTitleWithPath(final String filePath) {
        setTitle(LambdacModel.NAME + " - " 
                 + (filePath == null ? "Untitled" : new File(filePath).getName()));
    }

    private final class LambdacModelListener implements PropertyChangeListener {

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(LambdacModel.FILE_PATH_PROPERTY)) {
                setTitleWithPath(evt.getNewValue().toString());
            }
        }

    }
    
}
