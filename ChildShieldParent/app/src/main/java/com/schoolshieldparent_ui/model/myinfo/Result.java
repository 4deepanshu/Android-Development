package com.schoolshieldparent_ui.model.myinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 3/8/16.
 */
public class Result {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("parent")
@Expose
private Parent parent;

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
* The parent
*/
public Parent getParent() {
return parent;
}

/**
*
* @param parent
* The parent
*/
public void setParent(Parent parent) {
this.parent = parent;
}

}
