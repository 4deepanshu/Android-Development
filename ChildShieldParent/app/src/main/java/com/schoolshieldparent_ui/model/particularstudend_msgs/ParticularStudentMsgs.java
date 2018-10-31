package com.schoolshieldparent_ui.model.particularstudend_msgs;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ParticularStudentMsgs {

@SerializedName("stu_msgdata")
@Expose
private List<StuMsgdatum> stuMsgdata = new ArrayList<StuMsgdatum>();
@SerializedName("status")
@Expose
private Integer status;

/**
*
* @return
* The stuMsgdata
*/
public List<StuMsgdatum> getStuMsgdata() {
return stuMsgdata;
}

/**
*
* @param stuMsgdata
* The stu_msgdata
*/
public void setStuMsgdata(List<StuMsgdatum> stuMsgdata) {
this.stuMsgdata = stuMsgdata;
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
