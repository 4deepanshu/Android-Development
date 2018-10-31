package com.schoolshieldparent_ui.model.gallery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/8/16.
 */
public class Result {

    @SerializedName("images")
    @Expose
    private List<String> images = new ArrayList<String>();
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("status")
    @Expose
    private Integer status;

    /**
     * @return The images
     */
    public List<String> getImages() {
        return images;
    }

    /**
     * @param images The images
     */
    public void setImages(List<String> images) {
        this.images = images;
    }

    /**
     * @return The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(Integer count) {
        this.count = count;
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
