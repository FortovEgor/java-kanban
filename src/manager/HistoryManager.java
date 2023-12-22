package manager;

import model.Task;

import java.util.Collection;
import java.util.Queue;

public interface HistoryManager {
    void add(Task task);  // mark tasks as viewed
    Collection<Task> getHistory();
}
