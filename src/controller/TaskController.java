package controller;

import model.Task;
import service.TaskService;

import java.util.ArrayList;
import java.util.List;

public class TaskController {
    final private TaskService service = new TaskService();

    public String allTasks() {
        StringBuilder sb = new StringBuilder();

        try {
            List<Task> tasks = service.listAllTasks();

            sb.append("---- All Tasks ----\n");
            for (Task task : tasks) {
                sb.append(task.toString());
                sb.append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String doneTasks() {
        StringBuilder sb = new StringBuilder();

        try {
            List<Task> tasks = service.listDoneTasks();

            sb.append("---- Done Tasks ----\n");
            for (Task task : tasks) {
                sb.append(task.toString());
                sb.append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String notDoneTasks() {
        StringBuilder sb = new StringBuilder();

        try {
            List<Task> tasks = service.listNotDoneTasks();

            sb.append("---- Not Done Tasks ----\n");
            for (Task task : tasks) {
                sb.append(task.toString());
                sb.append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String inProgressTasks() {
        StringBuilder sb = new StringBuilder();

        try {
            List<Task> tasks = service.listInProgressTasks();

            sb.append("---- In Progress Tasks ----\n");
            for (Task task : tasks) {
                sb.append(task.toString());
                sb.append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
