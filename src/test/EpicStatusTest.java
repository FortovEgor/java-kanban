package test;

import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpicStatusTest {
    @Test
    public void allSubtasksAreNew() {
        InMemoryTaskManager manager = new InMemoryTaskManager(new InMemoryHistoryManager());

        Subtask subtask1 = new Subtask("subtask1_name", "subtask1_description",
                1, Status.NEW, 3, Duration.ZERO, LocalDateTime.now());
        Subtask subtask2 = new Subtask("subtask2_name", "subtask2_description",
                2, Status.NEW, 3, Duration.ofNanos(10), LocalDateTime.now());

        Epic epic1 = new Epic("epic1_name", "epic1_description", 3, Status.NEW,
                new ArrayList<>(Arrays.asList(subtask1, subtask2)));
        manager.addEpic(epic1);

        Epic epic = manager.getEpicById(epic1.getId());
        assertEquals(epic.getStatus(), Status.NEW.toString());
    }

    @Test
    public void allSubtasksAreDone() {
        InMemoryTaskManager manager = new InMemoryTaskManager(new InMemoryHistoryManager());

        Subtask subtask1 = new Subtask("subtask1_name", "subtask1_description",
                1, Status.DONE, 3, Duration.ZERO, LocalDateTime.now());
        Subtask subtask2 = new Subtask("subtask2_name", "subtask2_description",
                2, Status.DONE, 3, Duration.ofNanos(10), LocalDateTime.now());

        Epic epic1 = new Epic("epic1_name", "epic1_description", 3, Status.NEW,
                new ArrayList<>(Arrays.asList(subtask1, subtask2)));
        manager.addEpic(epic1);

        Epic epic = manager.getEpicById(3);
        assertEquals(epic.getStatus(), Status.DONE.toString());
    }

    @Test
    public void allSubtasksAreNewOrDone() {
        InMemoryTaskManager manager = new InMemoryTaskManager(new InMemoryHistoryManager());

        Subtask subtask1 = new Subtask("subtask1_name", "subtask1_description",
                1, Status.DONE, 3, Duration.ZERO, LocalDateTime.now());
        Subtask subtask2 = new Subtask("subtask2_name", "subtask2_description",
                2, Status.NEW, 3, Duration.ofNanos(10), LocalDateTime.now());

        Epic epic1 = new Epic("epic1_name", "epic1_description", 3, Status.NEW,
                new ArrayList<>(Arrays.asList(subtask1, subtask2)));
        manager.addEpic(epic1);

        Epic epic = manager.getEpicById(3);
        assertEquals(epic.getStatus(), Status.IN_PROGRESS.toString());
    }

    @Test
    public void allSubtasksAreInProgress() {
        InMemoryTaskManager manager = new InMemoryTaskManager(new InMemoryHistoryManager());

        Subtask subtask1 = new Subtask("subtask1_name", "subtask1_description",
                1, Status.IN_PROGRESS, 3, Duration.ZERO, LocalDateTime.now());
        Subtask subtask2 = new Subtask("subtask2_name", "subtask2_description",
                2, Status.IN_PROGRESS, 3, Duration.ofNanos(10), LocalDateTime.now());

        Epic epic1 = new Epic("epic1_name", "epic1_description", 3, Status.NEW,
                new ArrayList<>(Arrays.asList(subtask1, subtask2)));
        manager.addEpic(epic1);

        Epic epic = manager.getEpicById(3);
        assertEquals(epic.getStatus(), Status.IN_PROGRESS);
    }
}
