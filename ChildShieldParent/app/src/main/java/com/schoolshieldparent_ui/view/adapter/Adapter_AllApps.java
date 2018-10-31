package com.schoolshieldparent_ui.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.model.applications.Locked;
import com.schoolshieldparent_ui.presenter.WebServiceConnection;
import com.schoolshieldparent_ui.view.activity.Activity_ApplicationLock;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 12/8/16.
 */
public class Adapter_AllApps extends BaseAdapter {
    Activity activity;
    List<Locked> allAppsData = new ArrayList<>();
    int fragmentPos = 0;

    public Adapter_AllApps(Activity activity, List<Locked> allAppsData, int fragmentPos) {
        this.activity = activity;
        this.allAppsData = allAppsData;
        this.fragmentPos = fragmentPos;
    }

    @Override
    public int getCount() {
        return allAppsData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        LayoutInflater inflate = (LayoutInflater) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if (fragmentPos == 0) {
            convertView = inflate.inflate( R.layout.view_blockedapps, null );
            getIDSblockedapps( holder, convertView );
        } else {
            convertView = inflate.inflate( R.layout.view_unblockapps, null );
            getIDSunblockapps( holder, convertView );
        }
        getData( position, holder );
        convertView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( activity, Activity_ApplicationLock.class );
                intent.putExtra( "package", allAppsData.get( position ).getPackage() );
                intent.putExtra( "fragmentPos", fragmentPos + "" );
                intent.putExtra( "AppName", allAppsData.get( position ).getAppName() );
                intent.putExtra( "permanent", allAppsData.get( position ).getPermanentLock() );
                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                activity.startActivity( intent );
                activity.overridePendingTransition( 0, 0 );
            }
        } );

        return convertView;
    }

    private void getIDSunblockapps(ViewHolder holder, View convertView) {
        holder.textViewName = (TextView_Regular) convertView.findViewById( R.id.textViewName );
        holder.imageViewAppIcon = (ImageView) convertView.findViewById( R.id.imageViewAppIcon );
        holder.checkboxBlockAppsorNot = (CheckBox) convertView.findViewById( R.id.checkboxBlockAppsorNot );
    }

    private void getIDSblockedapps(ViewHolder holder, View convertView) {
        holder.textViewName = (TextView_Regular) convertView.findViewById( R.id.textViewName );
        holder.textViewDescName = (TextView_Regular) convertView.findViewById( R.id.textViewDescName );
        holder.imageViewAppIcon = (ImageView) convertView.findViewById( R.id.imageViewAppIcon );
        holder.checkboxBlockAppsorNot = (CheckBox) convertView.findViewById( R.id.checkboxBlockAppsorNot );
    }

    private void getData(int position, ViewHolder holder) {
        holder.textViewName.setText( allAppsData.get( position ).getAppName() );
        try {
            ImageLoader.getInstance().displayImage( WebServiceConnection.APPLICATION_ICON_URLS + allAppsData.get( position ).getAppIcon(), holder.imageViewAppIcon, MyApplication.options  );
        } catch (Exception e) {
            holder.imageViewAppIcon.setBackgroundResource( R.drawable.app_placeholder );
        }
        if (allAppsData.get( position ).getLockStatus().equalsIgnoreCase( "1" )) {
            holder.checkboxBlockAppsorNot.setChecked( true );
        } else {
            holder.checkboxBlockAppsorNot.setChecked( false );
        }
        if (allAppsData.get( position ).getPermanentLock().equalsIgnoreCase( "1" )) {
            holder.textViewDescName.setText( activity.getString( R.string.peramanentlyLocked ) );
        }
        if (!allAppsData.get( position ).getLockCount().equalsIgnoreCase( "0" )) {
            holder.textViewDescName.setText( allAppsData.get( position ).getLockCount() + " " + activity.getString( R.string.customlockapplied ) );
        }
        if (allAppsData.get( position ).getPermanentLock().equalsIgnoreCase( "1" ) && !allAppsData.get( position ).getLockCount().equalsIgnoreCase( "0" )) {
            holder.textViewDescName.setText( activity.getString( R.string.peramanentlyLocked ) + " " + activity.getString( R.string.and ) + allAppsData.get( position ).getLockCount() + " " + activity.getString( R.string.customlockapplied ) );
        }
    }

    public class ViewHolder {
        TextView_Regular textViewName;
        TextView_Regular textViewDescName;
        ImageView imageViewAppIcon;
        CheckBox checkboxBlockAppsorNot;


    }
}
