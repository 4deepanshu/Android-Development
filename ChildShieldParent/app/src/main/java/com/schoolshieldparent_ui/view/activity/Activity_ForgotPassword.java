package com.schoolshieldparent_ui.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.model.universalresponce.UniversalResponce;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.custom_controls.EditText_Light;
import com.schoolshieldparent_ui.view.custom_controls.EditText_Regular;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_ForgotPassword extends AppCompatActivity {

    private static Activity_ForgotPassword instance;
    @BindView(R.id.editextEmail)
    EditText_Light edittextEmail;
    @BindView(R.id.textViewErrorEmail)
    TextView_Regular errorEmail;
    @BindView(R.id.buttonSubmit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_forgot_password );
        ButterKnife.bind( this );
        instance = this;
        getSupportActionBar().hide();

    }

    @OnClick(R.id.buttonSubmit)
    public void submitEmail() {
        String email = edittextEmail.getText().toString();
        if (validation( email )) {
            if (EditText_Regular.isEmailValid( email )) {
                WebServiceResult.ForgotPassword( email );
            } else {
                errorEmail.setVisibility( View.VISIBLE );
                errorEmail.setText( getString( R.string.enterValidEmail ) );
                hideErrorOnTextChange( edittextEmail );
            }
        }
    }

    private boolean validation(String email) {
        if (EditText_Regular.isEmpty( email )) {
            errorEmail.setVisibility( View.VISIBLE );
            errorEmail.setText( getString( R.string.enterEmail ) );
            hideErrorOnTextChange( edittextEmail );
            return false;
        }
        return true;
    }

    @OnClick(R.id.imageButton_Back)
    public void goBack() {
        this.finish();
        overridePendingTransition( 0, 0 );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( 0, 0 );

    }


    private void hideErrorOnTextChange(EditText_Light editText) {
        editText.addTextChangedListener( new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (errorEmail.getVisibility() == View.VISIBLE) {
                    errorEmail.setVisibility( View.GONE );
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

    public static Activity_ForgotPassword getInstance() {
        return instance;
    }

    public void updateForgotPassword(UniversalResponce body) {
        if (body.getResult().getStatus().toString().equalsIgnoreCase( "1" )) {
            errorEmail.setVisibility( View.VISIBLE );
            errorEmail.setText( getString( R.string.checkYouremail ) );
            hideErrorOnTextChange( edittextEmail );
            errorEmail.setTextColor( Color.GREEN );
            edittextEmail.setVisibility( View.GONE );
            btnSubmit.setVisibility( View.GONE );
        } else {
            errorEmail.setVisibility( View.VISIBLE );
            errorEmail.setText( getString( R.string.somethingWentWrong ) );
            hideErrorOnTextChange( edittextEmail );
        }
    }
}
