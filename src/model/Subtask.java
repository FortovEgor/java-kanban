package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private int epicId;  // each task knows which epic it belongs to

    public Subtask(String name, String description, int id, Status status, int epicId, Duration duration, LocalDateTime startTime) throws IllegalArgumentException {
        super(name, description, id, status, duration, startTime);
        if (id == epicId) {
            throw new IllegalArgumentException("объект Subtask нельзя сделать своим же эпиком!");
        }
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", status='" + getStatus() + '\'' +
                ", epicId='" + epicId + '\'' +
                '}';
    }

    public int getEpicId() {
        return epicId;
    }
}
