package co.netguru.todolist.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.netguru.todolist.R;
import co.netguru.todolist.utils.SharedPref;

public class PasswordCheck extends AppCompatActivity implements View.OnKeyListener {

    @BindView(R.id.firstET)
    EditText firstET;

    @BindView(R.id.secondET)
    EditText secondET;

    @BindView(R.id.thirdET)
    EditText thirdET;

    @BindView(R.id.forthET)
    EditText forthET;

    @BindView(R.id.fifthET)
    EditText fifthET;

    @BindView(R.id.sixthET)
    EditText sixthET;
    public String passwordString;
    private int role;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_check);
        ButterKnife.bind(this);
        role=getIntent().getIntExtra("role",-1);
        settextWatcherlistener();
    }


    private void settextWatcherlistener() {
        firstET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //secondET.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0)
                    secondET.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // secondET.requestFocus ();

            }
        });
        secondET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //thirdET.requestFocus ();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0)
                    thirdET.requestFocus();
                else if(count==0){
                    firstET.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //thirdET.requestFocus ();

            }
        });
        thirdET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //thirdET.requestFocus ();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0)
                    forthET.requestFocus();
                else if(count==0){
                    secondET.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //thirdET.requestFocus ();

            }
        });
        forthET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //thirdET.requestFocus ();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0)
                    fifthET.requestFocus();
                else if(count==0){
                    thirdET.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                //thirdET.requestFocus ();

            }
        });
        fifthET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // sixthET.requestFocus ();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0)
                    sixthET.requestFocus();
                else if(count==0){
                    forthET.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //thirdET.requestFocus ();

            }
        });

        sixthET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //thirdET.requestFocus ();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // if (count != 0)
                // thirdET.requestFocus ();
                if(count==0){
                    fifthET.requestFocus();
                }else if(count==1){
                    checkVerification();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //thirdET.requestFocus ();

            }
        });

    }
public void checkVerification(){
    String firstDigit = firstET.getText().toString().trim();
    String secondDigit = secondET.getText().toString().trim();
    String thirdDigit = thirdET.getText().toString().trim();
    String forthDigit = forthET.getText().toString().trim();
    String fifthDigit = fifthET.getText().toString().trim();
    String sixthDigit = sixthET.getText().toString().trim();
    if (firstDigit.isEmpty() || secondDigit.isEmpty() || thirdDigit.isEmpty() || forthDigit.isEmpty() || fifthDigit.isEmpty() || sixthDigit.isEmpty()) {
        Toast.makeText(this, getString(R.string.secret_password_error), Toast.LENGTH_SHORT).show();
    } else {
        passwordString = firstDigit + secondDigit + thirdDigit + forthDigit + fifthDigit + sixthDigit;

        // match password with database password

        if (SharedPref.getInstance(this).getString("Password").equals(passwordString)) {
            if(role==1){
                Intent intent = new Intent(PasswordCheck.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(PasswordCheck.this, Settings.class);
                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
          //  count++;
        }
    }
}



    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {


        return false;
    }
}
