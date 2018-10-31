// Generated code from Butter Knife. Do not modify!
package com.schoolshieldchild_ui.view.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import com.schoolshieldchild_ui.R;
import com.schoolshieldchild_ui.view.custom_controls.TextView_Regular;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class TutorialActivity_ViewBinding<T extends TutorialActivity> implements Unbinder {
  protected T target;

  private View view2131689636;

  public TutorialActivity_ViewBinding(final T target, Finder finder, Object source) {
    this.target = target;

    View view;
    target.viewPager_tutorials = finder.findRequiredViewAsType(source, R.id.viewPager_tutorials, "field 'viewPager_tutorials'", ViewPager.class);
    view = finder.findRequiredView(source, R.id.textView_Next, "field 'textView_Next' and method 'setNextClick'");
    target.textView_Next = finder.castView(view, R.id.textView_Next, "field 'textView_Next'", TextView_Regular.class);
    view2131689636 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.setNextClick();
      }
    });
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.viewPager_tutorials = null;
    target.textView_Next = null;

    view2131689636.setOnClickListener(null);
    view2131689636 = null;

    this.target = null;
  }
}
