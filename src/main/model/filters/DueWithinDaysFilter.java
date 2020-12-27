package model.filters;

import model.tasks.Task;

// represents a filter with number of days until due for tasks with a due date
public class DueWithinDaysFilter implements Filter {
    public final int noOfDays;

    // MODIFIES: this
    // EFFECTS: creates new DueWithinDaysFilter with desired number of due within days
    public DueWithinDaysFilter(int days) {
        this.noOfDays = days;
    }

    // EFFECTS: returns true if days before task is due is between [0, noOfDays], else return false
    @Override
    public boolean satisfies(Task task) {
        long daysUntilDue = task.daysUntilDue();
        return daysUntilDue <= noOfDays && daysUntilDue >= 0;
    }
}
