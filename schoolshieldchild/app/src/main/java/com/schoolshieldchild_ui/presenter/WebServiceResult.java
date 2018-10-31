package com.schoolshieldchild_ui.presenter;

import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldchild_ui.controller.utils.DialogManager;
import com.schoolshieldchild_ui.model.login.Login;
import com.schoolshieldchild_ui.model.studentlocation.StudentLocation;
import com.schoolshieldchild_ui.model.universalresponce.UniversalResponce;
import com.schoolshieldchild_ui.model.uploadapps.UploadApps;
import com.schoolshieldchild_ui.view.activity.HomeActivity;
import com.schoolshieldchild_ui.view.activity.LoginActivity;
import com.schoolshieldchild_ui.view.services.UploadApplications;
import com.schoolshieldchild_ui.view.services.screenlock.SceenLockView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebServiceResult {
    static List<String> AllAppData = new ArrayList<>();


    public static void ChildLogin(String password, String deviceToken, String deviceType,String osVersion) {
        Call<Login> responseCall = WebServiceConnection.getInstance().childLogin(password, deviceToken, deviceType,osVersion);
        responseCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                DialogManager.stopProgressDialog();
                LoginActivity.getInstance().updateLogin(response.body());
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                DialogManager.stopProgressDialog();
            }
        });

    }

    public static void uploadApplicationToServer(final String app_package, String student_id, final String app_name, String app_icon) {

        Call<UploadApps> responseCall = WebServiceConnection.getInstance().uploadApps(app_package, student_id, app_name, app_icon);
        responseCall.enqueue(new Callback<UploadApps>() {
            @Override
            public void onResponse(Call<UploadApps> call, Response<UploadApps> response) {
                try {
                    UploadApplications.getInstance().updateDataBase(response.body());
                } catch (NullPointerException e) {

                }
            }

            @Override
            public void onFailure(Call<UploadApps> call, Throwable t) {
                // DialogManager.stopProgressDialog();
                UploadApplications.getInstance().updateDataBase(null);

            }
        });

    }

    public static void addNotifications(final String studentId, final String type, final String duration, String status, String package_name) {

        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().addNotification(studentId, type, duration, status, package_name);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {
                System.out.println("User Action Saved  " + type);
            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
                // DialogManager.stopProgressDialog();
                t.printStackTrace();
            }
        });

    }


    public static void updateLocation(double latitude, double longitude, String userid) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().updateUserLLocation(latitude + "", longitude + "", userid);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {
                System.out.println("User Location Updated  " + response.body().getResult());
            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public static void getStudentLatLong(String studentId) {
        Call<StudentLocation> responseCall = WebServiceConnection.getInstance().getStudentLatLong(studentId);
        responseCall.enqueue(new Callback<StudentLocation>() {
            @Override
            public void onResponse(Call<StudentLocation> call, Response<StudentLocation> response) {

                HomeActivity.getInstance().updatelatlong(response.body());

            }

            @Override
            public void onFailure(Call<StudentLocation> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void activatelock(String lock_id, String lock_from, String lock_from_id) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().activatelock(lock_id, lock_from, lock_from_id);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {
            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public static void deleteApplicationlock(String lock_id, String lock_by_id, String lock_by, String student_id, String permanent, String package_name) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().deleteLock(lock_id, lock_by_id, lock_by, student_id, permanent, package_name);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {
            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
            }
        });
    }


    public static void activateScreenlock(String lock_id, String lock_from, String lock_from_id) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().activateScreenlock(lock_id, lock_from, lock_from_id);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {

            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public static void deleteScreenlock(String lock_id, String lock_by_id, String lock_by, String student_id, String permanent) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().deleteScreenLock(lock_id, lock_by_id, lock_by, student_id, permanent);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {
            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
            }
        });
    }


    public static void deleteScreenLockPermanently(String lock_id, String lock_by_id, String lock_by, String student_id, final String permanent) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().deleteScreenLockPermanently(lock_id, lock_by_id, lock_by, student_id, permanent);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {

            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
            }
        });
    }


    public static void saveMessage(String app_student_id, String app_msg, String app_phno, String app_status, String app_msg_date) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().saveMessage(app_student_id, app_msg, app_phno, app_status, app_msg_date);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {
            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
            }
        });
    }

    public static void emergency(String student_id) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().emergency(student_id);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {


            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
            }
        });
    }

    public static void saveStuentStatus(String student_id, String status) {

        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().saveStuentStatus(student_id, status);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {


            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
            }
        });

    }
}