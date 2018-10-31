package com.schoolshieldparent_ui.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.model.particularstudend_msgs.StuMsgdatum;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Light;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import java.util.ArrayList;
import java.util.List;

public class Adapter_MsgChat extends BaseAdapter {

    List<StuMsgdatum> ChatMsgsList = new ArrayList<>();
    Activity activity;
    private ViewHolder holder;
    private LayoutInflater inflate;


    public Adapter_MsgChat(Activity activity, List<StuMsgdatum> ChatMsgsList) {
        this.activity = activity;
        this.ChatMsgsList = ChatMsgsList;
    }

    @Override
    public int getCount() {
        return ChatMsgsList.size();
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

        holder = new ViewHolder();
        inflate = (LayoutInflater) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if (ChatMsgsList.get( position ).getAppStatus().equalsIgnoreCase( "IN" )) {
            convertView = inflate.inflate( R.layout.view_msgchat_in, null );
            getIdInMsgs( convertView );
        } else if (ChatMsgsList.get( position ).getAppStatus().equalsIgnoreCase( "OUT" )) {
            convertView = inflate.inflate( R.layout.view_msgchat_out, null );
            getIdOutMsgs( convertView );
        }
        holder.textViewMsg.setText( ChatMsgsList.get( position ).getAppMsg() );
        holder.textViewTime.setText( ChatMsgsList.get( position ).getAppMsgServerDate() );
        return convertView;
    }


    private void getIdOutMsgs(View convertView) {
        holder.textViewMsg = (TextView_Regular) convertView.findViewById( R.id.textViewMsg );
        holder.textViewTime = (TextView_Light) convertView.findViewById( R.id.textViewTime );
    }

    private void getIdInMsgs(View convertView) {
        holder.textViewMsg = (TextView_Regular) convertView.findViewById( R.id.textViewMsg );
        holder.textViewTime = (TextView_Light) convertView.findViewById( R.id.textViewTime );
    }

    class ViewHolder {
        TextView_Regular textViewMsg;
        TextView_Light textViewTime;

    }

}
