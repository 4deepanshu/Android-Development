package com.schoolshieldparent_ui.model.applications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
* Created by root on 12/8/16.
*/
public class Locked {

@SerializedName("lock_count")
@Expose
private String lockCount;
@SerializedName("permanent_lock")
@Expose
private String permanentLock;
@SerializedName("package")
@Expose
private String _package;
@SerializedName("title")
@Expose
private String title;
@SerializedName("lock_status")
@Expose
private String lockStatus;
@SerializedName("app_name")
@Expose
private String appName;
@SerializedName("app_icon")
@Expose
private String appIcon;
@SerializedName("app_status")
@Expose
private String appStatus;

/**
*
* @return
* The lockCount
*/
public String getLockCount() {
return lockCount;
}

/**
*
* @param lockCount
* The lock_count
*/
public void setLockCount(String lockCount) {
this.lockCount = lockCount;
}

/**
*
* @return
* The permanentLock
*/
public String getPermanentLock() {
return permanentLock;
}

/**
*
* @param permanentLock
* The permanent_lock
*/
public void setPermanentLock(String permanentLock) {
this.permanentLock = permanentLock;
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
* The title
*/
public String getTitle() {
return title;
}

/**
*
* @param title
* The title
*/
public void setTitle(String title) {
this.title = title;
}

/**
*
* @return
* The lockStatus
*/
public String getLockStatus() {
return lockStatus;
}

/**
*
* @param lockStatus
* The lock_status
*/
public void setLockStatus(String lockStatus) {
this.lockStatus = lockStatus;
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
