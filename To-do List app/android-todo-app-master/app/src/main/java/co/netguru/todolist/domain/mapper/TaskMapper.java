package co.netguru.todolist.domain.mapper;

import java.util.List;

import co.netguru.todolist.data.local.model.ChecklistItemDb;
import co.netguru.todolist.data.local.model.TaskDb;
import co.netguru.todolist.domain.model.ChecklistItem;
import co.netguru.todolist.domain.model.Task;

public class TaskMapper {

    private TaskMapper() {
        throw new AssertionError();
    }

    public static TaskDb toTaskDb(Task task) {
        return new TaskDb(task.getId(), task.getTitle(),
                task.getDescription(), task.isDone(),
                task.getDueDate());
    }

    public static Task fromTaskDbAndChecklistDbList(TaskDb taskDb, List<ChecklistItemDb> checklist) {
        List<ChecklistItem> checklistItems = ChecklistItemMapper.toChecklistItemList(checklist);
        return new Task.Builder()
                .setId(taskDb.getId())
                .setTitle(taskDb.getTitle())
                .setDescription(taskDb.getDescription())
                .setIsDone(taskDb.isDone())
                .setDueDate(taskDb.getDueDate())
                .setChecklistItemList(checklistItems)
                .build();
    }
}
