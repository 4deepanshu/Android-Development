package co.netguru.todolist.ui.edittask.adapter.model;

import org.threeten.bp.LocalDate;

public class TaskDataCollector {

    private long id;

    private String title;

    private String description;

    private boolean isDone;

    private LocalDate dueDate;

    public TaskDataCollector() {

    }

    public TaskDataCollector(long id, String title, String description, boolean isDone, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isDone = isDone;
        this.dueDate = dueDate;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
