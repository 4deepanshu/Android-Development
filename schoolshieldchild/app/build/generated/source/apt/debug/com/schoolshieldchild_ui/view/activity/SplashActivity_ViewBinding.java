// Generated code from Butter Knife. Do not modify!
package com.schoolshieldchild_ui.view.activity;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import com.schoolshieldchild_ui.R;
import com.schoolshieldchild_ui.view.custom_controls.TextView_Regular;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class SplashActivity_ViewBinding<T extends SplashActivity> implements Unbinder {
  protected T target;

  private View view2131689634;

  public SplashActivity_ViewBinding(final T target, Finder finder, Object source) {
    this.target = target;

    View view;
    view = finder.findRequiredView(source, R.id.textView_AgreeAndContinue, "field 'textView_AgreeAndContinue' and method 'AgreeAndContinueClick'");
    target.textView_AgreeAndContinue = finder.castView(view, R.id.textView_AgreeAndContinue, "field 'textView_AgreeAndContinue'", TextView_Regular.class);
    view2131689634 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.AgreeAndContinueClick();
      }
    });
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.textView_AgreeAndContinue = null;

    view2131689634.setOnClickListener(null);
    view2131689634 = null;

    this.target = null;
  }
}
