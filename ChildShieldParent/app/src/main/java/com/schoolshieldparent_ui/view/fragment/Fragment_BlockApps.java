package com.schoolshieldparent_ui.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.schoolshieldparent_ui.view.adapter.Adapter_AllApps;
import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.model.applications.Locked;
import com.schoolshieldparent_ui.view.activity.Activity_Applications;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_BlockApps extends Fragment {
    @BindView(R.id.listViewBlockApps)
    ListView listViewBlockApps;
    @BindView(R.id.swipeContainerNotification)
    SwipeRefreshLayout swipeContainerNotification;
    private static Fragment_BlockApps instance;
    private View view;
    List<Locked> allAppsData = new ArrayList<>();
    int fragmentPos = 0;

    public Fragment_BlockApps(List<Locked> allAppsData, int fragmentPos) {
        this.allAppsData.clear();
        this.allAppsData.addAll( allAppsData );
        this.fragmentPos = fragmentPos;

    }

    public static Fragment_BlockApps getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_blockapps, container, false );
        ButterKnife.bind( this, view );
        instance = this;
        setAdapter();
        setSwipeLoader();
        return view;
    }

    public void setAdapter() {
        Adapter_AllApps adapter_allApps = new Adapter_AllApps( getActivity(), allAppsData, fragmentPos );
        listViewBlockApps.setAdapter( adapter_allApps );

    }

    private void setSwipeLoader() {
        swipeContainerNotification.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeContainerNotification.setRefreshing( false );
                Activity_Applications.getInstance().getAllApps("");
            }

        } );
        swipeContainerNotification.setColorSchemeResources( android.R.color.black, android.R.color.black, android.R.color.black, android.R.color.black );
    }

}
