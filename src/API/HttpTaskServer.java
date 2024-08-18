package API;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import manager.TaskManager;
import model.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLOutput;

import static manager.Managers.getInMemoryTaskManager;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private static TaskManager taskManager;

    public void main() throws IOException {
        taskManager = getInMemoryTaskManager();

        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler());
        httpServer.createContext("/subtasks", new TaskHandler());
        httpServer.createContext("/epics", new TaskHandler());
        httpServer.createContext("/history", new TaskHandler());
        httpServer.createContext("/prioritized", new TaskHandler());
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    static class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("Началась обработка /hello запроса от клиента.");

            String method = httpExchange.getRequestMethod();
            String response = "";
            switch(method) {
                case "GET":
                    String path = httpExchange.getRequestURI().getPath();
                    String[] splitted = path.split("/");
                    if (splitted.length == 2) {  // endpoint "/tasks"
                        System.out.println(taskManager.getAllTasks());  // @TODO: into GSON
                    } else if (splitted.length == 3) {  // endpoint "/tasks/{id}"
                        final Integer id = Integer.parseInt(splitted[2]);
                        System.out.println(taskManager.getTaskById(id));  // @TODO: into GSON
                    } else {
                        response = "No such endpoint!";
                    }
                    break;
                case "POST":
                    path = httpExchange.getRequestURI().getPath();
                    splitted = path.split("/");
                    if (splitted.length == 2) {  // endpoint "/tasks"
                        // @TODO: get task from body (.json)
//                        System.out.println(taskManager.addTask(new Task()));  // @TODO: into GSON
                    } else if (splitted.length == 3) {  // endpoint "/tasks/{id}"
                        final Integer id = Integer.parseInt(splitted[2]);
                        // @TODO: get task from body (.json)
//                        System.out.println(taskManager.updateTask(new Task()));  // @TODO: into GSON
                    } else {
                        response = "No such endpoint!";
                    }
                    break;
                case "DELETE":
                    path = httpExchange.getRequestURI().getPath();
                    splitted = path.split("/");
                    if (splitted.length == 3) {  // endpoint "/tasks/{id}"
                        final Integer id = Integer.parseInt(splitted[2]);
//                        System.out.println(taskManager.removeTask(id));  // @TODO: into GSON
                    } else {
                        response = "No such endpoint!";
                    }
                    break;
                default:
                    response = "Вы использовали какой-то другой метод!";
            }

//            String response = "Hey! Glad to see you on our server.";
            httpExchange.sendResponseHeaders(200, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
