package com.schoolshieldparent_ui.model.forum;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/8/16.
 */
public class Result {

    @SerializedName("apps")
    @Expose
    private List<App> apps = new ArrayList<App>();
    @SerializedName("status")
    @Expose
    private Integer status;

    /**
     * @return The apps
     */
    public List<App> getApps() {
        return apps;
    }

    /**
     * @param apps The apps
     */
    public void setApps(List<App> apps) {
        this.apps = apps;
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

}
