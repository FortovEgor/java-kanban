package manager;

import model.Task;

import java.util.Collection;
import java.util.Queue;
import java.util.List;

public interface HistoryManager {
    void add(Task task);  // mark tasks as viewed
    void remove(int id);
    List<Task> getHistory();
}
