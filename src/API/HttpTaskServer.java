package API;

import com.google.gson.Gson;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import manager.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLOutput;

import static manager.Managers.getInMemoryTaskManager;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private static TaskManager taskManager;
    private static Gson gson;

    public void main() throws IOException {
        taskManager = getInMemoryTaskManager();

        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler());
        httpServer.createContext("/subtasks", new SubTaskHandler());
        httpServer.createContext("/epics", new EpicHandler());
        httpServer.createContext("/history", new HistoryHandler());
        httpServer.createContext("/prioritized", new PrioritizedHandler());
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    static class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("Началась обработка /tasks запроса от клиента.");

            String method = httpExchange.getRequestMethod();
            String body = httpExchange.getResponseBody().toString();
            String response = "";
            String path = httpExchange.getRequestURI().getPath();
            String[] splitted = path.split("/");
            int responseCode = 200;
            switch(method) {
                case "GET":
                    if (splitted.length == 2) {  // endpoint "/subtasks"
                        response = gson.toJson(taskManager.getAllTasks());
                    } else if (splitted.length == 3) {  // endpoint "/subtasks/{id}"
                        final Integer id = Integer.parseInt(splitted[2]);
                        Task task = taskManager.getTaskById(id);
                        if (task == null) {
                            responseCode = 404;
                            break;
                        }
                        response = gson.toJson(task);
                    } else {
                        response = "No such endpoint!";
                        responseCode = 404;
                    }
                    break;
                case "POST":
                    if (splitted.length == 2) {  // endpoint "/tasks"
                        Task task = gson.fromJson(body, Task.class);
                        try {
                            taskManager.addTask(task);
                        } catch (Exception e) {
                            responseCode = 406;
                            response = "Internal Error";
                            break;
                        }
                    } else if (splitted.length == 3) {  // endpoint "/tasks/{id}"
                        final Integer id = Integer.parseInt(splitted[2]);
                        Task task = taskManager.getTaskById(id);
                        taskManager.updateTask(task);
                    } else {
                        response = "No such endpoint!";
                        responseCode = 404;
                        break;
                    }
                    responseCode = 201;
                    break;
                case "DELETE":
                    if (splitted.length == 3) {  // endpoint "/tasks/{id}"
                        final Integer id = Integer.parseInt(splitted[2]);
                        taskManager.removeTask(id);
                    } else {
                        response = "No such endpoint!";
                        responseCode = 404;
                    }
                    break;
                default:
                    response = "Вы использовали какой-то другой метод!";
                    responseCode = 404;
            }

            httpExchange.sendResponseHeaders(responseCode, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    static class SubTaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("Началась обработка /subtasks запроса от клиента.");

            String method = httpExchange.getRequestMethod();
            String body = httpExchange.getResponseBody().toString();
            String response = "";
            String path = httpExchange.getRequestURI().getPath();
            String[] splitted = path.split("/");
            int responseCode = 200;
            switch (method) {
                case "GET":
                    if (splitted.length == 2) {  // endpoint "/subtasks"
                        response = gson.toJson(taskManager.getAllSubtasks());
                    } else if (splitted.length == 3) {  // endpoint "/subtasks/{id}"
                        final Integer id = Integer.parseInt(splitted[2]);
                        Subtask subtask = taskManager.getSubtaskById(id);
                        if (subtask == null) {
                            responseCode = 404;
                            break;
                        }
                        response = gson.toJson(subtask);
                    } else {
                        response = "No such endpoint!";
                        responseCode = 404;
                    }
                    break;
                case "POST":
                    if (splitted.length == 2) {  // endpoint "/tasks"
                        Subtask subtask = gson.fromJson(body, Subtask.class);
                        try {
                            taskManager.addSubtask(subtask);
                        } catch (Exception e) {
                            responseCode = 406;
                            response = "Internal Error";
                            break;
                        }
                    } else if (splitted.length == 3) {  // endpoint "/tasks/{id}"
                        final Integer id = Integer.parseInt(splitted[2]);
                        Subtask subtask = taskManager.getSubtaskById(id);
                        taskManager.updateSubtask(subtask);
                    } else {
                        response = "No such endpoint!";
                        responseCode = 404;
                        break;
                    }
                    responseCode = 201;
                    break;
                case "DELETE":
                    if (splitted.length == 3) {  // endpoint "/tasks/{id}"
                        final Integer id = Integer.parseInt(splitted[2]);
                        taskManager.removeTask(id);
                    } else {
                        response = "No such endpoint!";
                        responseCode = 404;
                    }
                    break;
                default:
                    response = "Вы использовали какой-то другой метод!";
                    responseCode = 404;
            }

            httpExchange.sendResponseHeaders(responseCode, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    static class EpicHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("Началась обработка /epics запроса от клиента.");

            String method = httpExchange.getRequestMethod();
            String body = httpExchange.getResponseBody().toString();
            String response = "";
            String path = httpExchange.getRequestURI().getPath();
            String[] splitted = path.split("/");
            int responseCode = 200;
            switch(method) {
                case "GET":
                    if (splitted.length == 2) {  // endpoint "/epics"
                        response = gson.toJson(taskManager.getAllEpics());
                    } else if (splitted.length == 3) {  // endpoint "/epics/{id}"
                        final Integer id = Integer.parseInt(splitted[2]);
                        Epic epic = taskManager.getEpicById(id);
                        if (epic == null) {
                            responseCode = 404;
                            break;
                        }
                        response = gson.toJson(epic);
                    } else if (splitted.length == 4) {  // endpoint "/epics/{id}/subtasks"
                        final Integer id = Integer.parseInt(splitted[2]);
                        Epic epic = taskManager.getEpicById(id);
                        if (epic == null) {
                            responseCode = 404;
                            break;
                        }
                        response = gson.toJson(taskManager.getAllSubtasksOfTheEpic(id));
                    } else {
                        response = "No such endpoint!";
                        responseCode = 404;
                    }
                    break;
                case "POST":
                    if (splitted.length == 2) {  // endpoint "/epics"
                        Epic epic = gson.fromJson(body, Epic.class);
                        try {
                            taskManager.addEpic(epic);
                            responseCode = 201;
                        } catch (Exception e) {
                            responseCode = 406;
                            response = "Internal Error";
                        }
                    } else if (splitted.length == 3) {  // endpoint "/epics/{id}"
                        final Integer id = Integer.parseInt(splitted[2]);
                        Epic epic = taskManager.getEpicById(id);
                        taskManager.updateEpic(epic);
                        responseCode = 201;
                    } else {
                        response = "No such endpoint!";
                        responseCode = 404;
                        break;
                    }
                    break;
                case "DELETE":
                    if (splitted.length == 3) {  // endpoint "/tasks/{id}"
                        final Integer id = Integer.parseInt(splitted[2]);
                        taskManager.removeSubtask(id);
                    } else {
                        response = "No such endpoint!";
                    }
                    break;
                default:
                    response = "Вы использовали какой-то другой метод!";
                    responseCode = 404;
            }

            httpExchange.sendResponseHeaders(responseCode, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    static class HistoryHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("Началась обработка /history запроса от клиента.");

            String method = httpExchange.getRequestMethod();
            String body = httpExchange.getResponseBody().toString();
            String response = "";
            String path = httpExchange.getRequestURI().getPath();
            String[] splitted = path.split("/");
            int responseCode = 200;
            if (method.equals("GET")) {
                response = gson.toJson(taskManager.getHistory());
            } else {
                response = "Вы использовали какой-то другой метод!";
                responseCode = 404;
            }

            httpExchange.sendResponseHeaders(responseCode, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    static class PrioritizedHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("Началась обработка /prioritized запроса от клиента.");

            String method = httpExchange.getRequestMethod();
            String body = httpExchange.getResponseBody().toString();
            String response = "";
            String path = httpExchange.getRequestURI().getPath();
            String[] splitted = path.split("/");
            int responseCode = 200;
            if (method.equals("GET")) {
                response = gson.toJson(taskManager.getPrioritizedTasks());
            } else {
                response = "Вы использовали какой-то другой метод!";
                responseCode = 404;
            }

            httpExchange.sendResponseHeaders(responseCode, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
