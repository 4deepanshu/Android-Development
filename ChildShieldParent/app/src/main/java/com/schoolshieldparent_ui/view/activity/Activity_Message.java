package com.schoolshieldparent_ui.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.controller.utils.SimpleAlertDialog;
import com.schoolshieldparent_ui.model.allmsgs.AllMessage;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.adapter.Adapter_AllMsgs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Message extends AppCompatActivity {

    @BindView(R.id.listViewAllMsgs)
    ListView listViewAllMsgs;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.textView11)
    TextView textView11;

    public static Activity_Message instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_activity__message );
        ButterKnife.bind( this );
        instance = this;
        getSupportActionBar().hide();
        getAllMessagingData();
        textView11.setText( getString( R.string.messages ) );
        setSwipeLoader();
    }

    private void getAllMessagingData() {
        DialogManager.startProgressDialog( this );
        WebServiceResult.AllMessageingData( MyApplication.currentChildID );
    }

    public static Activity_Message getInstance() {
        return instance;
    }


    public void updateMessageList(AllMessage body) {
        if (body.getStatus().toString().equalsIgnoreCase( "1" )) {
            Adapter_AllMsgs adapter_allMsgs = new Adapter_AllMsgs( this, body.getStuPhnos() );
            listViewAllMsgs.setAdapter( adapter_allMsgs );
        } else {
            SimpleAlertDialog.showSimpleAlertDialog( this, getString( R.string.nomessage ) );
        }

    }



    private void setSwipeLoader() {
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing( false );
                getAllMessagingData();

            }
        } );

        swipeRefreshLayout.setColorSchemeResources( android.R.color.black,

                android.R.color.black,

                android.R.color.black,

                android.R.color.black );
    }


    @OnClick(R.id.imageButton_Back)
    public void doback() {
        finish();
        overridePendingTransition( 0, 0 );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( 0, 0 );
    }
}
