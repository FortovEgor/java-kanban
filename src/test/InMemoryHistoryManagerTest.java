package test;

import manager.InMemoryHistoryManager;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    @Test
    public void previousTaskSaved() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        final Task task1 = new Task("task1_name", "task1_description", 1, Status.NEW,
                Duration.ZERO, LocalDateTime.now());
        final Task task2 = new Task("task2_name", "task2_description", 2, Status.NEW,
                Duration.ZERO, LocalDateTime.now());

        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> tasksInHistory = historyManager.getHistory();
        final Task taskInHistory1 = tasksInHistory.get(0);
        final Task taskInHistory2 = tasksInHistory.get(1);

        assertEquals(task1, taskInHistory1);
        assertEquals(task2, taskInHistory2);
    }
}