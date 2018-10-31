package co.netguru.todolist.ui.main;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import co.netguru.todolist.R;
import co.netguru.todolist.utils.SharedPref;

public class SplashActivity extends AppCompatActivity {

    private Dialog dialogSelectRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        showDialog();
    }
    public void showDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.view_role_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final RadioButton opt_1 = (RadioButton) dialogView.findViewById(R.id.opt_1);
        final RadioButton opt_2 = (RadioButton) dialogView.findViewById(R.id.opt_2);
        Button buttonConfirm = (Button) dialogView.findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSelectRole.dismiss();
                if(opt_1.isChecked()){
                    if(SharedPref.getInstance(getApplicationContext()).getString("Password").equals("")){
                        Intent intent=new Intent(SplashActivity.this,CreatePasswordActivity.class);
                        intent.putExtra("role",1);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent=new Intent(SplashActivity.this,PasswordCheck.class);
                        intent.putExtra("role",1);
                        startActivity(intent);
                        finish();
                    }
                }else if(opt_2.isChecked()){
                    if(SharedPref.getInstance(getApplicationContext()).getString("Password").equals("")){
                        Intent intent=new Intent(SplashActivity.this,CreatePasswordActivity.class);
                        intent.putExtra("role",2);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent=new Intent(SplashActivity.this,PasswordCheck.class);
                        intent.putExtra("role",2);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });
        dialogSelectRole = builder.create();
        dialogSelectRole.setCancelable(false);
        dialogSelectRole.show();
    }
}
