package model.filters;

import model.tasks.Task;

// represents a filter that will always produce true, no matter the attributes of a task
public class TrueFilter implements Filter {

    // EFFECTS: always returns true
    @Override
    public boolean satisfies(Task task) {
        return true;
    }
}
