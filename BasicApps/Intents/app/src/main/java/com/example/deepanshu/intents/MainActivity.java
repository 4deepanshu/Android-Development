package com.example.deepanshu.intents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void navigate(View v)
    {
        Intent i1=new Intent(this,Main2Activity.class);
        i1.putExtra("key","Welcome to 2nd window");
        startActivity(i1);

    }
}
