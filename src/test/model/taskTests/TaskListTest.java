package model.taskTests;

import model.exceptions.TaskAlreadyExistsException;
import model.exceptions.TaskDoesntExistException;
import model.filters.*;
import model.tasks.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class TaskListTest {
    private TaskList testTaskList;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    public void setup() {
        task1 = new Task("2021-12-21", "t1", Priority.HIGH,Category.HEALTH);
        task2 = new Task("2022-03-15", "t2", Priority.MEDIUM,Category.OTHER);
        task3 = new Task("2022-05-23", "t3", Priority.LOW,Category.WORK);
        testTaskList = new TaskList();
    }

    @Test
    public void constructorTest() {
        //tests to ensure constructor inits empty hashmap
        assertEquals(0,testTaskList.size());
    }

    @Test
    public void sizeTest(){
        //tests case where 0 elements are in task list
        assertEquals(0,testTaskList.size());
        //tests case where 1 element is in task list
        testTaskList.addTask(task1);
        assertEquals(1,testTaskList.size());
        //tests case where many element are in task list
        testTaskList.addTask(task2);
        assertEquals(2,testTaskList.size());
    }

    @Test
    public void sizeWithFilterTest(){
        Filter trueFilter = new TrueFilter();
        Filter statusFilter = new StatusFilter(true);

        //tests case where 0 elements are in task list with true filter
        assertEquals(0,testTaskList.sizeWithFilter(trueFilter));
        //tests case where all elements satisfy filter
        testTaskList.addTask(task1);
        testTaskList.addTask(task2);
        assertEquals(2,testTaskList.sizeWithFilter(trueFilter));
        //tests case where no elements satisfy filter
        assertEquals(0,testTaskList.sizeWithFilter(statusFilter));
        //tests case where some elements satisfy filter, and others do not
        testTaskList.addTask(task3);
        testTaskList.completeTask(task1);
        testTaskList.completeTask(task2);
        assertEquals(2,testTaskList.sizeWithFilter(statusFilter));
    }

    @Test
    public void addTaskTest() {
        //tests if tasks are being added properly
        assertEquals(0,testTaskList.size());
        testTaskList.addTask(task1);
        assertEquals(1,testTaskList.size());
        assertTrue(testTaskList.containsTask(task1));
        testTaskList.addTask(task2);
        assertEquals(2, testTaskList.size());
        assertTrue(testTaskList.containsTask(task2));
    }

    @Test
    public void deleteTaskParamStringTest() {
        //tests if tasks are being removed properly when description key is passed as a parameter
        testTaskList.addTask(task1);
        testTaskList.addTask(task2);
        testTaskList.addTask(task3);
        assertEquals(3, testTaskList.size());
        assertTrue(testTaskList.containsTask(task1));
        assertTrue(testTaskList.containsTask(task2));
        assertTrue(testTaskList.containsTask(task3));

        //checks to make sure correct item, and only that item, is being removed
        testTaskList.deleteTask(task1.getDescription());
        assertFalse(testTaskList.containsTask(task1));
        assertEquals(2, testTaskList.size());
        assertTrue(testTaskList.containsTask(task2));
        assertTrue(testTaskList.containsTask(task3));

        //checks to make sure multiple tasks can be deleted with no issues
        testTaskList.deleteTask(task2.getDescription());
        testTaskList.deleteTask(task3.getDescription());
        assertEquals(0, testTaskList.size());

    }

    @Test
    public void deleteTaskParamTaskTest() {
        //tests if tasks are being removed properly when description key is passed as a parameter
        testTaskList.addTask(task1);
        testTaskList.addTask(task2);
        testTaskList.addTask(task3);
        assertEquals(3, testTaskList.size());
        assertTrue(testTaskList.containsTask(task1));
        assertTrue(testTaskList.containsTask(task2));
        assertTrue(testTaskList.containsTask(task3));

        //checks to make sure correct item, and only that item, is being removed
        testTaskList.deleteTask(task1);
        assertFalse(testTaskList.containsTask(task1));
        assertEquals(2, testTaskList.size());
        assertTrue(testTaskList.containsTask(task2));
        assertTrue(testTaskList.containsTask(task3));

        //checks to make sure multiple tasks can be deleted with no issues
        testTaskList.deleteTask(task2);
        testTaskList.deleteTask(task3);
        assertEquals(0, testTaskList.size());
    }

    @Test
    public void completeTaskParamStringTest() {
        task2.markComplete();
        assertFalse(task1.getCompletionStatus());
        assertTrue(task2.getCompletionStatus());
        testTaskList.addTask(task1);
        testTaskList.addTask(task2);
        Filter statusFilter = new StatusFilter(true);
        assertEquals(1,testTaskList.sizeWithFilter(statusFilter));
        assertTrue(testTaskList.filterBy(statusFilter).contains(task2));
        //checks to make sure nothing changes after marking an already complete task as complete
        testTaskList.completeTask(task2.getDescription());
        assertEquals(1,testTaskList.sizeWithFilter(statusFilter));
        assertTrue(testTaskList.filterBy(statusFilter).contains(task2));
        //checks to make sure tasks are being completed when they were not already marked completed
        testTaskList.completeTask(task1.getDescription());
        assertEquals(2,testTaskList.sizeWithFilter(statusFilter));
        assertTrue(testTaskList.filterBy(statusFilter).contains(task1));
    }

    @Test
    public void completeTaskParamTaskTest() {
        task2.markComplete();
        assertFalse(task1.getCompletionStatus());
        assertTrue(task2.getCompletionStatus());
        testTaskList.addTask(task1);
        testTaskList.addTask(task2);
        Filter statusFilter = new StatusFilter(true);
        assertEquals(1,testTaskList.sizeWithFilter(statusFilter));
        assertTrue(testTaskList.filterBy(statusFilter).contains(task2));
        //checks to make sure nothing changes after marking an already complete task as complete
        testTaskList.completeTask(task2);
        assertEquals(1,testTaskList.sizeWithFilter(statusFilter));
        assertTrue(testTaskList.filterBy(statusFilter).contains(task2));
        //checks to make sure tasks are being completed when they were not already marked completed
        testTaskList.completeTask(task1);
        assertEquals(2,testTaskList.sizeWithFilter(statusFilter));
        assertTrue(testTaskList.filterBy(statusFilter).contains(task1));
    }



    @Test
    public void getTaskTest() {
        testTaskList.addTask(task1);
        assertEquals(task1,testTaskList.getTask(task1.getDescription()));
    }

    @Test
    public void containsTaskParamStringTest() {
        assertFalse(testTaskList.containsTask(task2.getDescription()));
        testTaskList.addTask(task2);
        assertTrue(testTaskList.containsTask(task2.getDescription()));
    }

    @Test
    public void containsTaskParamTaskTest() {
        assertFalse(testTaskList.containsTask(task2));
        testTaskList.addTask(task2);
        assertTrue(testTaskList.containsTask(task2));
    }

    @Test
    public void getAllTasksTest() {
        testTaskList.addTask(task1);
        testTaskList.addTask(task2);
        testTaskList.addTask(task3);
        ArrayList<Task> actualTasks = new ArrayList<>();
        actualTasks.add(task1);
        actualTasks.add(task2);
        actualTasks.add(task3);
        ArrayList<Task> expectedTasks = testTaskList.getAllTasks();
        for (int i = 0; i < actualTasks.size(); i++) {
            assertTrue(actualTasks.contains(expectedTasks.get(i)));
        }
        assertEquals(expectedTasks.size(), actualTasks.size());
    }

    @Test
    public void getTasksTest() {
        assertEquals(0, testTaskList.getAllTasks().size());
        testTaskList.addTask(task1);
        assertEquals(1, testTaskList.getAllTasks().size());
        assertTrue(testTaskList.getAllTasks().contains(task1));
        testTaskList.addTask(task2);
        testTaskList.addTask(task3);
        assertEquals(3,testTaskList.getAllTasks().size());
        assertTrue(testTaskList.getAllTasks().contains(task2));
        assertTrue(testTaskList.getAllTasks().contains(task3));
    }

    @Test
    void testToJsonObject() {
        testTaskList.addTask(task1);
        testTaskList.addTask(task2);
        JSONObject t1 = task1.toJsonObject();
        JSONObject t2 = task2.toJsonObject();

        JSONObject jsonObject = testTaskList.toJsonObject();
        assertEquals(JSONObject.getNames(jsonObject).length, 1);
        JSONArray tasks = jsonObject.getJSONArray("tasks");

        for (Object json : tasks) {
            JSONObject jsonTask = (JSONObject) json;
            if (jsonTask.getString("description").equals(task1.getDescription())) {
                assertEquals(t1.toString(), jsonTask.toString());
            } else {
                assertEquals(t2.toString(), jsonTask.toString());
            }
        }
    }

    @Test
    public void exceptionTest() {
        try {
            testTaskList.deleteTask(task1);
        } catch (TaskDoesntExistException e) {
            // excepted
        }
        try {
            testTaskList.deleteTask(task2.getDescription());
        } catch (TaskDoesntExistException e) {
            // excepted
        }
        try {
            testTaskList.completeTask(task3);
        } catch (TaskDoesntExistException e) {
            // excepted
        }
        try {
            testTaskList.completeTask(task1.getDescription());
        } catch (TaskDoesntExistException e) {
            // excepted
        }
        testTaskList.addTask(task1);
        try {
            testTaskList.addTask(task1);
        } catch (TaskAlreadyExistsException e) {
            // excepted
        }
        try {
            testTaskList.addTask(new Task(task1.getDueDate(),task1.getDescription(),task1.getPriority(),Category.OTHER));
        } catch (TaskAlreadyExistsException e) {
            // excepted
        }
    }
}
