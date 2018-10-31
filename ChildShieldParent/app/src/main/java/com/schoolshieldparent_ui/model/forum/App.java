package com.schoolshieldparent_ui.model.forum;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class App {

    @SerializedName("total_duration")
    @Expose
    private String totalDuration;
    @SerializedName("package")
    @Expose
    private String _package;
    @SerializedName("app_name")
    @Expose
    private String appName;
    @SerializedName("app_icon")
    @Expose
    private String appIcon;
    @SerializedName("date_use")
    @Expose
    private String dateUse;
    @SerializedName("count_total")
    @Expose
    private String countTotal;
    @SerializedName("light")
    @Expose
    private String light;
    @SerializedName("age_range")
    @Expose
    private String ageRange;
    @SerializedName("app_id")
    @Expose
    private String appId;
    @SerializedName("percentage")
    @Expose
    private Float percentage;
    @SerializedName("locked")
    @Expose
    private Integer locked;
    @SerializedName("description")
    @Expose
    private String description;

    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The totalDuration
     */
    public String getTotalDuration() {
        return totalDuration;
    }

    /**
     * @param totalDuration The total_duration
     */
    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    /**
     * @return The _package
     */
    public String getPackage() {
        return _package;
    }

    /**
     * @param _package The package
     */
    public void setPackage(String _package) {
        this._package = _package;
    }

    /**
     * @return The appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * @param appName The app_name
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * @return The appIcon
     */
    public String getAppIcon() {
        return appIcon;
    }

    /**
     * @param appIcon The app_icon
     */
    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    /**
     * @return The dateUse
     */
    public String getDateUse() {
        return dateUse;
    }

    /**
     * @param dateUse The date_use
     */
    public void setDateUse(String dateUse) {
        this.dateUse = dateUse;
    }

    /**
     * @return The countTotal
     */
    public String getCountTotal() {
        return countTotal;
    }

    /**
     * @param countTotal The count_total
     */
    public void setCountTotal(String countTotal) {
        this.countTotal = countTotal;
    }

    /**
     * @return The light
     */
    public String getLight() {
        return light;
    }

    /**
     * @param light The light
     */
    public void setLight(String light) {
        this.light = light;
    }

    /**
     * @return The ageRange
     */
    public String getAgeRange() {
        return ageRange;
    }

    /**
     * @param ageRange The age_range
     */
    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    /**
     * @return The appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId The app_id
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * @return The percentage
     */
    public Float getPercentage() {
        return percentage;
    }

    /**
     * @param percentage The percentage
     */
    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    /**
     * @return The locked
     */
    public Integer getLocked() {
        return locked;
    }

    /**
     * @param locked The locked
     */
    public void setLocked(Integer locked) {
        this.locked = locked;
    }

}

