package model.filters;

import model.tasks.Task;

// represents a filter for overdue tasks
public class OverdueFilter implements Filter {

    // EFFECTS: returns true if daysBeforeDue < 0 and task is not complete, else return false
    @Override
    public boolean satisfies(Task task) {
        long daysUntilDue = task.daysUntilDue();
        return daysUntilDue < 0 && !task.getCompletionStatus();
    }
}
