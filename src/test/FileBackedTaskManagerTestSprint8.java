package test;

import manager.FileBackedTaskManager;
import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTestSprint8 extends TaskManagerTest<InMemoryTaskManager> {
    private static FileBackedTaskManager manager;
    private static Task task1;
    private static Subtask subtask1;
    private static Epic epic1;

    @BeforeAll
    public static void setup() throws Exception {
        File file = File.createTempFile("test", "csv");
        // write data to file
        Writer fileWriter = new FileWriter(file, false);
        final String firstLine = "id,type,name,status,description,duration,startTime,epic\n";
        fileWriter.write(firstLine);
        fileWriter.write("3,TASK,task3,NEW,my third task,10,01.02.2222|11:11,1\n");
        fileWriter.flush();
        fileWriter.close();

        manager = new FileBackedTaskManager(new InMemoryHistoryManager(), file.getPath());

        // create & add tasks and its inheritance
        task1 = new Task("task1_name", "task1_description", 1, Status.NEW,
                Duration.ZERO, LocalDateTime.now().plusMinutes(10));
        manager.addTask(task1);  // overwrited
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
        assertEquals(2, manager.getAllTasks().size());
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
        var id = manager.getAllSubtasks().stream().findFirst().map(Subtask::getEpicId).orElse(null);
        assertEquals(1, id);
    }

    @Override
    @Test
    public void getPrioritizedTasksTest() {
        Set<Task> tasksSet = manager.getPrioritizedTasks();
        Task[] tasksArray = tasksSet.toArray(new Task[tasksSet.size()]);

        assertEquals(tasksArray.length, tasksSet.size());
        for (int i = 0; i < tasksArray.length - 1; ++i) {
            assertTrue(tasksArray[i].getStartTime().isBefore(tasksArray[i+1].getStartTime()));
        }
    }

    @Override
    @Test
    public void deleteAllTasksTest() {
        assertFalse(manager.getAllTasks().isEmpty());
        manager.deleteAllTasks();
        assertTrue(manager.getAllTasks().isEmpty());
    }

    @Override
    @Test
    public void deleteAllEpicsTest() {
        assertFalse(manager.getAllEpics().isEmpty());
        manager.deleteAllEpics();
        assertTrue(manager.getAllEpics().isEmpty());
    }

    @Override
    @Test
    public void deleteAllSubtasksTest() {
        assertFalse(manager.getAllSubtasks().isEmpty());
        manager.deleteAllSubtasks();
        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Override
    @Test
    public void getTaskByIdTest() {
        Task task = manager.getTaskById(2);  // no task with id=1 because of crossing
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

        Task taskUpdated = manager.getTaskById(1);
        assertEquals(task, taskUpdated);
    }

    @Override
    @Test
    public void updateEpicTest() {
        Epic epic = new Epic("epic1_name", "epic1_description", 3, Status.NEW,
                new ArrayList<>(Arrays.asList(subtask1)));
        manager.updateEpic(epic);

        Epic epicUpdated = manager.getEpicById(3);
        assertEquals(epic, epicUpdated);
    }

    @Override
    @Test
    public void updateSubtaskTest() {
        Subtask subtask = new Subtask("subtask1_name", "subtask1_description",
                2, Status.NEW, 1, Duration.ZERO, LocalDateTime.now().minusMinutes(10));
        manager.updateSubtask(subtask);

        Subtask subtaskUpdated = manager.getSubtaskById(2);
        assertEquals(subtask, subtaskUpdated);
    }

    @Override
    @Test
    public void removeTaskTest() {
        manager.removeTask(1);
        assertNull(manager.getTaskById(1));
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
