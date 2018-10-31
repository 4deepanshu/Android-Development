package co.netguru.todolist.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.netguru.todolist.R;
import co.netguru.todolist.ui.base.BaseActivity;
import co.netguru.todolist.utils.SharedPref;

public class CreatePasswordActivity extends AppCompatActivity {

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

    @BindView(R.id.firstConfirmET)
    EditText firstConfirmET;

    @BindView(R.id.secondConfirmET)
    EditText secondConfirmET;

    @BindView(R.id.thirdConfirmET)
    EditText thirdConfirmET;

    @BindView(R.id.forthConfirmET)
    EditText forthConfirmET;

    @BindView(R.id.fifthConfirmET)
    EditText fifthConfirmET;

    @BindView(R.id.sixthConfirmET)
    EditText sixthConfirmET;

    private String passwordConfirmString;
    private String passwordString;
    int role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        ButterKnife.bind(this);
        role=getIntent().getIntExtra("role",-1);
        firstET.requestFocus();
        initUI();
    }

    @OnClick(R.id.btnNext)
    public void setPassword() {
        String firstDigit = firstET.getText().toString().trim();
        String secondDigit = secondET.getText().toString().trim();
        String thirdDigit = thirdET.getText().toString().trim();
        String forthDigit = forthET.getText().toString().trim();
        String fifthDigit = fifthET.getText().toString().trim();
        String sixthDigit = sixthET.getText().toString().trim();


        String firstConfirmDigit = firstConfirmET.getText().toString().trim();
        String secondConfirmDigit = secondConfirmET.getText().toString().trim();
        String thirdConfirmDigit = thirdConfirmET.getText().toString().trim();
        String forthConfirmDigit = forthConfirmET.getText().toString().trim();
        String fifthConfirmDigit = fifthConfirmET.getText().toString().trim();
        String sixthConfirmDigit = sixthConfirmET.getText().toString().trim();

        if (firstDigit.isEmpty() || secondDigit.isEmpty() || thirdDigit.isEmpty() || forthDigit.isEmpty() || fifthDigit.isEmpty() || sixthDigit.isEmpty()) {
            Toast.makeText(this, getString(R.string.secret_password_error), Toast.LENGTH_SHORT).show();
            return;
        } else {
            passwordString = firstDigit + secondDigit + thirdDigit + forthDigit + fifthDigit + sixthDigit;
        }



        if (firstConfirmDigit.isEmpty() || secondConfirmDigit.isEmpty() || thirdConfirmDigit.isEmpty() || forthConfirmDigit.isEmpty() || fifthConfirmDigit.isEmpty() || sixthConfirmDigit.isEmpty()) {
            Toast.makeText(this, getString(R.string.confirm_password_error), Toast.LENGTH_SHORT).show();
            return;
        } else {
            passwordConfirmString = firstConfirmDigit + secondConfirmDigit + thirdConfirmDigit + forthConfirmDigit + fifthConfirmDigit + sixthConfirmDigit;
        }


        if (!(passwordString.isEmpty()) && !(passwordConfirmString.isEmpty()) && passwordString.equals(passwordConfirmString)) {
          //  showToast(getString(R.string.working));
            SharedPref.getInstance(this).setString("Password",passwordString);
            if(role==1){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(this, Settings.class);
                intent.putExtra("password", passwordString);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, getString(R.string.password_mismatch_error), Toast.LENGTH_SHORT).show();
            return;
        }
    }


    private void initUI() {
        settextWatcherlistener();
        setconfirmtextWatcherlistener();

       /* fifthET.setOnEditorActionListener (new TextView.OnEditorActionListener () {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkVerification ();
                }

                return false;
            }
        });*/
    }


    private void settextWatcherlistener() {
        firstET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //secondET.requestFocus ();
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
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //thirdET.requestFocus ();

            }
        });
    }


    private void setconfirmtextWatcherlistener() {
        firstConfirmET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //secondET.requestFocus ();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0)
                    secondConfirmET.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {
                // secondET.requestFocus ();

            }
        });
        secondConfirmET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //thirdET.requestFocus ();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0)
                    thirdConfirmET.requestFocus();
                else if(count==0){
                    firstConfirmET.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //thirdET.requestFocus ();

            }
        });
        thirdConfirmET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //thirdET.requestFocus ();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0)
                    forthConfirmET.requestFocus();
                else if(count==0){
                    secondConfirmET.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //thirdET.requestFocus ();

            }
        });
        forthConfirmET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //thirdET.requestFocus ();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0)
                    fifthConfirmET.requestFocus();
                else if(count==0){
                    thirdConfirmET.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //thirdET.requestFocus ();

            }
        });
        fifthConfirmET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // sixthET.requestFocus ();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0)
                    sixthConfirmET.requestFocus();
                else if(count==0){
                    forthConfirmET.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //thirdET.requestFocus ();

            }
        });

        sixthConfirmET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //thirdET.requestFocus ();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // if (count != 0)
                // thirdET.requestFocus ();
                  if(count==0){
                    fifthConfirmET.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //thirdET.requestFocus ();

            }
        });

    }

}
