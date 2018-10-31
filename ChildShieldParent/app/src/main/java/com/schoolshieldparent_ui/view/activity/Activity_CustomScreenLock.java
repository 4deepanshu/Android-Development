package com.schoolshieldparent_ui.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.controller.utils.SimpleAlertDialog;
import com.schoolshieldparent_ui.model.customscreenlock.CusLock;
import com.schoolshieldparent_ui.model.universalresponce.UniversalResponce;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.adapter.Adapter_CustomScreenLocks;
import com.schoolshieldparent_ui.view.custom_controls.Button_Regular;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_CustomScreenLock extends AppCompatActivity {
    @BindView(R.id.listViewCustomLocks)
    ListView listViewCustomLocks;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeContainerNotification;

    public static Activity_CustomScreenLock instance;
    public String studentid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_custom_screen_lock );
        ButterKnife.bind( this );
        getSupportActionBar().hide();
        instance = this;
        getIntentData();
        getCustomLocks( "" );
        setSwipeLoader();
    }

    public void getIntentData() {
        studentid = getIntent().getStringExtra( "studentid" );
        textView11.setText( getString( R.string.customScreenLocks ) );
    }

    public void getCustomLocks(String refresh) {
        if (refresh.equalsIgnoreCase( getString( R.string.refresh ) )) {
            WebServiceResult.Custom_ScreenLocks( studentid );
        } else {
            DialogManager.startProgressDialog( this );
            WebServiceResult.Custom_ScreenLocks( studentid );
        }
    }

    public static Activity_CustomScreenLock getInstance() {
        return instance;
    }

    public void updateCustomScreenLock(List<CusLock> cusLocks) {
        if (cusLocks.size() == 0) {
            SimpleAlertDialog.showSimpleAlertDialog( this, getString( R.string.nolockyet ) );
        } else {
            Adapter_CustomScreenLocks adapter_customLocks = new Adapter_CustomScreenLocks( this, cusLocks );
            listViewCustomLocks.setAdapter( adapter_customLocks );
        }
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

    private void setSwipeLoader() {
        swipeContainerNotification.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeContainerNotification.setRefreshing( false );
                getCustomLocks( getString( R.string.refresh ) );
            }

        } );
        swipeContainerNotification.setColorSchemeResources( android.R.color.black, android.R.color.black, android.R.color.black, android.R.color.black );
    }

    public void lockDeleted(UniversalResponce body) {
        if (body.getResult().getStatus() == 1) {
            showLockStatus( this, getString( R.string.notifylockdeleted ) );
        } else {
            showLockStatus( this, getString( R.string.notifylockdeletedfail ) );

        }

    }

    public void showLockStatus(final Activity activity, final String message) {
        final Dialog dialog = new Dialog( activity );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );

        dialog.setContentView( R.layout.view_simple_alert );
        TextView_Regular text = (TextView_Regular) dialog.findViewById( R.id.message );
        text.setText( message );
        Button_Regular dialogButton = (Button_Regular) dialog.findViewById( R.id.button );
        dialogButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Applications.getInstance().getAllApps( getString( R.string.refresh ) );
                Activity_CustomLock.getInstance().getCustomLocks( getString( R.string.refresh ) );
                dialog.dismiss();
            }
        } );
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
        dialog.show();
        dialog.setOnCancelListener( new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                try {
                    Activity_Applications.getInstance().getAllApps( getString( R.string.refresh ) );

                    Activity_CustomLock.getInstance().getCustomLocks( getString( R.string.refresh ) );
                    dialog.dismiss();
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        } );
    }

}
