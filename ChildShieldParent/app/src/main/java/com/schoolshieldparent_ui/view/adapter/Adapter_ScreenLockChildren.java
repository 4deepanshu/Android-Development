package com.schoolshieldparent_ui.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.model.allscreenlockchildren.ScreenLock;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import java.util.List;

public class Adapter_ScreenLockChildren extends BaseAdapter {

    List<ScreenLock> childList;
    Activity activity;


    public Adapter_ScreenLockChildren(Activity activity, List<ScreenLock> childList) {
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
            convertView = inflate.inflate( R.layout.view_screenlockchildlist, null );
            holder.textViewName = (TextView_Regular) convertView.findViewById( R.id.textViewName );
            holder.textViewActivatedornot = (TextView_Regular) convertView.findViewById( R.id.textViewActivatedornot );
            holder.textViewLockedOrNot = (TextView_Regular) convertView.findViewById( R.id.textViewLockedOrNot );
            convertView.setTag( holder );
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setData( holder, position );


        return convertView;
    }

    private void setData(ViewHolder holder, int position) {
        holder.textViewName.setText( childList.get( position ).getStudentName() + " " + childList.get( position ).getStudentLname() );
        if (childList.get( position ).getStudentStatus().equalsIgnoreCase( "1" )) {
            holder.textViewActivatedornot.setText( activity.getString( R.string.activated ) );
            holder.textViewActivatedornot.setTextColor( activity.getResources().getColor( R.color.colorgreen ) );
        } else {
            holder.textViewActivatedornot.setText( activity.getString( R.string.deactivated ) );
            holder.textViewActivatedornot.setTextColor( activity.getResources().getColor( R.color.colordeactivated ) );
        }
        if (childList.get( position ).getPermanentLock().equalsIgnoreCase( "1" )) {
            holder.textViewLockedOrNot.setText( activity.getString( R.string.screenpermanentlylocked ) );
        }
        if (!childList.get( position ).getLockCount().equalsIgnoreCase( "0" )) {
            holder.textViewLockedOrNot.setText( childList.get( position ).getLockCount() + " " + activity.getString( R.string.customlockapplied ) );
        }
        if (childList.get( position ).getPermanentLock().equalsIgnoreCase( "1" ) && !childList.get( position ).getLockCount().equalsIgnoreCase( "0" )) {
            holder.textViewLockedOrNot.setText( activity.getString( R.string.screenpermanentlylocked ) + " " + activity.getString( R.string.and ) + " " + childList.get( position ).getLockCount() + " " + activity.getString( R.string.customlockapplied ) );
        }
        if (!childList.get( position ).getPermanentLock().equalsIgnoreCase( "1" ) && childList.get( position ).getLockCount().equalsIgnoreCase( "0" )) {
            holder.textViewLockedOrNot.setText( activity.getString( R.string.thereisnolockappliedyet ) );
        }
    }


    public class ViewHolder {
        TextView_Regular textViewName;
        TextView_Regular textViewActivatedornot;
        TextView_Regular textViewLockedOrNot;
    }

}
