package model.filters;

import model.tasks.Task;

import java.util.ArrayList;

// represents a multipleFilter that contains multiple task filters
public class MultipleFilters implements Filter {
    private final ArrayList<Filter> filters;
    private boolean satisfiesAll;

    // MODIFIES: this
    // EFFECTS: creates a new MultipleFilter with an empty arraylist of filters
    public MultipleFilters() {
        filters = new ArrayList<>();
        satisfiesAll = true;
    }

    // MODIFIES: this
    // EFFECTS: adds a filter to the list of filters
    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    // EFFECTS: returns list of filters currently in multiple filters
    public ArrayList<Filter> getFilters() {
        return filters;
    }

    // MODIFIES: this
    // EFFECTS: removes all filters currently in filter list
    public void clearFilters() {
        filters.clear();
    }

    // EFFECTS: if satisfiesAll = true, returns true if task satisfies all filters in filters
    //          if satisfiesAll = false, returns true if task satisfies >0 filters in filters
    //          else returns false
    @Override
    public boolean satisfies(Task task) {
        if (satisfiesAll) {
            for (Filter filter : filters) {
                if (!filter.satisfies(task)) {
                    return false;
                }
            }
            return true;
        } else {
            for (Filter filter : filters) {
                if (filter.satisfies(task)) {
                    return true;
                }
            }
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: setSatisfiesAll to either true or false;
    public void setSatisfiesAll(boolean andFilter) {
        this.satisfiesAll = andFilter;
    }

    // EFFECTS: returns satisfiesAll
    public boolean getSatisfiesAll() {
        return satisfiesAll;
    }
}
