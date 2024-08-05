package test;

import manager.FileBackedTaskManager;
import manager.InMemoryHistoryManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.*;

public class FileTest {
    @Test
    public void importExport() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        FileBackedTaskManager taskManager = null;
        File file = File.createTempFile("test", "csv");
        // write data to file
        Writer fileWriter = new FileWriter(file, false);
        final String firstLine = "id,type,name,status,description,duration,startTime,epic\n";
        fileWriter.write(firstLine);
        fileWriter.write("3,TASK,task3,NEW,my third task,10,01.02.2222|11:11,1\n");
        fileWriter.flush();
        fileWriter.close();

        taskManager = new FileBackedTaskManager(historyManager, file.getPath());
        assertEquals(1, taskManager.getAllTasks().size());

        final Task task1 = new Task("task1_name", "task1_description", 3, Status.NEW, Duration.ZERO, LocalDateTime.now());
        final Task task2 = new Task("task2_name", "task2_description", 4, Status.NEW, Duration.ZERO, LocalDateTime.now());

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        assertEquals(2, taskManager.getAllTasks().size());  // должно остаться 2 из-за наложений по времени как раз таки

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
        assertEquals(2, tasks);
    }
}