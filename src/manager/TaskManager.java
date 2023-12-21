package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public interface TaskManager {
    Collection<Task> getAllTasks();
    Collection<Epic> getAllEpics();
    Collection<Subtask> getAllSubtasks();

    void deleteAllTasks();
    void deleteAllEpics();
    void deleteAllSubtasks();

    Task getTaskById(int id);
    Subtask getSubtaskById(int id);
    Task getEpicById(int id);

    void addTask(Task task);
    void addEpic(Epic epic);
    void addSubtask(Subtask subtask);

    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateSubtask(Subtask subtask);

    void removeTask(int id);
    void removeEpic(int id);
    void removeSubtask(int id);

    Collection<Subtask> getAllSubtasksOfTheEpic(int epicId);
    void updateEpicStatus(int epicId);

    Queue<Task> getHistory();
}
