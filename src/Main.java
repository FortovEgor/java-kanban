import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static model.Status.*;

public class Main {
    public static void main(String[] args) {
        ///// creating objects /////
        Task task1 = new Task("task1", "my first task", 1, NEW);
        Task task2 = new Task("task2", "my second task", 2, NEW);

        Subtask subtask1 = new Subtask("subtask1", "my first subtask", 3, NEW, 6);
        Subtask subtask2 = new Subtask("subtask2", "my second subtask", 4, NEW, 6);
        Subtask subtask3 = new Subtask("subtask3", "my third subtask", 5, NEW, 6);

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
        System.out.println("Запрос созданных задач несколько раз в разном порядке:");
        Collection<Task> tasks = billGates.getAllTasks();
        String res = "Tasks=[";
        for (Task task : tasks) {
            res += task + ", ";
        }
        res += "]";
        System.out.println("Tasks: " + res);

        Collection<Epic> epics = billGates.getAllEpics();
        res = "Epics=[";
        for (Epic epic : epics) {
            res += epic + ", ";
        }
        res += "]";
        System.out.println("Epics: " + res);

        Collection<Subtask> subtasks = billGates.getAllSubtasks();
        res = "Subtasks=[";
        for (Subtask subtask : subtasks) {
            res += subtask + ", ";
        }
        res += "]";
        System.out.println("Subtasks: " + res);
        System.out.println();

        ///// testing history of views /////
        System.out.println("История просмотров (" + billGates.getHistory().size() + " элементов):");
        System.out.println("History of views: " + billGates.getHistory() + "\n");

        // test #1 (from Yandex Practicum TA)
        billGates.removeTask(1);
        System.out.println("История просмотров после удаления одной из задач (" + billGates.getHistory().size() + " элементов):");
        System.out.println("History of views: " + billGates.getHistory() + "\n");

        // test #2 (from Yandex Practicum TA)
        billGates.removeEpic(7);
        System.out.println("История просмотров после удаления эпика (" + billGates.getHistory().size() + " элементов):");
        System.out.println("History of views: " + billGates.getHistory() + "\n");

        // test #3 (custom test)
        billGates.removeSubtask(3);  // автоматически удаляется из всех эпиков, где присутствует
        System.out.println("История просмотров после удаления подзадачи (" + billGates.getHistory().size() + " элементов):");
        System.out.println("History of views: " + billGates.getHistory());

/*
PREVIOUS tests:
        System.out.println("////////////////////////////////////////");  // separator for check functional

        ///// changing statuses of out objects /////
        task1.setStatus(IN_PROGRESS);
        System.out.println(task1.getStatus());

        subtask3.setStatus(DONE);
        billGates.updateEpicStatus(2);  // we must call it, i.e. model.Epic owns only ids of subtasks,
                                                // not subtasks themself
        System.out.println(billGates.getAllEpics());
        System.out.println("////////////////////////////////////////");  // separator for check functional

        ///// removing objects from manager /////
        billGates.removeTask(2);
        billGates.removeEpic(1);
        billGates.removeSubtask(1);

        ///// final displaying data /////
        System.out.println(billGates.getAllTasks());
        System.out.println(billGates.getAllEpics());
        System.out.println(billGates.getAllSubtasks());

        // Test for history display
        billGates.getTaskById(1);
        System.out.println("History of views: " + billGates.getHistory());

 */
    }
}
