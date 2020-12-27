package model.filterTests;

import model.filters.PriorityFilter;
import model.tasks.Priority;
import model.tasks.Task;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class PriorityFilterTest extends FilterTestSetup implements FilterTest {
    PriorityFilter highFilter = new PriorityFilter(Priority.HIGH);
    PriorityFilter medFilter = new PriorityFilter(Priority.MEDIUM);
    PriorityFilter lowFilter = new PriorityFilter(Priority.LOW);

    @BeforeEach
    public void setup() {
        refreshList();
    }

    @Test
    public void constructorTest() {
        assertEquals(Priority.HIGH, highFilter.priority);
        assertEquals(Priority.MEDIUM, medFilter.priority);
        assertEquals(Priority.LOW, lowFilter.priority);
    }

    @Test
    public void satisfiesTestFalse() {
        assertFalse(lowFilter.satisfies(todayTask));
        assertFalse(medFilter.satisfies(thirtyDaysTask));
        assertFalse(highFilter.satisfies(overdueTask1));
    }

    @Test
    public void satisfiesTestTrue() {
        assertTrue(lowFilter.satisfies(thirtyDaysTask));
        assertTrue(medFilter.satisfies(sevenDaysTask));
        assertTrue(highFilter.satisfies(todayTask));
    }

    @Test
    public void filterTestAllIn() {
        taskList.addTask(sevenDaysTask);
        taskList.addTask(sixDaysTask);

        ArrayList<Task> medTasks = taskList.filterBy(medFilter);
        assertTrue(medTasks.contains(sevenDaysTask));
        assertTrue(medTasks.contains(sixDaysTask));
        assertEquals(2, medTasks.size());
    }

    @Test
    public void filterTestAllOut() {
        taskList.addTask(sevenDaysTask);
        taskList.addTask(sixDaysTask);

        ArrayList<Task> highTasks = taskList.filterBy(highFilter);
        assertEquals(0, highTasks.size());
    }

    @Test
    public void filterTestSomeInSomeOut() {
        taskList.addTask(sevenDaysTask);
        taskList.addTask(sixDaysTask);
        taskList.addTask(thirtyDaysTask);
        taskList.addTask(overdueTask1);
        ArrayList<Task> lowTasks = taskList.filterBy(lowFilter);
        assertTrue(lowTasks.contains(thirtyDaysTask));
        assertTrue(lowTasks.contains(overdueTask1));
        assertEquals(2, lowTasks.size());
    }
}
