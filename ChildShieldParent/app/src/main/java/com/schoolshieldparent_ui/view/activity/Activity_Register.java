package com.schoolshieldparent_ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.controller.utils.CustomToast;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.model.applicationprp.registration.Registration;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.custom_controls.EditText_Light;
import com.schoolshieldparent_ui.view.custom_controls.EditText_Regular;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Register extends AppCompatActivity {
    private static Activity_Register instance;
    @BindView(R.id.textViewMessage1)
    TextView_Regular message1;
    @BindView(R.id.textViewMessage2)
    TextView_Regular message2;

    @BindView(R.id.editextEmail)
    EditText_Light editTextEmail;
    @BindView(R.id.editextFirstName)
    EditText_Light editTextFirstName;
    @BindView(R.id.editextLastName)
    EditText_Light editTextLastName;
    @BindView(R.id.editextPassword)
    EditText_Light editTextPassword;
    @BindView(R.id.editextConfirmPassword)
    EditText_Light editTextConfirmPassword;

    @BindView(R.id.textViewErrorFirstName)
    TextView_Regular errorFirstName;
    @BindView(R.id.textViewErrorLastName)
    TextView_Regular errorLastName;
    @BindView(R.id.textViewErrorEmail)
    TextView_Regular errorEmail;
    @BindView(R.id.textViewErrorPassword)
    TextView_Regular errorPassword;
    @BindView(R.id.textViewErrorConfirmPassword)
    TextView_Regular errorConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        instance = this;
        getSupportActionBar().hide();
        ButterKnife.bind(this);
    }

    public static Activity_Register getInstance() {
        return instance;
    }

    @OnClick(R.id.buttonRegister)
    public void doRegister() {

        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (validatoion(firstName, lastName, email, password, confirmPassword)) {

            if (EditText_Regular.isEmailValid(email)) {
                if (EditText_Regular.isPasswordConfirm(password, confirmPassword)) {
                    DialogManager.startProgressDialog(this);
                    WebServiceResult.ParentRegistration(firstName, lastName, email, password);
                } else {
                    errorConfirmPassword.setVisibility(View.VISIBLE);
                    errorConfirmPassword.setText(getString(R.string.passwordNotMatched));
                    hideErrorOnTextChange(editTextConfirmPassword);
                }
            } else {
                errorEmail.setVisibility(View.VISIBLE);
                errorEmail.setText(getString(R.string.enterValidEmail));
                hideErrorOnTextChange(editTextEmail);
            }
        }

    }

    private boolean validatoion(String firstName, String lastName, String email, String password,
                                String confirmPassword) {
        if (CustomToast.toast != null) {
            CustomToast.toast.cancel();
        }
        if (EditText_Regular.isEmpty(firstName)) {
            errorFirstName.setVisibility(View.VISIBLE);
            errorFirstName.setText(getString(R.string.enterFirstName));
            hideErrorOnTextChange(editTextFirstName);
            return false;
        } else if (EditText_Regular.isEmpty(lastName)) {

            errorLastName.setVisibility(View.VISIBLE);
            errorLastName.setText(getString(R.string.enterLastName));
            hideErrorOnTextChange(editTextLastName);
            return false;
        } else if (EditText_Regular.isEmpty(email)) {
            errorEmail.setVisibility(View.VISIBLE);
            errorEmail.setText(getString(R.string.enterEmail));
            hideErrorOnTextChange(editTextEmail);
            return false;
        } else if (EditText_Regular.isEmpty(password)) {

            errorPassword.setVisibility(View.VISIBLE);
            errorPassword.setText(getString(R.string.enterPassword));
            hideErrorOnTextChange(editTextPassword);
            return false;
        } else if (EditText_Regular.isEmpty(confirmPassword)) {

            errorConfirmPassword.setVisibility(View.VISIBLE);
            errorConfirmPassword.setText(getString(R.string.enterconfirmPassword));
            hideErrorOnTextChange(editTextConfirmPassword);

            return false;
        }
        return true;
    }

    private void hideErrorOnTextChange(EditText_Light editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (errorFirstName.getVisibility() == View.VISIBLE) {
                    errorFirstName.setVisibility(View.GONE);
                }
                if (errorLastName.getVisibility() == View.VISIBLE) {
                    errorLastName.setVisibility(View.GONE);
                }
                if (errorEmail.getVisibility() == View.VISIBLE) {
                    errorEmail.setVisibility(View.GONE);
                }
                if (errorPassword.getVisibility() == View.VISIBLE) {
                    errorPassword.setVisibility(View.GONE);
                }
                if (errorConfirmPassword.getVisibility() == View.VISIBLE) {
                    errorConfirmPassword.setVisibility(View.GONE);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
    }

    @OnClick(R.id.imageButton_Back)
    public void goBack() {
        this.finish();
        overridePendingTransition(0, 0);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }


    public void updateRegistration(Registration body) {
        DialogManager.stopProgressDialog();
        if (body.getResult().getStatus().toString().equalsIgnoreCase("1")) {
            SharedPref.setString(MyApplication.PARENT_ID, body.getResult().getParentId().toString());
            CustomToast.showToast(this, getString(R.string.parentregistersucessfully));
            startActivity(new Intent(this, Activity_Login.class));
            finish();
            overridePendingTransition(0, 0);
        } else if (body.getResult().getMessage().equalsIgnoreCase("parent email already exists")) {
            CustomToast.showToast(this, getString(R.string.emailAllreadyExist));
        } else {
            CustomToast.showToast(this, getString(R.string.parentregisterfail));
        }

    }
}
