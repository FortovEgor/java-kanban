import java.util.ArrayList;
import java.util.Arrays;

public class Epic extends Task {
    private Integer[] subtaskIds;  // each epic knows which subtasks belong to it

    public Epic(String name, String description, int id, String status, Integer[] subtaskIds) {
        super(name, description, id, status);
        this.subtaskIds = subtaskIds;
    }

    public Integer[] getAllSubtasksIds() {
        return subtaskIds;
    }

    public void updateStatus(ArrayList<Subtask> subtaskArrayList) {  // suppose we get correct arrayList here
        if (subtaskIds.length == 0) {
            status = 1;  // NEW
            return;
        }

        int newStatusCount = 0;
        int doneStatusCount = 0;
        for (Subtask subtask : subtaskArrayList) {
            switch (subtask.getStatus()) {
                case "NEW":
                    ++newStatusCount;
                    break;
                case "DONE":
                    ++doneStatusCount;
                    break;
            }
        }

        if (newStatusCount == subtaskArrayList.size()) {
            status = 1;
            return;
        }

        if (doneStatusCount == subtaskArrayList.size()) {
            status = 3;
            return;
        }

        status = 2;  // IN_PROGRESS
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", status='" + getStatus() + '\'' +
                ", taskIds=" + Arrays.toString(subtaskIds) +
                '}';
    }
}
