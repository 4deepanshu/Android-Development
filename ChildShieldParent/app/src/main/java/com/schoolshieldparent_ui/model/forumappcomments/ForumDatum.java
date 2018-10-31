package com.schoolshieldparent_ui.model.forumappcomments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 11/8/16.
 */
public class ForumDatum {

    @SerializedName("parent_name")
    @Expose
    private String parentName;
    @SerializedName("parent_lname")
    @Expose
    private String parentLname;
    @SerializedName("comment")
    @Expose
    private String comment;

    /**
     * @return The parentName
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * @param parentName The parent_name
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * @return The parentLname
     */
    public String getParentLname() {
        return parentLname;
    }

    /**
     * @param parentLname The parent_lname
     */
    public void setParentLname(String parentLname) {
        this.parentLname = parentLname;
    }

    /**
     * @return The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

}
