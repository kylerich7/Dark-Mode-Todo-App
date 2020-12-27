package model.filterTests;

import model.filters.Filter;
import model.filters.TrueFilter;
import model.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrueFilterTest extends FilterTestSetup implements FilterTest {
    private final Filter trueFilter  = new TrueFilter();

    @BeforeEach
    public void setup() {
        // not needed
    }

    public void constructorTest() {
        // implemented from FilterTask. Left blank because...
        // this test category does not apply to true filter, as true filter has a default java constructor
    }


    public void satisfiesTestFalse() {
        // implemented from FilterTask. Left blank because...
        // this test category does not apply to true filter, as it is impossible to have tasks NOT satisfy trueFilter
    }

    @Test
    public void satisfiesTestTrue() {
        //tasks picked at random to ensure true is being produced
        assertTrue(trueFilter.satisfies(eightDaysTask));
        assertTrue(trueFilter.satisfies(overdueTask1));
    }

    @Test
    public void filterTestAllIn() {
        //tests case where random tasks are added and they all satisfy truthFilter
        taskList.addTask(eightDaysTask);
        taskList.addTask(todayTask);
        taskList.addTask(overdueTask1);
        ArrayList<Task> allTasks = taskList.filterBy(trueFilter);
        assertTrue(allTasks.contains(eightDaysTask));
        assertTrue(allTasks.contains(todayTask));
        assertTrue(allTasks.contains(overdueTask1));
    }

    public void filterTestAllOut() {
        // implemented from FilterTask. Left blank because...
        // this test category does not apply to true filter, as it is impossible to have tasks NOT satisfy trueFilter
    }

    public void filterTestSomeInSomeOut() {
        // implemented from FilterTask. Left blank because...
        // this test category does not apply to true filter, as it is impossible to have tasks NOT satisfy trueFilter
    }
}
