package com.schoolshieldchild_ui.model.uploadapps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

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
* The message
*/
public String getMessage() {
return message;
}

/**
*
* @param message
* The message
*/
public void setMessage(String message) {
this.message = message;
}

}
