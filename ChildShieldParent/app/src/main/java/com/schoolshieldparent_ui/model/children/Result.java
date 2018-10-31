package com.schoolshieldparent_ui.model.children;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root gpson 28/7/16.
 */
public class Result {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("childrens")
@Expose
private List<Childs> childrens = new ArrayList<Childs>();

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
* The childrens
*/
public List<Childs> getChildrens() {
return childrens;
}

/**
*
* @param childrens
* The childrens
*/
public void setChildrens(List<Childs> childrens) {
this.childrens = childrens;
}

}
