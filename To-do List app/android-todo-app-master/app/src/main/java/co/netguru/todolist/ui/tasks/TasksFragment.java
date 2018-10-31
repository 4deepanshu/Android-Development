package co.netguru.todolist.ui.tasks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import co.netguru.todolist.R;
import co.netguru.todolist.app.App;
import co.netguru.todolist.domain.model.Task;
import co.netguru.todolist.ui.base.BaseMvpFragment;
import co.netguru.todolist.ui.edittask.EditTaskActivity;
import co.netguru.todolist.ui.tasks.adapter.TaskDeleteListener;
import co.netguru.todolist.ui.tasks.adapter.TaskDoneListener;
import co.netguru.todolist.ui.tasks.adapter.TaskEditListener;
import co.netguru.todolist.ui.tasks.adapter.TasksAdapter;
import co.netguru.todolist.utils.SharedPref;

public class TasksFragment extends BaseMvpFragment<TasksPresenter>
        implements TasksView, TaskEditListener, TaskDeleteListener, TaskDoneListener {

    private static final String TASK_TYPE_ARG = "task_type_arg";

    private final TasksAdapter tasksAdapter = new TasksAdapter(this, this, this);

    @BindView(R.id.tasks_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.no_tasks_text_view) TextView noTasksTextView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.btnSendMail)Button btnSendMail;
    static  int type;
    public static TasksFragment newInstance(TasksType tasksType,int ty) {
        Bundle args = new Bundle();
        args.putInt(TASK_TYPE_ARG, tasksType.ordinal());
        type=ty;

        TasksFragment fragment = new TasksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        TasksType tasksType = TasksType.fromOrdinal(getArguments().getInt(TASK_TYPE_ARG));
        getPresenter().setupTasksSubscription(tasksType);
        if(type==1)
        {
            btnSendMail.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
        }
    }

    private void setupRecyclerView() {
        recyclerView.setAdapter(tasksAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected TasksPresenter createPresenter() {
        return App.getAppComponent(getContext()).tasksFragmentComponent().getPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_tasks;
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        getPresenter().handleAddTaskClick();
    }

    @Override
    public void onTaskEdit(Task task) {
        getPresenter().editTask(task);
    }

    @Override
    public void onTaskDelete(Task task) {
        getPresenter().deleteTask(task);
    }

    @Override
    public void onTaskDoneStateChange(Task task) {
        getPresenter().updateTaskDoneState(task);
    }

    @Override
    public void displayTasks(List<Task> tasks) {
        noTasksTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        tasksAdapter.showTasks(tasks);
    }

    @Override
    public void showAddTaskView() {
        EditTaskActivity.start(getContext());
    }

    @Override
    public void showNoTasksView() {
        noTasksTextView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showEditTaskView(Task task) {
        EditTaskActivity.start(getContext(), task);
    }

    @Override
    public void showTaskMarkedAsDoneMessage(String taskTitle) {
        showTextOnSnackbar(getString(R.string.message_task_marked_as_done, taskTitle), Snackbar.LENGTH_LONG);
    }

    @Override
    public void showTaskUnmarkedAsDoneMessage(String taskTitle) {
        showTextOnSnackbar(getString(R.string.message_task_unmarked_as_done, taskTitle), Snackbar.LENGTH_LONG);
    }

    @Override
    public void showTaskDeletedMessage(String taskTitle) {
        showTextOnSnackbar(getString(R.string.message_task_deleted, taskTitle), Snackbar.LENGTH_LONG);
    }
    @OnClick(R.id.btnSendMail)
    public void sendEmail(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{SharedPref.getInstance(getContext()).getString("Teacherid")});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Send Mentor Detail");
        intent.putExtra(Intent.EXTRA_TEXT, "Name: "+SharedPref.getInstance(getContext()).getString("Name")+"\n"+"Address: "+SharedPref.getInstance(getContext()).getString("Address")+"\n"+"Teacherid: "+SharedPref.getInstance(getContext()).getString("Teacherid")+"\n"+"Mentor: "+SharedPref.getInstance(getContext()).getString("Mentor")+"\n"+"Company: "+SharedPref.getInstance(getContext()).getString("Company")+"\n"+"Mentor Comments: "+SharedPref.getInstance(getContext()).getString("Post")+"\n"+"Project Name: "+SharedPref.getInstance(getContext()).getString("ProjectName")+"\n"+"Technology: "+SharedPref.getInstance(getContext()).getString("Technology")+"\n"+"ProjectLeader: "+SharedPref.getInstance(getContext()).getString("ProjectLeader")+"\n"+"TotalMarks: "+SharedPref.getInstance(getContext()).getString("TotalMarks"));
        startActivity(Intent.createChooser(intent, ""));
    }
}
