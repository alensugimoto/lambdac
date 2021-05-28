package ch.usi.pf2.model.command;

import java.util.ArrayList;

public class EditHistory extends ArrayList<Edit> {

    private int pointer;

    public EditHistory() {
        super();
        pointer = -1;
    }

    @Override
    public boolean add(final Edit edit) {
        assert pointer >= -1 && pointer < size();
        removeRange(++pointer, size());
        return super.add(edit);
    }

    public void redo() {
        incrementPointer();
        final Edit edit = getPointedEdit();
        if (edit != null) {
            edit.reexecute();
        }
    }

    public void undo() {
        final Edit edit = getPointedEdit();
        if (edit != null) {
            edit.unexecute();
        }
        decrementPointer();
    }

    private void incrementPointer() {
        assert pointer >= -1 && pointer < size();
        if (pointer + 1 < size()) {
            pointer++;
        }
    }

    private void decrementPointer() {
        assert pointer >= -1 && pointer < size();
        if (pointer - 1 >= -1) {
            pointer--;
        }
    }

    private Edit getPointedEdit() {
        assert pointer >= -1 && pointer < size();
        if (pointer == -1) {
            return null;
        } else {
            return get(pointer);
        }
    }
    
}
