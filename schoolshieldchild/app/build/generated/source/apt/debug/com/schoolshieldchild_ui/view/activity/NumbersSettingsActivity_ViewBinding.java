// Generated code from Butter Knife. Do not modify!
package com.schoolshieldchild_ui.view.activity;

import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import com.schoolshieldchild_ui.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class NumbersSettingsActivity_ViewBinding<T extends NumbersSettingsActivity> implements Unbinder {
  protected T target;

  private View view2131689633;

  public NumbersSettingsActivity_ViewBinding(final T target, Finder finder, Object source) {
    this.target = target;

    View view;
    target.editTextCallFater = finder.findRequiredViewAsType(source, R.id.edittext_CallNumberFather, "field 'editTextCallFater'", EditText.class);
    target.editTextCallMother = finder.findRequiredViewAsType(source, R.id.edittext_CallNumberMother, "field 'editTextCallMother'", EditText.class);
    target.editTextMessageMother = finder.findRequiredViewAsType(source, R.id.edittext_MessageNumberFather, "field 'editTextMessageMother'", EditText.class);
    target.editTextMessageFather = finder.findRequiredViewAsType(source, R.id.edittext_MessageNumberMother, "field 'editTextMessageFather'", EditText.class);
    target.editTextMessageText = finder.findRequiredViewAsType(source, R.id.edittext_MessageText, "field 'editTextMessageText'", EditText.class);
    view = finder.findRequiredView(source, R.id.buttonSave, "method 'save'");
    view2131689633 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.save();
      }
    });
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.editTextCallFater = null;
    target.editTextCallMother = null;
    target.editTextMessageMother = null;
    target.editTextMessageFather = null;
    target.editTextMessageText = null;

    view2131689633.setOnClickListener(null);
    view2131689633 = null;

    this.target = null;
  }
}
