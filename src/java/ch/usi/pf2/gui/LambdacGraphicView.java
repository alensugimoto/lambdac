package ch.usi.pf2.gui;

import ch.usi.pf2.model.LambdacModel;
import ch.usi.pf2.model.LambdacModelListener;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;

import javax.swing.JFrame;


/**
 * The main frame of the Function Plotter application.
 * The "GUI".
 * The "GUI" knows the "model", it depends on the "model",
 * and it cannot exist without the "model".
 * The "model" of a PlotterFrame is a Plot.
 */
public final class LambdacGraphicView extends JFrame {

    /**
     * Create a new LambdaTextEditorFrame for the given LambdaTextEditor.
     * @param textEditor the model to show
     */
    public LambdacGraphicView(final LambdacModel model) {
        super();

        setFileName(model.getFileName());
        setLayout(new BorderLayout());
        add(new ButtonsPanel(model), BorderLayout.NORTH);
        add(new TextAreasPanel(model), BorderLayout.CENTER);

        model.addPropertyChangeListener(new LambdacModelListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(LambdacModel.FILE_NAME_PROPERTY)) {
                    setFileName(evt.getNewValue().toString());
                }
            }
            
        });

        pack();
    }

    public void display() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                setVisible(true);
            }
        });
    }

    private void setFileName(final String fileName) {
        final String displayedFileName = fileName == null ? "Untitled" : fileName;
        setTitle(LambdacModel.NAME + " - " + displayedFileName);
    }
    
}
