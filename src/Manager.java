import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Manager {
    private HashMap<Integer, Task> tasks;  // HashMap's key always matches task's id
    private HashMap<Integer, Epic> epics;  // HashMap's key always matches task's id
    private HashMap<Integer, Subtask> subtasks;  // HashMap's key always matches task's id

    public Manager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    ////////////////////////////////////////
    public String getAllTasks() {
        String res = "Tasks=[";
        for (Task task : tasks.values()) {
            res += task + ", ";
        }
        res += "]";
        return res;
    }

    public String getAllEpics() {
        String res = "Epics=[";
        for (Epic epic : epics.values()) {
            res += epic + ", ";
        }
        res += "]";
        return res;
    }

    public String getAllSubtasks() {
        String res = "Subtasks=[";
        for (Subtask subtask : subtasks.values()) {
            res += subtask + ", ";
        }
        res += "]";
        return res;
    }
    ////////////////////////////////////////

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
    }
    ////////////////////////////////////////

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public Task getEpicById(int id) {
        return epics.get(id);
    }
    ////////////////////////////////////////

    public void addTask(Task task) {
        int taskId = tasks.size() + 1;  // taskId must be unique
        while (tasks.containsKey(taskId)) {
            ++taskId;
        }
        task.setId(taskId);

        tasks.put(taskId, task);
    }

    public void addEpic(Epic epic) {
        int epicId = epics.size() + 1;  // epicId must be unique
        while (epics.containsKey(epicId)) {
            ++epicId;
        }
        epic.setId(epicId);

        epics.put(epicId, epic);
    }

    public void addSubtask(Subtask subtask) {
        int subtaskId = subtasks.size() + 1;  // subtaskId must be unique
        while (subtasks.containsKey(subtaskId)) {
            ++subtaskId;
        }
        subtask.setId(subtaskId);

        subtasks.put(subtaskId, subtask);
    }
    ////////////////////////////////////////

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }
    ////////////////////////////////////////

    public void removeTask(int id) {
        tasks.remove(id);
    }
    public void removeEpic(int id) {
        epics.remove(id);
    }

    public void removeSubtask(int id) {
        subtasks.remove(id);
    }
    ////////////////////////////////////////

    public void getAllSubtasksOfTheEpic(int epicId) {
        Epic epic = epics.get(epicId);  // shallow copy, no extra memory used (except for the reference)

        Integer[] subtasksIds = epic.getAllSubtasksIds();

        for (int i = 0; i < subtasksIds.length; ++i) {
            System.out.println(subtasks.get(i));
        }
    }
    ////////////////////////////////////////

    public void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        Integer[] subtasksIds = epic.getAllSubtasksIds();

        ArrayList<Subtask> epicSubtasks = new ArrayList<>();  // collect all subtasks for out epic

        for (Subtask subtask : subtasks.values()) {
            if (Arrays.asList(subtasksIds).contains(subtask.getId())) {
                epicSubtasks.add(subtask);
            }
        }

        epic.updateStatus(epicSubtasks);
    }
}
