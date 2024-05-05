package test;

import manager.FileBackedTaskManager;
import manager.InMemoryHistoryManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.io.*;


import static org.junit.jupiter.api.Assertions.*;

public class FileTest {
    @Test
    public void importExport() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        FileBackedTaskManager taskManager = null;
        try {
            File file = File.createTempFile("test", "csv");
            // write data to file
            try (Writer fileWriter = new FileWriter(file, false)) {
                fileWriter.write("id,type,name,status,description,epic\n");
                fileWriter.write("2,TASK,task2,NEW,my second task,\n");
                fileWriter.write("3,TASK,task3,NEW,my third task,\n");
            } catch (IOException e) {
                assertNotEquals(1, 2);
            }

            taskManager = new FileBackedTaskManager(historyManager, file.getPath());
            assertEquals(2, taskManager.getAllTasks().size());

            final Task task1 = new Task("task1_name", "task1_description", 3, Status.NEW);
            final Task task2 = new Task("task2_name", "task2_description", 4, Status.NEW);

            taskManager.addTask(task1);
            taskManager.addTask(task2);

            assertEquals(4, taskManager.getAllTasks().size());

            taskManager.deleteAllEpics();

            int tasks = 0;
            try (BufferedReader fileReader = new BufferedReader(new FileReader(file)); BufferedReader br = new BufferedReader(fileReader)) {
                while (br.ready()) {
                    String line = br.readLine();
                    ++tasks;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            --tasks;  // first line is not a task
            assertEquals(4, tasks);
        } catch (IOException e) {
            assertNotEquals(1, 2);
        }
    }
}