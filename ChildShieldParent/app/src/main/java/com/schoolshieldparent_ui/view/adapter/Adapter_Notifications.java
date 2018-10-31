package com.schoolshieldparent_ui.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.model.notification.NotificationResult;
import com.schoolshieldparent_ui.presenter.WebServiceConnection;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Adapter_Notifications extends BaseAdapter {

    private final DisplayImageOptions options;
    List<NotificationResult> notificationResult = new ArrayList<>();
    String type = "";
    Activity activity;
    private ViewHolderInstalled holderinstalled;
    private LayoutInflater inflate = null;
    private String appname = "";
    private String icon = "";
    private String status = "";
    private String duration = "";
    private String time = "";

    public Adapter_Notifications(Activity activity, List<NotificationResult> notificationResult) {
        this.activity = activity;
        this.notificationResult = notificationResult;
        options = new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(0))
                .imageScaleType(ImageScaleType.EXACTLY).cacheInMemory(true).cacheOnDisk(true)
                .resetViewBeforeLoading(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public int getCount() {
        return notificationResult.size();
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

        holderinstalled = new ViewHolderInstalled();
        inflate = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (notificationResult.get(position).getType().equalsIgnoreCase(activity.getString(R.string.uninstall))) {
            convertView = inflate.inflate(R.layout.view_notification_installed, null);
            getIDSinstalled(holderinstalled, convertView);
        } else if (notificationResult.get(position).getType().equalsIgnoreCase(activity.getString(R.string.install))) {
            convertView = inflate.inflate(R.layout.view_notification_installed, null);
            getIDSinstalled(holderinstalled, convertView);
        } else if (notificationResult.get(position).getType().equalsIgnoreCase(activity.getString(R.string.gps))) {
            convertView = inflate.inflate(R.layout.view_notivication_gps, null);
            getIDSGPS(holderinstalled, convertView);
        } else if (notificationResult.get(position).getType().equalsIgnoreCase(activity.getString(R.string.lock))) {
            convertView = inflate.inflate(R.layout.view_notification_installed, null);
            getIDSinstalled(holderinstalled, convertView);
        } else if (notificationResult.get(position).getType().equalsIgnoreCase(activity.getString(R.string.unlock))) {
            convertView = inflate.inflate(R.layout.view_notification_installed, null);
            getIDSinstalled(holderinstalled, convertView);
        } else if (notificationResult.get(position).getType().equalsIgnoreCase(activity.getString(R.string.phonerestart))) {
            convertView = inflate.inflate(R.layout.view_notification_restart, null);
            getIdsRestart(holderinstalled, convertView);
        } else {
            convertView = inflate.inflate(R.layout.view_notification, null);
            getIDSrunning(holderinstalled, convertView);
        }
        getData(position, holderinstalled, convertView);
        return convertView;
    }

    private void getData(int position, ViewHolderInstalled holder, View convertView) {
        try {
            appname = notificationResult.get(position).getAppName();
            icon = notificationResult.get(position).getAppIcon();
            status = notificationResult.get(position).getStatus();
            type = notificationResult.get(position).getType();
            duration = notificationResult.get(position).getDuration();
            time = notificationResult.get(position).getDate();
        } catch (Exception e) {
        }

        setData(position, convertView);
    }

    private void setData(int position, View convertView) {
        if (notificationResult.get(position).getType().equalsIgnoreCase(activity.getString(R.string.uninstall))) {
            setInstalle_UninstalledApssData(convertView);
        } else if (notificationResult.get(position).getType().equalsIgnoreCase(activity.getString(R.string.install))) {
            setInstalle_UninstalledApssData(convertView);
        } else if (notificationResult.get(position).getType().equalsIgnoreCase(activity.getString(R.string.lock))) {
            setLocksApssData(convertView);
        } else if (notificationResult.get(position).getType().equalsIgnoreCase(activity.getString(R.string.unlock))) {
            setUnlockApssData(convertView);
        } else if (notificationResult.get(position).getType().equalsIgnoreCase(activity.getString(R.string.gps))) {
            setGPSdata(convertView);
        } else if (notificationResult.get(position).getType().equalsIgnoreCase(activity.getString(R.string.time))) {
            setRunningAppsData(convertView);
        } else if (notificationResult.get(position).getType().equalsIgnoreCase(activity.getString(R.string.phonerestart))) {
            setRestartAppsData(convertView);
        }
    }

    private void setRestartAppsData(View convertView) {
        holderinstalled.textViewRestartTitle.setText(activity.getString(R.string.phnrestart));
        String date = convertDate(time, "yyyy-MM-dd HH:mm:ss");
        holderinstalled.textViewRestartTime.setText(date);

    }

    private void setUnlockApssData(View convertView) {
        if (appname != null) {
            holderinstalled.textViewAppName.setText(appname);
            holderinstalled.textViewInstalleddornot.setText(activity.getString(R.string.lockedApps));
            String date = convertDate(time, "yyyy-MM-dd HH:mm:ss");
            holderinstalled.textViewTime.setText(date);
            try {
                ImageLoader.getInstance().displayImage(WebServiceConnection.APPLICATION_ICON_URLS + icon, holderinstalled.imageViewApps, MyApplication.options);
            } catch (Exception e) {
            }
        } else {
            convertView.setLayoutParams(new AbsListView.LayoutParams(-1,1));
            convertView.setVisibility(View.GONE);
        }
    }

    private void setLocksApssData(View convertView) {
        if (appname != null) {
            holderinstalled.textViewAppName.setText(appname);
            holderinstalled.textViewInstalleddornot.setText(activity.getString(R.string.lockedApps));
            String date = convertDate(time, "yyyy-MM-dd HH:mm:ss");
            holderinstalled.textViewTime.setText(date);
            try {
                ImageLoader.getInstance().displayImage(WebServiceConnection.APPLICATION_ICON_URLS + icon, holderinstalled.imageViewApps, MyApplication.options);
            } catch (Exception e) {
            }
        } else {
            convertView.setLayoutParams(new AbsListView.LayoutParams(-1,1));
            convertView.setVisibility(View.GONE);

        }
    }


    private void setRunningAppsData(View convertView) {
        try {
            if (appname != null) {
                holderinstalled.textViewRunningName.setText(appname);
                if (duration.length() == 0) {
                    holderinstalled.textViewRunningDuration.setText(activity.getString(R.string.startedandclosed));
                } else {
                    int totalDuration = Integer.parseInt(duration);
                    final int MINUTES_IN_AN_HOUR = 60;
                    final int SECONDS_IN_A_MINUTE = 60;

                    int minutes = totalDuration / SECONDS_IN_A_MINUTE;
                    totalDuration -= minutes * SECONDS_IN_A_MINUTE;

                    int hours = minutes / MINUTES_IN_AN_HOUR;
                    minutes -= hours * MINUTES_IN_AN_HOUR;
                    holderinstalled.textViewRunningDuration.setText(hours + "h " + minutes + "m " + totalDuration + "s");
                }
                String date = convertDate(time, "yyyy-MM-dd HH:mm:ss");
                holderinstalled.textViewRunningTime.setText(date);
                try {
                    ImageLoader.getInstance().displayImage(WebServiceConnection.APPLICATION_ICON_URLS + icon, holderinstalled.imageViewRunningImage, MyApplication.options);

                } catch (Exception e) {
                    holderinstalled.imageViewRunningImage.setBackgroundResource(R.mipmap.ic_launcher);
                }
            } else {
                convertView.setLayoutParams(new AbsListView.LayoutParams(-1,1));
                convertView.setVisibility(View.GONE);


            }

        } catch (Exception e) {
        }

    }

    private void setGPSdata(View convertView) {
        try {
            String date = convertDate(time, "yyyy-MM-dd HH:mm:ss");
            holderinstalled.textViewDurationGPS.setText(date);

            if (status.equalsIgnoreCase(activity.getString(R.string.off))) {
                holderinstalled.imageViewGPS.setBackgroundResource(R.drawable.gpsoff);
                holderinstalled.textViewTitleGPS.setText(activity.getString(R.string.turnedoff));

            } else {
                holderinstalled.imageViewGPS.setBackgroundResource(R.drawable.gpson);
                holderinstalled.textViewTitleGPS.setText(activity.getString(R.string.turnedon));
            }
        } catch (Exception e) {
        }
    }

    private void setInstalle_UninstalledApssData(View convertView) {
        if (appname != null) {
            holderinstalled.textViewAppName.setText(appname);
            if (type.equalsIgnoreCase(activity.getString(R.string.uninstall))) {
                holderinstalled.textViewInstalleddornot.setTextColor(activity.getResources().getColor(R.color.red));
            } else {
                holderinstalled.textViewInstalleddornot.setTextColor(activity.getResources().getColor(R.color.colorgreen));
            }
            holderinstalled.textViewInstalleddornot.setText(type);

            String date = convertDate(time, "yyyy-MM-dd HH:mm:ss");
            holderinstalled.textViewTime.setText(date);
            try {
                ImageLoader.getInstance().displayImage(WebServiceConnection.APPLICATION_ICON_URLS + icon, holderinstalled.imageViewApps, MyApplication.options);
            } catch (Exception e) {
            }
        } else {
            convertView.setLayoutParams(new AbsListView.LayoutParams(-1,1));
            convertView.setVisibility(View.GONE);

        }
    }


    public class ViewHolderInstalled {
        ImageView imageViewApps;
        TextView_Regular textViewAppName;
        TextView_Regular textViewInstalleddornot;
        TextView_Regular textViewTime;
        RelativeLayout relativeLayoutGPS;
        ImageView imageViewGPS;
        TextView_Regular textViewTitleGPS;
        TextView_Regular textViewDurationGPS;
        ImageView imageViewRunningImage;
        TextView_Regular textViewRunningName;
        TextView_Regular textViewRunningDuration;
        TextView_Regular textViewRunningTime;
        TextView_Regular textViewRestartTitle;
        TextView_Regular textViewRestartTime;

    }

    private void getIdsRestart(ViewHolderInstalled holder, View convertView) {
        holder.textViewRestartTitle = (TextView_Regular) convertView.findViewById(R.id.textViewRestartTitle);
        holder.textViewRestartTime = (TextView_Regular) convertView.findViewById(R.id.textViewRestartTime);
    }

    private void getIDSrunning(ViewHolderInstalled holder, View convertView) {
        holder.imageViewRunningImage = (ImageView) convertView.findViewById(R.id.imageViewRunningImage);
        holder.textViewRunningName = (TextView_Regular) convertView.findViewById(R.id.textViewRunningName);
        holder.textViewRunningDuration = (TextView_Regular) convertView.findViewById(R.id.textViewRunningDuration);
        holder.textViewRunningTime = (TextView_Regular) convertView.findViewById(R.id.textViewRunningTime);
    }

    private void getIDSGPS(ViewHolderInstalled holder, View convertView) {
        holder.relativeLayoutGPS = (RelativeLayout) convertView.findViewById(R.id.relativeLayoutGPS);
        holder.imageViewGPS = (ImageView) convertView.findViewById(R.id.imageViewGPS);
        holder.textViewTitleGPS = (TextView_Regular) convertView.findViewById(R.id.textViewTitleGPS);
        holder.textViewDurationGPS = (TextView_Regular) convertView.findViewById(R.id.textViewDurationGPS);
    }

    public void getIDSinstalled(ViewHolderInstalled holder, View convertView) {
        holder.imageViewApps = (ImageView) convertView.findViewById(R.id.imageViewApps);
        holder.textViewAppName = (TextView_Regular) convertView.findViewById(R.id.textViewAppName);
        holder.textViewInstalleddornot = (TextView_Regular) convertView.findViewById(R.id.textViewInstalleddornot);
        holder.textViewTime = (TextView_Regular) convertView.findViewById(R.id.textViewTime);


    }

    public static String convertDate(String date, String DateFormat) {
        try {

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String currentDate1 = df.format(c.getTime());

            Date currentDate = df.parse(currentDate1);
            System.out.println(currentDate);

            long diff = currentDate.getTime();

            long days = diff / (24 * 60 * 60 * 1000);
            diff -= days * (24 * 60 * 60 * 1000);

            long hours = diff / (60 * 60 * 1000);
            diff -= hours * (60 * 60 * 1000);

            long minutes = diff / (60 * 1000);
            diff -= minutes * (60 * 1000);

            long seconds = diff / 1000;

            SimpleDateFormat format = new SimpleDateFormat(DateFormat);
            Date d = format.parse(date);

            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.HOUR, (int) hours); // add 28 days
            cal.add(Calendar.MINUTE, (int) minutes); // add 28 days

            cal.add(Calendar.SECOND, (int) seconds); // add 28 days

            d = (Date) cal.getTime();

            SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss");
            return serverFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
