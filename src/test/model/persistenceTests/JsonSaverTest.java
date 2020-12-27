package model.persistenceTests;

import model.tasks.*;
import persistance.*;
import java.io.IOException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class JsonSaverTest {
    private TaskList taskList;

    @BeforeEach
    public void setup() {
        taskList = new TaskList();
    }

    @Test
    void testSaverInvalidFile() {
        try {
            JsonSaver js = new JsonSaver("./src/resources/data/testing/this|File|Name|Is|Invalid.json");
            js.save(taskList);
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testSaverEmptyTaskList() {
        try {
            JsonSaver jsonSaver = new JsonSaver("./src/resources/data/testing/testJsonEmptyList.json");
            jsonSaver.save(taskList);
            JsonLoader jsonLoader = new JsonLoader("./src/resources/data/testing/testJsonEmptyList.json");
            TaskList loadedTaskList = jsonLoader.load();
            assertEquals(0, loadedTaskList.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testSaverPopulatedTaskList() {
        try {
            Task task1 = new Task("2020-12-15", "t1", Priority.HIGH, Category.SCHOOL);
            Task task2 = new Task("2021-11-10", "t2", Priority.MEDIUM, Category.OTHER);
            Task task3 = new Task("2022-05-27", "t3", Priority.LOW,Category.HEALTH);
            task2.markComplete();
            taskList.addTask(task1);
            taskList.addTask(task2);
            taskList.addTask(task3);
            JsonSaver jsonSaver = new JsonSaver("./src/resources/data/testing/testJsonPopulatedList.json");
            jsonSaver.save(taskList);
            JsonLoader jsonLoader = new JsonLoader("./src/resources/data/testing/testJsonPopulatedList.json");
            TaskList loadedTaskList = jsonLoader.load();
            assertEquals(3, loadedTaskList.size());
            Task loadedTask1 = loadedTaskList.getTask("t1");
            Task loadedTask2 = loadedTaskList.getTask("t2");
            Task loadedTask3 = loadedTaskList.getTask("t3");
            assertTrue(loadedTaskList.containsTask("t1"));
            assertTrue(loadedTaskList.containsTask("t2"));
            assertEquals("2020-12-15", loadedTask1.getDueDate());
            assertEquals(Priority.LOW,loadedTask3.getPriority());
            assertEquals(Category.OTHER,loadedTask2.getCategory());
            assertTrue(loadedTask2.getCompletionStatus());
            assertFalse(loadedTask1.getCompletionStatus());
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testSaverDefaultConstructor() {
        try {
            JsonLoader jsonLoader = new JsonLoader();
            TaskList defaultLoad = jsonLoader.load();
            JsonSaver jsonSaver = new JsonSaver();
            jsonSaver.save(defaultLoad);
            TaskList newLoad = jsonLoader.load();
            assertEquals(defaultLoad.size(),newLoad.size());
            for (Task task : defaultLoad.getAllTasks()) {
                assertTrue(newLoad.containsTask(task.getDescription()));
            }
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
