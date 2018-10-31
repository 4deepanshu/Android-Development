package co.netguru.todolist.ui.tasks;

import co.netguru.todolist.common.di.FragmentScope;
import dagger.Subcomponent;

@FragmentScope
@Subcomponent
public interface TasksFragmentComponent {

    void inject(TasksFragment tasksFragment);

    TasksPresenter getPresenter();
}
