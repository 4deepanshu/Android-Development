package com.schoolshieldparent_ui.model.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 26/8/16.
 */
public class Result {

@SerializedName("current_day")
@Expose
private List<CurrentDay> currentDay = new ArrayList<CurrentDay>();
@SerializedName("week")
@Expose
private List<CurrentDay> week = new ArrayList<CurrentDay>();
@SerializedName("month")
@Expose
private List<CurrentDay> month = new ArrayList<CurrentDay>();
@SerializedName("status")
@Expose
private Integer status;

/**
*
* @return
* The currentDay
*/
public List<CurrentDay> getCurrentDay() {
return currentDay;
}

/**
*
* @param currentDay
* The current_day
*/
public void setCurrentDay(List<CurrentDay> currentDay) {
this.currentDay = currentDay;
}

/**
*
* @return
* The week
*/
public List<CurrentDay> getWeek() {
return week;
}

/**
*
* @param week
* The week
*/
public void setWeek(List<CurrentDay> week) {
this.week = week;
}

/**
*
* @return
* The month
*/
public List<CurrentDay> getMonth() {
return month;
}

/**
*
* @param month
* The month
*/
public void setMonth(List<CurrentDay> month) {
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
