package model;

import java.util.ArrayList;
import java.util.Map;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;

    public Epic(String name, String description, int id, Status status, ArrayList<Subtask> subtasks) {
        super(name, description, id, status);
        this.subtasks = subtasks;
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return subtasks;
    }

    public void deleteSubtask(int id) {
        subtasks.removeIf(elem -> elem.getId() == id);
    }

    public void updateStatus() {  // suppose we get correct arrayList here
        if (subtasks.size() == 0) {
            status = Status.NEW;  // NEW
            return;
        }

        int newStatusCount = 0;
        int doneStatusCount = 0;
        for (Subtask subtask : this.subtasks) {
            switch (subtask.getStatus()) {
                case "NEW":
                    ++newStatusCount;
                    break;
                case "DONE":
                    ++doneStatusCount;
                    break;
            }
        }

        if (newStatusCount == subtasks.size()) {
            status = Status.NEW;
            return;
        }

        if (doneStatusCount == subtasks.size()) {
            status = Status.DONE;
            return;
        }

        status = Status.IN_PROGRESS;  // IN_PROGRESS
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", status='" + getStatus() + '\'' +
                ", taskIds=" + subtasks +
                '}';
    }
}
