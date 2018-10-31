package com.schoolshieldparent_ui.view.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.controller.utils.SimpleAlertDialog;
import com.schoolshieldparent_ui.view.activity.Activity_AllChild_LockScreen;
import com.schoolshieldparent_ui.view.activity.Activity_Analytics;
import com.schoolshieldparent_ui.view.activity.Activity_Applications;
import com.schoolshieldparent_ui.view.activity.Activity_Children;
import com.schoolshieldparent_ui.view.activity.Activity_Forums_Discription;
import com.schoolshieldparent_ui.view.activity.Activity_Gallery;
import com.schoolshieldparent_ui.view.activity.Activity_History;
import com.schoolshieldparent_ui.view.activity.Activity_Location;
import com.schoolshieldparent_ui.view.activity.Activity_Message;
import com.schoolshieldparent_ui.view.activity.Activity_MyInfo;
import com.schoolshieldparent_ui.view.activity.Activity_Notification;
import com.schoolshieldparent_ui.view.activity.Activity_Settings;

public class Adapter_Menu extends BaseAdapter {

    int menuIcons[] = new int[]{R.drawable.notification_menu, R.drawable.analytics, R.drawable.location,
            R.drawable.history_icon, R.drawable.block, R.drawable.screen_off_icon, R.drawable.msg_icon,
            R.drawable.forum, R.drawable.mychildren, R.drawable.gallery, R.drawable.info_icon,
            R.drawable.setting_icon};
    String menuIconName[];
    Activity activity;
    int childCount = 0;

    public Adapter_Menu(Activity activity, int i) {
        this.activity = activity;
        this.menuIconName = activity.getResources().getStringArray(R.array.menuNames);
        this.childCount = i;
        if (SharedPref.getPremimumVersionFree(this.activity) == true) {
            MyApplication.isSubscriptionActivated = true;
        } else MyApplication.isSubscriptionActivated = false;
    }

    @Override
    public int getCount() {

        return menuIcons.length;
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
            convertView = inflate.inflate(R.layout.view_menu_adapter, null);
            holder.imageButton_MENU = (ImageButton) convertView.findViewById(R.id.imageButtonMenu);
            holder.textView_MenuName = (TextView) convertView.findViewById(R.id.textViewMenuName);
            holder.layoutOuter = (LinearLayout) convertView.findViewById(R.id.rootLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView_MenuName.setText(menuIconName[position]);
        holder.imageButton_MENU.setBackgroundResource(menuIcons[position]);
        loadOnTouch(holder.layoutOuter, position);

        if (this.childCount > 0) {

        } else {
            if (position == 8) {
                ScaleAnimation scal = new ScaleAnimation(0.8f, 1f, 0.8f, 1f, Animation.RELATIVE_TO_SELF, (float) 0.5,
                        Animation.RELATIVE_TO_SELF, (float) 0.5);

                scal.setRepeatCount(2);
                scal.setRepeatMode(Animation.REVERSE);
                scal.setDuration(1000);
                scal.setFillAfter(true);
                holder.layoutOuter.setEnabled(true);
                holder.layoutOuter.setAlpha(1f);
                holder.layoutOuter.setAnimation(scal);

            } else {
                // holder.layoutOuter.setEnabled(false);
                // holder.layoutOuter.setAlpha(0.5f);
            }
        }

        return convertView;

    }

    public class ViewHolder {
        ImageButton imageButton_MENU;
        TextView textView_MenuName;
        LinearLayout layoutOuter;
    }

    public void loadOnTouch(final LinearLayout layout, final int position) {
        layout.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v1, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        layout.setAlpha(0.5f);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        layout.setAlpha(1f);
                        showView(position);
                        break;
                    case MotionEvent.ACTION_CANCEL: {
                        layout.setAlpha(1f);
                        break;
                    }
                }
                return true;

            }
        });
    }

    public void showView(int position) {
        switch (position) {
            case 0:
                if (childCount == 0) {
                    SimpleAlertDialog.showAlertDialog(activity,
                            activity.getString(R.string.thereisnochildyetCreatechildFirst));
                } else if (childCount > 0 && MyApplication.isSubscriptionActivated == false) {
                    SimpleAlertDialog.showAlertDialog(activity,
                            "Analytics\n\n You are on  basic version , Please upgrade application to premium version from settings ");
                } else if (childCount > 0 && MyApplication.isSubscriptionActivated == true) {
                    if (MyApplication.currentChildID == -2) {
                        SimpleAlertDialog.showAlertDialog(activity, activity.getString(R.string.pleseselectchildalert));
                    } else if (MyApplication.currentChildID > 0) {
                        activity.startActivity(new Intent(activity, Activity_Notification.class));
                        activity.overridePendingTransition(0, 0);
                    } else {
                        SimpleAlertDialog.showAlertDialog(activity, activity.getString(R.string.childNotActivated));
                    }
                }
                break;
            case 1:
                if (childCount == 0) {
                    SimpleAlertDialog.showAlertDialog(activity,
                            "Analytics\n\n There is no child yet , Create child first from \"My Children\" Option");
                } else if (childCount > 0 && MyApplication.isSubscriptionActivated == false) {
                    SimpleAlertDialog.showAlertDialog(activity,
                            "Analytics\n\n You are on  basic version , Please upgrade application to premium version from settings ");

                } else if (childCount > 0 && MyApplication.isSubscriptionActivated == true) {

                    if (MyApplication.currentChildID == -2) {
                        SimpleAlertDialog.showAlertDialog(activity, activity.getString(R.string.pleseselectchildalert));
                    } else if (MyApplication.currentChildID > 0) {
                        activity.startActivity(new Intent(activity, Activity_Analytics.class));
                        activity.overridePendingTransition(0, 0);
                    } else {
                        SimpleAlertDialog.showAlertDialog(activity, activity.getString(R.string.childNotActivated));
                    }
                }

                break;
            case 2:
                if (this.childCount > 0) {

                    if (MyApplication.currentChildID == -2) {
                        SimpleAlertDialog.showAlertDialog(activity, activity.getString(R.string.pleseselectchildalert));
                    } else if (MyApplication.currentChildID > 0) {
                        activity.startActivity(new Intent(activity, Activity_Location.class).putExtra("userid",
                                MyApplication.currentChildID + ""));
                        activity.overridePendingTransition(0, 0);
                    } else {
                        SimpleAlertDialog.showAlertDialog(activity, activity.getString(R.string.childNotActivated));
                    }

                } else {
                    SimpleAlertDialog.showAlertDialog(activity,
                            "Location \n\n There is no child yet , Create child first from \"My Children\" Option");
                }
                break;
            case 3:
                if (this.childCount > 0) {

                    if (MyApplication.currentChildID == -2) {
                        SimpleAlertDialog.showAlertDialog(activity, activity.getString(R.string.pleseselectchildalert));
                    } else if (MyApplication.currentChildID > 0) {
                        activity.startActivity(new Intent(activity, Activity_History.class));
                        activity.overridePendingTransition(0, 0);
                    } else {
                        SimpleAlertDialog.showAlertDialog(activity, activity.getString(R.string.childNotActivated));
                    }

                } else {
                    SimpleAlertDialog.showAlertDialog(activity,
                            "History \n\n There is no child yet , Create child first from \"My Children\" Option");
                }
                break;
            case 4:
                if (childCount == 0) {
                    SimpleAlertDialog.showAlertDialog(activity,
                            "Block Apps\n\n There is no child yet , Create child first from \"My Children\" Option");
                } else if (childCount > 0 && MyApplication.isSubscriptionActivated == false) {
                    SimpleAlertDialog.showAlertDialog(activity,
                            "Block Apps\n\n You are on  basic version , Please upgrade application to premium version from settings ");
                } else if (childCount > 0 && MyApplication.isSubscriptionActivated == true) {
                    if (MyApplication.currentChildID == -2) {
                        SimpleAlertDialog.showAlertDialog(activity, activity.getString(R.string.pleseselectchildalert));
                    } else if (MyApplication.currentChildID > 0) {
                        activity.startActivity(
                                new Intent(activity, Activity_Applications.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        activity.overridePendingTransition(0, 0);

                    } else {
                        SimpleAlertDialog.showAlertDialog(activity, activity.getString(R.string.childNotActivated));
                    }
                }
                break;
            case 5:
                activity.startActivity(new Intent(activity, Activity_AllChild_LockScreen.class));
                activity.overridePendingTransition(0, 0);
                break;
            case 6:
                if (childCount == 0) {
                    SimpleAlertDialog.showAlertDialog(activity,
                            "Messages\n\n There is no child yet , Create child first from \"My Children\" Option");
                } else if (childCount > 0 && MyApplication.isSubscriptionActivated == false) {
                    SimpleAlertDialog.showAlertDialog(activity,
                            "Message\n\n You are on  basic version , Please upgrade application to premium version from settings ");

                } else if (childCount > 0 && MyApplication.isSubscriptionActivated == true) {
                    if (MyApplication.currentChildID == -2) {
                        SimpleAlertDialog.showAlertDialog(activity, activity.getString(R.string.pleseselectchildalert));
                    } else if (MyApplication.currentChildID > 0) {
                        activity.startActivity(new Intent(activity, Activity_Message.class));
                        activity.overridePendingTransition(0, 0);

                    } else {
                        SimpleAlertDialog.showAlertDialog(activity, activity.getString(R.string.childNotActivated));
                    }
                }
                break;

            case 7:
                activity.startActivity(new Intent(activity, Activity_Forums_Discription.class));
                activity.overridePendingTransition(0, 0);
                break;
            case 8:
                activity.startActivity(new Intent(activity, Activity_Children.class));
                activity.overridePendingTransition(0, 0);
                break;

            case 9:
                if (childCount == 0) {
                    SimpleAlertDialog.showAlertDialog(activity,
                            "Gallery\n\n There is no child yet , Create child first from \"My Children\" Option");
                } else if (childCount > 0 && MyApplication.isSubscriptionActivated == false) {
                    SimpleAlertDialog.showAlertDialog(activity,
                            "Gallery\n\n You are on  basic version , Please upgrade application to premium version from settings ");

                } else if (childCount > 0 && MyApplication.isSubscriptionActivated == true) {
                    if (MyApplication.currentChildID == -2) {
                        SimpleAlertDialog.showAlertDialog(activity, activity.getString(R.string.pleseselectchildalert));
                    } else if (MyApplication.currentChildID > 0) {
                        activity.startActivity(new Intent(activity, Activity_Gallery.class));
                        activity.overridePendingTransition(0, 0);
                    } else {
                        SimpleAlertDialog.showAlertDialog(activity, activity.getString(R.string.childNotActivated));
                    }
                }

                break;
            case 10:
                activity.startActivity(new Intent(activity, Activity_MyInfo.class));
                activity.overridePendingTransition(0, 0);
                break;
            case 11:
                activity.startActivity(new Intent(activity, Activity_Settings.class));
                activity.overridePendingTransition(0, 0);
                break;

        }
    }

}