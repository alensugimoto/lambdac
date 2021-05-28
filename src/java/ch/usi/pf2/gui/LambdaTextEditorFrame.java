package ch.usi.pf2.gui;

import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.usi.pf2.model.LambdaTextEditor;


/**
 * The main frame of the Function Plotter application.
 * The "GUI".
 * The "GUI" knows the "model", it depends on the "model",
 * and it cannot exist without the "model".
 * The "model" of a PlotterFrame is a Plot.
 */
public final class LambdaTextEditorFrame extends JFrame {

    /**
     * Create a new LambdaTextEditorFrame for the given LambdaTextEditor.
     * @param textEditor the model to show
     */
    public LambdaTextEditorFrame(final LambdaTextEditor textEditor) {
        super();
        setTitle(LambdaTextEditor.NAME);
        setLayout(new BorderLayout());
        //setMenuBar(new LambdaMenuBar(textEditor.getFile()));
        
        final JPanel headerPane = new JPanel();
        headerPane.setLayout(new BoxLayout(headerPane, BoxLayout.LINE_AXIS));
        headerPane.add(new FileInfoLabel(textEditor.getFile()));
        headerPane.add(Box.createHorizontalGlue());
        headerPane.add(new ButtonsPanel(textEditor));
        add(headerPane, BorderLayout.NORTH);
        
        final JPanel textAreaPane = new JPanel();
        textAreaPane.setLayout(new BorderLayout());
        textAreaPane.add(new DefinitionsArea(textEditor.getFile().getText()), BorderLayout.CENTER);
        textAreaPane.add(new InteractionsArea(textEditor), BorderLayout.PAGE_END);
        add(textAreaPane, BorderLayout.CENTER);
        
        pack();
    }
    
}
