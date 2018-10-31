package co.netguru.todolist.ui.tasks.adapter;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import co.netguru.todolist.R;
import co.netguru.todolist.common.LocalDateFormatterUtil;
import co.netguru.todolist.domain.model.ChecklistItem;
import co.netguru.todolist.domain.model.Task;
import co.netguru.todolist.ui.base.BaseViewHolder;

public class TaskViewHolder extends BaseViewHolder<Task> implements Toolbar.OnMenuItemClickListener {

    private final TaskEditListener taskEditListener;
    private final TaskDeleteListener taskDeleteListener;
    private final TaskDoneListener taskDoneListener;

    @BindView(R.id.task_toolbar) Toolbar toolbar;
    @BindView(R.id.title_text_view) TextView titleTextView;
    @BindView(R.id.description_text_view) TextView descriptionTextView;
    @BindView(R.id.due_date_layout) LinearLayout dueDateLayout;
    @BindView(R.id.due_date_text_view) TextView dueDateTextView;
    @BindView(R.id.checklist_layout) LinearLayout checklistLayout;
    @BindView(R.id.checklist_text_view) TextView checklistTextView;
    @BindView(R.id.done_checkbox) CheckBox doneCheckbox;

    private Task currentTask;

    public TaskViewHolder(ViewGroup parent, TaskEditListener taskEditListener,
                          TaskDeleteListener taskDeleteListener, TaskDoneListener taskDoneListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false));
        this.taskEditListener = taskEditListener;
        this.taskDeleteListener = taskDeleteListener;
        this.taskDoneListener = taskDoneListener;
        toolbar.inflateMenu(R.menu.task_card_menu);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public void bind(Task item) {
        this.currentTask = item;
        doneCheckbox.setChecked(item.isDone());
        titleTextView.setText(item.getTitle());
        descriptionTextView.setText(item.getDescription());
        if (item.getDueDate() == null) {
            dueDateLayout.setVisibility(View.GONE);
        } else {
            dueDateLayout.setVisibility(View.VISIBLE);
            dueDateTextView.setText(LocalDateFormatterUtil.getShortMonthDayAndYearFormattedDate(item.getDueDate()));
        }
        String formattedChecklistItems = getFormattedChecklistItems(item.getChecklistItemList());
        if (TextUtils.isEmpty(formattedChecklistItems)) {
            checklistLayout.setVisibility(View.GONE);
        } else {
            checklistLayout.setVisibility(View.VISIBLE);
            checklistTextView.setText(formattedChecklistItems);
        }
    }

    private String getFormattedChecklistItems(List<ChecklistItem> checklistItems) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ChecklistItem checklistItem : checklistItems) {
            stringBuilder.append("- ").append(checklistItem.getName()).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                taskEditListener.onTaskEdit(currentTask);
                return true;
            case R.id.action_delete:
                taskDeleteListener.onTaskDelete(currentTask);
                return true;
            default:
                throw new IllegalArgumentException("Unknown item menu item");
        }
    }

    @OnCheckedChanged(R.id.done_checkbox)
    void onTaskDoneStateChange(boolean isDone) {
        if (currentTask.isDone() != isDone) {
            taskDoneListener.onTaskDoneStateChange(
                    new Task.Builder()
                            .setId(currentTask.getId())
                            .setTitle(currentTask.getTitle())
                            .setDescription(currentTask.getDescription())
                            .setDueDate(currentTask.getDueDate())
                            .setIsDone(isDone)
                            .setChecklistItemList(currentTask.getChecklistItemList())
                            .build());
        }
    }
}
