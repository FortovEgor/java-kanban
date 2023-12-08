package model;

public class Task {
    protected String name;
    protected String description;
    protected int id;  // must be unique
    protected Status status;  // 1 - NEW; 2 - IN_PROGRESS; 3 - DONE

    public Task(String name, String description, int id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
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
