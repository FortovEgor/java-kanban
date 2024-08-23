package test;

import manager.TaskManager;
import org.junit.jupiter.api.Test;

// базовый класс с тестами на каждый метод из интерфейса TaskManager
public abstract class TaskManagerTest<T extends TaskManager> {
    @Test
    public abstract void getAllTasksTest();

    @Test
    public abstract void getAllEpicsTest();

    @Test
    public abstract void getAllSubtasksTest();

    @Test
    public abstract void getPrioritizedTasksTest();

    @Test
    public abstract void deleteAllTasksTest();

    @Test
    public abstract void deleteAllEpicsTest();

    @Test
    public abstract void deleteAllSubtasksTest();

    @Test
    public abstract void getTaskByIdTest();

    @Test
    public abstract void getSubtaskByIdTest();

    @Test
    public abstract void getEpicByIdTest();

    @Test
    public abstract void updateTaskTest();

    @Test
    public abstract void updateEpicTest();

    @Test
    public abstract void updateSubtaskTest();

    @Test
    public abstract void removeTaskTest();

    @Test
    public abstract void removeEpicTest();

    @Test
    public abstract void removeSubtaskTest();

    @Test
    public abstract void getAllSubtasksOfTheEpicTest();

    @Test
    public abstract void updateEpicStatusTest();

    @Test
    public abstract void getHistory();
}
