package co.netguru.todolist.ui.tasks.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.netguru.todolist.domain.model.Task;

public class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private final List<Task> tasks = new ArrayList<>();
    private final TaskEditListener taskEditListener;
    private final TaskDeleteListener taskDeleteListener;
    private final TaskDoneListener taskDoneListener;

    public TasksAdapter(TaskEditListener taskEditListener, TaskDeleteListener taskDeleteListener, TaskDoneListener taskDoneListener) {
        this.taskEditListener = taskEditListener;
        this.taskDeleteListener = taskDeleteListener;
        this.taskDoneListener = taskDoneListener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskViewHolder(parent, taskEditListener, taskDeleteListener, taskDoneListener);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.bind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void showTasks(List<Task> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }
}
