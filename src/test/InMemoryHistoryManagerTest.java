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

    @Test
    public void emptyTaskHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

        final Task task1 = new Task("task1_name", "task1_description", 1, Status.NEW,
                Duration.ZERO, LocalDateTime.now());
        historyManager.add(task1);
        assertEquals(1, historyManager.getHistory().size());  // add test

        historyManager.remove(1);
        assertTrue(historyManager.getHistory().isEmpty());  // remove test

        List<Task> tasksInHistory = historyManager.getHistory();
        assertTrue(tasksInHistory.isEmpty());  // getHistory test
    }

    @Test
    public void duplicationTaskHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

        final Task task1 = new Task("task1_name", "task1_description", 1, Status.NEW,
                Duration.ZERO, LocalDateTime.now());
        historyManager.add(task1);
        historyManager.add(task1);
        assertEquals(1, historyManager.getHistory().size());  // add test

        historyManager.remove(1);
        historyManager.remove(1);
        assertTrue(historyManager.getHistory().isEmpty());  // remove test

        List<Task> tasksInHistory = historyManager.getHistory();
        assertTrue(tasksInHistory.isEmpty());  // getHistory test
    }

    @Test
    public void deletionFromTaskHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

        final Task task1 = new Task("task1_name", "task1_description", 1, Status.NEW,
                Duration.ZERO, LocalDateTime.now());
        final Task task2 = new Task("task2_name", "task2_description", 2, Status.NEW,
                Duration.ZERO, LocalDateTime.now());
        final Task task3 = new Task("task3_name", "task3_description", 3, Status.NEW,
                Duration.ZERO, LocalDateTime.now());
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        assertEquals(3, historyManager.getHistory().size());  // add test

        historyManager.remove(1);
        historyManager.remove(2);
        historyManager.remove(3);
        assertTrue(historyManager.getHistory().isEmpty());  // remove test

        List<Task> tasksInHistory = historyManager.getHistory();
        assertTrue(tasksInHistory.isEmpty());  // getHistory test
    }
}