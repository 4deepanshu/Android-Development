package co.netguru.todolist.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;

import java.util.ArrayList;
import java.util.List;

import co.netguru.todolist.R;

public class LockActivity extends AppCompatActivity {

    private PatternLockView mPatternLockView;
    private List<PatternLockView.Dot> dots;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        PatternLockView.Dot dot1=PatternLockView.Dot.of(0,0);
        PatternLockView.Dot dot2=PatternLockView.Dot.of(0,1);
        PatternLockView.Dot dot3=PatternLockView.Dot.of(0,2);
        dots=new ArrayList<>();
        dots.add(dot1);
        dots.add(dot2);
        dots.add(dot3);
        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {


            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                if(pattern.equals(dots)){
                    startActivity(new Intent(LockActivity.this,MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(LockActivity.this, "Wrong Pattern", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCleared() {

            }
        });
    }
}
