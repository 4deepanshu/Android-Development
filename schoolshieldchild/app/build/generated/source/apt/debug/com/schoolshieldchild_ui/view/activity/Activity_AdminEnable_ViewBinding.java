// Generated code from Butter Knife. Do not modify!
package com.schoolshieldchild_ui.view.activity;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import com.schoolshieldchild_ui.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class Activity_AdminEnable_ViewBinding<T extends Activity_AdminEnable> implements Unbinder {
  protected T target;

  private View view2131689661;

  public Activity_AdminEnable_ViewBinding(final T target, Finder finder, Object source) {
    this.target = target;

    View view;
    view = finder.findRequiredView(source, R.id.activate, "method 'activate'");
    view2131689661 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.activate();
      }
    });
  }

  @Override
  public void unbind() {
    if (this.target == null) throw new IllegalStateException("Bindings already cleared.");

    view2131689661.setOnClickListener(null);
    view2131689661 = null;

    this.target = null;
  }
}
