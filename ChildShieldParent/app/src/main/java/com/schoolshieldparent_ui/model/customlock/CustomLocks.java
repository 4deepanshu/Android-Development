package com.schoolshieldparent_ui.model.customlock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
* Created by root on 17/8/16.
*/
public class CustomLocks {

@SerializedName("cus_locks")
@Expose
private List<CusLock> cusLocks = new ArrayList<CusLock>();
@SerializedName("status")
@Expose
private Integer status;

/**
*
* @return
* The cusLocks
*/
public List<CusLock> getCusLocks() {
return cusLocks;
}

/**
*
* @param cusLocks
* The cus_locks
*/
public void setCusLocks(List<CusLock> cusLocks) {
this.cusLocks = cusLocks;
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
