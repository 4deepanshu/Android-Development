package com.schoolshieldparent_ui.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.model.children.Childs;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import java.util.List;

public class Adapter_SelectChild_home_spinner extends BaseAdapter {

    List<Childs> childList;
    Activity activity;

    public Adapter_SelectChild_home_spinner(Activity activity, List<Childs> childList) {
        this.activity = activity;
        this.childList = childList;

    }

    @Override
    public int getCount() {

        return childList.size();
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflate = (LayoutInflater) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflate.inflate( R.layout.view_childlist_home, null );
            holder.textViewName = (TextView_Regular) convertView.findViewById( R.id.textViewName );
            holder.imageViewTick = (ImageView) convertView.findViewById( R.id.imageView_Enable );
            convertView.setTag( holder );
        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.textViewName.setText( childList.get( position ).getStudentName() + " " + childList.get( position ).getStudentLname() );

        if (!childList.get( position ).getStudentName().equalsIgnoreCase( activity.getString( R.string.selectChild ) )) {
            if (childList.get( position ).getStudentStatus().equalsIgnoreCase( "1" )) {
                holder.imageViewTick.setVisibility( View.VISIBLE );
                holder.imageViewTick.setBackgroundResource( R.drawable.tick );
            } else {
                holder.imageViewTick.setVisibility( View.VISIBLE );
                holder.imageViewTick.setBackgroundResource( R.drawable.minus );

            }
        } else {
            holder.imageViewTick.setBackgroundResource( R.drawable.arrowdown );
        }
        return convertView;

    }

    public class ViewHolder {
        TextView_Regular textViewName;
        ImageView imageViewTick;
    }

}
