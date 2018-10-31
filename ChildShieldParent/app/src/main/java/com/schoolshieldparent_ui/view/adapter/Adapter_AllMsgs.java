package com.schoolshieldparent_ui.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.model.allmsgs.StuPhno;
import com.schoolshieldparent_ui.view.activity.Activity_MsgChat;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/8/16.
 */
public class Adapter_AllMsgs extends BaseAdapter {
    Activity activity;
    List<StuPhno> allmsgs = new ArrayList<>();

    private ViewHolder viewHolder = null;
    private LayoutInflater inflater;

    public Adapter_AllMsgs(Activity activity, List<StuPhno> allmsgs) {
        this.activity = activity;
        this.allmsgs = allmsgs;
    }

    @Override
    public int getCount() {
        return allmsgs.size();
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
            view = inflater.inflate( R.layout.view_allmsgs, null );
            viewHolder.textViewPhoneNumber = (TextView_Regular) view.findViewById( R.id.textViewPhoneNumber );
            viewHolder.textViewMsgsCount = (TextView_Regular) view.findViewById( R.id.textViewMsgsCount );
            view.setTag( viewHolder );
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textViewPhoneNumber.setText( allmsgs.get( position ).getAppPhno() );
        viewHolder.textViewMsgsCount.setText( allmsgs.get( position ).getTotal() + " "+activity.getString( R.string.messages ) );
        view.setOnClickListener( new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         activity.startActivity( new Intent( activity, Activity_MsgChat.class ).putExtra( "PHN_NO", allmsgs.get( position ).getAppPhno() ) );
                                         activity.overridePendingTransition( 0, 0 );

                                     }
                                 }
        );
        return view;
    }


    class ViewHolder {
        TextView_Regular textViewPhoneNumber;
        TextView_Regular textViewMsgsCount;

    }
}
