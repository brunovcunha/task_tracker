package controller;

import model.Task;
import service.TaskService;

import java.io.IOException;
import java.util.List;

public class TaskController {
    final private TaskService service = new TaskService();

    public Task findById(Integer id) {
        try {
            return service.findTaskById(id);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
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
            System.out.println(e.getMessage());
            return null;
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
            System.out.println(e.getMessage());
            return null;
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
            System.out.println(e.getMessage());
            return null;
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
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void deleteTask(Integer id) {
        try {
            service.deleteTaskById(id);
            System.out.println("DELETED TASK: " + id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateTask(Task task) {
        try {
            service.updateTaskById(task);

            System.out.println("task-cli update " + task.getId() + " " + task.getDescription() + " " + task.getStatus());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTasks(List<Task> tasks) {
        try {
            service.createTask(tasks);
        } catch (Exception e) {
            System.out.println("Não foi possível cadastrar Task(s): " + e.getMessage());
        }

    }
}
