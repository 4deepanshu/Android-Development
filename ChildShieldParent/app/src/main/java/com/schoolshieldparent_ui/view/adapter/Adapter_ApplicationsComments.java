package com.schoolshieldparent_ui.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.model.forumappcomments.ForumDatum;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 11/8/16.
 */
public class Adapter_ApplicationsComments extends BaseAdapter {
    Activity activity;
    private LayoutInflater inflater = null;
    private VIewHolder viewholder = null;
    List<ForumDatum> forumData = new ArrayList<>();

    public Adapter_ApplicationsComments(Activity activity, List<ForumDatum> forumData) {
        this.activity = activity;
        this.forumData = forumData;
    }

    @Override
    public int getCount() {
        return forumData.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        viewholder = new VIewHolder();
        if (view == null) {
            inflater = (LayoutInflater) activity.getSystemService( activity.LAYOUT_INFLATER_SERVICE );
            view = inflater.inflate( R.layout.view_appcomments, null );
            viewholder.textViewAppComments = (TextView_Regular) view.findViewById( R.id.textViewAppComments );
            viewholder.textViewParentName = (TextView_Regular) view.findViewById( R.id.textViewParentName );
            view.setTag( viewholder );
        } else {
            viewholder = (VIewHolder) view.getTag();
        }
        viewholder.textViewAppComments.setText( forumData.get( position ).getComment() );
        viewholder.textViewParentName.setText( forumData.get( position ).getParentName() );
        return view;
    }

    public class VIewHolder {
        TextView_Regular textViewAppComments;
        TextView_Regular textViewParentName;

    }
}
