package model.filters;

import model.tasks.Priority;
import model.tasks.Task;

// represents a priority filter with a priority
public class PriorityFilter implements Filter {
    public final Priority priority;

    // MODIFIES: this
    // EFFECTS: creates a priority filter with a priority
    public PriorityFilter(Priority priority) {
        this.priority = priority;
    }

    // EFFECTS: returns true if days before task is due is between [0, noOfDays], else return false
    @Override
    public boolean satisfies(Task task) {
        return task.getPriority().equals(priority);
    }
}
