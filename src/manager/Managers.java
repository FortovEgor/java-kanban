package manager;

public class Managers {
    public static TaskManager getDefault() {
        // changed from InMemoryTaskManager to test
        return new FileBackedTaskManager(getDefaultHistory(),
                "/Users/egorfortov/Desktop/YandexPracticum/java-kanban/test.csv");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
