package manager;

import model.Task;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class InMemoryHistoryManager implements HistoryManager {
    private Queue<Task> tasksHistory;

    public InMemoryHistoryManager() {
        tasksHistory = new LinkedList<>();
    }

    @Override
    public void add(Task task) {
        if (tasksHistory.size() >= 10) {
            tasksHistory.poll();
        }
        tasksHistory.add(task);
    }

    @Override
    public Collection<Task> getHistory() {
        return tasksHistory;
    }
}
