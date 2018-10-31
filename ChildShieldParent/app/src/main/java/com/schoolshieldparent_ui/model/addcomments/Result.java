package com.schoolshieldparent_ui.model.addcomments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
* Created by root on 11/8/16.
*/
public class Result {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("forum_id")
@Expose
private Integer forumId;

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

/**
*
* @return
* The forumId
*/
public Integer getForumId() {
return forumId;
}

/**
*
* @param forumId
* The forum_id
*/
public void setForumId(Integer forumId) {
this.forumId = forumId;
}

}
