package model.filterTests;

import model.filters.StatusFilter;
import model.tasks.Task;
import java.util.ArrayList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class StatusFilterTest extends FilterTestSetup implements FilterTest {
    private final StatusFilter completedFilter = new StatusFilter(true);
    private final StatusFilter notCompletedFilter = new StatusFilter(false);

    @BeforeEach
    public void setup() {
        refreshList();
        refreshTasks();
    }

    @Test
    public void constructorTest() {
        assertTrue(completedFilter.status);
        assertFalse(notCompletedFilter.status);
    }

    @Test
    public void satisfiesTestFalse() {
        //tests case where task is complete but satisfies is looking for not complete
        todayTask.markComplete();
        assertFalse(notCompletedFilter.satisfies(todayTask));
        //tests case where task is not complete but satisfies is looking for complete
        assertFalse(completedFilter.satisfies(sevenDaysTask));
    }

    @Test
    public void satisfiesTestTrue() {
        //tests cases where satisfies is given tasks who's status match what it is looking for
        todayTask.markComplete();
        assertTrue(completedFilter.satisfies(todayTask));
    }

    @Test
    public void filterTestAllIn() {
        // tests case where all tasks satisfy filter
        todayTask.markComplete();
        thirtyDaysTask.markComplete();
        taskList.addTask(todayTask);
        taskList.addTask(thirtyDaysTask);


        ArrayList<Task> completedTasks = taskList.filterBy(completedFilter);
        assertTrue(completedTasks.contains(todayTask));
        assertTrue(completedTasks.contains(thirtyDaysTask));
        assertEquals(2, completedTasks.size());
    }

    @Test
    public void filterTestAllOut() {
        // tests case where no tasks satisfy filter
        taskList.addTask(todayTask);
        taskList.addTask(thirtyDaysTask);

        ArrayList<Task> completedTasks = taskList.filterBy(completedFilter);
        assertEquals(0, completedTasks.size());
    }

    @Test
    public void filterTestSomeInSomeOut() {
        //tests case where some tasks satisfy filter, and others do not
        todayTask.markComplete();
        thirtyDaysTask.markComplete();
        taskList.addTask(todayTask);
        taskList.addTask(thirtyDaysTask);
        taskList.addTask(sevenDaysTask);
        taskList.addTask(eightDaysTask);

        ArrayList<Task> completedTasks = taskList.filterBy(completedFilter);
        assertTrue(completedTasks.contains(todayTask));
        assertTrue(completedTasks.contains(thirtyDaysTask));
        assertEquals(2, completedTasks.size());

        ArrayList<Task> unCompletedTasks = taskList.filterBy(notCompletedFilter);
        assertTrue(unCompletedTasks.contains(sevenDaysTask));
        assertTrue(unCompletedTasks.contains(eightDaysTask));
        assertEquals(2, unCompletedTasks.size());
    }
}
