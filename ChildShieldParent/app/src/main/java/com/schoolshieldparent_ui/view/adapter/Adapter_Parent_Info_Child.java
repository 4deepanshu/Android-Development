package com.schoolshieldparent_ui.view.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.model.children.Childs;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.custom_controls.Button_Regular;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Thin;

import java.util.List;

public class Adapter_Parent_Info_Child extends BaseAdapter {

    List<Childs> childList;
    Activity activity;

    public Adapter_Parent_Info_Child(Activity activity, List<Childs> childList) {
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
            convertView = inflate.inflate( R.layout.view_childlist_info, null );
            holder.textViewName = (TextView_Regular) convertView.findViewById( R.id.textViewName );
            holder.textDeviceName = (TextView_Regular) convertView.findViewById( R.id.textViewDeviceName );
            holder.imageButtonDelete = (ImageButton) convertView.findViewById( R.id.imageButtonDelete );
            holder.textAppCount = (TextView_Regular) convertView.findViewById( R.id.textViewTotal_Applications );
            convertView.setTag( holder );
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textViewName.setText( childList.get( position ).getStudentName() + " "+ childList.get( position ).getStudentLname());

        if (childList.get( position ).getStudentDeviceToken().length() == 0) {
            holder.textDeviceName.setVisibility( View.GONE );
            holder.textAppCount.setText( activity.getString( R.string.notactivatedyet ) );
        } else {
            if (childList.get( position ).getStudentDeviceName().length() != 0) {
                holder.textDeviceName
                        .setText( activity.getString( R.string.Device ) + ": " + childList.get( position ).getStudentDeviceName() );
            } else {
                holder.textDeviceName.setText( activity.getString( R.string.Device ) + activity.getString( R.string.notfound ) );
            }
            holder.textAppCount.setText(
                    activity.getString( R.string.Applications ) + ": " + childList.get( position ).getTotalApps() );

        }

        holder.imageButtonDelete.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteChild( position );
            }
        } );

        return convertView;

    }

    public class ViewHolder {
        TextView_Regular textViewName;
        TextView_Regular textDeviceName;
        TextView_Regular textAppCount;
        ImageButton imageButtonDelete;
    }

    private void deleteChild(final int position) {

        final Dialog dialog = new Dialog( activity );
        dialog.setCancelable( true );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.view_delete_child );

        final Button cancelButton = (Button) dialog.findViewById( R.id.buttonCancel );

        TextView_Thin textMessage = (TextView_Thin) dialog.findViewById( R.id.textViewMessage );

        dialog.setOnShowListener( new OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {
                cancelButton.setOnClickListener( new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                } );
            }
        } );

        final Button_Regular buttonDelete = (Button_Regular) dialog.findViewById( R.id.buttonDelete );

        final Button_Regular cancel = (Button_Regular) dialog.findViewById( R.id.buttonCancel );

        dialog.setOnShowListener( new OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {

                buttonDelete.setOnClickListener( new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        WebServiceResult.DeleteChild(childList.get( position ).getStudentId());
                    }
                } );
                cancel.setOnClickListener( new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                } );

            }
        } );

        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        dialog.getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );

        dialog.show();
        dialog.getWindow().setLayout( (6 * width) / 7, LayoutParams.WRAP_CONTENT );

    }
}
