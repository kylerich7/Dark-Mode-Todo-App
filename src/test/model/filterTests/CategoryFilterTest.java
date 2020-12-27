package model.filterTests;

import model.filters.CategoryFilter;
import model.tasks.Category;
import model.tasks.Task;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryFilterTest extends FilterTestSetup implements FilterTest {
    CategoryFilter schoolFilter = new CategoryFilter(Category.SCHOOL);
    CategoryFilter workFilter = new CategoryFilter(Category.WORK);
    CategoryFilter otherFilter = new CategoryFilter(Category.OTHER);
    CategoryFilter personalFilter = new CategoryFilter(Category.PERSONAL);
    CategoryFilter healthFilter = new CategoryFilter(Category.HEALTH);

    @BeforeEach
    public void setup() {
        refreshList();
    }

    @Test
    public void constructorTest() {
        assertEquals(Category.SCHOOL, schoolFilter.category);
        assertEquals(Category.WORK, workFilter.category);
        assertEquals(Category.OTHER, otherFilter.category);
        assertEquals(Category.PERSONAL, personalFilter.category);
        assertEquals(Category.HEALTH, healthFilter.category);
    }

    @Test
    public void satisfiesTestFalse() {
        assertFalse(schoolFilter.satisfies(todayTask));
        assertFalse(otherFilter.satisfies(eightDaysTask));
    }

    @Test
    public void satisfiesTestTrue() {
        assertTrue(personalFilter.satisfies(overdueTask1));
        assertTrue(workFilter.satisfies(todayTask));
    }

    @Test
    public void filterTestAllIn() {
        taskList.addTask(sevenDaysTask);
        taskList.addTask(overdueTask2);

        ArrayList<Task> schoolTasks = taskList.filterBy(schoolFilter);
        assertTrue(schoolTasks.contains(sevenDaysTask));
        assertTrue(schoolTasks.contains(overdueTask2));
        assertEquals(2, schoolTasks.size());
    }

    @Test
    public void filterTestAllOut() {
        taskList.addTask(overdueTask1);
        taskList.addTask(thirtyDaysTask);

        ArrayList<Task> withinWeekTasks = taskList.filterBy(healthFilter);
        assertEquals(0, withinWeekTasks.size());
    }

    @Test
    public void filterTestSomeInSomeOut() {
        taskList.addTask(todayTask);
        taskList.addTask(sixDaysTask);
        taskList.addTask(sevenDaysTask);
        taskList.addTask(eightDaysTask);
        taskList.addTask(thirtyDaysTask);

        ArrayList<Task> withinWeekTasks = taskList.filterBy(healthFilter);
        assertTrue(withinWeekTasks.contains(sixDaysTask));
        assertTrue(withinWeekTasks.contains(eightDaysTask));
        assertEquals(2, withinWeekTasks.size());
    }
}
