package persistance;

import model.tasks.Category;
import model.tasks.Priority;
import model.tasks.Task;
import model.tasks.TaskList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// represents a loader class that can read data from json files
public class JsonLoader {
    private static final String defaultFilepath = "./src/resources/data/taskList.json";
    private final String filePath;

    // MODIFIES: this
    // EFFECTS: constructs loader to load data from filepath
    public JsonLoader(String filepath) {
        this.filePath = filepath;
    }

    // MODIFIES: this
    // EFFECTS: constructs loader to load data from default filepath
    public JsonLoader() {
        this(defaultFilepath);
    }

    // EFFECTS: reads TaskList from filepath Json file and returns it
    // THROWS: IOException if file at filepath doesnt exist
    public TaskList load() throws IOException {
        String jsonData = readFile(filePath);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTaskList(jsonObject);
    }

    // EFFECTS: reads file at filepath as string and returns it
    // THROWS: IOException if file at filepath doesnt exist
    private String readFile(String filePath) throws IOException {
        StringBuilder lines = new StringBuilder();
        String line = "";
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((line = bufferedReader.readLine()) != null) {
            lines.append(line);
        }
        return lines.toString();
    }

    // EFFECTS: parses TaskList from jsonObject and returns it
    private TaskList parseTaskList(JSONObject jsonObject) {
        TaskList loadedTaskList = new TaskList();
        addTasks(loadedTaskList, jsonObject);
        return loadedTaskList;
    }

    // EFFECTS: parses priority tasks from jsonObject and adds them to loadedTaskList;
    private void addTasks(TaskList loadedTaskList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object json : jsonArray) {
            JSONObject jsonTask = (JSONObject) json;
            Task newTask = jsonToTask(jsonTask);
            loadedTaskList.addTask(newTask);
        }
    }

    // EFFECTS: parses priority task from jsonTask and returns it
    private Task jsonToTask(JSONObject jsonTask) {
        String description = jsonTask.getString("description");
        String dueDate = jsonTask.getString("due date");
        Priority priority = Priority.valueOf(jsonTask.getString("priority"));
        Category category = Category.valueOf(jsonTask.getString("category"));
        boolean status = jsonTask.getBoolean("status");
        Task newTask = new Task(dueDate, description, priority, category);
        if (status) {
            newTask.markComplete();
        }
        return newTask;
    }
}
