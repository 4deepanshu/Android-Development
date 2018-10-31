package com.schoolshieldparent_ui.model.location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 12/8/16.
 */

public class Result {

@SerializedName("all_location")
@Expose
private List<AllLocation> allLocation = new ArrayList<AllLocation>();
@SerializedName("top_10")
@Expose
private List<AllLocation> top10 = new ArrayList<AllLocation>();
@SerializedName("status")
@Expose
private Integer status;

/**
*
* @return
* The allLocation
*/
public List<AllLocation> getAllLocation() {
return allLocation;
}

/**
*
* @param allLocation
* The all_location
*/
public void setAllLocation(List<AllLocation> allLocation) {
this.allLocation = allLocation;
}

/**
*
* @return
* The top10
*/
public List<AllLocation> getTop10() {
return top10;
}

/**
*
* @param top10
* The top_10
*/
public void setTop10(List<AllLocation> top10) {
this.top10 = top10;
}

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

}
