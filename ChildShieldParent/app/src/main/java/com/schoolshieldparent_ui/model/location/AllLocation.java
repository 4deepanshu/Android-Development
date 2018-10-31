package com.schoolshieldparent_ui.model.location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllLocation {

@SerializedName("date")
@Expose
private String date;
@SerializedName("latitude")
@Expose
private String latitude;
@SerializedName("longitude")
@Expose
private String longitude;

/**
*
* @return
* The date
*/
public String getDate() {
return date;
}

/**
*
* @param date
* The date
*/
public void setDate(String date) {
this.date = date;
}

/**
*
* @return
* The latitude
*/
public String getLatitude() {
return latitude;
}

/**
*
* @param latitude
* The latitude
*/
public void setLatitude(String latitude) {
this.latitude = latitude;
}

/**
*
* @return
* The longitude
*/
public String getLongitude() {
return longitude;
}

/**
*
* @param longitude
* The longitude
*/
public void setLongitude(String longitude) {
this.longitude = longitude;
}

}


