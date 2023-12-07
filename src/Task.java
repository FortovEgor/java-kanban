public class Task {
    protected String name;
    protected String description;
    protected int id;  // must be unique
    protected byte status;  // 1 - NEW; 2 - IN_PROGRESS; 3 - DONE

    public Task(String name, String description, int id, String status) {
        this.name = name;
        this.description = description;
        this.id = id;
        setStatus(status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String statusText) {
        switch (statusText) {
            case "NEW":
                this.status = 1;
                break;
            case "IN_PROGRESS":
                this.status = 2;
                break;
            case "DONE":
                this.status = 3;
                break;
            default:
                this.status = 0;  // unreachable
        }
    }

    protected String getStatus() {
        switch (status) {
            case 1:
                return "NEW";
            case 2:
                return "IN_PROGRESS";
            case 3:
                return "DONE";
        }
        return "EmptyStatus";
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
