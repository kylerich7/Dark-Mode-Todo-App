package model.filters;

import model.tasks.Task;

// represents a filter with a status for tasks with a status
public class StatusFilter implements Filter {
    public boolean status;

    // EFFECTS: creates a new StatusFilter with a desired status
    public StatusFilter(boolean status) {
        this.status = status;
    }

    // EFFECTS: returns true if task status == this status, else returns false
    @Override
    public boolean satisfies(Task task) {
        return task.getCompletionStatus() == status;
    }
}
