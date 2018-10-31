package co.netguru.todolist.domain;

import org.threeten.bp.LocalDate;

import java.util.List;

import javax.inject.Inject;

import co.netguru.todolist.data.local.TasksRepository;
import co.netguru.todolist.domain.model.ChecklistItem;
import co.netguru.todolist.domain.model.Task;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TaskController {

    private final TasksRepository tasksRepository;

    @Inject
    public TaskController(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public Completable saveNewTask(Task task) {
        return tasksRepository.saveNewTask(task);
    }

    public Completable updateTaskWithChecklist(Task task, List<ChecklistItem> checklistItemsToDelete,
                                               List<ChecklistItem> checklistItemsToUpdate, List<ChecklistItem> checklistItemsToAdd) {
        return tasksRepository.updateTask(task, checklistItemsToDelete, checklistItemsToUpdate, checklistItemsToAdd);
    }

    public Completable updateTaskOnly(Task task) {
        return tasksRepository.updateTaskOnly(task);
    }

    public Flowable<List<Task>> getToDoTasks() {
        return tasksRepository.getToDoTasks();
    }

    public Flowable<List<Task>> getDoneTasks() {
        return tasksRepository.getDoneTasks();
    }

    public Flowable<List<Task>> getTasksWithDueDateBefore(LocalDate localDate) {
        return tasksRepository.getTasksWithDueDateBefore(localDate);
    }

    public Completable deleteTask(Task task) {
        return tasksRepository.deleteTask(task);
    }
}
