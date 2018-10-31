package com.schoolshieldparent_ui.model.location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
* Created by root on 12/8/16.
*/

public class Location {

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
