package com.schoolshieldparent_ui.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.model.customlock.CusLock;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import java.util.List;

public class Adapter_CustomLocks extends BaseAdapter {

    List<CusLock> customLocks;
    Activity activity;
    private String finalDayString = "";

    public Adapter_CustomLocks(Activity activity, List<CusLock> customLocks) {
        this.activity = activity;
        this.customLocks = customLocks;
    }

    @Override
    public int getCount() {
        return customLocks.size();
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
            LayoutInflater inflate = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflate.inflate(R.layout.view_customlocks, null);
            holder.textView_lockOrNot = (TextView_Regular) convertView.findViewById(R.id.textView_lockOrNot);
            holder.textView_time = (TextView_Regular) convertView.findViewById(R.id.textView_time);
            holder.textView_days = (TextView_Regular) convertView.findViewById(R.id.textView_days);
            holder.textView_desc = (TextView_Regular) convertView.findViewById(R.id.textView_desc);
            holder.imageButton_delete = (ImageButton) convertView.findViewById(R.id.imageButton_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setData(holder, position);
        holder.imageButton_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.startProgressDialog(activity);
                WebServiceResult.deletelock(customLocks.get(position).getLockId(), SharedPref.getString(MyApplication.PARENT_ID), "parent", MyApplication.currentChildID + "", "", customLocks.get(position).getPackageName());

            }
        });
        return convertView;
    }

    private void setData(ViewHolder holder, int position) {
        String[] daysArray = customLocks.get(position).getDays().split(",");
        holder.textView_lockOrNot.setText(customLocks.get(position).getTitle());
        if (daysArray.length == 7) {
            holder.textView_days.setVisibility(View.VISIBLE);
            holder.textView_days.setText(activity.getString(R.string.daily));
            holder.textView_time.setText(customLocks.get(position).getStartTime() + " - " + customLocks.get(position).getEndTime() + ", Daily");
        } else {
            holder.textView_days.setVisibility(View.VISIBLE);
            holder.textView_days.setText(customLocks.get(position).getDays());
            holder.textView_time.setText(customLocks.get(position).getStartTime() + " - " + customLocks.get(position).getEndTime());
        }
        if (customLocks.get(position).getIsActivated().equalsIgnoreCase("1")) {
            holder.textView_desc.setVisibility(View.GONE);
        } else {
            holder.textView_desc.setVisibility(View.VISIBLE);
            holder.textView_desc.setText(activity.getString(R.string.locknotactivated));
        }
        notifyDataSetChanged();

    }


    public class ViewHolder {
        TextView_Regular textView_lockOrNot;
        TextView_Regular textView_time;
        TextView_Regular textView_days;
        TextView_Regular textView_desc;
        ImageButton imageButton_delete;
    }


}
