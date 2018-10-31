package com.schoolshieldchild_ui.view.services;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.SQLException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.util.Base64;

import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldchild_ui.model.applicationprp.ApplicationPrp;
import com.schoolshieldchild_ui.model.uploadapps.UploadApps;
import com.schoolshieldchild_ui.presenter.WebServiceResult;
import com.schoolshieldchild_ui.view.database.DataBaseHandler;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UploadApplications extends Service {
    static UploadApplications instance;
    List<ResolveInfo> installedApplications = new ArrayList<>();
    PackageManager pm;
    Handler mHandler;
    private int RESTART_UPLOAD_DURATION = (60 * 1000);
    int currentIndex = 0;
    private DataBaseHandler dataBaseHandler;


    public static UploadApplications getInstance() {
        return instance;
    }


    public void useHandler() {
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 1000);
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            currentIndex = 0;
            getInstalledApplications();
            mHandler.postDelayed(this, RESTART_UPLOAD_DURATION * 3);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        instance = this;
        dataBaseHandler = new DataBaseHandler(MyApplication.getInstance().getApplicationContext());
        useHandler();
        return START_STICKY;
    }

    public void getInstalledApplications() {
        pm = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        installedApplications = pm.queryIntentActivities(intent, PackageManager.GET_META_DATA);
        uploadApplication(currentIndex);
    }


    private void uploadApplication(int position) {
        try {
            if (!isApplicationExistInUploadedApplications(installedApplications.get(position).activityInfo.packageName)) {
                String imageIcon = DrawableToBase64(installedApplications.get(position).activityInfo.loadIcon(pm));
                WebServiceResult.uploadApplicationToServer(installedApplications.get(position).activityInfo.packageName, SharedPref.getString(MyApplication.STUDENT_ID), installedApplications.get(position).activityInfo.loadLabel(pm).toString(), imageIcon);
            } else {
                currentIndex++;
                uploadApplication(currentIndex);
            }
        } catch (IndexOutOfBoundsException e) {

        } catch (Exception e) {

        }
    }


    private boolean isApplicationExistInUploadedApplications(String packageName) {
        boolean isExist = false;
        try {
            int count = dataBaseHandler.isApplicationExist(packageName);
            if (count > 0) {
                isExist = true;
            } else {
                isExist = false;
            }
        } catch (SQLiteCantOpenDatabaseException w) {

        } catch (SQLException e) {

        }
        if(isExist)
        {
            System.out.println("APPLICATION EXIST");
        }
        return isExist;
    }


    public static String DrawableToBase64(Drawable icon) {
        String appIcon64 = new String();
        Bitmap bitmap = drawableToBitmap(icon);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();
        appIcon64 = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        return appIcon64;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void updateDataBase(UploadApps response) {
        try {
            if (response.getResult().getStatus().toString().equalsIgnoreCase("1")) {
                DataBaseHandler dataBaseHandler = new DataBaseHandler(MyApplication.getInstance().getApplicationContext());
                String appName = installedApplications.get(currentIndex).activityInfo.loadLabel(pm).toString();
                String packageName = installedApplications.get(currentIndex).activityInfo.packageName;
                if (appName == null) {
                    appName = "NO NAME";
                } else if (appName.equalsIgnoreCase("")) {
                    appName = "NO NAME";
                }
                dataBaseHandler.addRow(appName, packageName);
                currentIndex++;
                uploadApplication(currentIndex);

            } else {
                currentIndex++;
                uploadApplication(currentIndex);
            }
        } catch (NullPointerException e) {
            currentIndex++;
            uploadApplication(currentIndex);
        } catch (Exception e) {
            currentIndex++;
            uploadApplication(currentIndex);
        }
    }
}

