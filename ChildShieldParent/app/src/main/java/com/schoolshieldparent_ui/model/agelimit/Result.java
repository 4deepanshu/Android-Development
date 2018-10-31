package com.schoolshieldparent_ui.model.agelimit;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("forum_id")
@Expose
private Integer forumId;
@SerializedName("commnent")
@Expose
private String commnent;

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

/**
*
* @return
* The commnent
*/
public String getCommnent() {
return commnent;
}

/**
*
* @param commnent
* The commnent
*/
public void setCommnent(String commnent) {
this.commnent = commnent;
}

}

