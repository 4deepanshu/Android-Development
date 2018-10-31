package com.schoolshieldparent_ui.model.myinfo;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parent {

@SerializedName("parent_id")
@Expose
private String parentId;
@SerializedName("parent_name")
@Expose
private String parentName;
@SerializedName("parent_lname")
@Expose
private String parentLname;
@SerializedName("parent_phone_no")
@Expose
private String parentPhoneNo;
@SerializedName("parent_email")
@Expose
private String parentEmail;
@SerializedName("parent_password")
@Expose
private String parentPassword;
@SerializedName("parent_address")
@Expose
private String parentAddress;
@SerializedName("other_info")
@Expose
private String otherInfo;
@SerializedName("device_token")
@Expose
private String deviceToken;

/**
*
* @return
* The parentId
*/
public String getParentId() {
return parentId;
}

/**
*
* @param parentId
* The parent_id
*/
public void setParentId(String parentId) {
this.parentId = parentId;
}

/**
*
* @return
* The parentName
*/
public String getParentName() {
return parentName;
}

/**
*
* @param parentName
* The parent_name
*/
public void setParentName(String parentName) {
this.parentName = parentName;
}

/**
*
* @return
* The parentLname
*/
public String getParentLname() {
return parentLname;
}

/**
*
* @param parentLname
* The parent_lname
*/
public void setParentLname(String parentLname) {
this.parentLname = parentLname;
}

/**
*
* @return
* The parentPhoneNo
*/
public String getParentPhoneNo() {
return parentPhoneNo;
}

/**
*
* @param parentPhoneNo
* The parent_phone_no
*/
public void setParentPhoneNo(String parentPhoneNo) {
this.parentPhoneNo = parentPhoneNo;
}

/**
*
* @return
* The parentEmail
*/
public String getParentEmail() {
return parentEmail;
}

/**
*
* @param parentEmail
* The parent_email
*/
public void setParentEmail(String parentEmail) {
this.parentEmail = parentEmail;
}

/**
*
* @return
* The parentPassword
*/
public String getParentPassword() {
return parentPassword;
}

/**
*
* @param parentPassword
* The parent_password
*/
public void setParentPassword(String parentPassword) {
this.parentPassword = parentPassword;
}

/**
*
* @return
* The parentAddress
*/
public String getParentAddress() {
return parentAddress;
}

/**
*
* @param parentAddress
* The parent_address
*/
public void setParentAddress(String parentAddress) {
this.parentAddress = parentAddress;
}

/**
*
* @return
* The otherInfo
*/
public String getOtherInfo() {
return otherInfo;
}

/**
*
* @param otherInfo
* The other_info
*/
public void setOtherInfo(String otherInfo) {
this.otherInfo = otherInfo;
}

/**
*
* @return
* The deviceToken
*/
public String getDeviceToken() {
return deviceToken;
}

/**
*
* @param deviceToken
* The device_token
*/
public void setDeviceToken(String deviceToken) {
this.deviceToken = deviceToken;
}

}

