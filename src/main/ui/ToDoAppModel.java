package ui;

import model.filters.MultipleFilters;
import model.tasks.TaskList;
import persistance.JsonLoader;
import persistance.JsonSaver;

// represents that data model for the to-do app
public class ToDoAppModel {
    public JsonLoader jsonLoader;
    public JsonSaver jsonSaver;
    public MultipleFilters multipleFilter;
    public TaskList taskList;

    // EFFECTS: creates a new ToDoAppModel with a json loader, saver, multiple filter, and task list
    public ToDoAppModel() {
        jsonSaver = new JsonSaver();
        jsonLoader = new JsonLoader();
        multipleFilter = new MultipleFilters();
        taskList = new TaskList();
    }

}