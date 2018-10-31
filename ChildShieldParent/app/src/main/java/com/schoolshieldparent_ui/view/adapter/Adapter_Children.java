package com.schoolshieldparent_ui.view.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.model.children.Childs;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.custom_controls.Button_Regular;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Light;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

public class Adapter_Children extends BaseAdapter {

    List<Childs> childList;
    Activity activity;

    public Adapter_Children(Activity activity, List<Childs> childList) {
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
            LayoutInflater inflate = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflate.inflate(R.layout.view_childlist, null);
            holder.textViewName = (TextView_Regular) convertView.findViewById(R.id.textViewName);
            holder.textViewPin = (TextView_Regular) convertView.findViewById(R.id.textViewPin);
            holder.textViewActivatedornot = (TextView_Regular) convertView.findViewById(R.id.textViewActivatedornot);
            holder.imageButtonRegenrate = (ImageButton) convertView.findViewById(R.id.imageButtonRegenrate);
            holder.textViewEmail = (TextView_Light) convertView.findViewById(R.id.textViewEmail);
            holder.imageViewImage = (ImageView) convertView.findViewById(R.id.imageViewImage);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewName.setText(childList.get(position).getStudentName() + " " + childList.get(position).getStudentLname());
        holder.textViewEmail.setText(showDate(childList.get(position).getStudentDob()) + " Years Old");

        holder.textViewPin.setText(activity.getString(R.string.pin) + ":  " + childList.get(position).getStudentPassword());
        if (childList.get(position).getStudentGender().equalsIgnoreCase(activity.getString(R.string.female))) {
            holder.imageViewImage.setBackgroundResource(R.drawable.female);
        } else {
            holder.imageViewImage.setBackgroundResource(R.drawable.male);

        }
        if (childList.get(position).getStudentStatus().equalsIgnoreCase("1")) {
            holder.textViewActivatedornot.setText(activity.getString(R.string.activated));
            holder.textViewActivatedornot.setTextColor(activity.getResources().getColor(R.color.colorgreen));
        } else {
            holder.textViewActivatedornot.setText(activity.getString(R.string.deactivated));
            holder.textViewActivatedornot.setTextColor(activity.getResources().getColor(R.color.colordeactivated));
        }
        holder.imageButtonRegenrate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                alertPasswordRest(position);
            }
        });
        return convertView;
    }

    private String showDate(String date) {
        try {
            String [] items = date.split("/");

            return getAge(Integer.parseInt(items[2]), Integer.parseInt(items[1]),Integer.parseInt(items[0])) + "";
        } catch (Exception e) {
            return "";
        }
    }

    public int getAge(int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if (a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;
    }

    private void alertPasswordRest(final int position) {

        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_password_reset);

        final Button_Regular cancelButton = (Button_Regular) dialog.findViewById(R.id.buttonCancel);
        TextView_Regular textMessage = (TextView_Regular) dialog.findViewById(R.id.textViewMessage);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {
                cancelButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        final Button_Regular passwordReset = (Button_Regular) dialog.findViewById(R.id.buttonReset);

        final Button_Regular cancel = (Button_Regular) dialog.findViewById(R.id.buttonCancel);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {

                passwordReset.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        dialog.dismiss();

                        Random r = new Random();
                        int pasword = r.nextInt(9999 - 1000) + 1000;
                        WebServiceResult.ResetPassword(childList.get(position).getStudentId(), pasword + "");

                    }
                });

                cancel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

            }
        });

        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        dialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    public class ViewHolder {
        TextView_Regular textViewName;
        TextView_Regular textViewActivatedornot;
        TextView_Regular textViewPin;
        TextView_Light textViewEmail;
        ImageView imageViewImage;
        ImageButton imageButtonRegenrate;
    }

}
