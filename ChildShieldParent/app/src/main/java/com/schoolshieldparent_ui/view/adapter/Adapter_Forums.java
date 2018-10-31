package com.schoolshieldparent_ui.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.model.forum.App;
import com.schoolshieldparent_ui.presenter.WebServiceConnection;
import com.schoolshieldparent_ui.view.activity.Activity_ForumAppDetail;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/8/16.
 */
public class Adapter_Forums extends BaseAdapter {
    Activity activity;
    List<App> apps = new ArrayList<>();

    private ViewHolder viewHolder = null;
    private LayoutInflater inflater;

    public Adapter_Forums(Activity activity, List<App> apps) {
        this.activity = activity;
        this.apps = apps;
    }

    @Override
    public int getCount() {
        return apps.size();
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
    public View getView(final int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            viewHolder = new ViewHolder();
            inflater = (LayoutInflater) activity.getSystemService( activity.LAYOUT_INFLATER_SERVICE );
            view = inflater.inflate( R.layout.view_forum, null );
            viewHolder.textViewName = (TextView_Regular) view.findViewById( R.id.textViewName );
            viewHolder.textViewAgeLimit = (TextView_Regular) view.findViewById( R.id.textViewAgeLimit );
            viewHolder.red = (CheckBox) view.findViewById( R.id.red );
            viewHolder.green = (CheckBox) view.findViewById( R.id.green );
            viewHolder.orange = (CheckBox) view.findViewById( R.id.orange );
            viewHolder.imageViewAppIcon = (ImageView) view.findViewById( R.id.imageViewAppIcon );
            view.setTag( viewHolder );
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        try {
            viewHolder.textViewName.setText( apps.get( position ).getAppName() );
        } catch (Exception e) {
        }
        if (!apps.get( position ).getAgeRange().equalsIgnoreCase( "" )) {
            viewHolder.textViewAgeLimit.setText( activity.getString( R.string.age )+" " + apps.get( position ).getAgeRange() );
        } else viewHolder.textViewAgeLimit.setText( "" );
        ImageLoader.getInstance().displayImage( WebServiceConnection.APPLICATION_ICON_URLS + apps.get( position ).getAppIcon(), viewHolder.imageViewAppIcon, MyApplication.options  );

        if (apps.get( position ).getLight().equalsIgnoreCase( "1" )) {
            viewHolder.red.setChecked( true );
            viewHolder.green.setChecked( false );
            viewHolder.orange.setChecked( false );
        } else if (apps.get( position ).getLight().equalsIgnoreCase( "2" )) {
            viewHolder.red.setChecked( false );
            viewHolder.green.setChecked( false );
            viewHolder.orange.setChecked( true );
        } else if (apps.get( position ).getLight().equalsIgnoreCase( "3" )) {
            viewHolder.red.setChecked( false );
            viewHolder.green.setChecked( true );
            viewHolder.orange.setChecked( false );
        }
        view.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( activity, Activity_ForumAppDetail.class );
                intent.putExtra( "APP_NAME", apps.get( position ).getAppName() );
                intent.putExtra( "APP_ID", apps.get( position ).getAppId() );
                intent.putExtra( "DESC", apps.get( position ).getDescription() );
                intent.putExtra( "PACKAGE", apps.get( position ).getPackage() );
                activity.startActivity( intent );
                activity.overridePendingTransition( 0, 0 );


            }
        } );

        return view;
    }


    class ViewHolder {
        TextView_Regular textViewName;
        ImageView imageViewAppIcon;
        TextView_Regular textViewAgeLimit;
        CheckBox red;
        CheckBox green;
        CheckBox orange;
    }
}
