package model.filters;

import model.tasks.Task;

// represents a filter interface for task filters
public interface Filter {

    //EFFECTS: produce true if the task satisfies this condition, false otherwise
    boolean satisfies(Task task);
}