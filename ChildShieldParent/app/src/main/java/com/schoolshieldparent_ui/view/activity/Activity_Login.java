package com.schoolshieldparent_ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.controller.utils.CustomToast;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.model.applicationprp.registration.Registration;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.custom_controls.EditText_Light;
import com.schoolshieldparent_ui.view.custom_controls.EditText_Regular;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Login extends AppCompatActivity {
    @BindView(R.id.editextEmail)
    EditText_Light edittextEmail;
    @BindView(R.id.editextPassword)
    EditText_Light edittextPassword;
    @BindView(R.id.textViewErrorEmail)
    TextView errorEmail;
    @BindView(R.id.textViewErrorPassword)
    TextView errorPassword;
    static Activity_Login instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        instance = this;
        getSupportActionBar().hide();
        ButterKnife.bind( this );

    }



    public static Activity_Login getInstance() {
        return instance;
    }

    @OnClick(R.id.textViewSignUp)
    public void signUP() {
        startActivity( new Intent( this, Activity_Register.class ) );
        overridePendingTransition( 0,0 );
        // overridePendingTransition(R.anim.in, R.anim.out);
    }

    private void getExtraString() {
        String email = getIntent().getStringExtra( "email" );
        String password = getIntent().getStringExtra( "password" );

        if (email != null && password != null) {
            edittextEmail.setText( email );
            edittextPassword.setText( password );
            DialogManager.startProgressDialog( this );
            WebServiceResult.ParentLogin( email, password, FirebaseInstanceId.getInstance().getToken() );
        }
    }


    @OnClick(R.id.buttonLogin)
    public void doLogin() {

        String email = edittextEmail.getText().toString();
        String password = edittextPassword.getText().toString();
        if (validation( email, password )) {
            if (EditText_Regular.isEmailValid( email )) {
                if (FirebaseInstanceId.getInstance().getToken().toString().length() > 0) {
                    DialogManager.startProgressDialog( this );
                    WebServiceResult.ParentLogin( email, password, FirebaseInstanceId.getInstance().getToken() );
                } else {

                }
            } else {
                errorEmail.setVisibility( View.VISIBLE );
                errorEmail.setText( getString( R.string.enterValidEmail ) );
                hideErrorOnTextChange( edittextEmail );

            }
        }
    }

    private boolean validation(String email, String password) {
        if (EditText_Regular.isEmpty( email )) {
            errorEmail.setVisibility( View.VISIBLE );
            errorEmail.setText( getString( R.string.enterEmail ) );
            hideErrorOnTextChange( edittextEmail );
            return false;
        } else if (EditText_Regular.isEmpty( password )) {
            errorPassword.setVisibility( View.VISIBLE );
            errorPassword.setText( getString( R.string.enterPassword ) );
            hideErrorOnTextChange( edittextPassword );
            return false;
        }
        return true;
    }

    @OnClick(R.id.textViewForGotPassword)
    public void showForgotPassword() {
        startActivity( new Intent( this, Activity_ForgotPassword.class ) );
        overridePendingTransition( 0,0 );

    }


    @Override
    protected void onStop() {

        super.onStop();
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void hideErrorOnTextChange(EditText_Light editText) {
        editText.addTextChangedListener( new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (errorEmail.getVisibility() == View.VISIBLE) {
                    errorEmail.setVisibility( View.GONE );
                }
                if (errorPassword.getVisibility() == View.VISIBLE) {
                    errorPassword.setVisibility( View.GONE );
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
        } );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( 0,0 );
    }


    public void updateLogin(Registration body) {
        DialogManager.stopProgressDialog();
        if (body.getResult().getStatus().toString().equalsIgnoreCase( "1" )) {
            SharedPref.setString( MyApplication.PARENT_ID, body.getResult().getParentId().toString() );
            startActivity( new Intent( this, Activity_Home.class ) );
            finish();
            overridePendingTransition( 0,0 );
        } else {
            CustomToast.showToast(this, getString(R.string.loginfail));
        }
    }
}
