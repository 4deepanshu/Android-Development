package com.schoolshieldchild_ui.presenter;


import com.schoolshieldchild_ui.model.login.Login;
import com.schoolshieldchild_ui.model.studentlocation.StudentLocation;
import com.schoolshieldchild_ui.model.universalresponce.UniversalResponce;
import com.schoolshieldchild_ui.model.uploadapps.UploadApps;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WebServiceHolder {

    @FormUrlEncoded
    @POST("StudentLogin/")
    public Call<Login> childLogin(@Field("password") String password, @Field("device_token") String deviceToken, @Field("device_type") String device_type,@Field("os_version")String os_version);

    @FormUrlEncoded
    @POST("StudentApplications")
    public Call<UploadApps> uploadApps(@Field("app_package") String app_package, @Field("student_id") String student_id, @Field("app_name") String app_name, @Field("app_icon") String app_icon);


    @FormUrlEncoded
    @POST("AddChildNotifications")
    public Call<UniversalResponce> addNotification(@Field("student_id") String student_id, @Field("type") String type, @Field("duration") String duration, @Field("status") String status, @Field("package") String package_name);


    @FormUrlEncoded
    @POST("addLocation")
    public Call<UniversalResponce> updateUserLLocation(@Field("latitude") String latitude, @Field("longitude") String longitude, @Field("student_id") String student_id);

    @FormUrlEncoded
    @POST("GetStuLatLon")
    public Call<StudentLocation> getStudentLatLong(@Field("student_id") String student_id);

    @FormUrlEncoded
    @POST("UpdateLock")
    public Call<UniversalResponce> activatelock(@Field("lock_id") String lock_id, @Field("lock_by") String lock_from, @Field("lock_by_id") String lock_from_id);


    @FormUrlEncoded
    @POST("Delete_lock_permanent")
    Call<UniversalResponce> deleteLock(@Field("lock_id") String lock_id, @Field("lock_by_id") String lock_by_id, @Field("lock_by") String lock_by, @Field("student_id") String student_id, @Field("is_permanent") String permanent, @Field("package_name") String package_name);


    @FormUrlEncoded
    @POST("UpdateScreenLock")
    public Call<UniversalResponce> activateScreenlock(@Field("lock_id") String lock_id, @Field("lock_by") String lock_from, @Field("lock_by_id") String lock_from_id);

    @FormUrlEncoded
    @POST("DeleteScreenLock")
    Call<UniversalResponce> deleteScreenLock(@Field("lock_id") String lock_id, @Field("lock_by_id") String lock_by_id, @Field("lock_by") String lock_by, @Field("student_id") String student_id, @Field("is_permanent") String permanent);


    @FormUrlEncoded
    @POST("Delete_Screen_lock_permanent")
    Call<UniversalResponce> deleteScreenLockPermanently(@Field("lock_id") String lock_id, @Field("lock_by_id") String lock_by_id, @Field("lock_by") String lock_by, @Field("student_id") String student_id, @Field("is_permanent") String permanent);


    @FormUrlEncoded
    @POST("SaveStudentMessage")
    Call<UniversalResponce> saveMessage(@Field("app_student_id") String app_student_id, @Field("app_msg") String app_msg, @Field("app_phno") String app_phno, @Field("app_status") String app_status, @Field("app_msg_date") String app_msg_date);

    @FormUrlEncoded
    @POST("SendEmergencyNotification")
    Call<UniversalResponce> emergency(@Field("student_id") String app_student_id);

    @FormUrlEncoded
    @POST("addStudentReport")
    Call<UniversalResponce> saveStuentStatus(@Field("student_id") String app_student_id,@Field("type") String status);
}
