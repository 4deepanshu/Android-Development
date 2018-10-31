// Generated code from Butter Knife. Do not modify!
package com.schoolshieldchild_ui.view.activity;

import android.view.View;
import android.widget.ImageButton;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import com.schoolshieldchild_ui.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class HomeActivity_ViewBinding<T extends HomeActivity> implements Unbinder {
  protected T target;

  private View view2131689655;

  private View view2131689660;

  private View view2131689656;

  private View view2131689657;

  private View view2131689658;

  private View view2131689659;

  public HomeActivity_ViewBinding(final T target, final Finder finder, Object source) {
    this.target = target;

    View view;
    view = finder.findRequiredView(source, R.id.settings, "method 'openSettingsScreen'");
    view2131689655 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.openSettingsScreen();
      }
    });
    view = finder.findRequiredView(source, R.id.notification, "method 'sendNotification'");
    view2131689660 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.sendNotification();
      }
    });
    view = finder.findRequiredView(source, R.id.boy_msg, "method 'fatherMessage'");
    view2131689656 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fatherMessage(finder.<ImageButton>castParam(p0, "doClick", 0, "fatherMessage", 0));
      }
    });
    view = finder.findRequiredView(source, R.id.girl_msg, "method 'motherMessage'");
    view2131689657 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.motherMessage(finder.<ImageButton>castParam(p0, "doClick", 0, "motherMessage", 0));
      }
    });
    view = finder.findRequiredView(source, R.id.boy_call, "method 'fatherCall'");
    view2131689658 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.fatherCall(finder.<ImageButton>castParam(p0, "doClick", 0, "fatherCall", 0));
      }
    });
    view = finder.findRequiredView(source, R.id.girl_call, "method 'motherCall'");
    view2131689659 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.motherCall(finder.<ImageButton>castParam(p0, "doClick", 0, "motherCall", 0));
      }
    });
  }

  @Override
  public void unbind() {
    if (this.target == null) throw new IllegalStateException("Bindings already cleared.");

    view2131689655.setOnClickListener(null);
    view2131689655 = null;
    view2131689660.setOnClickListener(null);
    view2131689660 = null;
    view2131689656.setOnClickListener(null);
    view2131689656 = null;
    view2131689657.setOnClickListener(null);
    view2131689657 = null;
    view2131689658.setOnClickListener(null);
    view2131689658 = null;
    view2131689659.setOnClickListener(null);
    view2131689659 = null;

    this.target = null;
  }
}
