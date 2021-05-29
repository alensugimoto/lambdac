package ch.usi.pf2.gui;

import ch.usi.pf2.model.LambdacModel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
        setFileName(model.getFileName());
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
            public void run() {
                setVisible(true);
            }
        });
    }

    private final void setFileName(final String fileName) {
        final String fileNameToShow = fileName == null ? "Untitled" : fileName;
        setTitle(LambdacModel.NAME + " - " + fileNameToShow);
    }

    private final class LambdacModelListener implements PropertyChangeListener {

        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(LambdacModel.FILE_NAME_PROPERTY)) {
                setFileName(evt.getNewValue().toString());
            }
        }

    }
    
}
