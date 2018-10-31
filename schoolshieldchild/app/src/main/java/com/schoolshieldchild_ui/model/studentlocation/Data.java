package com.schoolshieldchild_ui.model.studentlocation;



 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Data {

@SerializedName("school_latitude")
@Expose
private String  schoolLatitude;
@SerializedName("school_longitude")
@Expose
private String  schoolLongitude;

/**
* 
* @return
* The schoolLatitude
*/
public String  getSchoolLatitude() {
return schoolLatitude;
}

/**
* 
* @param schoolLatitude
* The school_latitude
*/
public void setSchoolLatitude(String  schoolLatitude) {
this.schoolLatitude = schoolLatitude;
}

/**
* 
* @return
* The schoolLongitude
*/
public String  getSchoolLongitude() {
return schoolLongitude;
}

/**
* 
* @param schoolLongitude
* The school_longitude
*/
public void setSchoolLongitude(String  schoolLongitude) {
this.schoolLongitude = schoolLongitude;
}

}

