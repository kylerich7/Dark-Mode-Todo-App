package model.taskTests;

import model.exceptions.InvalidDateException;
import model.tasks.Category;
import model.tasks.Priority;
import model.tasks.Task;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static java.time.temporal.ChronoUnit.DAYS;

public class TaskTest {
    private Task task1;
    private Task task2;

    @BeforeEach
    public void setup(){
        task1 = new Task("2020-12-14","call mom",Priority.LOW, Category.WORK);
        task2 = new Task("2020-09-20","bobs birthday",Priority.MEDIUM,Category.OTHER);
    }

    @Test
    public void constructorTest() {
        assertEquals("2020-12-14",task1.getDueDate());
        assertEquals("call mom", task1.getDescription());
        assertEquals(Priority.LOW, task1.getPriority());
        assertEquals(Category.WORK, task1.getCategory());
        assertFalse(task1.getCompletionStatus());

        assertEquals("2020-09-20",task2.getDueDate());
        assertEquals("bobs birthday", task2.getDescription());
        assertEquals(Priority.MEDIUM, task2.getPriority());
        assertEquals(Category.OTHER, task2.getCategory());
        assertFalse(task2.getCompletionStatus());
    }

    @Test
    public void daysUntilDueTest() {
        long expected1 = -1 * DAYS.between(LocalDate.parse(task1.getDueDate()),LocalDate.now());
        assertEquals(expected1,task1.daysUntilDue());

        long expected2 = -1 * DAYS.between(LocalDate.parse(task2.getDueDate()),LocalDate.now());
        assertEquals(expected2,task2.daysUntilDue());
    }

    @Test
    public void markCompleteTest() {
        assertFalse(task1.getCompletionStatus());
        task1.markComplete();
        assertTrue(task1.getCompletionStatus());
    }

    @Test
    public void compareToTest() {
        LocalDate today = LocalDate.now();

        // same status same date
        Task task1 = new Task(today.toString(),"same date same stat",Priority.MEDIUM,Category.WORK);
        Task task2 = new Task(today.toString(),"same date same stat2",Priority.HIGH,Category.SCHOOL);
        task1.markComplete();
        task2.markComplete();
        assertEquals(0, task1.compareTo(task2));
        assertEquals(0, task2.compareTo(task1));

        // different date;  same status
        Task task3 = new Task(today.toString(),"date same stat",Priority.LOW,Category.WORK);
        Task task4 = new Task(today.plusDays(5).toString(),"further date same stat2",Priority.MEDIUM,Category.WORK);
        assertEquals(-1, task3.compareTo(task4));
        assertEquals(1, task4.compareTo(task3));

        // different status
        // date doesnt matter in these cases, as status has priority over date
        assertEquals(1, task1.compareTo(task3));
        assertEquals(-1, task4.compareTo(task2));
    }

    @Test
    void testToJsonObject() {
        JSONObject jsonObject = task1.toJsonObject();
        assertEquals(5, JSONObject.getNames(jsonObject).length);
        assertEquals("call mom", jsonObject.getString("description"));
        assertEquals("2020-12-14", jsonObject.getString("due date"));
        assertEquals(Priority.LOW, jsonObject.get("priority"));
        assertFalse(jsonObject.getBoolean("status"));
        assertEquals(Category.WORK, jsonObject.get("category"));
    }

    @Test
    public void exceptionTest() {
        try {
            new Task("2020--10-10","exception", Priority.LOW,Category.WORK);
        } catch (InvalidDateException e) {
            // excepted
        }
        try {
            new Task("1999-13-10","exception",Priority.HIGH,Category.HEALTH);
        } catch (InvalidDateException e) {
            // excepted
        }
    }



}
