package com.schoolshieldparent_ui.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.model.notification.NotificationResult;
import com.schoolshieldparent_ui.view.activity.Activity_Notification;
import com.schoolshieldparent_ui.view.adapter.Adapter_Notifications;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Fragment_Notification extends Fragment {
    @BindView(R.id.listViewNotifications)
    ListView listviewNotifications;
    @BindView(R.id.swipeContainerNotification)
    SwipeRefreshLayout swipeContainerNotification;


    private static Fragment_Notification instance;
    List<NotificationResult> notifications=new ArrayList<>();

    public Fragment_Notification(List<NotificationResult> notifications) {
        this.notifications=notifications;
    }
    public static Fragment_Notification getInstance() {
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_notifications, container, false );
        ButterKnife.bind( this, view );
        instance = this;
        setAdapter();
        setSwipeLoader();
        return view;
    }

    private void setSwipeLoader() {
        swipeContainerNotification.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeContainerNotification.setRefreshing( false );
                Activity_Notification.getInstance().getNotificationData(getString( R.string.refresh ) );
            }

        } );

        swipeContainerNotification.setColorSchemeResources( android.R.color.black, android.R.color.black, android.R.color.black, android.R.color.black );
    }

    public void setAdapter() {

            Adapter_Notifications analytics_adapter = new Adapter_Notifications( getActivity(), this.notifications);
            listviewNotifications.setAdapter( analytics_adapter );
    }
}
