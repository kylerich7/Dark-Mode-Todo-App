package model.filters;

import model.tasks.Category;
import model.tasks.Task;

// represents a filter with a category
public class CategoryFilter implements Filter {
    public final Category category;

    // MODIFIES: this
    // EFFECTS: creates a new CategoryFilter with a category
    public CategoryFilter(Category category) {
        this.category = category;
    }

    // EFFECTS: returns true if task category equals category
    @Override
    public boolean satisfies(Task task) {
        return task.getCategory().equals(category);
    }
}
