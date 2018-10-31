package com.schoolshieldchild_ui.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.schoolshieldchild_ui.R;
import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NumbersSettingsActivity extends AppCompatActivity {


    @BindView(R.id.edittext_CallNumberFather)
    EditText editTextCallFater;
    @BindView(R.id.edittext_CallNumberMother)
    EditText editTextCallMother;
    @BindView(R.id.edittext_MessageNumberFather)
    EditText editTextMessageMother;
    @BindView(R.id.edittext_MessageNumberMother)
    EditText editTextMessageFather;
    @BindView(R.id.edittext_MessageText)
    EditText editTextMessageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        ButterKnife.bind(this);


        editTextCallFater.setText(SharedPref.getString(MyApplication.PHONE_FATHER));
        editTextCallMother.setText(SharedPref.getString(MyApplication.PHONE_MOTHER));
        editTextMessageFather.setText(SharedPref.getString(MyApplication.MESSAGE_FATHER));
        editTextMessageMother.setText(SharedPref.getString(MyApplication.MESSAGE_MOTHER));
        editTextMessageText.setText(SharedPref.getString(MyApplication.MESSAGE_DEFAULT));

    }


    @OnClick(R.id.buttonSave)
    public void save() {
        String phoneCallFather = editTextCallFater.getText().toString();
        String phoneCallMother = editTextCallMother.getText().toString();
        String phoneMessageFather = editTextMessageFather.getText().toString();
        String phoneMessageMother = editTextMessageMother.getText().toString();
        String phoneMessageText = editTextMessageText.getText().toString();

        if (isPhoneValid(phoneCallFather) && isPhoneValid(phoneCallMother) && isPhoneValid(phoneMessageFather)
                && isPhoneValid(phoneMessageMother)) {
            String val;
            if (phoneMessageText.length() > 0) {
                val = phoneMessageText;
            } else {
                val = getString(R.string.contactme);
            }
            SharedPref.setString(MyApplication.PHONE_FATHER, phoneCallFather);
            SharedPref.setString(MyApplication.PHONE_MOTHER, phoneCallMother);
            SharedPref.setString(MyApplication.MESSAGE_FATHER, phoneMessageFather);
            SharedPref.setString(MyApplication.MESSAGE_MOTHER, phoneMessageMother);
            SharedPref.setString(MyApplication.MESSAGE_DEFAULT, val);


            Toast.makeText(getApplicationContext(), "Numbers updated , Please try to call or message", Toast.LENGTH_SHORT).show();

            finish();
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        } else {
            Toast.makeText(getApplicationContext(), "Something went wrong, Please check all details again", Toast.LENGTH_SHORT)
                    .show();

        }
        super.onPause();
    }

    static boolean isPhoneValid(String phoneNo) {
        String expression = "^[0-9-+]{9,15}$";
        CharSequence inputStr = phoneNo;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return (matcher.matches()) ? true : false;
    }


}
