package com.schoolshieldparent_ui.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.model.history.CurrentDay;
import com.schoolshieldparent_ui.view.activity.Activity_History;
import com.schoolshieldparent_ui.view.adapter.Adapter_History;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_History extends Fragment {
    @BindView(R.id.listViewNotifications)
    ListView listviewAnalytics;
    @BindView(R.id.swipeContainerNotification)
    SwipeRefreshLayout swipeContainerNotification;
    private static Fragment_History instance;
    private View view;
    List<CurrentDay> historyData = new ArrayList<>();

    public Fragment_History(List<CurrentDay> historyData) {
        this.historyData = historyData;
    }

    public static Fragment_History getInstance() {
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

        Adapter_History analytics_adapter = new Adapter_History( getActivity(), this.historyData );
        listviewAnalytics.setAdapter( analytics_adapter );

    }

    private void setSwipeLoader() {
        swipeContainerNotification.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeContainerNotification.setRefreshing( false );
                Activity_History.getInstance().getHistoryData();
            }

        } );

        swipeContainerNotification.setColorSchemeResources( android.R.color.black, android.R.color.black, android.R.color.black, android.R.color.black );
    }

}
