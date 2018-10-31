package com.schoolshieldparent_ui.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.model.forum.Result;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.adapter.Adapter_Forums;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_ForumsAllApps extends AppCompatActivity {

    @BindView(R.id.listViewForums)
    ListView listViewForums;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    static Activity_ForumsAllApps instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_forums );
        ButterKnife.bind( this );
        instance = this;
        getSupportActionBar().hide();
        textView11.setText( getResources().getString( R.string.forum ) );
        getForumsData();
        setSwipeLayout();
    }

    private void setSwipeLayout() {
        swipeLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing( false );
                getForumsData();
            }
        });
        swipeLayout.setColorSchemeResources( android.R.color.black, android.R.color.black, android.R.color.black, android.R.color.black );

    }

    public static Activity_ForumsAllApps getInstance() {
        return instance;
    }

    public void getForumsData() {
        DialogManager.startProgressDialog( this );
        WebServiceResult.ForumApplications( SharedPref.getString( MyApplication.PARENT_ID ) );
    }

    public void updateForumsData(Result result) {
        Adapter_Forums adapter_forums = new Adapter_Forums( this, result.getApps() );
        listViewForums.setAdapter( adapter_forums );
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

}
