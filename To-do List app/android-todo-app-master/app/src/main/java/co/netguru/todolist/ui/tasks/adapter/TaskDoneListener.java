package co.netguru.todolist.ui.tasks.adapter;

import co.netguru.todolist.domain.model.Task;

public interface TaskDoneListener {

    void onTaskDoneStateChange(Task task);
}
