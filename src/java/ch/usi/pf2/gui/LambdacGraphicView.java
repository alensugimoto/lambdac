package ch.usi.pf2.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.usi.pf2.model.LambdacModel;


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
        setTitle(LambdacModel.NAME);
        setLayout(new BorderLayout());
        //setMenuBar(new LambdaMenuBar(textEditor.getFile()));

        final JPanel headerPane = new JPanel();
        headerPane.setLayout(new BoxLayout(headerPane, BoxLayout.LINE_AXIS));
        headerPane.add(new FileInfoLabel(model));
        headerPane.add(Box.createHorizontalGlue());
        headerPane.add(new ButtonsPanel(model));
        add(headerPane, BorderLayout.NORTH);

        final JPanel textAreaPane = new JPanel();
        textAreaPane.setLayout(new BorderLayout());
        textAreaPane.add(new DefinitionsArea(model), BorderLayout.CENTER);
        textAreaPane.add(new InteractionsArea(model), BorderLayout.PAGE_END);
        add(textAreaPane, BorderLayout.CENTER);
        
        pack();
    }

    public void display() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                setVisible(true);
            }
        });
    }
    
}
