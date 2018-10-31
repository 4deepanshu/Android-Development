package co.netguru.todolist.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import co.netguru.todolist.R;
import co.netguru.todolist.ui.base.BaseActivity;
import co.netguru.todolist.ui.tasks.TasksFragment;
import co.netguru.todolist.ui.tasks.TasksType;

public class CompletedTask extends BaseActivity {

    private TasksFragment fragment;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_task);
        setSupportActionBar(toolbar);

        fragment = TasksFragment.newInstance(TasksType.DONE,1);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_completed_task;
    }
}
