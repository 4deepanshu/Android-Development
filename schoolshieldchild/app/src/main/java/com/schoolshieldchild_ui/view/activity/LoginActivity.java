package com.schoolshieldchild_ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.schoolshieldchild_ui.R;
import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.Consts_Variable;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldchild_ui.controller.utils.DialogManager;
import com.schoolshieldchild_ui.model.login.Login;
import com.schoolshieldchild_ui.presenter.WebServiceResult;
import com.schoolshieldchild_ui.view.custom_controls.Button_Regular;
import com.schoolshieldchild_ui.view.custom_controls.EditText_Regular;
import com.schoolshieldchild_ui.view.custom_controls.TextView_Regular;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.button_confirm)
    Button_Regular button_confirm;
    @BindView(R.id.editText_childpin)
    EditText_Regular editText_childpin;
    @BindView(R.id.textView_HowToGetPin)
    TextView_Regular textView_HowToGetPin;
    public static LoginActivity instance;

    public static LoginActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        instance = LoginActivity.this;
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        showConfirmButton();

    }

    public void showConfirmButton() {
        editText_childpin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText_childpin.getText().toString().length() >= 4) {
                    button_confirm.setVisibility(View.VISIBLE);
                } else button_confirm.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick(R.id.button_confirm)
    public void setConfirmClick() {
        try {
            String data=FirebaseInstanceId.getInstance().getToken();
            if (!FirebaseInstanceId.getInstance().getToken().equalsIgnoreCase("")) {
                DialogManager.startProgressDialog(this);
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                WebServiceResult.ChildLogin(editText_childpin.getText().toString(), FirebaseInstanceId.getInstance().getToken(), Consts_Variable.deviceType,currentapiVersion+"");
            } else
                Toast.makeText(getApplicationContext(), R.string.loginUnSuccesfully,
                        Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), R.string.loginUnSuccesfully,
                    Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.textView_HowToGetPin)
    public void openTutorialScreen() {
        this.finish();
        startActivity(new Intent(this, TutorialActivity.class));
        overridePendingTransition(0, 0);
    }


    public void updateLogin(Login response) {

        if (response.getResult().getStatus().toString().equalsIgnoreCase("1")) {
            SharedPref.setString(MyApplication.STUDENT_PASSWORD, editText_childpin.getText().toString());
            SharedPref.setString(MyApplication.STUDENT_ID, response.getResult().getStudentId().toString());
            SharedPref.setString(MyApplication.STUDENT_ROLE, response.getResult().getRole());
            Toast.makeText(MyApplication.getInstance().getApplicationContext(), R.string.loginsuccessfully, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MyApplication.getInstance().getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        } else {
        }

    }
}
