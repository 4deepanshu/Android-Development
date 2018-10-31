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

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.model.analytics.AnalyticsData;
import com.schoolshieldparent_ui.presenter.WebServiceConnection;
import com.schoolshieldparent_ui.view.activity.Activity_TopRunnungApp;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Analytics_Adapter extends BaseAdapter {


    Activity activity;
    List<AnalyticsData> analyticsData = new ArrayList<>();
    int type;
    private String light = "";
    private String appname = "";
    private String icon = "";
    private String count = "";
    private String age = "";
    private String duration = "";
    private String time = "";


    public Analytics_Adapter(Activity activity, List<AnalyticsData> analyticsData) {
        this.activity = activity;
        this.analyticsData = analyticsData;
    }

    @Override
    public int getCount() {
        return analyticsData.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflate = (LayoutInflater) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflate.inflate( R.layout.view_analytics, null );
            holder.textViewName = (TextView_Regular) convertView.findViewById( R.id.textViewName );
            holder.textViewTime = (TextView_Regular) convertView.findViewById( R.id.textViewTime );
            holder.textViewDuration = (TextView_Regular) convertView.findViewById( R.id.textViewDuration );
            holder.red = (CheckBox) convertView.findViewById( R.id.red );
            holder.green = (CheckBox) convertView.findViewById( R.id.green );
            holder.orange = (CheckBox) convertView.findViewById( R.id.orange );
            holder.imageViewAppIcon = (ImageView) convertView.findViewById( R.id.imageViewAppIcon );
            holder.nttextViewCount = (TextView_Regular) convertView.findViewById( R.id.nttextViewCount );
            holder.nttextViewTime = (TextView_Regular) convertView.findViewById( R.id.nttextViewTime );
            convertView.setTag( holder );
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        getData( position, holder );
        convertView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( activity, Activity_TopRunnungApp.class );
                intent.putExtra( "From", "LIST_CLICK" );
                intent.putExtra( "light", analyticsData.get( position ).getLight() );
                intent.putExtra( "appname", analyticsData.get( position ).getAppName() );
                intent.putExtra( "package_name", analyticsData.get( position ).getPackage() );
                intent.putExtra( "permanent", analyticsData.get( position ).getPermanentLock() );
                intent.putExtra( "icon", analyticsData.get( position ).getAppIcon() );
                intent.putExtra( "count", analyticsData.get( position ).getCountTotal() );
                intent.putExtra( "age", analyticsData.get( position ).getAgeRange() );
                intent.putExtra( "duration", analyticsData.get( position ).getTotalDuration() );
                intent.putExtra( "locked", analyticsData.get( position ).getLocked() + "" );
                intent.putExtra( "percentage", analyticsData.get( position ).getPercentage() + "" +
                        "" );
                activity.startActivity( intent );
                activity.overridePendingTransition( 0, 0 );

            }
        } );

        return convertView;

    }

    private void getData(int position, ViewHolder holder) {

        try {
            light = analyticsData.get( position ).getLight();
            appname = analyticsData.get( position ).getAppName();
            icon = analyticsData.get( position ).getAppIcon();
            count = analyticsData.get( position ).getCountTotal();
            age = analyticsData.get( position ).getAgeRange();
            duration = analyticsData.get( position ).getTotalDuration();
            time = analyticsData.get( position ).getDateUse();
        } catch (Exception e) {
        }

        setData( holder );
    }

    private void setData(ViewHolder holder) {
        holder.red.setChecked( false );
        holder.green.setChecked( false );
        holder.orange.setChecked( false );
        if (light.equalsIgnoreCase( "1" )) {
            holder.red.setChecked( true );
            holder.green.setChecked( false );
            holder.orange.setChecked( false );
        } else if (light.equalsIgnoreCase( "2" )) {
            holder.red.setChecked( false );
            holder.green.setChecked( true );
            holder.orange.setChecked( false );
        } else if (light.equalsIgnoreCase( "3" )) {
            holder.red.setChecked( false );
            holder.green.setChecked( false );
            holder.orange.setChecked( true );
        } else {
            holder.red.setChecked( false );
            holder.green.setChecked( false );
            holder.orange.setChecked( false );
        }

        holder.textViewName.setText( appname + "" );
        holder.nttextViewCount.setText( "X "
                + count );
        holder.nttextViewTime.setText( age );
        holder.textViewDuration.setText( convertDuration( duration ) );
        holder.textViewTime.setText( convertDate( time ) );

        try {
            if (!icon.equalsIgnoreCase( "" )) {
                ImageLoader.getInstance().displayImage( WebServiceConnection.APPLICATION_ICON_URLS + icon, holder.imageViewAppIcon, MyApplication.options  );
            } else
                holder.imageViewAppIcon.setBackgroundResource( R.drawable.app_placeholder );
        } catch (Exception e) {
        }
    }

    public class ViewHolder {
        TextView_Regular textViewName;
        TextView_Regular textViewTime;
        TextView_Regular textViewDuration;
        ImageView imageViewAppIcon;
        CheckBox red;
        CheckBox green;
        CheckBox orange;
        TextView_Regular nttextViewCount;
        TextView_Regular nttextViewTime;
    }

    public String convertDuration(String duration) {
        int totalDuraton = Integer.parseInt( duration );
        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int minutes = totalDuraton / SECONDS_IN_A_MINUTE;
        totalDuraton -= minutes * SECONDS_IN_A_MINUTE;

        int hours = minutes / MINUTES_IN_AN_HOUR;
        minutes -= hours * MINUTES_IN_AN_HOUR;
        String durationinhours = hours + "h " + minutes + "m " + totalDuraton + "s";
        return durationinhours;
    }

    public static String convertDate(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
            Date newDate = format.parse( time );

            format = new SimpleDateFormat( "dd.MM.yyyy" );
            String date = format.format( newDate );
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
