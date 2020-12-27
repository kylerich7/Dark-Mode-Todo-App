package model.tasks;

import model.exceptions.InvalidDateException;
import org.json.JSONObject;
import persistance.Savable;

import java.time.LocalDate;

import static java.lang.Long.compare;
import static java.time.temporal.ChronoUnit.DAYS;

// represents a task with a due date, description, priority, category, and status (either complete or not complete)
public class Task implements Comparable<Task>, Savable {
    protected final String taskDueDate;
    protected final String taskDescription;
    private final Priority taskPriority;
    private final Category taskCategory;
    protected Boolean taskCompletion;

    // REQUIRES: date to be real date in the form of YYYY-MM-DD
    // MODIFIES: this
    // EFFECTS: creates an instance of task with a date, description, priority, category. completion is false.
    // THROWS: InvalidDateException if date is not an actual date or if date is in wrong format
    public Task(String dueDate, String description, Priority priority, Category category) {
        try {
            LocalDate.parse(dueDate);
        } catch (Exception e) {
            throw new InvalidDateException();
        }
        this.taskDueDate = dueDate;
        this.taskDescription = description;
        this.taskPriority = priority;
        this.taskCategory = category;
        this.taskCompletion = false;
    }

    // EFFECTS: returns number of days from now (today) until the task is due
    //          negative number if due date has passed
    //          0 if due date is today
    public long daysUntilDue() {
        LocalDate dueDate = LocalDate.parse(taskDueDate);
        return DAYS.between(LocalDate.now(), dueDate);
    }

    // EFFECTS: compares this with task; returns integer 1, 0, or -1 for sorting.
    //          returns 1 if this is complete and task is not
    //                  1 if tasks have same status, and this has more days until due than task
    //                  0 if tasks have same status, and same days until due
    //                  else returns -1
    public int compareTo(Task task) {
        int compare = getCompletionStatus().compareTo(task.getCompletionStatus());
        if (compare == 0) {
            compare = compare(daysUntilDue(), task.daysUntilDue());
        }
        return compare;
    }

    /////////////////////////////////////////////  GETTERS and SETTERS  ////////////////////////////////////////////////

    // EFFECTS: returns taskDueDate
    public String getDueDate() {
        return taskDueDate;
    }

    // EFFECTS: returns taskDescription
    public String getDescription() {
        return taskDescription;
    }

    // EFFECTS: returns taskStatus
    public Boolean getCompletionStatus() {
        return taskCompletion;
    }

    // EFFECTS: sets taskStatus == true (completes task)
    public void markComplete() {
        this.taskCompletion = true;
    }

    // EFFECTS: returns taskPriority
    public Priority getPriority() {
        return taskPriority;
    }

    // EFFECTS: returns taskPriority
    public Category getCategory() {
        return taskCategory;
    }

    ////////////////////////////////////////////////////  JSON  ////////////////////////////////////////////////////////

    // EFFECTS: returns this as a JSONObject
    @Override
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.put("description", taskDescription);
        json.put("due date", taskDueDate);
        json.put("status", taskCompletion);
        json.put("priority", taskPriority);
        json.put("category", taskCategory);
        return json;
    }
}
