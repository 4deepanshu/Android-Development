package co.netguru.todolist.ui.edittask;

import org.threeten.bp.LocalDate;

import java.util.List;

import co.netguru.todolist.domain.model.ChecklistItem;
import co.netguru.todolist.domain.model.Task;
import co.netguru.todolist.ui.base.MvpView;

interface EditTaskView extends MvpView {

    void setTaskDueDate(LocalDate localDate);

    void finish();

    void setupWithTaskToEdit(Task task);

    void setupViewToCreateNewTask();

    Task getTaskToSave();

    List<ChecklistItem> getChecklistItemsToDelete();

    List<ChecklistItem> getChecklistItemsToUpdate();

    List<ChecklistItem> getChecklistItemsToAdd();
}
