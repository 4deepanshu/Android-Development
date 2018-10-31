package com.schoolshieldparent_ui.model.forumappcomments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 11/8/16.
 */
public class Result {

    @SerializedName("forum_data")
    @Expose
    private List<ForumDatum> forumData = new ArrayList<ForumDatum>();
    @SerializedName("status")
    @Expose
    private Integer status;

    /**
     * @return The forumData
     */
    public List<ForumDatum> getForumData() {
        return forumData;
    }

    /**
     * @param forumData The forum_data
     */
    public void setForumData(List<ForumDatum> forumData) {
        this.forumData = forumData;
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
