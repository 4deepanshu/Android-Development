package com.schoolshieldparent_ui.model.getrating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 12/8/16.
 */
public class Result {

    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("status")
    @Expose
    private Integer status;

    /**
     * @return The rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * @param rating The rating
     */
    public void setRating(String rating) {
        this.rating = rating;
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
