package test;

import model.Epic;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    public void shouldBeEqualIfIdsAreEqual() {
        Task task1 = new Task("task1", "task1 description", 111, Status.NEW);
        Task task2 = new Task("task2", "task2 description", 111, Status.NEW);

        assertEquals(task1, task2);
    }
}