package manager;

import java.util.*;

import model.*;

public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Task> tasks;  // HashMap's key always matches task's id
    private Map<Integer, Epic> epics;  // HashMap's key always matches task's id
    private Map<Integer, Subtask> subtasks;  // HashMap's key always matches task's id
    HistoryManager historyManager;

    Set<Task> prioritizedTasks;  // список задач по приоритету

    public InMemoryTaskManager(HistoryManager historyManager) {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        this.historyManager = new InMemoryHistoryManager();
        prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));  // "автосортировка"
    }

    ////////////////////////////////////////
    @Override
    public Collection<Task> getAllTasks() {
        for (Task task : tasks.values()) {
            historyManager.add(task);
        }
        return tasks.values();
    }

    @Override
    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    @Override
    public Collection<Epic> getAllEpics() {
        for (Epic epic : epics.values()) {
            historyManager.add(epic);
        }
        return epics.values();
    }

    @Override
    public Collection<Subtask> getAllSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            historyManager.add(subtask);
        }
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
    public Epic getEpicById(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }
    ////////////////////////////////////////

    protected boolean isCrossing(Task task1, Task task2) {
        // математич. метод пересечения временных отрезков
        return task1.getStartTime().isBefore(task2.getEndTime()) && task2.getStartTime().isBefore(task1.getEndTime());
    }

    protected boolean isCrossingWithAnyOther(Task task, Set<Task> tasks) {
        return tasks.stream().anyMatch(currTask -> isCrossing(task, currTask));
    }
    ////////////////////////////////////////

    @Override
    public void addTask(Task task) throws Exception {
        if (isCrossingWithAnyOther(task, prioritizedTasks)) throw new Exception("");

        int taskId = tasks.size() + 1;  // taskId must be unique
        while (tasks.containsKey(taskId)) {
            ++taskId;
        }
        task.setId(taskId);

        tasks.put(taskId, task);
        prioritizedTasks.add(task);
    }

    @Override
    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        if (isCrossingWithAnyOther(subtask, prioritizedTasks)) return;
        subtasks.put(subtask.getId(), subtask);
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

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }
    ////////////////////////////////////////

    @Override
    public void removeTask(int id) {
        historyManager.remove(id);
        tasks.remove(id);
    }
    public void removeEpic(int id) {
        historyManager.remove(id);
        epics.remove(id);
    }

    @Override
    public void removeSubtask(int id) {
        historyManager.remove(id);
        // удаляем эту подзадачу из всех эпиков
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            Epic value = entry.getValue();
            value.deleteSubtask(id);
        }
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
