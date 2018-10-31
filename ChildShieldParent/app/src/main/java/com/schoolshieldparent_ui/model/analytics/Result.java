package com.schoolshieldparent_ui.model.analytics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 5/8/16.
 */
public class Result {

    @SerializedName("today")
    @Expose
    private List<AnalyticsData> today = new ArrayList<AnalyticsData>();
    @SerializedName("week")
    @Expose
    private List<AnalyticsData> week = new ArrayList<AnalyticsData>();
    @SerializedName("month")
    @Expose
    private List<AnalyticsData> month = new ArrayList<AnalyticsData>();
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("best")
    @Expose
    private Best best;

    /**
     * @return The today
     */
    public List<AnalyticsData> getToday() {
        return today;
    }

    /**
     * @param today The today
     */
    public void setToday(List<AnalyticsData> today) {
        this.today = today;
    }

    /**
     * @return The week
     */
    public List<AnalyticsData> getWeek() {
        return week;
    }

    /**
     * @param week The week
     */
    public void setWeek(List<AnalyticsData> week) {
        this.week = week;
    }

    /**
     * @return The month
     */
    public List<AnalyticsData> getMonth() {
        return month;
    }

    /**
     * @param month The month
     */
    public void setMonth(List<AnalyticsData> month) {
        this.month = month;
    }

    /**
     * @return The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Best getBest() {
        return best;
    }

    /**
     * @param best The best
     */
    public void setBest(Best best) {
        this.best = best;
    }
}
