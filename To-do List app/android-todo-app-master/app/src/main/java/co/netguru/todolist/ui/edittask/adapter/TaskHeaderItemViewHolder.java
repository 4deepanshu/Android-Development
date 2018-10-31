package co.netguru.todolist.ui.edittask.adapter;

import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import org.threeten.bp.LocalDate;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import co.netguru.todolist.R;
import co.netguru.todolist.common.LocalDateFormatterUtil;
import co.netguru.todolist.ui.edittask.adapter.model.TaskDataCollector;
import co.netguru.todolist.ui.base.BaseViewHolder;

public class TaskHeaderItemViewHolder extends BaseViewHolder<TaskDataCollector> {

    @BindView(R.id.title_edit_text) TextInputEditText titleEditText;
    @BindView(R.id.description_edit_text) TextInputEditText descriptionEditText;
    @BindView(R.id.due_date_edit_text) EditText dueDateTextView;

    private final DueDateRequestListener dueDateRequestListener;

    @Nullable
    private TaskDataCollector currentTask;

    TaskHeaderItemViewHolder(ViewGroup parent, DueDateRequestListener dueDateRequestListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_description, parent, false));
        this.dueDateRequestListener = dueDateRequestListener;

        disableDueDateEditTextEditAbility();
    }

    private void disableDueDateEditTextEditAbility() {
        dueDateTextView.setKeyListener(null);
    }

    @Override
    public void bind(TaskDataCollector item) {
        currentTask = item;
        titleEditText.setText(item.getTitle());
        descriptionEditText.setText(item.getDescription());
        final LocalDate dueDate = item.getDueDate();
        if (dueDate != null) {
            String formattedDate = LocalDateFormatterUtil.getShortMonthDayAndYearFormattedDate(item.getDueDate());
            dueDateTextView.setText(formattedDate);
        }
    }

    @OnTextChanged(R.id.title_edit_text)
    void onTitleTextChange(CharSequence charSequence) {
        if (currentTask != null) {
            currentTask.setTitle(charSequence.toString());
        }
    }

    @OnTextChanged(R.id.description_edit_text)
    void onDescriptionChange(CharSequence charSequence) {
        if (currentTask != null) {
            currentTask.setDescription(charSequence.toString());
        }
    }

    @OnClick(R.id.due_date_edit_text)
    void onDueDateClick() {
        dueDateRequestListener.onTaskRequestDate();
    }
}
