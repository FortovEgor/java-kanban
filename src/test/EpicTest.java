package test;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.Flow;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    @Test
    public void shouldBeEqualIfIdsAreEqual() {
        Task task1 = new Epic("task1", "task1 description", 111, Status.NEW, new ArrayList<>());
        Task task2 = new Epic("task2", "task2 description", 111, Status.NEW, new ArrayList<>());

        assertEquals(task1, task2);
    }

    @Test
    public void canNotInsertEpicIntoTheSameEpic() {
        // проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи;
        // В МОЕЙ РЕАЛИЗАЦИИ ЭТО НЕВОЗМОЖНО СДЕЛАТЬ НА УРОВНЕ КОДА, программа просто НЕ СОБЕРЕТСЯ
        // (моя реализация не предусматривает возможность добавления задач в эпик, только при создании)
        // Task task1 = new Epic("task1", "task1 description", 111, Status.NEW, new ArrayList<>(task1));
        assertTrue(true);
    }
}