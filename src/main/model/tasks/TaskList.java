package model.tasks;

import model.exceptions.TaskAlreadyExistsException;
import model.exceptions.TaskDoesntExistException;
import model.filters.Filter;
import model.filters.TrueFilter;
import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Savable;

import java.util.ArrayList;
import java.util.HashMap;

// represents a list of tasks
public class TaskList implements Savable {
    private final HashMap<String, Task> tasks;

    // EFFECTS: creates a new TaskList with no tasks
    public TaskList() {
        tasks = new HashMap<>();
    }

    // EFFECTS: returns number of tasks in this.tasks
    public int size() {
        return tasks.size();
    }

    // EFFECTS: returns number of tasks in this.tasks with given filter property
    public int sizeWithFilter(Filter filter) {
        return this.filterBy(filter).size();
    }

    // REQUIRES: task description to not exist within the tasks key set
    // MODIFIES: this
    // EFFECTS: adds task to this.tasks with task description as key
    // THROWS: TaskAlreadyExistsException if the task name already exists within the taskList
    public void addTask(Task task) {
        if (containsTask(task.getDescription())) {
            throw new TaskAlreadyExistsException();
        }
        tasks.put(task.getDescription(), task);
    }

    // REQUIRES: task to exist within the task list
    // MODIFIES: this
    // EFFECTS: removes task mapped to description key from this.tasks
    // THROWS: TaskDoesntExistException if the task does not exist within the taskList
    public void deleteTask(String description) {
        throwExceptionIfTaskDoesntExist(description);
        tasks.remove(description);
    }

    // REQUIRES: task to exist within the task list
    // MODIFIES: this
    // EFFECTS: removes task from this.tasks
    // THROWS: TaskDoesntExistException if the task does not exist within the taskList
    public void deleteTask(Task task) {
        deleteTask(task.getDescription());
    }

    // REQUIRES: task to exist within the task list
    // MODIFIES: task mapped to description key
    // EFFECTS: marks task mapped to description key as complete (sets completion to true)
    // THROWS: TaskDoesntExistException if the task does not exist within the taskList
    public void completeTask(String description) {
        throwExceptionIfTaskDoesntExist(description);
        getTask(description).markComplete();
    }

    // REQUIRES: task to exist within the task list
    // MODIFIES: task
    // EFFECTS: marks provided task as complete
    // THROWS: TaskDoesntExistException if the task does not exist within the taskList
    public void completeTask(Task task) {
        completeTask(task.getDescription());
    }

    // THROWS: TaskDoesntExistException if the task does not exist within the taskList
    private void throwExceptionIfTaskDoesntExist(String description) {
        if (!containsTask(description)) {
            throw new TaskDoesntExistException();
        }
    }

    // EFFECTS: returns true if task exists within taskList
    public boolean containsTask(String description) {
        return tasks.containsKey(description);
    }

    // EFFECTS: returns true if task exists within taskList
    public boolean containsTask(Task task) {
        return containsTask(task.getDescription());
    }

    // EFFECTS: returns ArrayList<Task> of all tasks that satisfy given filter
    //          returns empty ArrayList if no tasks satisfy filter
    public ArrayList<Task> filterBy(Filter filter) {
        ArrayList<Task> filteredList = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (filter.satisfies(task)) {
                filteredList.add(task);
            }
        }
        return filteredList;
    }

    /////////////////////////////////////////////  GETTERS /////////////////////////////////////////////////////////////

    // EFFECTS: returns task mapped to description key
    // THROWS: TaskDoesntExistException if the task does not exist within the taskList
    public Task getTask(String description) {
        return tasks.get(description);
    }

    // EFFECTS: returns ArrayList<Task> of all tasks
    public ArrayList<Task> getAllTasks() {
        Filter allFilter = new TrueFilter();
        return filterBy(allFilter);
    }

    ////////////////////////////////////////////////////  JSON  ////////////////////////////////////////////////////////

    // EFFECTS: returns this as a JSONObject
    @Override
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.put("tasks", tasksToJson(getAllTasks()));
        return json;
    }

    // EFFECTS: returns tasks in this taskList as a JSON array
    private JSONArray tasksToJson(ArrayList<Task> tasks) {
        JSONArray jsonArray = new JSONArray();
        for (Task task : tasks) {
            jsonArray.put(task.toJsonObject());
        }
        return jsonArray;
    }
}
