package model.filterTests;

import model.tasks.Category;
import model.tasks.Priority;
import model.tasks.Task;
import model.tasks.TaskList;

import java.time.LocalDate;

public class FilterTestSetup {
    public Task todayTask;
    public Task sevenDaysTask;
    public Task sixDaysTask;
    public Task eightDaysTask;
    public Task thirtyDaysTask;
    public Task overdueTask1;
    public Task overdueTask2;
    public TaskList taskList;
    private final LocalDate today = LocalDate.now();
    private final String todayStr = LocalDate.now().toString();
    private final String sixDaysFromNowStr = today.plusDays(6).toString();
    private final String sevenDaysFromNowStr = today.plusDays(7).toString();
    private final String eightDaysFromNowStr = today.plusDays(8).toString();
    private final String thirtyDaysFromNowStr = today.plusDays(30).toString();
    private final String oneDaysPast = today.minusDays(1).toString();
    private final String sixDaysPast = today.minusDays(6).toString();

    protected FilterTestSetup() {
        refreshTasks();
        refreshList();
    }

    protected void refreshTasks() {
        todayTask = new Task(todayStr,"t1,0 days from now", Priority.HIGH, Category.WORK);
        sevenDaysTask = new Task(sevenDaysFromNowStr,"t2,7 days from now", Priority.MEDIUM,Category.SCHOOL);
        sixDaysTask = new Task(sixDaysFromNowStr, "t3, 6 days from now", Priority.MEDIUM,Category.HEALTH);
        eightDaysTask = new Task(eightDaysFromNowStr, "t4, 8 days from now", Priority.HIGH,Category.HEALTH);
        thirtyDaysTask = new Task(thirtyDaysFromNowStr, "t5, 30 days from now", Priority.LOW,Category.OTHER);
        overdueTask1 = new Task(oneDaysPast, "t5, -1 days from now", Priority.LOW,Category.PERSONAL);
        overdueTask2 = new Task(sixDaysPast, "t5, -6 days from now", Priority.MEDIUM,Category.SCHOOL);
    }

    protected void refreshList() {
        taskList = new TaskList();
    }
}
