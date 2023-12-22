package manager;

import java.util.*;

import model.*;

public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Task> tasks;  // HashMap's key always matches task's id
    private Map<Integer, Epic> epics;  // HashMap's key always matches task's id
    private Map<Integer, Subtask> subtasks;  // HashMap's key always matches task's id
    private HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        this.historyManager = historyManager;
    }

    ////////////////////////////////////////
    @Override
    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    @Override
    public Collection<Epic> getAllEpics() {
        return epics.values();
    }

    @Override
    public Collection<Subtask> getAllSubtasks() {
        return subtasks.values();
    }
    ////////////////////////////////////////

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
    }
    ////////////////////////////////////////

    @Override
    public Task getTaskById(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Task getEpicById(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }
    ////////////////////////////////////////

    @Override
    public void addTask(Task task) {
        int taskId = tasks.size() + 1;  // taskId must be unique
        while (tasks.containsKey(taskId)) {
            ++taskId;
        }
        task.setId(taskId);

        tasks.put(taskId, task);
    }

    @Override
    public void addEpic(Epic epic) {
        int epicId = epics.size() + 1;  // epicId must be unique
        while (epics.containsKey(epicId)) {
            ++epicId;
        }
        epic.setId(epicId);

        epics.put(epicId, epic);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        int subtaskId = subtasks.size() + 1;  // subtaskId must be unique
        while (subtasks.containsKey(subtaskId)) {
            ++subtaskId;
        }
        subtask.setId(subtaskId);

        subtasks.put(subtaskId, subtask);
    }
    ////////////////////////////////////////

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }
    ////////////////////////////////////////

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
    }
    public void removeEpic(int id) {
        epics.remove(id);
    }

    @Override
    public void removeSubtask(int id) {
        subtasks.remove(id);
    }
    ////////////////////////////////////////

    @Override
    public Collection<Subtask> getAllSubtasksOfTheEpic(int epicId) {
        Epic epic = epics.get(epicId);  // shallow copy, no extra memory used (except for the reference)
        return epic.getAllSubtasks();
    }
    ////////////////////////////////////////

    @Override
    public void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        epic.updateStatus();
    }

    @Override
    public Collection<Task> getHistory() {
        return historyManager.getHistory();
    }
}
