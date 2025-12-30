package service;

import model.Status;
import model.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class TaskService {
    private final Path path = Path.of("src/files/tasks.json");

    public List<Task> listAllTasks() throws Exception {
        List<Task> allTasks = new ArrayList<>();

        if (Files.exists(path) && Files.size(path) > 0) {
            String json = Files.readString(path, StandardCharsets.UTF_8);
            allTasks.addAll(jsonToTasks(json));
        }

        if (allTasks.isEmpty()) {
            throw new Exception("There are no records.");
        }
        return allTasks;
    }

    public List<Task> listDoneTasks() throws Exception {
        List<Task> allTasks = new ArrayList<>();
        List<Task> doneTasks = new ArrayList<>();

        if (Files.exists(path) && Files.size(path) > 0) {
            String json = Files.readString(path, StandardCharsets.UTF_8);
            allTasks.addAll(jsonToTasks(json));
        }

        if (!allTasks.isEmpty()) {
            for (Task task : allTasks) {
                if (task.getStatus() == Status.DONE) {
                    doneTasks.add(task);
                }
            }
        }

        if (doneTasks.isEmpty()) {
            throw new Exception("There are no completed records.");
        }
        return doneTasks;
    }

    public List<Task> listNotDoneTasks() throws Exception {
        List<Task> allTasks = new ArrayList<>();
        List<Task> notDoneTasks = new ArrayList<>();

        if (Files.exists(path) && Files.size(path) > 0) {
            String json = Files.readString(path, StandardCharsets.UTF_8);
            allTasks.addAll(jsonToTasks(json));
        }

        if (!allTasks.isEmpty()) {
            for (Task task : allTasks) {
                if (task.getStatus() != Status.DONE) {
                    notDoneTasks.add(task);
                }
            }
        }
        if (notDoneTasks.isEmpty()) {
            throw new Exception("There are no pending records.");
        }
        return notDoneTasks;
    }

    public List<Task> listInProgressTasks() throws Exception {
        List<Task> allTasks = new ArrayList<>();
        List<Task> inProgress = new ArrayList<>();
        if (Files.exists(path) && Files.size(path) > 0) {
            String json = Files.readString(path, StandardCharsets.UTF_8);
            allTasks.addAll(jsonToTasks(json));
        }

        if (!allTasks.isEmpty()) {
            for (Task task : allTasks) {
                if (task.getStatus() == Status.IN_PROGRESS) {
                    inProgress.add(task);
                }
            }
        }

        if (inProgress.isEmpty()) {
            throw new Exception("There are no pending records.");
        }
        return inProgress;
    }


    public void createTask(List<Task> tasks) throws IOException {
        Files.createDirectories(path.getParent());

        List<Task> allTasks = new ArrayList<>();

        if (Files.exists(path) && Files.size(path) > 0) {
            String json = Files.readString(path, StandardCharsets.UTF_8);
            allTasks.addAll(jsonToTasks(json));
        }

        int id = nextId(allTasks);

        for (Task task : tasks) {
            task.setId(id++);
            task.setCreatedAt(LocalDateTime.now());
            task.setUpdatedAt(LocalDateTime.now());
            allTasks.add(task);
        }
        Files.writeString(path, tasksToJson(allTasks), StandardCharsets.UTF_8);

    }

    public List<Task> jsonToTasks(String json) {
        List<Task> tasks = new ArrayList<>();

        json = json.trim();
        if (json.equals("[]")) return tasks;

        json = json.substring(1, json.length() - 1);

        List<String> objetos = new ArrayList<>();
        StringBuilder atual = new StringBuilder();
        int nivel = 0;

        for (char c : json.toCharArray()) {

            if (c == '{') {
                nivel++;
            }

            if (nivel > 0) {
                atual.append(c);
            }

            if (c == '}') {
                nivel--;

                if (nivel == 0) {
                    objetos.add(atual.toString().trim());
                    atual.setLength(0);
                }
            }
        }
        for (String obj : objetos) {
            tasks.add(parseTask(obj));
        }

        return tasks;
    }

    private Task parseTask(String json) {
        Task task = new Task();

        json = json.replace("{", "")
                .replace("}", "")
                .trim();

        String[] campos = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");


        for (String campo : campos) {
            String[] kv = campo.split(":", 2);

            String chave = kv[0].replace("\"", "").trim();
            String valor = kv[1].replace("\"", "").trim();

            switch (chave) {
                case "id" -> task.setId(Integer.parseInt(valor));
                case "description" -> task.setDescription(valor);
                case "status" -> task.setStatus(Status.valueOf(valor));
                case "createdAt" -> task.setCreatedAt(LocalDateTime.parse(valor));
                case "updatedAt" -> task.setUpdatedAt(LocalDateTime.parse(valor));
            }

        }

        return task;
    }


    public static String tasksToJson(List<Task> tasks) {
        StringBuilder sb = new StringBuilder("[\n");

        for (int i = 0; i < tasks.size(); i++) {
            sb.append(tasks.get(i).taskToJson());
            if (i < tasks.size() - 1) sb.append(",\n");
        }

        sb.append("\n]");
        return sb.toString();
    }

    private int nextId(List<Task> allTasks) {
        return allTasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0) + 1;
    }

}
