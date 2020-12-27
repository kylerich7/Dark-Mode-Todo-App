package model.filterTests;
import model.filters.OverdueFilter;
import model.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class OverdueFilterTest extends FilterTestSetup implements FilterTest {
    private final OverdueFilter overdueFilter = new OverdueFilter();

    @BeforeEach
    public void setup() {
        refreshList();
    }

    @Test
    public void constructorTest() {
        // implemented from FilterTask. Left blank because...
        // this test category does not apply to overdue filter, as overdue filter has a default java constructor
    }

    @Test
    public void satisfiesTestFalse() {
        // tests case where due today
        assertFalse(overdueFilter.satisfies(todayTask));
        // tests case where due a while from now and is not complete
        assertFalse(overdueFilter.satisfies(sevenDaysTask));
        // tests case where due a while from now and is complete
        thirtyDaysTask.markComplete();
        assertFalse(overdueFilter.satisfies(thirtyDaysTask));
        // tests case where due date has passed, but task is complete (and therefore, not overdue)
        overdueTask1.markComplete();
        assertFalse(overdueFilter.satisfies(overdueTask1));
    }

    @Test
    public void satisfiesTestTrue() {
        // tests case where task is 1 day passed due date and task is not complete
        assertTrue(overdueFilter.satisfies(overdueTask1));
        // tests case where task is many days passed due date and task is not complete
        assertTrue(overdueFilter.satisfies(overdueTask2));
    }

    @Test
    public void filterTestAllIn() {
        // tests case where all tasks satisfy filter
        taskList.addTask(overdueTask1);
        taskList.addTask(overdueTask2);

        ArrayList<Task> overdueTasks = taskList.filterBy(overdueFilter);
        assertTrue(overdueTasks.contains(overdueTask1));
        assertTrue(overdueTasks.contains(overdueTask2));
        assertEquals(2, overdueTasks.size());
    }

    @Test
    public void filterTestAllOut() {
        // tests case where no tasks satisfy filter
        taskList.addTask(sevenDaysTask);
        taskList.addTask(todayTask);

        ArrayList<Task> overdueTasks = taskList.filterBy(overdueFilter);
        assertEquals(0, overdueTasks.size());
    }

    @Test
    public void filterTestSomeInSomeOut() {
        //tests case where some tasks satisfy filter, and others do not
        taskList.addTask(sevenDaysTask);
        taskList.addTask(todayTask);
        taskList.addTask(overdueTask1);
        taskList.addTask(overdueTask2);

        ArrayList<Task> overdueTasks = taskList.filterBy(overdueFilter);
        assertTrue(overdueTasks.contains(overdueTask1));
        assertTrue(overdueTasks.contains(overdueTask2));
        assertEquals(2, overdueTasks.size());
    }
}
