package com.schoolshieldchild_ui.model.uploadapps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
* Created by root on 21/7/16.
*/
public class UploadApps {

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
