package com.schoolshieldparent_ui.model.customlock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CusLock {

@SerializedName("lock_id")
@Expose
private String lockId;
@SerializedName("package_name")
@Expose
private String packageName;
@SerializedName("start_time")
@Expose
private String startTime;
@SerializedName("end_time")
@Expose
private String endTime;
@SerializedName("is_lock_daily")
@Expose
private String isLockDaily;
@SerializedName("title")
@Expose
private String title;
@SerializedName("status")
@Expose
private String status;
@SerializedName("days")
@Expose
private String days;
@SerializedName("is_activated")
@Expose
private String isActivated;

/**
*
* @return
* The lockId
*/
public String getLockId() {
return lockId;
}

/**
*
* @param lockId
* The lock_id
*/
public void setLockId(String lockId) {
this.lockId = lockId;
}

/**
*
* @return
* The packageName
*/
public String getPackageName() {
return packageName;
}

/**
*
* @param packageName
* The package_name
*/
public void setPackageName(String packageName) {
this.packageName = packageName;
}

/**
*
* @return
* The startTime
*/
public String getStartTime() {
return startTime;
}

/**
*
* @param startTime
* The start_time
*/
public void setStartTime(String startTime) {
this.startTime = startTime;
}

/**
*
* @return
* The endTime
*/
public String getEndTime() {
return endTime;
}

/**
*
* @param endTime
* The end_time
*/
public void setEndTime(String endTime) {
this.endTime = endTime;
}

/**
*
* @return
* The isLockDaily
*/
public String getIsLockDaily() {
return isLockDaily;
}

/**
*
* @param isLockDaily
* The is_lock_daily
*/
public void setIsLockDaily(String isLockDaily) {
this.isLockDaily = isLockDaily;
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
* The days
*/
public String getDays() {
return days;
}

/**
*
* @param days
* The days
*/
public void setDays(String days) {
this.days = days;
}

/**
*
* @return
* The isActivated
*/
public String getIsActivated() {
return isActivated;
}

/**
*
* @param isActivated
* The is_activated
*/
public void setIsActivated(String isActivated) {
this.isActivated = isActivated;
}

}

