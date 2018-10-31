package com.schoolshieldparent_ui.model.agelimit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
* Created by root on 11/8/16.
*/
public class SetAgeLimit {

@SerializedName("result")
@Expose
private Result result;

/**
*
* @return
* The result
*/
public Result getResult() {
return result;
}

/**
*
* @param result
* The result
*/
public void setResult(Result result) {
this.result = result;
}

}
