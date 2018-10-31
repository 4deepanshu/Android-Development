package com.schoolshieldparent_ui.model.allmsgs;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AllMessage {

@SerializedName("stu_phnos")
@Expose
private List<StuPhno> stuPhnos = new ArrayList<StuPhno>();
@SerializedName("status")
@Expose
private Integer status;

/**
*
* @return
* The stuPhnos
*/
public List<StuPhno> getStuPhnos() {
return stuPhnos;
}

/**
*
* @param stuPhnos
* The stu_phnos
*/
public void setStuPhnos(List<StuPhno> stuPhnos) {
this.stuPhnos = stuPhnos;
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
