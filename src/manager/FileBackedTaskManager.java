package manager;

import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FileBackedTaskManager extends InMemoryTaskManager {

    class ManagerSaveException extends RuntimeException {
        public ManagerSaveException(String message) {
            super(message);
        }
    }

    final String file;
    public FileBackedTaskManager(HistoryManager historyManager, final String fileName) {
        super(historyManager);
        this.file = fileName;
        File file = new File(fileName);
        loadFromFile(file);
    }

    // Note: с т з уже написанного кода логичнее сделать данный метод НЕ static
    void loadFromFile(File file) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file)); BufferedReader br = new BufferedReader(fileReader)) {
            boolean firstLine = true;
            while (br.ready()) {
                String line = br.readLine();
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                Task task = fromString(line);
                if (task instanceof Epic) {
                    epics.put(task.getId(), (Epic) task);
                } else if (task instanceof Subtask) {
                    subtasks.put(task.getId(), (Subtask) task);
                } else {
                    tasks.put(task.getId(), task);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /// get task in format "1,TASK,Task1,NEW,Description task1,"
    private String toString(Task task, TaskType type) {
        return task.getId() + "," + type.name() + "," + task.getName() + "," +
                task.getStatus() + "," + task.getDescription();
    }

    /// creates task from strings stored in format "1,TASK,Task1,NEW,Description task1,"
    private Task fromString(String value) throws Exception {
        String[] parts = value.split(",");
        var type = parts[1];
        if (type.equals(TaskType.TASK.toString())) {
            return new Task(parts[2], parts[4], Integer.parseInt(parts[0]), Status.valueOf(parts[3]));
        } else if (type.equals(TaskType.EPIC.toString())) {
            return new Epic(parts[2], parts[4], Integer.parseInt(parts[0]), Status.valueOf(parts[3]),
                    new ArrayList<>());
        } else if (type.equals(TaskType.SUBTASK.toString())) {
            return new Subtask(parts[2], parts[4], Integer.parseInt(parts[0]), Status.valueOf(parts[3]),
                    Integer.parseInt(parts[5]));
        } else {
            throw new Exception("unknown task type");
        }
    }

    /// saves all tasks, subtasks & epics into file
    // i.e. each time refresh full file with actual data
    private void save() {
        try (Writer fileWriter = new FileWriter(file, false)) {
            fileWriter.write("id,type,name,status,description,epic\n");

            final int tasksNumber = tasks.size() + epics.size() + subtasks.size();
            int savedTasksNumber = 0;
            boolean isLastLine;
            for (Task task : tasks.values()) {
                isLastLine = (savedTasksNumber + 1 == tasksNumber);
                fileWriter.write(toString(task, TaskType.TASK) + (isLastLine ? "\n" : ",\n"));
                ++savedTasksNumber;
            }
            // save all epics
            for (Task epic : epics.values()) {
                isLastLine = (savedTasksNumber + 1 == tasksNumber);
                fileWriter.write(toString(epic, TaskType.EPIC) + (isLastLine ? "\n" : ",\n"));
                ++savedTasksNumber;
            }
            // save all subtasks
            for (Subtask subtask : subtasks.values()) {
                isLastLine = (savedTasksNumber + 1 == tasksNumber);
                fileWriter.write(toString(subtask, TaskType.SUBTASK) + "," + subtask.getEpicId() + (isLastLine ? "\n" : ",\n"));
                ++savedTasksNumber;
            }
        } catch (IOException e) {
            throw new ManagerSaveException(e.toString());
        }
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }
    public void removeEpic(int id) {
        super.removeEpic(id);
        save();
    }

    @Override
    public void removeSubtask(int id) {
        super.removeSubtask(id);
        save();
    }

    @Override
    public void updateEpicStatus(int epicId) {
        super.updateEpicStatus(epicId);
        save();
    }
}
