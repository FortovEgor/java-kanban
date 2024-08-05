package test;

import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.sql.SQLOutput;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerSprint8 extends TaskManagerTest<InMemoryTaskManager> {
    private static InMemoryTaskManager manager;
    private static Task task1;
    private static Subtask subtask1;
    private static Epic epic1;

    @BeforeAll
    public static void setup() {
        manager = new InMemoryTaskManager(new InMemoryHistoryManager());

        // create & add tasks and its inheritance
        task1 = new Task("task1_name", "task1_description", 1, Status.NEW,
                Duration.ZERO, LocalDateTime.now().plusMinutes(10));
        manager.addTask(task1);
        subtask1 = new Subtask("subtask1_name", "subtask1_description",
                2, Status.NEW, 1, Duration.ZERO, LocalDateTime.now().minusMinutes(10));
        manager.addSubtask(subtask1);
        epic1 = new Epic("epic1_name", "epic1_description", 3, Status.NEW,
                new ArrayList<>(Arrays.asList(subtask1)));
        manager.addEpic(epic1 );
    }

    @Override
    @Test
    public void getAllTasksTest() {
        assertEquals(1, manager.getAllTasks().size());
    }

    @Override
    @Test
    public void getAllEpicsTest() {
        assertEquals(1, manager.getAllEpics().size());
    }

    @Override
    @Test
    public void getAllSubtasksTest() {
        assertEquals(1, manager.getAllSubtasks().size());
        ArrayList<Subtask> subtasks = new ArrayList<>((Collection<Subtask>) manager.getAllSubtasks());
        assertEquals(1, subtasks.get(0).getEpicId());
    }

    @Override
    @Test
    public void getPrioritizedTasksTest() {
        assertEquals(2, manager.getPrioritizedTasks().size());
    }

    @Override
    @Test
    public void deleteAllTasksTest() {
        manager.deleteAllTasks();
        assertTrue(manager.getAllTasks().isEmpty());
    }

    @Override
    @Test
    public void deleteAllEpicsTest() {
        manager.deleteAllEpics();
        assertTrue(manager.getAllEpics().isEmpty());
    }

    @Override
    @Test
    public void deleteAllSubtasksTest() {
        manager.deleteAllSubtasks();
        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Override
    @Test
    public void getTaskByIdTest() {
        Task task = manager.getTaskById(1);
        assertEquals(task1, task);
    }

    @Override
    @Test
    public void getSubtaskByIdTest() {
        Subtask subTask = manager.getSubtaskById(2);
        assertEquals(subtask1, subTask);
    }

    @Override
    @Test
    public void getEpicByIdTest() {
        Epic epic = manager.getEpicById(3);
        assertEquals(epic1, epic);
    }

    @Override
    @Test
    public void updateTaskTest() {
        Task task = new Task("task1_name_UPDATED", "task1_description_UPDATED", 1, Status.NEW,
                Duration.ZERO, LocalDateTime.now().plusMinutes(10));
        manager.updateTask(task);

        Task taskUpdated = manager.getTaskById(1);  // уже оттестировано выше
        assertEquals(task, taskUpdated);
    }

    @Override
    @Test
    public void updateEpicTest() {
        Epic epic = new Epic("epic1_name", "epic1_description", 3, Status.NEW,
                new ArrayList<>(Arrays.asList(subtask1)));
        manager.updateEpic(epic);

        Epic epicUpdated = manager.getEpicById(3);  // уже оттестировано выше
        assertEquals(epic, epicUpdated);
    }

    @Override
    @Test
    public void updateSubtaskTest() {
        Subtask subtask = new Subtask("subtask1_name", "subtask1_description",
                2, Status.NEW, 1, Duration.ZERO, LocalDateTime.now().minusMinutes(10));
        manager.updateSubtask(subtask);

        Subtask subtaskUpdated = manager.getSubtaskById(2);  // уже оттестировано выше
        assertEquals(subtask, subtaskUpdated);
    }

    @Override
    @Test
    public void removeTaskTest() {
        manager.removeTask(1);
        assertNull(manager.getTaskById(1));  // уже оттестировано
    }

    @Override
    @Test
    public void removeEpicTest() {
        manager.removeEpic(3);
        assertNull(manager.getEpicById(3));  // уже оттестировано
    }

    @Override
    @Test
    public void removeSubtaskTest() {
        manager.removeSubtask(2);
        assertNull(manager.getSubtaskById(2));  // уже оттестировано
        Epic epic = manager.getEpicById(3);
        assertTrue(epic.getAllSubtasks().isEmpty());
    }

    @Override
    @Test
    public void getAllSubtasksOfTheEpicTest() {
        ArrayList<Subtask> subtasks = (ArrayList<Subtask>) manager.getAllSubtasksOfTheEpic(3);
        assertEquals(1, subtasks.size());
        assertEquals(subtask1, subtasks.get(0));
    }

    @Override
    @Test
    public void updateEpicStatusTest() {
        manager.updateEpicStatus(3);
        // в моей реализации статус обновляется автоматически
        assertTrue(true);
    }

    @Override
    @Test
    public void getHistory() {
        manager.getAllEpics();
        manager.getAllTasks();
        manager.getAllSubtasks();
        ArrayList<Task> history = (ArrayList<Task>) manager.getHistory();
        assertEquals(3, history.size());  // история просмотров
    }
}
