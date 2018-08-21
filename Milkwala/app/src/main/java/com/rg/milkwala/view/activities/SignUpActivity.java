package com.rg.milkwala.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rg.milkwala.R;
import com.rg.milkwala.controller.utils.Dialogs;
import com.rg.milkwala.model.user.User;
import com.rg.milkwala.view.controls.ButtonRegular;
import com.rg.milkwala.view.controls.EditTextRegular;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity {

    @BindView(R.id.textview_signup_login)
    TextView textview_signup_login;

    @BindView(R.id.button_signupnow)
    ButtonRegular button_signupnow;

    @BindView(R.id.edittext_signup_username)
    EditTextRegular edittext_signup_username;

    public EditTextRegular edittext_signup_email;
    @BindView(R.id.edittext_signup_mobilenumber)

    EditTextRegular edittext_signup_mobilenumber;
    @BindView(R.id.edittext_signup_password)

    EditTextRegular edittext_signup_password;
    @BindView(R.id.imageViewNameTick)

    ImageView imageViewNameTick;
    @BindView(R.id.ImageviewEmailTick)
    ImageView ImageviewEmailTick;


    private static final String TAG = "USER REGISTRATION";
    private SignUpActivity activity;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        ButterKnife.bind(this);
        edittext_signup_email = (EditTextRegular) findViewById(R.id.edittext_signup_email);
        activity = this;
        // [START initialize_auth]
        firebaseAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        hideandshowtick();
    }

    @OnClick(R.id.button_signupnow)
    public void signup() {
        String username = (edittext_signup_username.getText().toString().trim());
        String email = edittext_signup_email.getText().toString().trim();
        String mobilenumber = edittext_signup_mobilenumber.getText().toString().trim();
        String password = edittext_signup_password.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseenterusername));
        } else if (username.length() < 2) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.usernamemusthavetwocharacter));
        } else if (TextUtils.isEmpty(email)) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseEnterEmail));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseEnterValidEmail));
        } else if (TextUtils.isEmpty(mobilenumber)) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseEnterphnnumber));
        } else if (TextUtils.isEmpty(password)) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseEnterPassword));
        } else if (password.length() < 6) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.passwrdmustcontains));
        } else {
            registerUser(email, password);
        }

    }

    private void registerUser(final String email, String password) {

        showProgressDialog(getString(R.string.loadingpleasewait));
        // [START create_user_with_email]
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, getString(R.string.registrationfail) + ": " + task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                             saveUser(task.getResult().getUser().getUid());
                        }
                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void saveUser(String userid ) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Creating new user node, which returns the unique key value
        // new user node would be /users/$userid/
        String userId = mDatabase.push().getKey();
        // creating user object
        User user = new User();
        String username = edittext_signup_username.getText().toString();
        String email = edittext_signup_email.getText().toString();
        String mobilenumber = edittext_signup_mobilenumber.getText().toString();
        String password = edittext_signup_password.getText().toString();
        user.setEmail(email);
        user.setPassword("");
        user.setPhoneNumber(mobilenumber);
        user.setUserName(username);
        user.setDeviceToken("");
        user.setAddress("");
        user.setCity("");
        user.setState("");
        user.setPincode("");
        user.setCountry("");
        user.setLatitude("");
        user.setLongitude("");
        user.setUpdateOn("");
        user.setQrCodeImage("");
        user.setCashWallet("00.00");
        // pushing user to 'users' node using the userId
        mDatabase.child(userId).setValue(user);

        Intent intent = new Intent(SignUpActivity.this, PersonalInfoActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("userId", userid);
        intent.putExtra("userName",username);
        intent.putExtra("password", edittext_signup_password.getText().toString());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void hideandshowtick() {
        edittext_signup_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 2) {
                    imageViewNameTick.setVisibility(View.VISIBLE);
                } else {
                    imageViewNameTick.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edittext_signup_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    ImageviewEmailTick.setVisibility(View.VISIBLE);
                } else {
                    ImageviewEmailTick.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick(R.id.textview_signup_login)
    public void backtologoin(){
        finish();
    }

}
