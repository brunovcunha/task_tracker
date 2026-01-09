import controller.TaskController;
import model.Status;
import model.Task;
import service.TaskService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        TaskService service = new TaskService();
        TaskController controller = new TaskController();

        boolean menu = true;
        while (menu) {
            System.out.println("-----WELCOME TO TASK TRACKER-----");
            System.out.println("1 - List All Tasks");
            System.out.println("2 - List Done Tasks");
            System.out.println("3 - List Not Done Tasks");
            System.out.println("4 - List In Progress Tasks");
            System.out.println("5 - Create Task");
            System.out.println("6 - Update Task");
            System.out.println("7 - Delete Task");
            System.out.println("8 - Close");
            System.out.println("---------------------------------");

            int n = s.nextInt();
            s.nextLine();

            switch (n) {
                case 1:
                    System.out.println(controller.allTasks());

                    System.out.println("\nPress ENTER to continue...");
                    s.nextLine();
                    break;
                case 2:
                    System.out.println(controller.doneTasks());
                    System.out.println("\nPress ENTER to continue...");
                    s.nextLine();
                    break;
                case 3:
                    System.out.println(controller.notDoneTasks());
                    System.out.println("\nPress ENTER to continue...");
                    s.nextLine();
                    break;
                case 4:
                    System.out.println(controller.inProgressTasks());
                    System.out.println("\nPress ENTER to continue...");
                    s.nextLine();
                    break;
                case 5:
                    System.out.println("Create Tasks");
                    System.out.println("How many taks do you want to create?");
                    int nTasks = s.nextInt();
                    s.nextLine();
                    String name;
                    List<Task> tasks = new ArrayList<>();
                    for (int i = 0; i < nTasks; i++) {
                        System.out.printf("Task name: ");
                        name = s.nextLine();

                        while (name == null || name.isEmpty()) {
                            System.out.println("Please provide a valid description.");
                            name = s.nextLine();
                        }
                        boolean menuStatus = true;
                        int status = 0;
                        Status status1 = Status.TODO;
                        while (menuStatus) {
                            System.out.println("Status: ");
                            System.out.println("1 - Todo");
                            System.out.println("2 - In Progress");
                            System.out.println("3 - Done");

                            status = s.nextInt();
                            s.nextLine();
                            if (status == 1) {
                                menuStatus = false;
                            } else if (status == 2) {
                                status1 = Status.IN_PROGRESS;
                                menuStatus = false;
                            } else if (status == 3) {
                                status1 = Status.DONE;
                                menuStatus = false;
                            }
                        }

                        Task task = new Task(name, status1);
                        tasks.add(task);
                    }
                    controller.createTasks(tasks);
                    System.out.println("Tasks created");
                    System.out.println("\nPress ENTER to continue...");
                    s.nextLine();
                    break;

                case 6:
                    System.out.println("Update Task by ID");
                    System.out.println("Id: ");
                    int id = s.nextInt();
                    s.nextLine();
                    Task taskToUpdate = controller.findById(id);

                    if (taskToUpdate == null) {
                        System.out.println("Task not found.");
                        break;
                    }

                    System.out.println(taskToUpdate);
                    String nameTask = taskToUpdate.getDescription();
                    Status status1 = taskToUpdate.getStatus();

                    boolean validate = true;
                    while (validate) {
                        System.out.println("1 - Set name");
                        System.out.println("2 - Set Status");
                        System.out.println("3 - Done");

                        int chose = s.nextInt();
                        s.nextLine();

                        if (chose == 1) {
                            nameTask = s.nextLine();
                        }
                        if (chose == 2) {
                            boolean menuStatus = true;
                            int status = 0;

                            while (menuStatus) {
                                System.out.println("Status: ");
                                System.out.println("1 - Todo");
                                System.out.println("2 - In Progress");
                                System.out.println("3 - Done");

                                status = s.nextInt();
                                s.nextLine();
                                if (status == 1) {
                                    status1 = Status.TODO;
                                    menuStatus = false;
                                } else if (status == 2) {
                                    status1 = Status.IN_PROGRESS;
                                    menuStatus = false;
                                } else if (status == 3) {
                                    status1 = Status.DONE;
                                    menuStatus = false;
                                }
                            }
                        }

                        if (chose == 3) {
                            validate = false;
                        }
                        taskToUpdate.setStatus(status1);
                        taskToUpdate.setDescription(nameTask);
                    }

                    controller.updateTask(taskToUpdate);
                    System.out.println("\nPress ENTER to continue...");
                    s.nextLine();
                    break;

                case 7:
                    System.out.println("Delete Task");
                    System.out.printf("Id: ");
                    id = s.nextInt();
                    s.nextLine();
                    Task taskToDelete = controller.findById(id);
                    if (taskToDelete == null) {
                        System.out.println("Task not found.");
                        break;
                    }
                    System.out.println("\nTask to delete: ");
                    System.out.println(taskToDelete);

                    System.out.println("Do you want to delete this task? (s/n)");

                    String delete = s.nextLine();

                    if (delete.equalsIgnoreCase("s")) {
                        controller.deleteTask(id);
                    } else {
                        System.out.println("Cancellation of deletion....");
                    }
                    System.out.println("\nPress ENTER to continue...");
                    s.nextLine();
                    break;

                case 8:
                    menu = false;
                    break;
                default:
                    System.out.println("Invalid option");

            }
        }
    }
}