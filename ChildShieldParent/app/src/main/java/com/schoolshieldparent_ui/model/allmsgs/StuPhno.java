package com.schoolshieldparent_ui.model.allmsgs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 22/8/16.
 */
public class StuPhno {

    @SerializedName("app_phno")
    @Expose
    private String appPhno;
    @SerializedName("total")
    @Expose
    private String total;

    /**
     * @return The appPhno
     */
    public String getAppPhno() {
        return appPhno;
    }

    /**
     * @param appPhno The app_phno
     */
    public void setAppPhno(String appPhno) {
        this.appPhno = appPhno;
    }

    /**
     * @return The total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

}
