package com.schoolshieldparent_ui.model.applications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 12/8/16.
 */
public class Result {

@SerializedName("locked")
@Expose
private List<Locked> locked = new ArrayList<Locked>();
@SerializedName("unlocked")
@Expose
private List<Locked> unlocked = new ArrayList<Locked>();
@SerializedName("status")
@Expose
private Integer status;

/**
*
* @return
* The locked
*/
public List<Locked> getLocked() {
return locked;
}

/**
*
* @param locked
* The locked
*/
public void setLocked(List<Locked> locked) {
this.locked = locked;
}

/**
*
* @return
* The unlocked
*/
public List<Locked> getUnlocked() {
return unlocked;
}

/**
*
* @param unlocked
* The unlocked
*/
public void setUnlocked(List<Locked> unlocked) {
this.unlocked = unlocked;
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
