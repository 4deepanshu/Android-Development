package com.schoolshieldparent_ui.model.applications;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllApps {

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

