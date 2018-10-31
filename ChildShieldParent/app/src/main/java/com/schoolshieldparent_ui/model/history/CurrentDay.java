package com.schoolshieldparent_ui.model.history;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentDay {

@SerializedName("date")
@Expose
private String date;
@SerializedName("history_title")
@Expose
private String historyTitle;
@SerializedName("history_url")
@Expose
private String historyUrl;

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
* The historyTitle
*/
public String getHistoryTitle() {
return historyTitle;
}

/**
*
* @param historyTitle
* The history_title
*/
public void setHistoryTitle(String historyTitle) {
this.historyTitle = historyTitle;
}

/**
*
* @return
* The historyUrl
*/
public String getHistoryUrl() {
return historyUrl;
}

/**
*
* @param historyUrl
* The history_url
*/
public void setHistoryUrl(String historyUrl) {
this.historyUrl = historyUrl;
}

}

