package com.example.deepanshu.activitycycle;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        Toast.makeText(this,"Paused",Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onResume() {
        Toast.makeText(this,"Resumed",Toast.LENGTH_SHORT).show();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"BackPressed",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this,"Destroyed",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Toast.makeText(this,"Restarted",Toast.LENGTH_SHORT).show();
        super.onRestart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Toast.makeText(this,"Created",Toast.LENGTH_SHORT).show();
    }
}
