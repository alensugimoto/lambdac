package ch.usi.pf2.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@FunctionalInterface
public interface LambdacModelListener extends PropertyChangeListener {
    
    @Override
    public void propertyChange(final PropertyChangeEvent evt);

}
