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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rg.milkwala.R;
import com.rg.milkwala.controller.utils.Dialogs;
import com.rg.milkwala.controller.utils.SharedPref;
import com.rg.milkwala.model.user.Constants;
import com.rg.milkwala.model.user.User;
import com.rg.milkwala.view.controls.ButtonRegular;
import com.rg.milkwala.view.controls.EditTextRegular;
import com.rg.milkwala.view.controls.TextViewRegular;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivtiy extends BaseActivity {
    @BindView(R.id.button_signinnow)
    ButtonRegular button_signinnow;
    @BindView(R.id.edittext_login_email)
    EditTextRegular edittext_login_email;
    @BindView(R.id.edittext_login_password)
    EditTextRegular edittext_login_password;
    @BindView(R.id.textview_login_signup)
    TextViewRegular textview_login_signup;
    @BindView(R.id.ImageviewEmailTick)
    ImageView ImageviewEmailTick;
    User user;
    private LoginActivtiy activity;
    FirebaseAuth firebaseAuth;
    private static final String TAG = "USER LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activtiy);
        ButterKnife.bind(this);
        activity = this;
        FirebaseInstanceId.getInstance().getToken();
        // [START initialize_auth]
        firebaseAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        showiamgetick();


    }

    @OnClick(R.id.button_signinnow)
    public void login() {
        String email = edittext_login_email.getText().toString().trim();
        String password = edittext_login_password.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseEnterEmail));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseEnterValidEmail));
        } else if (TextUtils.isEmpty(password)) {
            Dialogs.getInstance().showtoast(activity, getString(R.string.pleaseEnterPassword));
        } else {
            userLogin(email, password);
        }
    }

    private void userLogin(String email, String password) {

        showProgressDialog(getString(R.string.loadingpleasewait));
        // [START create_user_with_email]
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            hideProgressDialog();
                            Toast.makeText(LoginActivtiy.this, getString(R.string.loginFail) + ": " + task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            String userId = task.getResult().getUser().getUid();
                            updateUser(userId);
                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
        //FirebaseInstanceId.getInstance().getToken();

    }

    private void updateUser(final String userId) {
        String email = edittext_login_email.getText().toString();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Creating new user node, which returns the unique key value
        // new user node would be /users/$userid/

        mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isProfileCompleted = true;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    user=child.getValue(User.class);
                    if(user.getAddress().trim().length()==0||user.getCity().trim().length()==0||user.getCountry().trim().length()==0||user.getState().trim().length()==0){
                        isProfileCompleted=false;
                    }
                    mDatabase.child(child.getKey()).child("deviceToken").setValue(FirebaseInstanceId.getInstance().getToken());
                    break;
                }
                if(isProfileCompleted){
                    hideProgressDialog();
                    SharedPref.getInstance().setString(Constants.USER_ID, userId);
                    SharedPref.getInstance().setString(Constants.USER_NAME,user.getUserName());
                    SharedPref.getInstance().setString(Constants.USER_EMAIL, edittext_login_email.getText().toString());
                    SharedPref.getInstance().setString(Constants.USER_PASSWORD, edittext_login_password.getText().toString());
                    startActivity(new Intent(LoginActivtiy.this, HomeActivity.class));
                    finish();
                }
                else{
                    hideProgressDialog();
                    Intent intent = new Intent(LoginActivtiy.this, PersonalInfoActivity.class);
                    intent.putExtra("email", edittext_login_email.getText().toString());
                    intent.putExtra("userId",userId);
                    intent.putExtra("password",edittext_login_password.getText().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }


    @OnClick(R.id.textview_login_signup)
    public void nextsecreen() {
        startActivity(new Intent(LoginActivtiy.this, SignUpActivity.class));
    }

    public void showiamgetick() {
        edittext_login_email.addTextChangedListener(new TextWatcher() {
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
}

