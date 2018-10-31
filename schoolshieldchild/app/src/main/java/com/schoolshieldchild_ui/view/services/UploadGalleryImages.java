package com.schoolshieldchild_ui.view.services;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;

import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.model.uploadimages.UploadGalleryImagesToServer;
import com.schoolshieldchild_ui.view.database.DataBaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UploadGalleryImages extends Service {
    private static UploadGalleryImages instance;
    Handler mHandler;
    private int RESTART_UPLOAD_DURATION = (60 * 1000) * 1;
    ArrayList<String> galleryImages = new ArrayList<>();
    int currentIndex = 0;
    private ArrayList<String> imageNamesOnly = new ArrayList<>();
    DataBaseHandler dataBaseHandler;

    public static UploadGalleryImages getinstance() {
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
            galleryImages.clear();
            galleryImages.addAll(getGalleryImages());
            if (galleryImages.size() > 0) {
                uploadGalleryImages(currentIndex);
            }
            mHandler.postDelayed(this, RESTART_UPLOAD_DURATION);
        }
    };

    public ArrayList<String> getGalleryImages() {
        try {
            ArrayList<String> arrPath = new ArrayList<>();
            int ids[];
            int count;
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
            final String orderBy = MediaStore.Images.Media._ID;
            Cursor imageCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
            int image_column_index = imageCursor.getColumnIndex(MediaStore.Images.Media._ID);
            count = imageCursor.getCount();
            ids = new int[count];
            for (int i = 0; i < count; i++) {
                imageCursor.moveToPosition(i);
                ids[i] = imageCursor.getInt(image_column_index);
                int dataColumnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
                imageNamesOnly.add(imageCursor.getString(dataColumnIndex).substring(
                        imageCursor.getString(dataColumnIndex).lastIndexOf("/") + 1,
                        imageCursor.getString(dataColumnIndex).length()));
                arrPath.add(imageCursor.getString(dataColumnIndex));
            }
            return arrPath;
        } catch (NullPointerException e) {
            ArrayList<String> arrPath = new ArrayList<>();
            return arrPath;
        } catch (IndexOutOfBoundsException e) {
            ArrayList<String> arrPath = new ArrayList<>();
            return arrPath;
        } catch (Exception e) {
            ArrayList<String> arrPath = new ArrayList<>();
            return arrPath;
        }

    }


    private void uploadGalleryImages(final int position) {
        try {
            if (position < galleryImages.size()) {
                if (!isImageExistInUploadedImages(galleryImages.get(position))) {
                    new UploadGalleryImagesToServer(galleryImages.get(position)).execute();
                } else {
                    currentIndex = currentIndex + 1;
                    uploadGalleryImages(currentIndex);
                }
            }
        } catch (IndexOutOfBoundsException e) {

        } catch (Exception e) {

        }
    }

    private boolean isImageExistInUploadedImages(String imagePath) {

        boolean isExist = false;
        try {
            int count = dataBaseHandler.isImageExist(imagePath);
            if (count > 0) {
                isExist = true;
            } else {
                isExist = false;
            }
        } catch (SQLiteCantOpenDatabaseException w) {

        } catch (SQLException e) {

        }
        return isExist;
    }

    public void updateDataBase(JSONObject response) {
        try {
            if (response.getString("status").equalsIgnoreCase("1")) {
                dataBaseHandler.addGalleryImagesRow(galleryImages.get(currentIndex));
                currentIndex = currentIndex + 1;
                uploadGalleryImages(currentIndex);
            } else {
                currentIndex = currentIndex + 1;
                uploadGalleryImages(currentIndex);
            }
        } catch (NullPointerException e) {
            currentIndex = currentIndex + 1;
            uploadGalleryImages(currentIndex);
        } catch (JSONException e) {
            e.printStackTrace();
            currentIndex = currentIndex + 1;
            uploadGalleryImages(currentIndex);
        } catch (IndexOutOfBoundsException e) {
            currentIndex = currentIndex + 1;
            uploadGalleryImages(currentIndex);
        } catch (SQLiteCantOpenDatabaseException e) {
            currentIndex = currentIndex + 1;
            uploadGalleryImages(currentIndex);
        } catch (SQLException e) {
            currentIndex = currentIndex + 1;
            uploadGalleryImages(currentIndex);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        instance = this;
        dataBaseHandler = new DataBaseHandler(getApplicationContext());
        useHandler();
        return START_STICKY;
    }
}
