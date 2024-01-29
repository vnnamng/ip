import java.util.Scanner;

public class Ui {

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    private static final String HORIZONTAL_LINE =
            "_______________________________________";
    public void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showWelcome() {
        System.out.println("Hello");
    }

    public void showFarewell() {
        this.scanner.close();
        System.out.println("bye");
    }

    public void showTaskList(TaskList taskList) {
        this.showTaskListStatus(taskList);
        for (Task task : taskList) {
            System.out.println(task);
        }
    }

    public void showAddedTask(Task task) {
        System.out.println("added: " + task.toString());
    }

    public void showTaskListStatus(TaskList taskList) {
        System.out.printf("Now you have %d tasks in the list.%n\n", taskList.size());
    }

    public void showMarkedTask(Task task) {
        System.out.println("marked: " + task.toString());
    }

    public void showUnmarkedTask(Task task) {
        System.out.println("unmarked: " + task.toString());
    }

    public void showDeletedTask(Task task) {
        System.out.println("deleted: " + task.toString());
    }
}
