package test;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    @Test
    public void shouldBeEqualIfIdsAreEqual() {
        Task task1 = new Subtask("task1", "task1 description", 111, Status.NEW, 5);
        Task task2 = new Subtask("task2", "task2 description", 111, Status.NEW, 6);

        assertEquals(task1, task2);
    }

    @Test
    public void canNotSubtaskBeItsOwnEpic() {
        try {
            Task subtask = new Subtask("subtask1", "subtask1 description", 111, Status.NEW, 111);
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }
}