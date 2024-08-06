package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    protected String name;
    protected String description;
    protected int id;  // must be unique
    protected Status status;  // 1 - NEW; 2 - IN_PROGRESS; 3 - DONE
    protected Duration duration;
    protected LocalDateTime startTime;


    public Task(String name, String description, int id, Status status, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStatus() {
        return status.toString();
    }

    public LocalDateTime getEndTime() { return startTime.plus(duration); }

    public LocalDateTime getStartTime() { return startTime; }

    public Duration getDuration() { return duration; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        // оставляем только id - т.к. каждый id уникален по условию задачи
        // условие: "...две задачи с одинаковым id должны выглядеть для менеджера как одна и та же. "
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
