package com.schoolshieldparent_ui.presenter;


import com.schoolshieldparent_ui.model.addcomments.AddComments;
import com.schoolshieldparent_ui.model.agelimit.SetAgeLimit;
import com.schoolshieldparent_ui.model.allmsgs.AllMessage;
import com.schoolshieldparent_ui.model.allscreenlockchildren.AllChildForScreenLock;
import com.schoolshieldparent_ui.model.analytics.Analytics;
import com.schoolshieldparent_ui.model.applicationprp.registration.Registration;
import com.schoolshieldparent_ui.model.applications.AllApps;
import com.schoolshieldparent_ui.model.children.GetChildren;
import com.schoolshieldparent_ui.model.customlock.CustomLocks;
import com.schoolshieldparent_ui.model.customscreenlock.CustomScreenLocks;
import com.schoolshieldparent_ui.model.forum.ForumData;
import com.schoolshieldparent_ui.model.forumappcomments.ForumApplicationsComments;
import com.schoolshieldparent_ui.model.gallery.GalleryImages;
import com.schoolshieldparent_ui.model.getrating.AppRating;
import com.schoolshieldparent_ui.model.history.History;
import com.schoolshieldparent_ui.model.location.Location;
import com.schoolshieldparent_ui.model.myinfo.ParentInfo;
import com.schoolshieldparent_ui.model.notification.Notification;
import com.schoolshieldparent_ui.model.particularstudend_msgs.ParticularStudentMsgs;
import com.schoolshieldparent_ui.model.statuswithmsg.StatusWithMessage;
import com.schoolshieldparent_ui.model.universalresponce.UniversalResponce;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WebServiceHolder {
    @FormUrlEncoded
    @POST("parentRegister")
    public Call<Registration> parentRegistration(@Field("first_name") String first_name, @Field("last_name") String last_name, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("parentLogin")
    Call<Registration> parentLogin(@Field("email") String email, @Field("password") String password, @Field("token_id") String token_id);

    @FormUrlEncoded
    @POST("parentForgetpassword")
    Call<UniversalResponce> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("studentRegister")
    Call<UniversalResponce> addChild(@Field("first_name") String fName, @Field("last_name") String lName, @Field("password") String password, @Field("parent_id") String parentId, @Field("dob") String dob, @Field("gender") String gender);

    @FormUrlEncoded
    @POST("GetChildrens")
    Call<GetChildren> getParentsChildren(@Field("parent_id") String parentId);

    @FormUrlEncoded
    @POST("GetChildNotifications")
    Call<Notification> getChildNotification(@Field("student_id") int currentChildID);

    @FormUrlEncoded
    @POST("ParentInformation")
    Call<ParentInfo> getParentInfo(@Field("parent_id") String string);

    @FormUrlEncoded
    @POST("AllAppsAnalytics")
    Call<Analytics> getAnalytics(@Field("student_id") String string);

    @FormUrlEncoded
    @POST("getGalleryImages")
    Call<GalleryImages> getGalleryImages(@Field("student_id") String student_id, @Field("page") String page);

    @FormUrlEncoded
    @POST("getAllApps")
    Call<ForumData> getForumData(@Field("parent_id") String string);

    @FormUrlEncoded
    @POST("getForumData")
    Call<ForumApplicationsComments> getForumApplicationsComments(@Field("package_name") String package_name);

    @FormUrlEncoded
    @POST("getForumData")
    Call<ForumApplicationsComments> getMyComments(@Field("package_name") String package_name, @Field("parent_id") String parent_id);

    @FormUrlEncoded
    @POST("addForums")
    Call<AddComments> getAddCommentStatus(@Field("package_name") String package_name, @Field("parent_id") String parent_id, @Field("comment") String comment);

    @FormUrlEncoded
    @POST("addAgeRange")
    Call<SetAgeLimit> setAgeLimit(@Field("parent_id") String parent_id, @Field("package_name") String aPackage, @Field("age_range") String age_range);

    @FormUrlEncoded
    @POST("addTraficLight")
    Call<SetAgeLimit> setTrafficLight(@Field("parent_id") String parent_id, @Field("package_name") String aPackage, @Field("light") String light);

    @FormUrlEncoded
    @POST("GetAppRating")
    Call<AppRating> getAppRating(@Field("package_name") String aPackage, @Field("parent_id") String parent_id);

    @FormUrlEncoded
    @POST("addRating")
    Call<StatusWithMessage> getAddRatingStatus(@Field("package_name") String aPackage, @Field("parent_id") String parent_id, @Field("rating") String rating);

    @FormUrlEncoded
    @POST("DeleteStudent")
    Call<UniversalResponce> getDeleteChild(@Field("student_id") String studentId);

    @FormUrlEncoded
    @POST("StudentUpdatepassword")
    Call<UniversalResponce> resetStudentPassword(@Field("student_id") String studentId, @Field("password") String password);

    @FormUrlEncoded
    @POST("GetChildLocation")
    Call<Location> getStudentLocation(@Field("student_id") String currentChildID,@Field("locale") String locale);

    @FormUrlEncoded
    @POST("getChildApps")
    Call<AllApps> getStudentApplications(@Field("student_id") String student_id);

    @FormUrlEncoded
    @POST("LockApplication")
    Call<UniversalResponce> lockStudentApp(@Field("title") String title, @Field("app_package") String packageName, @Field("student_id") String studentid, @Field("lock_by") String from, @Field("lock_from_id") String parentid, @Field("start_time") String startTime, @Field("end_time") String endTime, @Field("days") String days, @Field("is_lock_daily") String daily, @Field("permanent_lock") String permanent);

    @FormUrlEncoded
    @POST("GetChildAppSingle")
    Call<CustomLocks> getCustomLocks(@Field("package_name") String packageName, @Field("student_id") String currentChildID);


    @FormUrlEncoded
    @POST("DeleteLock")
    Call<UniversalResponce> deleteLock(@Field("lock_id") String lock_id, @Field("lock_by_id") String lock_by_id, @Field("lock_by") String lock_by, @Field("student_id") String student_id, @Field("permanent") String permanent, @Field("package_name") String package_name);

    @FormUrlEncoded
    @POST("getChildScreenLocks")
    Call<AllChildForScreenLock> getAllChildForScreenLock(@Field("parent_id") String parent_id);

    @FormUrlEncoded
    @POST("LockScreen")
    Call<UniversalResponce> setScreenLock(@Field("title") String title, @Field("student_id") String studentid, @Field("lock_by") String from, @Field("lock_from_id") String parentid, @Field("start_time") String startTime, @Field("end_time") String endTime, @Field("days") String days, @Field("is_lock_daily") String daily, @Field("permanent_lock") String permanent);

    @FormUrlEncoded
    @POST("GetChildScLock")
    Call<CustomScreenLocks> getAllCustomScreenLock(@Field("student_id") String studentId);

    @FormUrlEncoded
    @POST("DeleteScreenLock")
    Call<UniversalResponce> deleteScreenLockLock(@Field("lock_id") String lockid, @Field("lock_by_id") String parent_id, @Field("lock_by") String from, @Field("student_id") String studentId, @Field("permanent") String permanent);

    @FormUrlEncoded
    @POST("GetStudentMessage")
    Call<AllMessage> getAllMessage(@Field("student_id") String currentChildID);

    @FormUrlEncoded
    @POST("GetStudentPhoneMessage")
    Call<ParticularStudentMsgs> getParticulatChat(@Field("student_id") String student_id, @Field("app_phno") String phn_number);

    @FormUrlEncoded
    @POST("parentLogOut")
    Call<UniversalResponce> logoutParent(@Field("parent_id") String parent_id);

    @FormUrlEncoded
    @POST("GetChildHistory")
    Call<History> getChildHistory(@Field("student_id") String currentChildID);

    @FormUrlEncoded
    @POST("updateNotifiation")
    Call<UniversalResponce> sendNotificationStatus(@Field("parent_id") String parentId, @Field("value") String value);
}
