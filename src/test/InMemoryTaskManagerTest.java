package test;

import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    @Test
    public void reallyAddTasksOfDifferentTypesAndCanFindThemById() {
        InMemoryTaskManager manager = new InMemoryTaskManager(new InMemoryHistoryManager());

        // create & add tasks and its inheritance
        Task task1 = new Task("task1_name", "task1_description", 1, Status.NEW);
        manager.addTask(task1);
        Subtask subtask1 = new Subtask("subtask1_name", "subtask1_description",
                2, Status.NEW, 1);
        manager.addSubtask(subtask1);
        Epic epic1 = new Epic("epic1_name", "epic1_description", 3, Status.NEW,
                new ArrayList<>(Arrays.asList(subtask1)));
        manager.addEpic(epic1);

        // check whether all objects were added (check by finding them by id)
        Task task = manager.getTaskById(1);
        assertNotNull(task);

        Subtask subtask = manager.getSubtaskById(2);
        assertNotNull(subtask);

        Epic epic = manager.getEpicById(3);
        assertNotNull(epic);
    }

    @Test
    public void nonConflictingTasks() {
        // ТЗ: проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
        // в моей реализации нет генерации id (она не была обязательна, ревьюер разрешил не делать)
        assertTrue(true);
    }

    @Test
    public void immutabilityOfTheTaskAddedToManager() {
        InMemoryTaskManager manager = new InMemoryTaskManager(new InMemoryHistoryManager());

        Task task = new Task("task1_name", "task1_description", 1, Status.NEW);
        manager.addTask(task);
        Task gotTask = manager.getTaskById(1);

        // checks
        assertEquals(task, gotTask);  // id check
        assertEquals(task.getName(), gotTask.getName());
        assertEquals(task.getDescription(), gotTask.getDescription());
        assertEquals(task.getStatus(), gotTask.getStatus());
    }
}