package com.schoolshieldparent_ui.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.model.analytics.AnalyticsData;
import com.schoolshieldparent_ui.view.activity.Activity_Analytics;
import com.schoolshieldparent_ui.view.adapter.Analytics_Adapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_Analytics extends Fragment {
    @BindView(R.id.listViewNotifications)
    ListView listviewAnalytics;
    @BindView(R.id.swipeContainerNotification)
    SwipeRefreshLayout swipeContainerNotification;
    private static Fragment_Analytics instance;
    private View view;
    List<AnalyticsData> analyticsData = new ArrayList<>();

    public Fragment_Analytics(List<AnalyticsData> analyticsData) {
        this.analyticsData = analyticsData;
    }

    public static Fragment_Analytics getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_todayanalytics, container, false );
        ButterKnife.bind( this, view );
        instance = this;
        setAdapter();
        setSwipeLoader();
        return view;
    }


    public void setAdapter() {

        Analytics_Adapter analytics_adapter = new Analytics_Adapter( getActivity(), this.analyticsData );
        listviewAnalytics.setAdapter( analytics_adapter );

    }

    private void setSwipeLoader() {
        swipeContainerNotification.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeContainerNotification.setRefreshing( false );
                Activity_Analytics.getInstance().getAnalyticsData();
            }

        } );

        swipeContainerNotification.setColorSchemeResources( android.R.color.black, android.R.color.black, android.R.color.black, android.R.color.black );
    }

}
