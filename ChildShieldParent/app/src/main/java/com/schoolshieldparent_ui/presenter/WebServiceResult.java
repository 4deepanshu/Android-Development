package com.schoolshieldparent_ui.presenter;


import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
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
import com.schoolshieldparent_ui.pushnotification.MyFirebaseMessagingService;
import com.schoolshieldparent_ui.view.activity.Activity_AllChild_LockScreen;
import com.schoolshieldparent_ui.view.activity.Activity_Analytics;
import com.schoolshieldparent_ui.view.activity.Activity_ApplicationLock;
import com.schoolshieldparent_ui.view.activity.Activity_Applications;
import com.schoolshieldparent_ui.view.activity.Activity_Children;
import com.schoolshieldparent_ui.view.activity.Activity_CustomLock;
import com.schoolshieldparent_ui.view.activity.Activity_CustomScreenLock;
import com.schoolshieldparent_ui.view.activity.Activity_ForgotPassword;
import com.schoolshieldparent_ui.view.activity.Activity_ForumAddAgeLimit;
import com.schoolshieldparent_ui.view.activity.Activity_ForumAddMyCommments;
import com.schoolshieldparent_ui.view.activity.Activity_ForumAddTrafficLight;
import com.schoolshieldparent_ui.view.activity.Activity_ForumAppCommets;
import com.schoolshieldparent_ui.view.activity.Activity_ForumAppDetail;
import com.schoolshieldparent_ui.view.activity.Activity_ForumsAllApps;
import com.schoolshieldparent_ui.view.activity.Activity_Gallery;
import com.schoolshieldparent_ui.view.activity.Activity_History;
import com.schoolshieldparent_ui.view.activity.Activity_Home;
import com.schoolshieldparent_ui.view.activity.Activity_Location;
import com.schoolshieldparent_ui.view.activity.Activity_Login;
import com.schoolshieldparent_ui.view.activity.Activity_Message;
import com.schoolshieldparent_ui.view.activity.Activity_MsgChat;
import com.schoolshieldparent_ui.view.activity.Activity_MyInfo;
import com.schoolshieldparent_ui.view.activity.Activity_Notification;
import com.schoolshieldparent_ui.view.activity.Activity_Register;
import com.schoolshieldparent_ui.view.activity.Activity_Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebServiceResult {
    static List<String> AllAppData = new ArrayList<>();


    public static void ParentRegistration(String first_name, String last_name, String email, String password) {
        Call<Registration> responseCall = WebServiceConnection.getInstance().parentRegistration(first_name, last_name, email, password);
        responseCall.enqueue(new Callback<Registration>() {
            @Override
            public void onResponse(Call<Registration> call, Response<Registration> response) {
                Activity_Register.getInstance().updateRegistration(response.body());
            }

            @Override
            public void onFailure(Call<Registration> call, Throwable t) {
                DialogManager.stopProgressDialog();
            }
        });

    }

    public static void ParentLogin(String email, String password, String token_id) {
        Call<Registration> responseCall = WebServiceConnection.getInstance().parentLogin(email, password, token_id);
        responseCall.enqueue(new Callback<Registration>() {
            @Override
            public void onResponse(Call<Registration> call, Response<Registration> response) {
                Activity_Login.getInstance().updateLogin(response.body());
            }

            @Override
            public void onFailure(Call<Registration> call, Throwable t) {
                DialogManager.stopProgressDialog();
            }
        });

    }

    public static void ForgotPassword(String email) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().forgotPassword(email);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {
                Activity_ForgotPassword.getInstance().updateForgotPassword(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
                DialogManager.stopProgressDialog();
            }
        });

    }

    public static void AddChild(String fName, String lName, String password, String parentId, String dob, String gender) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().addChild(fName, lName, password, parentId, dob, gender);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus().toString().equals("1")) {
                    Activity_Children.getInstance().updateAddChildStatus(response.body().getResult().getStatus().toString());
                }
            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
                DialogManager.stopProgressDialog();
            }
        });
    }

    public static void ParentsChildren(String parentId) {
        Call<GetChildren> responseCall = WebServiceConnection.getInstance().getParentsChildren(parentId);
        responseCall.enqueue(new Callback<GetChildren>() {
            @Override
            public void onResponse(Call<GetChildren> call, Response<GetChildren> response) {
                DialogManager.stopProgressDialog();
                Activity_Home.getInstance().updateChildren(response.body());

            }

            @Override
            public void onFailure(Call<GetChildren> call, Throwable t) {
                DialogManager.stopProgressDialog();
            }
        });
    }

    public static void ParentsAllChildren(String parentId) {
        Call<GetChildren> responseCall = WebServiceConnection.getInstance().getParentsChildren(parentId);
        responseCall.enqueue(new Callback<GetChildren>() {
            @Override
            public void onResponse(Call<GetChildren> call, Response<GetChildren> response) {
                DialogManager.stopProgressDialog();
                Activity_Children.getInstance().updateChildrenList(response.body(), 1);

            }

            @Override
            public void onFailure(Call<GetChildren> call, Throwable t) {
                DialogManager.stopProgressDialog();
            }
        });
    }

    public static void NotificationData(int currentChildID, final int tabPosition) {
        Call<Notification> responseCall = WebServiceConnection.getInstance().getChildNotification(currentChildID);
        responseCall.enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus().toString().equalsIgnoreCase("1")) {
                    Activity_Notification.getInstance().updateNotification(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                DialogManager.stopProgressDialog();
            }
        });
    }

    public static void ParentInfo() {
        Call<ParentInfo> responseCall = WebServiceConnection.getInstance().getParentInfo(SharedPref.getString(MyApplication.PARENT_ID));
        responseCall.enqueue(new Callback<ParentInfo>() {
            @Override
            public void onResponse(Call<ParentInfo> call, Response<ParentInfo> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus().toString().equalsIgnoreCase("1")) {
                    Activity_MyInfo.getInstance().updateParentInfo(response.body().getResult());

                }
            }

            @Override
            public void onFailure(Call<ParentInfo> call, Throwable t) {
                DialogManager.stopProgressDialog();
            }
        });
    }

    public static void AnalyticsAllApplications() {
        Call<Analytics> responseCall = WebServiceConnection.getInstance().getAnalytics(MyApplication.currentChildID + "");
        responseCall.enqueue(new Callback<Analytics>() {
            @Override
            public void onResponse(Call<Analytics> call, Response<Analytics> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus().toString().equalsIgnoreCase("1")) {
                    Activity_Analytics.getInstance().updateAnalytics(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<Analytics> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });

    }

    public static void GalleryImages(String string, String page) {
        Call<GalleryImages> responseCall = WebServiceConnection.getInstance().getGalleryImages(string, page);
        responseCall.enqueue(new Callback<GalleryImages>() {
            @Override
            public void onResponse(Call<GalleryImages> call, Response<GalleryImages> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus().toString().equalsIgnoreCase("1")) {
                    Activity_Gallery.getInstance().updateGallery(response.body().getResult().getImages());
                }
            }

            @Override
            public void onFailure(Call<GalleryImages> call, Throwable t) {
                DialogManager.stopProgressDialog();
            }
        });

    }

    public static void ForumApplications(String string) {
        Call<ForumData> responseCall = WebServiceConnection.getInstance().getForumData(string);
        responseCall.enqueue(new Callback<ForumData>() {
            @Override
            public void onResponse(Call<ForumData> call, Response<ForumData> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus() == 1) {
                    Activity_ForumsAllApps.getInstance().updateForumsData(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<ForumData> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });

    }

    public static void AppsComments(String PACKAGE) {
        Call<ForumApplicationsComments> responseCall = WebServiceConnection.getInstance().getForumApplicationsComments(PACKAGE);
        responseCall.enqueue(new Callback<ForumApplicationsComments>() {
            @Override
            public void onResponse(Call<ForumApplicationsComments> call, Response<ForumApplicationsComments> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus() == 1) {
                    Activity_ForumAppCommets.getInstance().updateAppliacationComments(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<ForumApplicationsComments> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });

    }

    public static void AddMyComments(String PACKAGE, String parent_id) {
        Call<ForumApplicationsComments> responseCall = WebServiceConnection.getInstance().getMyComments(PACKAGE, parent_id);
        responseCall.enqueue(new Callback<ForumApplicationsComments>() {
            @Override
            public void onResponse(Call<ForumApplicationsComments> call, Response<ForumApplicationsComments> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus() == 1) {
                    Activity_ForumAddMyCommments.getInstance().updateMyComments(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<ForumApplicationsComments> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void AddMycommetsData(String PACKAGE, String parent_id, String comment) {
        Call<AddComments> responseCall = WebServiceConnection.getInstance().getAddCommentStatus(PACKAGE, parent_id, comment);
        responseCall.enqueue(new Callback<AddComments>() {
            @Override
            public void onResponse(Call<AddComments> call, Response<AddComments> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus() == 1) {
                    Activity_ForumAddMyCommments.getInstance().updateComments();
                }
            }

            @Override
            public void onFailure(Call<AddComments> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void SetAgeLimit(String parent_id, String aPackage, String age) {
        Call<SetAgeLimit> responseCall = WebServiceConnection.getInstance().setAgeLimit(parent_id, aPackage, age);
        responseCall.enqueue(new Callback<SetAgeLimit>() {
            @Override
            public void onResponse(Call<SetAgeLimit> call, Response<SetAgeLimit> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus() == 1) {
                    Activity_ForumAddAgeLimit.getInstance().updateAgeStatus();
                }
            }

            @Override
            public void onFailure(Call<SetAgeLimit> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void setTrafficLimit(String parent_id, String aPackage, String light) {
        Call<SetAgeLimit> responseCall = WebServiceConnection.getInstance().setTrafficLight(parent_id, aPackage, light);
        responseCall.enqueue(new Callback<SetAgeLimit>() {
            @Override
            public void onResponse(Call<SetAgeLimit> call, Response<SetAgeLimit> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus() == 1) {
                    Activity_ForumAddTrafficLight.getInstance().updateAgeStatus();
                }
            }

            @Override
            public void onFailure(Call<SetAgeLimit> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void GetAppRating(String aPackage, String parent_id) {
        Call<AppRating> responseCall = WebServiceConnection.getInstance().getAppRating(aPackage, parent_id);
        responseCall.enqueue(new Callback<AppRating>() {
            @Override
            public void onResponse(Call<AppRating> call, Response<AppRating> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus() == 1) {
                    Activity_ForumAppDetail.getInstance().updateRating(response.body().getResult().getRating());
                }
            }

            @Override
            public void onFailure(Call<AppRating> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void addAppRating(String aPackage, String parent_id, String rating) {
        Call<StatusWithMessage> responseCall = WebServiceConnection.getInstance().getAddRatingStatus(aPackage, parent_id, rating);
        responseCall.enqueue(new Callback<StatusWithMessage>() {
            @Override
            public void onResponse(Call<StatusWithMessage> call, Response<StatusWithMessage> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus() == 1) {
                    Activity_ForumAppDetail.getInstance().updateaddRatingStatus();
                }
            }

            @Override
            public void onFailure(Call<StatusWithMessage> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void DeleteChild(final String studentId) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().getDeleteChild(studentId);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus() == 1) {
                    Activity_MyInfo.getInstance().updateChildList(studentId);
                }
            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void ResetPassword(final String studentId, String password) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().resetStudentPassword(studentId, password);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus() == 1) {
                    Activity_Children.getInstance().updateChildPassword();
                }
            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void StudentsLocation(int currentChildID, String aDefault) {
        Call<Location> responseCall = WebServiceConnection.getInstance().getStudentLocation(currentChildID + "", aDefault);
        responseCall.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus() == 1) {
                    Activity_Location.getInstance().updateLocations(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void GetAllApplication(int currentChildID) {
        Call<AllApps> responseCall = WebServiceConnection.getInstance().getStudentApplications(currentChildID + "");
        responseCall.enqueue(new Callback<AllApps>() {
            @Override
            public void onResponse(Call<AllApps> call, Response<AllApps> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus() == 1) {
                    Activity_Applications.getInstance().updateAllApps(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<AllApps> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void LockApplication(String title, String packageName, int currentChildID, String from, String parentid, String startTime, String endTime, String days, String daily, String permanent) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().lockStudentApp(title, packageName, currentChildID + "", from, parentid, startTime, endTime, days, daily, permanent);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {
                DialogManager.stopProgressDialog();
                Activity_ApplicationLock.getInstance().updateAppLock(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void Custom_Locks(String packageName, int currentChildID) {
        Call<CustomLocks> responseCall = WebServiceConnection.getInstance().getCustomLocks(packageName, currentChildID + "");
        responseCall.enqueue(new Callback<CustomLocks>() {
            @Override
            public void onResponse(Call<CustomLocks> call, Response<CustomLocks> response) {
                DialogManager.stopProgressDialog();
                Activity_CustomLock.getInstance().updateCustomLocks(response.body().getCusLocks());

            }

            @Override
            public void onFailure(Call<CustomLocks> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void deletelock(final String lock_id, String lock_by_id, String lock_by, String student_id, String permanent, String package_name) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().deleteLock(lock_id, lock_by_id, lock_by, student_id, permanent, package_name);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {
                DialogManager.stopProgressDialog();
                if (lock_id.equalsIgnoreCase("-1")) {
                    Activity_ApplicationLock.getInstance().lockDeleted(response.body());
                } else {
                    Activity_CustomLock.getInstance().lockDeleted(response.body());
                }
            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }


    public static void AllChildForScreenLock(String parent_id) {
        Call<AllChildForScreenLock> responseCall = WebServiceConnection.getInstance().getAllChildForScreenLock(parent_id);
        responseCall.enqueue(new Callback<AllChildForScreenLock>() {
            @Override
            public void onResponse(Call<AllChildForScreenLock> call, Response<AllChildForScreenLock> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus().toString().equalsIgnoreCase("1")) {
                    Activity_AllChild_LockScreen.getInstance().updateChildren(response.body());
                }
            }

            @Override
            public void onFailure(Call<AllChildForScreenLock> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void LockScreen(String title, String child_id, String from, String parentid, String startTime, String endTime, String finalDayString, String daily, String permanent) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().setScreenLock(title, child_id, from, parentid, startTime, endTime, finalDayString, daily, permanent);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {
                DialogManager.stopProgressDialog();
                Activity_AllChild_LockScreen.getInstance().updateLock(response.body());
            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void Custom_ScreenLocks(String studentid) {
        Call<CustomScreenLocks> responseCall = WebServiceConnection.getInstance().getAllCustomScreenLock(studentid);
        responseCall.enqueue(new Callback<CustomScreenLocks>() {
            @Override
            public void onResponse(Call<CustomScreenLocks> call, Response<CustomScreenLocks> response) {
                DialogManager.stopProgressDialog();
                Activity_CustomScreenLock.getInstance().updateCustomScreenLock(response.body().getCusLocks());
            }

            @Override
            public void onFailure(Call<CustomScreenLocks> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void deleteScreenlock(final String lockid, String parent_id, String from, String studentId, String permanent) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().deleteScreenLockLock(lockid, parent_id, from, studentId, permanent);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {
                DialogManager.stopProgressDialog();
                if (lockid.equalsIgnoreCase("-1")) {
                    Activity_AllChild_LockScreen.getInstance().updateChildLOck(response.body());
                } else {
                    Activity_CustomScreenLock.getInstance().getCustomLocks("refresh");
                }
            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });


    }


    public static void AllMessageingData(int currentChildID) {
        Call<AllMessage> responseCall = WebServiceConnection.getInstance().getAllMessage(currentChildID + "");
        responseCall.enqueue(new Callback<AllMessage>() {
            @Override
            public void onResponse(Call<AllMessage> call, Response<AllMessage> response) {
                DialogManager.stopProgressDialog();
                Activity_Message.getInstance().updateMessageList(response.body());
            }

            @Override
            public void onFailure(Call<AllMessage> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void GetParticulatChat(int currentChildID, String phn_number) {
        Call<ParticularStudentMsgs> responseCall = WebServiceConnection.getInstance().getParticulatChat(currentChildID + "", phn_number);
        responseCall.enqueue(new Callback<ParticularStudentMsgs>() {
            @Override
            public void onResponse(Call<ParticularStudentMsgs> call, Response<ParticularStudentMsgs> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                    Activity_MsgChat.getInstance().updateMsgChat(response.body().getStuMsgdata());
                }
            }

            @Override
            public void onFailure(Call<ParticularStudentMsgs> call, Throwable t) {
                DialogManager.stopProgressDialog();

            }
        });
    }

    public static void MyInfoChildren(String parentId) {
        Call<GetChildren> responseCall = WebServiceConnection.getInstance().getParentsChildren(parentId);
        responseCall.enqueue(new Callback<GetChildren>() {
            @Override
            public void onResponse(Call<GetChildren> call, Response<GetChildren> response) {
                DialogManager.stopProgressDialog();
                Activity_MyInfo.getInstance().updateChildren(response.body());
            }

            @Override
            public void onFailure(Call<GetChildren> call, Throwable t) {
                DialogManager.stopProgressDialog();
            }
        });
    }

    public static void LogoutParent(String parentId, final String type) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().logoutParent(parentId);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {
                DialogManager.stopProgressDialog();
                if (type.equalsIgnoreCase("logout")) {
                    MyFirebaseMessagingService.getInstance().updateLogout(response.body());
                } else {
                    Activity_Settings.getInstance().updateLogout(response.body());
                }
            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
                DialogManager.stopProgressDialog();
            }
        });
    }

    public static void GetHistory(int currentChildID) {
        Call<History> responseCall = WebServiceConnection.getInstance().getChildHistory("128");
        responseCall.enqueue(new Callback<History>() {
            @Override
            public void onResponse(Call<History> call, Response<History> response) {
                DialogManager.stopProgressDialog();
                Activity_History.getInstance().updateHistoryData(response.body());

            }

            @Override
            public void onFailure(Call<History> call, Throwable t) {
                DialogManager.stopProgressDialog();
            }
        });
    }

    public static void AddNotification(String parentId, String value) {
        Call<UniversalResponce> responseCall = WebServiceConnection.getInstance().sendNotificationStatus(parentId, value);
        responseCall.enqueue(new Callback<UniversalResponce>() {
            @Override
            public void onResponse(Call<UniversalResponce> call, Response<UniversalResponce> response) {
                DialogManager.stopProgressDialog();
                Activity_Settings.getInstance().updateNotificationStatus(response.body());

            }

            @Override
            public void onFailure(Call<UniversalResponce> call, Throwable t) {
                DialogManager.stopProgressDialog();
            }
        });
    }
}