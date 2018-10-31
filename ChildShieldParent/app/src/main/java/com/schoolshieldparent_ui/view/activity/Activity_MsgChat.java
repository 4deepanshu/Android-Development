package com.schoolshieldparent_ui.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.model.particularstudend_msgs.StuMsgdatum;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.adapter.Adapter_MsgChat;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_MsgChat extends AppCompatActivity {
    @BindView(R.id.listViewMsgs)
    ListView listViewMsgs;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.textView11)
    TextView textView11;

    public static Activity_MsgChat instance;
    private String Phn_number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_msg_chat );
        ButterKnife.bind( this );
        instance = this;
        getSupportActionBar().hide();
        getIntentData();
        getMsgChat();
        setSwipeLoader();

    }

    private void getMsgChat() {
        DialogManager.startProgressDialog( this );
        WebServiceResult.GetParticulatChat( MyApplication.currentChildID, Phn_number );
    }

    private void getIntentData() {
        Phn_number = getIntent().getStringExtra( "PHN_NO" );
        textView11.setText( Phn_number );
    }

    public static Activity_MsgChat getInstance() {
        return instance;
    }

    @OnClick(R.id.imageButton_Back)
    public void doback() {
        finish();        overridePendingTransition( 0, 0 );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( 0, 0 );

    }

    public void updateMsgChat(List<StuMsgdatum> stuMsgdata) {
        Adapter_MsgChat adapter_msgChat = new Adapter_MsgChat( this, stuMsgdata );
        listViewMsgs.setAdapter( adapter_msgChat );
    }

    private void setSwipeLoader() {
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing( false );
                getMsgChat();

            }
        } );

        swipeRefreshLayout.setColorSchemeResources( android.R.color.black,

                android.R.color.black,

                android.R.color.black,

                android.R.color.black );
    }

}
