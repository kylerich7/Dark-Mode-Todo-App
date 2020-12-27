package model.persistenceTests;

import model.tasks.*;
import persistance.*;
import java.io.IOException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class JsonLoaderTest {
    JsonLoader jsonLoader;

    @Test
    void testLoaderNonExistentFile() {
        try {
            JsonLoader jsonLoader = new JsonLoader("./src/resources/data/testing/thisFileNameDoesntExist.json");
            jsonLoader.load();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testLoaderEmptyTaskList() {
        try {
            JsonLoader jsonLoader = new JsonLoader("./src/resources/data/testing/testJsonEmptyList.json");
            TaskList taskList = jsonLoader.load();
            assertEquals(0, taskList.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testLoaderPopulatedTaskList() {
        try {
            JsonLoader jsonLoader = new JsonLoader("./src/resources/data/testing/testJsonPopulatedList.json");
            TaskList loadedTaskList = jsonLoader.load();
            assertEquals(3, loadedTaskList.size());
            Task loadedTask1 = loadedTaskList.getTask("t1");
            Task loadedTask2 = loadedTaskList.getTask("t2");
            assertTrue(loadedTaskList.containsTask("t1"));
            assertTrue(loadedTaskList.containsTask("t2"));
            assertEquals("2020-12-15", loadedTask1.getDueDate());
            assertEquals(Priority.MEDIUM, loadedTask2.getPriority());
            assertEquals(Category.SCHOOL, loadedTask1.getCategory());
            assertTrue(loadedTask2.getCompletionStatus());
            assertFalse(loadedTask1.getCompletionStatus());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testLoaderDefaultConstructor() {
        try {
            JsonLoader jsonLoaderDefaultFile = new JsonLoader();
            JsonLoader jsonLoaderFromFile = new JsonLoader("./src/resources/data/taskList.json");
            TaskList defaultLoad = jsonLoaderDefaultFile.load();
            TaskList fileLoad = jsonLoaderFromFile.load();
            assertEquals(defaultLoad.size(),fileLoad.size());
            for (Task task : fileLoad.getAllTasks()) {
                assertTrue(defaultLoad.containsTask(task.getDescription()));
            }
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
