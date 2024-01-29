package duke.task;

import duke.exception.ChatBotParameterException;
import duke.parser.Parser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskList implements Iterable<Task> {
    protected List<Task> tasks;


    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Returns an iterator over elements of type {@code duke.task.Task}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }

    public Task addToDo(String description, boolean isDone) {
        ToDo toDo = new ToDo(description, isDone);
        this.addTask(toDo);
        return toDo;
    }

    public Task addToDo(String parameters) throws ChatBotParameterException {
        String[] parametersArr = Parser.parseToDo(parameters);
        return this.addToDo(parametersArr[0], false);
    }


    public Task addDeadline(String description, String by, boolean isDone) {
        LocalDateTime byDateTime = Parser.parseDateTime(by);
        Deadline deadline = new Deadline(description, byDateTime, isDone);
        this.addTask(deadline);
        return deadline;
    }

    public Task addDeadline(String parameters) throws ChatBotParameterException {
        String[] parametersArr = Parser.parseDeadline(parameters);
        return this.addDeadline(parametersArr[0], parametersArr[1], false);

    }

    public Task addEvent(String parameters) throws ChatBotParameterException {
        String[] parametersArr = Parser.parseEvent(parameters);
        return this.addEvent(parametersArr[0], parametersArr[1], parametersArr[2], false);
    }


    public Task addEvent(String description, String from, String to, boolean isDone) {
        Event event = new Event(description, from, to, isDone);
        this.addTask(event);
        return event;
    }

    public int size() {
        return this.tasks.size();
    }

    public Task markTaskAsDone(String parameters) throws ChatBotParameterException {
        int taskNumber = Parser.parseInteger(parameters);
        Task taskToBeMarked;
        try {
            taskToBeMarked = this.tasks.get(taskNumber - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new ChatBotParameterException("The task does not exists in the task list.");
        }
        if (taskToBeMarked.isDone()) {
            throw new ChatBotParameterException("This task is already marked done!");
        }
        taskToBeMarked.markDone();
        return taskToBeMarked;
    }

    public Task markTaskAsUndone(String parameters) throws ChatBotParameterException {
        int taskNumber = Parser.parseInteger(parameters);
        Task taskToBeMarked;
        try {
            taskToBeMarked = this.tasks.get(taskNumber - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new ChatBotParameterException("The task does not exists in the task list.");
        }
        if (!taskToBeMarked.isDone()) {
            throw new ChatBotParameterException("This task is already marked undone!");
        }
        taskToBeMarked.markUndone();
        return taskToBeMarked;
    }


    public Task deleteTask(String parameters) throws ChatBotParameterException {
        int taskNumber = Parser.parseInteger(parameters);
        Task taskToBeDeleted;
        try {
            taskToBeDeleted = this.tasks.remove(taskNumber - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new ChatBotParameterException("The task does not exists in the task list.");
        }
        return taskToBeDeleted;
    }
}