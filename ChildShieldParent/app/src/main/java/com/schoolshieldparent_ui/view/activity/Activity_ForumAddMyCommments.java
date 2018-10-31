package com.schoolshieldparent_ui.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.controller.utils.CustomToast;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.model.forumappcomments.Result;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.adapter.Adapter_ApplicationsComments;
import com.schoolshieldparent_ui.view.custom_controls.EditText_Regular;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_ForumAddMyCommments extends AppCompatActivity {

    @BindView(R.id.listViewForums)
    ListView listViewForums;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.buttonAddComments)
    Button buttonAddComments;
    @BindView(R.id.editTextComments)
    EditText_Regular editTextComments;
    boolean commentAdded = false;

    private static Activity_ForumAddMyCommments instance;
    private String PACKAGE = "";
    private String APP_NAME = "";
    private String PARENT_ID = "";
    public Adapter_ApplicationsComments adapter_applicationsComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_forum_add_my_commments );
        ButterKnife.bind( this );
        getSupportActionBar().hide();
        instance = this;
        getIntentData();
        getMyComments();
    }

    @OnClick(R.id.buttonAddComments)
    public void addComments() {
        if (editTextComments.getText().toString().equalsIgnoreCase( "" )) {
            CustomToast.showToast(this, getString(R.string.pleaseentercommenttoadd));
        } else {
            WebServiceResult.AddMycommetsData( PACKAGE, PARENT_ID, editTextComments.getText().toString() );
        }


    }

    public void getMyComments() {
        DialogManager.startProgressDialog( this );
        WebServiceResult.AddMyComments( PACKAGE, PARENT_ID );
    }

    public static Activity_ForumAddMyCommments getInstance() {
        return instance;
    }

    private void getIntentData() {
        PACKAGE = getIntent().getStringExtra( "PACKAGE" );
        APP_NAME = getIntent().getStringExtra( "APP_NAME" );
        PARENT_ID = getIntent().getStringExtra( "PARENT_ID" );
        textView11.setText( APP_NAME );

    }

    public void updateMyComments(Result result) {
        adapter_applicationsComments = new Adapter_ApplicationsComments( this, result.getForumData() );
        listViewForums.setAdapter( adapter_applicationsComments );
        if (commentAdded == true) {
            listViewForums.setSelection( result.getForumData().size() - 1 );
        }
    }

    public void updateComments() {
        commentAdded = true;
        getMyComments();
        adapter_applicationsComments.notifyDataSetChanged();
        editTextComments.setText( "" );

    }

    @OnClick(R.id.imageButton_Back)
    public void goBack() {
        finish();
        overridePendingTransition( 0, 0 );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( 0, 0 );

    }
}
