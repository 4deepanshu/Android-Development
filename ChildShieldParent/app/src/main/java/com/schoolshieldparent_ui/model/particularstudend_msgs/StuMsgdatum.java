package com.schoolshieldparent_ui.model.particularstudend_msgs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
* Created by root on 22/8/16.
*/
public class StuMsgdatum {

@SerializedName("app_msg")
@Expose
private String appMsg;
@SerializedName("app_status")
@Expose
private String appStatus;
@SerializedName("app_msg_date")
@Expose
private String appMsgDate;
@SerializedName("app_msg_server_date")
@Expose
private String appMsgServerDate;

/**
*
* @return
* The appMsg
*/
public String getAppMsg() {
return appMsg;
}

/**
*
* @param appMsg
* The app_msg
*/
public void setAppMsg(String appMsg) {
this.appMsg = appMsg;
}

/**
*
* @return
* The appStatus
*/
public String getAppStatus() {
return appStatus;
}

/**
*
* @param appStatus
* The app_status
*/
public void setAppStatus(String appStatus) {
this.appStatus = appStatus;
}

/**
*
* @return
* The appMsgDate
*/
public String getAppMsgDate() {
return appMsgDate;
}

/**
*
* @param appMsgDate
* The app_msg_date
*/
public void setAppMsgDate(String appMsgDate) {
this.appMsgDate = appMsgDate;
}

/**
*
* @return
* The appMsgServerDate
*/
public String getAppMsgServerDate() {
return appMsgServerDate;
}

/**
*
* @param appMsgServerDate
* The app_msg_server_date
*/
public void setAppMsgServerDate(String appMsgServerDate) {
this.appMsgServerDate = appMsgServerDate;
}

}
