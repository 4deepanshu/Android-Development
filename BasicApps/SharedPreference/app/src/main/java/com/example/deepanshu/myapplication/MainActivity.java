package com.example.deepanshu.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp=getSharedPreferences("Mypref",MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("key","INDIA");
        edit.commit();

        String var = sp.getString("key","Idontknnow");
        Toast.makeText(this,var,Toast.LENGTH_SHORT).show();


    }

int count = 0;
public void save(View v)
{
    EditText email = findViewById(R.id.EditText1);
    EditText pass = findViewById(R.id.EditText2);
    String emailid = email.getText().toString();
    String password = pass.getText().toString();
    count = count + 1;

    SharedPreferences sharedPref = getSharedPreferences("List",Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putString("Key1",emailid);
    editor.putString("Key2",password);
    editor.apply();

    Toast.makeText(this,"Saved info "+count, Toast.LENGTH_SHORT).show();

}

public void display(View v)
{
    SharedPreferences sharedPref = getSharedPreferences("List", Context.MODE_PRIVATE);

    String name = sharedPref.getString("Key1","");
    String pass1 = sharedPref.getString("Key2","");

    TextView tx = findViewById(R.id.TextView);
    tx.setText("Recent - "+name+" : "+pass1);

    Toast.makeText(this,"Displaying",Toast.LENGTH_SHORT).show();
}


}



