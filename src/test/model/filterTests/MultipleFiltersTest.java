package model.filterTests;

import model.filters.*;
import model.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MultipleFiltersTest extends FilterTestSetup implements FilterTest {
    private MultipleFilters multipleFilters;
    private final Filter overdueFilter = new OverdueFilter();
    private final Filter statusFilter = new StatusFilter(false);
    private final Filter withinWeekFilter = new DueWithinDaysFilter(7);


    @BeforeEach
    public void setup() {
        refreshList();
        refreshTasks();
        multipleFilters = new MultipleFilters();
    }

    @Test
    public void constructorTest() {
        assertTrue(multipleFilters.getFilters().isEmpty());
        assertTrue(multipleFilters.getSatisfiesAll());
    }

    @Test
    public void addFilterTest() {
        assertTrue(multipleFilters.getFilters().isEmpty());
        multipleFilters.addFilter(statusFilter);
        assertEquals(1,multipleFilters.getFilters().size());
        assertEquals(statusFilter,multipleFilters.getFilters().get(0));
    }

    @Test
    public void clearFiltersTest() {
        assertTrue(multipleFilters.getFilters().isEmpty());
        multipleFilters.addFilter(statusFilter);
        multipleFilters.addFilter(withinWeekFilter);
        assertEquals(2,multipleFilters.getFilters().size());
        multipleFilters.clearFilters();
        assertEquals(0,multipleFilters.getFilters().size());
    }

    @Test
    public void satisfiesTestFalse() {
        //one filter produces false, other doesnt
        multipleFilters.addFilter(statusFilter);
        multipleFilters.addFilter(withinWeekFilter);
        assertFalse(multipleFilters.satisfies(thirtyDaysTask));
        //both filters produce false
        thirtyDaysTask.markComplete();
        assertFalse(multipleFilters.satisfies(thirtyDaysTask));

        multipleFilters.setSatisfiesAll(false);
        //one filter produces false, other doesnt
        assertFalse(multipleFilters.satisfies(thirtyDaysTask));
        //both filters produce false
        assertFalse(multipleFilters.satisfies(thirtyDaysTask));
    }

    @Test
    public void satisfiesTestTrue() {
        //one filter produces true
        multipleFilters.addFilter(withinWeekFilter);
        assertTrue(multipleFilters.satisfies(sixDaysTask));

        //multiple filters produce true;
        multipleFilters.addFilter(statusFilter);
        assertTrue(multipleFilters.satisfies(sixDaysTask));

        multipleFilters.setSatisfiesAll(false);
        //one filter produces true
        assertTrue(multipleFilters.satisfies(sixDaysTask));

        //multiple filters produce true;
        assertTrue(multipleFilters.satisfies(sixDaysTask));
    }

    @Test
    public void filterTestAllIn() {
        taskList.addTask(sixDaysTask);
        taskList.addTask(todayTask);
        taskList.addTask(sevenDaysTask);
        multipleFilters.addFilter(statusFilter);
        multipleFilters.addFilter(withinWeekFilter);
        ArrayList<Task> filteredTasks = taskList.filterBy(multipleFilters);
        assertEquals(3, filteredTasks.size());
        assertTrue(filteredTasks.contains(sixDaysTask));
        assertTrue(filteredTasks.contains(todayTask));
        assertTrue(filteredTasks.contains(sevenDaysTask));

        multipleFilters.setSatisfiesAll(false);
        filteredTasks = taskList.filterBy(multipleFilters);
        assertEquals(3, filteredTasks.size());
        assertTrue(filteredTasks.contains(sixDaysTask));
        assertTrue(filteredTasks.contains(todayTask));
        assertTrue(filteredTasks.contains(sevenDaysTask));
    }

    @Test
    public void filterTestAllOut() {
        taskList.addTask(todayTask);
        taskList.addTask(sevenDaysTask);
        multipleFilters.addFilter(overdueFilter);
        multipleFilters.addFilter(withinWeekFilter);
        ArrayList<Task> filteredTasks = taskList.filterBy(multipleFilters);
        assertEquals(0, filteredTasks.size());

        multipleFilters.setSatisfiesAll(false);
        multipleFilters.clearFilters();
        multipleFilters.addFilter(overdueFilter);
        filteredTasks = taskList.filterBy(multipleFilters);
        assertEquals(0, filteredTasks.size());
    }

    @Test
    public void filterTestSomeInSomeOut() {
        taskList.addTask(sixDaysTask);
        taskList.addTask(todayTask);
        taskList.addTask(sevenDaysTask);
        taskList.addTask(thirtyDaysTask);
        taskList.addTask(eightDaysTask);
        multipleFilters.addFilter(withinWeekFilter);
        multipleFilters.addFilter(statusFilter);
        ArrayList<Task> filteredTasks = taskList.filterBy(multipleFilters);
        assertTrue(filteredTasks.contains(todayTask));
        assertTrue(filteredTasks.contains(sevenDaysTask));
        assertTrue(filteredTasks.contains(sixDaysTask));
        assertEquals(3, filteredTasks.size());

        multipleFilters.setSatisfiesAll(false);
        filteredTasks = taskList.filterBy(multipleFilters);
        assertTrue(filteredTasks.contains(todayTask));
        assertTrue(filteredTasks.contains(sevenDaysTask));
        assertTrue(filteredTasks.contains(sixDaysTask));
        assertTrue(filteredTasks.contains(thirtyDaysTask));
        assertTrue(filteredTasks.contains(eightDaysTask));
        assertEquals(5, filteredTasks.size());
    }
}
