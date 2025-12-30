import model.Status;
import model.Task;
import service.TaskService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("iniciando tasks: ");
        Task task = new Task("teste", Status.DONE);

        List<Task> tasks = new ArrayList<>();

        tasks.add(task);
        TaskService service = new TaskService();

        service.createTask(tasks);
    }
}