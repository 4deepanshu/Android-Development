package com.schoolshieldparent_ui.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.model.history.CurrentDay;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Adapter_History extends BaseAdapter {


    Activity activity;
    List<CurrentDay> historyData = new ArrayList<>();
    int type;
    private String light = "";
    private String appname = "";
    private String icon = "";
    private String count = "";
    private String age = "";
    private String duration = "";
    private String time = "";


    public Adapter_History(Activity activity, List<CurrentDay> historyData) {
        this.activity = activity;
        this.historyData = historyData;
    }

    @Override
    public int getCount() {
        return historyData.size();
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
            convertView = inflate.inflate( R.layout.view_history, null );
            holder.textViewURL = (TextView_Regular) convertView.findViewById( R.id.textViewURL );
            holder.textViewTime = (TextView_Regular) convertView.findViewById( R.id.textViewTime );
            holder.textViewTitle = (TextView_Regular) convertView.findViewById( R.id.textViewTitle );

            convertView.setTag( holder );
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setData( position, holder );


        return convertView;

    }

    private void setData(int position, ViewHolder holder) {
        holder.textViewTitle.setText( historyData.get( position ).getHistoryTitle() );
        holder.textViewURL.setText( historyData.get( position ).getHistoryUrl() );
        holder.textViewTime.setText( convertDate( historyData.get( position ).getDate() ) );

    }

    public class ViewHolder {
        TextView_Regular textViewTitle;
        TextView_Regular textViewURL;
        TextView_Regular textViewTime;
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
