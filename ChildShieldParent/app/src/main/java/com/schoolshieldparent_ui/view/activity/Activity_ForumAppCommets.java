package com.schoolshieldparent_ui.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.model.forumappcomments.Result;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.adapter.Adapter_ApplicationsComments;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_ForumAppCommets extends AppCompatActivity {
    @BindView(R.id.listViewForums)
    ListView listViewForums;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private static Activity_ForumAppCommets instance;
    private String PACKAGE = "";
    private String APP_NAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_forum_app_commets );
        ButterKnife.bind( this );
        instance = this;
        getSupportActionBar().hide();
        getIntentData();
        getAppsComments();
        setSwipeLayout();
    }

    private void getAppsComments() {
        DialogManager.startProgressDialog( this );
        WebServiceResult.AppsComments( PACKAGE );

    }



    private void getIntentData() {
        PACKAGE = getIntent().getStringExtra( "PACKAGE" );
        APP_NAME = getIntent().getStringExtra( "APP_NAME" );
        textView11.setText( APP_NAME );

    }

    private void setSwipeLayout() {
        swipeLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing( false );

            }
        } );
        swipeLayout.setColorSchemeResources( android.R.color.black, android.R.color.black, android.R.color.black, android.R.color.black );

    }

    public static Activity_ForumAppCommets getInstance() {
        return instance;
    }

    @OnClick(R.id.imageButton_Back)
    public void goBack() {
        finish();        overridePendingTransition( 0, 0 );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( 0, 0 );

    }

    public void updateAppliacationComments(Result result) {
        Adapter_ApplicationsComments adapter_applicationsComments = new Adapter_ApplicationsComments( this, result.getForumData() );
        listViewForums.setAdapter( adapter_applicationsComments );

    }
}
