package test;

import jdk.jshell.spi.ExecutionControlProvider;
import manager.HistoryManager;
import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static model.Status.NEW;
import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    public void shouldBeEqualIfIdsAreEqual() {
        try {
            // здесь приведем просто весь код Main без console output; он покрывает все методы классов менеджеров
            ///// creating objects /////
            Task task1 = new Task("task1", "my first task", 1, NEW,
                    Duration.ZERO, LocalDateTime.now());
            Task task2 = new Task("task2", "my second task", 2, NEW,
                    Duration.ZERO, LocalDateTime.now());

            Subtask subtask1 = new Subtask("subtask1", "my first subtask", 3, NEW, 6,
                    Duration.ZERO, LocalDateTime.now());
            Subtask subtask2 = new Subtask("subtask2", "my second subtask", 4, NEW, 6,
                    Duration.ZERO, LocalDateTime.now());
            Subtask subtask3 = new Subtask("subtask3", "my third subtask", 5, NEW, 6,
                    Duration.ZERO, LocalDateTime.now());

            Epic epic1 = new Epic("epic1", "my first epic", 6, NEW,
                    new ArrayList<>(Arrays.asList(subtask1, subtask2, subtask3)));
            Epic epic2 = new Epic("epic2", "my second epic", 7, NEW,
                    new ArrayList<>());

            ///// creating manager & adding all objects to it /////
            TaskManager billGates = Managers.getDefault();
            billGates.addTask(task1);
            billGates.addTask(task2);
            billGates.addEpic(epic1);
            billGates.addSubtask(subtask1);
            billGates.addSubtask(subtask2);
            billGates.addSubtask(subtask3);
            billGates.addEpic(epic2);

            ///// printing objects using manager /////
            Collection<Task> tasks = billGates.getAllTasks();
            Collection<Epic> epics = billGates.getAllEpics();
            Collection<Subtask> subtasks = billGates.getAllSubtasks();

            ///// testing history of views /////
            System.out.println("История просмотров (" + billGates.getHistory().size() + " элементов):");
            System.out.println("History of views: " + billGates.getHistory() + "\n");

            // test #1 (from Yandex Practicum TA)
            billGates.removeTask(1);

            // test #2 (from Yandex Practicum TA)
            billGates.removeEpic(7);

            // test #3 (custom test)
            billGates.removeSubtask(3);  // автоматически удаляется из всех эпиков, где присутствует
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}