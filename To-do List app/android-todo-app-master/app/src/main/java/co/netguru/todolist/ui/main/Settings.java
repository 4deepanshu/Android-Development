package co.netguru.todolist.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.netguru.todolist.R;
import co.netguru.todolist.utils.SharedPref;

public class Settings extends AppCompatActivity {
    @BindView(R.id.etName)EditText etName;
    @BindView(R.id.etAddress)EditText etAddress;
    @BindView(R.id.etTeacherid)EditText etTeacherid;
    @BindView(R.id.etMentor)EditText etMentor;
    @BindView(R.id.etCompanyName)EditText etCompanyName;
    @BindView(R.id.etPost)EditText etPost;
    @BindView(R.id.etProjectName)EditText etProjectName;
    @BindView(R.id.etTechnology)EditText etTechnology;
    @BindView(R.id.etProjectLeader)EditText etProjectLeader;
    @BindView(R.id.etTotalMarks)EditText etTotalMarks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        etName.setText(SharedPref.getInstance(this).getString("Name"));
        etAddress.setText(SharedPref.getInstance(this).getString("Address"));
        etTeacherid.setText(SharedPref.getInstance(this).getString("Teacherid"));
        etMentor.setText(SharedPref.getInstance(this).getString("Mentor"));
        etCompanyName.setText(SharedPref.getInstance(this).getString("Company"));
        etPost.setText(SharedPref.getInstance(this).getString("Post"));
        etProjectName.setText(SharedPref.getInstance(this).getString("ProjectName"));
        etTechnology.setText(SharedPref.getInstance(this).getString("Technology"));
        etProjectLeader.setText(SharedPref.getInstance(this).getString("ProjectLeader"));
        etTotalMarks.setText(SharedPref.getInstance(this).getString("TotalMarks"));
    }
    @OnClick(R.id.ivBack)
    public void goBack(){
        super.onBackPressed();
    }
    @OnClick(R.id.btnSave)
    public void save(){
        SharedPref.getInstance(this).setString("Name",etName.getText().toString());
        SharedPref.getInstance(this).setString("Address",etAddress.getText().toString());
        SharedPref.getInstance(this).setString("Teacherid",etTeacherid.getText().toString());
        SharedPref.getInstance(this).setString("Mentor",etMentor.getText().toString());
        SharedPref.getInstance(this).setString("Company",etCompanyName.getText().toString());
        SharedPref.getInstance(this).setString("Post",etPost.getText().toString());
        SharedPref.getInstance(this).setString("ProjectName",etProjectName.getText().toString());
        SharedPref.getInstance(this).setString("Technology",etTechnology.getText().toString());
        SharedPref.getInstance(this).setString("ProjectLeader",etProjectLeader.getText().toString());
        SharedPref.getInstance(this).setString("TotalMarks",etTotalMarks.getText().toString());
        Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.btnCheckCompleted)
    public void openCompleted() {
        Intent intent = new Intent(this, CompletedTask.class);
        startActivity(intent);
    }
}
