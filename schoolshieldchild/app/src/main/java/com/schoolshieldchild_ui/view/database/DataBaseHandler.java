package com.schoolshieldchild_ui.view.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteReadOnlyDatabaseException;

import com.schoolshieldchild_ui.model.applicationprp.ApplicationPrp;
import com.schoolshieldchild_ui.model.lockprp.LockPrp;

import java.util.ArrayList;

/**
 * Created by root on 20/7/16.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "STUDENT_APPLICATIONS";
    private static final String TABLE_APPS = "INSTALLED_APPS";
    private static final String KEY_ID = "id";
    private static final String KEY_PACKAGE_NAME = "package_name";
    private static final String KEY_APP_NAME = "app_name";


    private static final String TABLE_IMAGES = "GALLERY_IMAGES";
    private static final String KEY_IMAGEPATH = "IMAGE_PATH";


    private static final String TABLE_LOCK = "TABLE_LOCK";
    private static final String LOCK_KEY = "lock_key";
    private static final String LOCK_PACKAGE_NAME = "package_name";
    private static final String LOCK_START_TIME = "start_time";
    private static final String LOCK_END_TIME = "end_time";
    private static final String LOCK_DAYS = "days";
    private static final String LOCK_FROM = "fromTeacherOrParent";
    private static final String LOCK_PERMANENT = "permanent";
    private static final String LOCK_ID = "lockid";
    private static final String LOCK_TITLE = "lock_title";


    private static final String TABLE_SCREEN_LOCK = "TABLE_SCREEN_LOCK";
    private static final String SCREEN_LOCK_STAR_TIME = "SCREEN_LOCK_STAR_TIME";
    private static final String SCREEN_LOCK_END_TIME = "SCREEN_LOCK_END_TIME";
    private static final String SCREEN_LOCK_DAYS = "SCREEN_LOCK_DAYS";

    private static final String SCREEN_LOCK_ID = "SCREEN_LOCK_ID";


    private static final String CREATE_TABLE_APPS = "CREATE TABLE "
            + TABLE_APPS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PACKAGE_NAME
            + " TEXT," + KEY_APP_NAME
            + " TEXT" + ")";


    private static final String CREATE_TABLE_GALLERY_IMAGES = "CREATE TABLE "
            + TABLE_IMAGES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_IMAGEPATH
            + " TEXT" + ")";


    private static final String CREATE_TABLE_APPLICATION_LOCK = "CREATE TABLE "
            + TABLE_LOCK + "(" + LOCK_KEY + " INTEGER PRIMARY KEY," + LOCK_PACKAGE_NAME
            + " TEXT," + LOCK_START_TIME + " TEXT," + LOCK_END_TIME + " TEXT," + LOCK_DAYS + " TEXT, " + LOCK_FROM + " TEXT, " + LOCK_PERMANENT + " TEXT," + LOCK_TITLE + " TEXT," + LOCK_ID + " TEXT" + ")";


    private static final String CREATE_TABLE_SCREEN_LOCK = "CREATE TABLE "
            + TABLE_SCREEN_LOCK + "(" + SCREEN_LOCK_STAR_TIME
            + " TEXT," + SCREEN_LOCK_END_TIME + " TEXT," + SCREEN_LOCK_DAYS + " TEXT, " + SCREEN_LOCK_ID + " TEXT)";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_APPS);
        sqLiteDatabase.execSQL(CREATE_TABLE_GALLERY_IMAGES);
        sqLiteDatabase.execSQL(CREATE_TABLE_APPLICATION_LOCK);
        sqLiteDatabase.execSQL(CREATE_TABLE_SCREEN_LOCK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);

    }


    public void truncateTableScreenLock() {
        try {
            String truncateTable = "truncate table " + TABLE_SCREEN_LOCK;
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(truncateTable);
        } catch (SQLException e) {

        } catch (NullPointerException e) {

        } catch (Exception e) {

        }

    }

    public void truncateTableAppLockLock() {
        try {
            String truncateTable = "truncate table " + TABLE_LOCK;
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(truncateTable);
        } catch (SQLException e) {

        } catch (NullPointerException e) {

        } catch (Exception e) {

        }

    }


    public boolean addRow(String appname, String packagename) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_APP_NAME, appname);
            contentValues.put(KEY_PACKAGE_NAME, packagename);
            long apps = db.insert(TABLE_APPS, null, contentValues);
            return true;
        } catch (SQLiteException e) {
            return false;

        } catch (Exception e) {
            return false;

        }
    }

    public boolean addGalleryImagesRow(String imagepath) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_IMAGEPATH, imagepath);
            long images = db.insert(TABLE_IMAGES, null, contentValues);
            return true;
        } catch (SQLiteException e) {
            return false;

        } catch (Exception e) {
            return false;

        }
    }

    public ArrayList<ApplicationPrp> getAllRowData() {
        ArrayList<ApplicationPrp> array_list = new ArrayList<ApplicationPrp>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_APPS, null);
        while (res.moveToNext()) {
            ApplicationPrp applicationPrp = new ApplicationPrp();
            applicationPrp.setApplictionname(res.getString(res.getColumnIndex(KEY_APP_NAME)));
            applicationPrp.setPackageName(res.getString(res.getColumnIndex(KEY_PACKAGE_NAME)));
            array_list.add(applicationPrp);
        }
        return array_list;
    }

    public ArrayList<String> getAllGalleryImages() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_IMAGES, null);
        while (res.moveToNext()) {
            array_list.add(res.getString(res.getColumnIndex(KEY_IMAGEPATH)));
        }
        return array_list;
    }

    public boolean deleteApplication(String packageName) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String qry = "delete from " + TABLE_APPS + " where " + KEY_PACKAGE_NAME + "='" + packageName + "'";
            db.execSQL(qry);
            return true;
        } catch (SQLException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public int isImageExist(String imagePath) {
        try {
            String countQuery = "SELECT  * FROM " + TABLE_IMAGES + " WHERE " + KEY_IMAGEPATH + "='" + imagePath + "'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            int cnt = cursor.getCount();
            return cnt;
        } catch (SQLException e) {
            return 0;
        }
    }


    public int isApplicationExist(String packageName) {
        try {
            String countQuery = "SELECT  * FROM " + TABLE_APPS + " WHERE " + KEY_PACKAGE_NAME + "='" + packageName + "'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            int cnt = cursor.getCount();
            return cnt;
        } catch (SQLException e) {
            return 0;
        }
    }


    public boolean applyApplicationLock(String packageName, String lockStartTime, String lockEndTime, String lockDays, String lockFrom, String lockPermanent, String title, String lockId) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(LOCK_PACKAGE_NAME, packageName);
            contentValues.put(LOCK_START_TIME, lockStartTime);
            contentValues.put(LOCK_END_TIME, lockEndTime);
            contentValues.put(LOCK_DAYS, lockDays);
            contentValues.put(LOCK_FROM, lockFrom);
            contentValues.put(LOCK_PERMANENT, lockPermanent);
            contentValues.put(LOCK_TITLE, lockPermanent);
            contentValues.put(LOCK_ID, lockId);
            db.insert(TABLE_LOCK, null, contentValues);
            return true;
        } catch (SQLiteException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    public ArrayList<LockPrp> getApplicationLocks(String packageName) {
        ArrayList<LockPrp> array_list_locks = new ArrayList<LockPrp>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from " + TABLE_LOCK + " where " + LOCK_PACKAGE_NAME + " = '" + packageName + "'", null);
            while (res.moveToNext()) {
                LockPrp lockPrp = new LockPrp();
                lockPrp.setLOCK_START_TIME(res.getString(res.getColumnIndex(LOCK_START_TIME)));
                lockPrp.setLOCK_END_TIME(res.getString(res.getColumnIndex(LOCK_END_TIME)));
                lockPrp.setLOCK_PERMANENT(res.getString(res.getColumnIndex(LOCK_PERMANENT)));
                lockPrp.setLOCK_DAYS(res.getString(res.getColumnIndex(LOCK_DAYS)));
                array_list_locks.add(lockPrp);
            }
        }
        catch (SQLiteReadOnlyDatabaseException e)
        {

        }
        catch (SQLException e)
        {

        }
        return array_list_locks;

    }


    public boolean deleteAppPermanetLock(String package_name) {


        try {
            String qry = "delete from " + TABLE_LOCK + "  where " + LOCK_PACKAGE_NAME + " = '" + package_name + "' and " + LOCK_PERMANENT + "=1";
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(qry);
            return true;
        } catch (SQLException e) {
            return false;
        } catch (NullPointerException e) {
            return false;

        }
    }

    public boolean deleteAppLock(String lock_id) {
        try {
            String qry = "delete from " + TABLE_LOCK + "  where " + LOCK_ID + " = " + lock_id;
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(qry);
            return true;
        } catch (SQLException e) {
            return false;
        } catch (NullPointerException e) {
            return false;

        }
    }


    public boolean applyScreenLock(String startTime, String endTime, String days, String id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(SCREEN_LOCK_STAR_TIME, startTime);
            contentValues.put(SCREEN_LOCK_END_TIME, endTime);
            contentValues.put(SCREEN_LOCK_DAYS, days);
            contentValues.put(SCREEN_LOCK_ID, id);
            db.insert(TABLE_SCREEN_LOCK, null, contentValues);
            return true;
        } catch (SQLiteException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    public ArrayList<LockPrp> getScreenLocks() {
        ArrayList<LockPrp> array_list_locks = new ArrayList<LockPrp>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from " + TABLE_SCREEN_LOCK, null);
            while (res.moveToNext()) {
                LockPrp lockPrp = new LockPrp();
                lockPrp.setLOCK_START_TIME(res.getString(res.getColumnIndex(SCREEN_LOCK_STAR_TIME)));
                lockPrp.setLOCK_END_TIME(res.getString(res.getColumnIndex(SCREEN_LOCK_END_TIME)));
                lockPrp.setLOCK_ID(res.getString(res.getColumnIndex(SCREEN_LOCK_ID)));
                lockPrp.setLOCK_DAYS(res.getString(res.getColumnIndex(SCREEN_LOCK_DAYS)));
                array_list_locks.add(lockPrp);
            }
        } catch (SQLException e) {

        } catch (NullPointerException e) {

        } catch (Exception e) {

        }
        return array_list_locks;

    }


    public boolean deleteScreenLock(String lock_id) {
        try {
            String qry = "delete from " + TABLE_SCREEN_LOCK + "  where " + SCREEN_LOCK_ID + " = " + lock_id;
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(qry);
            return true;
        } catch (SQLException e) {
            return false;
        } catch (NullPointerException e) {
            return false;

        }
    }
}

