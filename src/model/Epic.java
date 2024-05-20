package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;
    protected LocalDateTime endTime;

    public Epic(String name, String description, int id, Status status, ArrayList<Subtask> subtasks) {
        super(name, description, id, status, Duration.ZERO, LocalDateTime.now());
        this.subtasks = subtasks;
        updateTime();
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

    public void updateTime() {
        // @TODO: calculate startTime & then endTime according to all tasks in the epic
        LocalDateTime earliestDateTime = LocalDateTime.MAX;
        for (int i = 0; i < subtasks.size(); ++i) {
            if (subtasks.get(i).getStartTime().isBefore(earliestDateTime)) {
                earliestDateTime = subtasks.get(i).getStartTime();
            }
        }
        this.startTime = earliestDateTime;

        LocalDateTime latestDateTime = LocalDateTime.MIN;
        for (int i = 0; i < subtasks.size(); ++i) {
            if (subtasks.get(i).getStartTime().isAfter(latestDateTime)) {
                latestDateTime = subtasks.get(i).getStartTime();
            }
        }
        this.endTime = latestDateTime;

        this.duration = Duration.between(startTime, endTime);
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
