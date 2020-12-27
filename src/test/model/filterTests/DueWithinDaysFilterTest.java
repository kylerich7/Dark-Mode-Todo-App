package model.filterTests;

import model.filters.DueWithinDaysFilter;
import model.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DueWithinDaysFilterTest extends FilterTestSetup implements FilterTest {
    private final DueWithinDaysFilter withinWeekFilter = new DueWithinDaysFilter(7);

    @BeforeEach
    public void setup() {
        refreshList();
    }

    @Test
    public void constructorTest() {
        assertEquals(7, withinWeekFilter.noOfDays);
    }

    @Test
    public void satisfiesTestFalse() {
        //tests case where satisfies is looking for due date within x days and is given a task due in x+1 days
        assertFalse(withinWeekFilter.satisfies(thirtyDaysTask));
    }

    @Test
    public void satisfiesTestTrue() {
        //tests case where satisfies is looking for due date within x days and is given a task due in 0 days
        assertTrue(withinWeekFilter.satisfies(todayTask));
        //tests case where satisfies is looking for due date within x days and is given a task due in x-1 days
        assertTrue(withinWeekFilter.satisfies(sixDaysTask));
        //tests case where satisfies is looking for due date within x days and is given a task due in x days
        assertTrue(withinWeekFilter.satisfies(sevenDaysTask));
    }

    @Test
    public void filterTestAllIn() {
        //tests case where all tasks satisfy filter
        taskList.addTask(todayTask);
        taskList.addTask(sixDaysTask);
        taskList.addTask(sevenDaysTask);

        ArrayList<Task> withinWeekTasks = taskList.filterBy(withinWeekFilter);
        assertTrue(withinWeekTasks.contains(todayTask));
        assertTrue(withinWeekTasks.contains(sixDaysTask));
        assertTrue(withinWeekTasks.contains(sevenDaysTask));
        assertEquals(3, withinWeekTasks.size());
    }

    @Test
    public void filterTestAllOut() {
        //tests case where NO tasks satisfy filter
        taskList.addTask(overdueTask1);
        taskList.addTask(thirtyDaysTask);

        ArrayList<Task> withinWeekTasks = taskList.filterBy(withinWeekFilter);
        assertEquals(0, withinWeekTasks.size());
    }

    @Test
    public void filterTestSomeInSomeOut() {
        //tests case where some tasks satisfy filter, and others do not
        taskList.addTask(todayTask);
        taskList.addTask(sixDaysTask);
        taskList.addTask(sevenDaysTask);
        taskList.addTask(eightDaysTask);
        taskList.addTask(thirtyDaysTask);

        ArrayList<Task> withinWeekTasks = taskList.filterBy(withinWeekFilter);
        assertTrue(withinWeekTasks.contains(todayTask));
        assertTrue(withinWeekTasks.contains(sixDaysTask));
        assertTrue(withinWeekTasks.contains(sevenDaysTask));
        assertEquals(3, withinWeekTasks.size());
    }
}
