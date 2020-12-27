package persistance;

import model.tasks.TaskList;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// represents a saver class that can write data to json files
public class JsonSaver {
    private static final int INDENT = 2;
    private static final String defaultFilepath = "./src/resources/data/taskList.json";
    private final String filePath;

    // MODIFIES: this
    // EFFECTS: constructs saver to save data to filepath
    public JsonSaver(String filepath) {
        this.filePath = filepath;
    }

    // MODIFIES: this
    // EFFECTS: constructs saver to save data to default filepath
    public JsonSaver() {
        this(defaultFilepath);
    }

    // EFFECTS: writes JSON representation of taskList to file at defaultFilepath
    // THROWS: FileNotFoundException if file cant be found
    public void save(TaskList taskList) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(filePath));
        JSONObject json = taskList.toJsonObject();
        writer.print(json.toString(INDENT));
        writer.close();
    }
}
