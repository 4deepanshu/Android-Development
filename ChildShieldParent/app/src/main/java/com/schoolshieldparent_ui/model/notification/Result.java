package com.schoolshieldparent_ui.model.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root gpson 29/7/16.
 */
public class Result {

@SerializedName("current_day")
@Expose
private List<NotificationResult> currentDay = new ArrayList<NotificationResult>();
@SerializedName("week")
@Expose
private List<NotificationResult> week = new ArrayList<NotificationResult>();
@SerializedName("month")
@Expose
private List<NotificationResult> month = new ArrayList<NotificationResult>();
@SerializedName("status")
@Expose
private Integer status;

/**
*
* @return
* The currentDay
*/
public List<NotificationResult> getCurrentDay() {
return currentDay;
}

/**
*
* @param currentDay
* The current_day
*/
public void setCurrentDay(List<NotificationResult> currentDay) {
this.currentDay = currentDay;
}

/**
*
* @return
* The week
*/
public List<NotificationResult> getWeek() {
return week;
}

/**
*
* @param week
* The week
*/
public void setWeek(List<NotificationResult> week) {
this.week = week;
}

/**
*
* @return
* The month
*/
public List<NotificationResult> getMonth() {
return month;
}

/**
*
* @param month
* The month
*/
public void setMonth(List<NotificationResult> month) {
this.month = month;
}

/**
*
* @return
* The status
*/
public Integer getStatus() {
return status;
}

/**
*
* @param status
* The status
*/
public void setStatus(Integer status) {
this.status = status;
}

}
