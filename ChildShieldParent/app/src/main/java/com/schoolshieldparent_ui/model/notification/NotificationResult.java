package com.schoolshieldparent_ui.model.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationResult {

@SerializedName("id")
@Expose
private String id;
@SerializedName("type")
@Expose
private String type;
@SerializedName("duration")
@Expose
private String duration;
@SerializedName("status")
@Expose
private String status;
@SerializedName("date")
@Expose
private String date;
@SerializedName("package")
@Expose
private String _package;
@SerializedName("student_id")
@Expose
private String studentId;
@SerializedName("app_id")
@Expose
private String appId;
@SerializedName("app_name")
@Expose
private String appName;
@SerializedName("app_package")
@Expose
private String appPackage;
@SerializedName("app_icon")
@Expose
private String appIcon;
@SerializedName("app_status")
@Expose
private String appStatus;

/**
*
* @return
* The id
*/
public String getId() {
return id;
}

/**
*
* @param id
* The id
*/
public void setId(String id) {
this.id = id;
}

/**
*
* @return
* The type
*/
public String getType() {
return type;
}

/**
*
* @param type
* The type
*/
public void setType(String type) {
this.type = type;
}

/**
*
* @return
* The duration
*/
public String getDuration() {
return duration;
}

/**
*
* @param duration
* The duration
*/
public void setDuration(String duration) {
this.duration = duration;
}

/**
*
* @return
* The status
*/
public String getStatus() {
return status;
}

/**
*
* @param status
* The status
*/
public void setStatus(String status) {
this.status = status;
}

/**
*
* @return
* The date
*/
public String getDate() {
return date;
}

/**
*
* @param date
* The date
*/
public void setDate(String date) {
this.date = date;
}

/**
*
* @return
* The _package
*/
public String getPackage() {
return _package;
}

/**
*
* @param _package
* The package
*/
public void setPackage(String _package) {
this._package = _package;
}

/**
*
* @return
* The studentId
*/
public String getStudentId() {
return studentId;
}

/**
*
* @param studentId
* The student_id
*/
public void setStudentId(String studentId) {
this.studentId = studentId;
}

/**
*
* @return
* The appId
*/
public String getAppId() {
return appId;
}

/**
*
* @param appId
* The app_id
*/
public void setAppId(String appId) {
this.appId = appId;
}

/**
*
* @return
* The appName
*/
public String getAppName() {
return appName;
}

/**
*
* @param appName
* The app_name
*/
public void setAppName(String appName) {
this.appName = appName;
}

/**
*
* @return
* The appPackage
*/
public String getAppPackage() {
return appPackage;
}

/**
*
* @param appPackage
* The app_package
*/
public void setAppPackage(String appPackage) {
this.appPackage = appPackage;
}

/**
*
* @return
* The appIcon
*/
public String getAppIcon() {
return appIcon;
}

/**
*
* @param appIcon
* The app_icon
*/
public void setAppIcon(String appIcon) {
this.appIcon = appIcon;
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

}

