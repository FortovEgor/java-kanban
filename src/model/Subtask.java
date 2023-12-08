package model;

public class Subtask extends Task {
    private int epicId;  // each task knows which epic it belongs to

    public Subtask(String name, String description, int id, Status status, int epicId) {
        super(name, description, id, status);
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
}
