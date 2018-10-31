package co.netguru.todolist.data.local;

import org.threeten.bp.LocalDate;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.netguru.todolist.data.local.model.ChecklistItemDb;
import co.netguru.todolist.data.local.model.TaskDb;
import co.netguru.todolist.domain.mapper.ChecklistItemMapper;
import co.netguru.todolist.domain.mapper.TaskMapper;
import co.netguru.todolist.domain.model.ChecklistItem;
import co.netguru.todolist.domain.model.Task;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import timber.log.Timber;

@Singleton
public class TasksRepository {

    private final AppDatabase appDatabase;

    @Inject
    public TasksRepository(AppDatabase appDatabase) {

        this.appDatabase = appDatabase;
    }

    public Completable saveNewTask(Task task) {
        return Completable.fromAction(() -> {
                    TaskDb taskDb = TaskMapper.toTaskDb(task);
                    appDatabase.beginTransaction();
                    try {
                        long taskId = appDatabase.tasksDao().insertTask(taskDb);
                        List<ChecklistItemDb> checklistItemDbs = ChecklistItemMapper.toChecklistItemDbList(taskId, task.getChecklistItemList());
                        appDatabase.checklistDao().insertAll(checklistItemDbs);
                        appDatabase.setTransactionSuccessful();
                    } finally {
                        appDatabase.endTransaction();
                    }
                }
        );
    }

    public Completable updateTask(Task task, List<ChecklistItem> checklistItemsToDelete,
                                  List<ChecklistItem> checklistItemsToUpdate, List<ChecklistItem> checklistItemsToAdd) {
        return Completable.fromAction(() -> {
                    TaskDb taskDb = TaskMapper.toTaskDb(task);
                    appDatabase.beginTransaction();
                    try {
                        appDatabase.tasksDao().updateTask(taskDb);

                        List<ChecklistItemDb> checklistItemDbsToDelete =
                                ChecklistItemMapper.toChecklistItemDbList(task.getId(), checklistItemsToDelete);
                        appDatabase.checklistDao().deleteAll(checklistItemDbsToDelete);

                        List<ChecklistItemDb> checklistItemDbsToUpdate =
                                ChecklistItemMapper.toChecklistItemDbList(task.getId(), checklistItemsToUpdate);
                        appDatabase.checklistDao().updateAll(checklistItemDbsToUpdate);

                        List<ChecklistItemDb> checklistItemDbsToAdd =
                                ChecklistItemMapper.toChecklistItemDbList(task.getId(), checklistItemsToAdd);
                        appDatabase.checklistDao().insertAll(checklistItemDbsToAdd);

                        appDatabase.setTransactionSuccessful();
                    } finally {
                        appDatabase.endTransaction();
                    }
                }
        );
    }

    public Completable updateTaskOnly(Task task) {
        return Completable.fromAction(() -> {
            TaskDb taskDb = TaskMapper.toTaskDb(task);
            long updatedRow = appDatabase.tasksDao().updateTask(taskDb);
            Timber.d("Updated row " + updatedRow);
        });
    }

    public Completable deleteTask(Task task) {
        return Completable.fromAction(() ->
                appDatabase.tasksDao().deleteTask(TaskMapper.toTaskDb(task))
        );
    }

    /**
     * Flowable will emit new list of tasks whenever tasks table is modified
     */
    public Flowable<List<Task>> getToDoTasks() {
        return appDatabase.tasksDao().getToDoTasks()
                .flatMapSingle(this::getTasksWithChecklistFromTaskDbs);
    }

    public Flowable<List<Task>> getDoneTasks() {
        return appDatabase.tasksDao().getDoneTasks()
                .flatMapSingle(this::getTasksWithChecklistFromTaskDbs);
    }

    public Flowable<List<Task>> getTasksWithDueDateBefore(LocalDate date) {
        return appDatabase.tasksDao().getTaskWithDueDateBefore(date)
                .flatMapSingle(this::getTasksWithChecklistFromTaskDbs);
    }

    private Single<List<ChecklistItemDb>> getSingleCheckListItems(TaskDb taskDb) {
        return appDatabase.checklistDao().getChecklistItems(taskDb.getId()).firstOrError();
    }

    private Single<List<Task>> getTasksWithChecklistFromTaskDbs(List<TaskDb> taskDbs) {
        return Flowable.fromIterable(taskDbs)
                .flatMapSingle(taskDb -> getSingleCheckListItems(taskDb)
                        .map(checklistItemDbs -> TaskMapper.fromTaskDbAndChecklistDbList(taskDb, checklistItemDbs)), false, 1)
                .toList();
    }
}
