package com.schoolshieldparent_ui.model.allscreenlockchildren;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
* Created by root on 19/8/16.
*/
public class ScreenLock {

   @SerializedName("lock_count")
   @Expose
   private String lockCount;
   @SerializedName("permanent_lock")
   @Expose
   private String permanentLock;
   @SerializedName("student_name")
   @Expose
   private String studentName;
   @SerializedName("student_lname")
   @Expose
   private String studentLname;
   @SerializedName("student_status")
   @Expose
   private String studentStatus;
   @SerializedName("student_id")
   @Expose
   private String studentId;

   /**
    *
    * @return
    * The lockCount
    */
   public String getLockCount() {
      return lockCount;
   }

   /**
    *
    * @param lockCount
    * The lock_count
    */
   public void setLockCount(String lockCount) {
      this.lockCount = lockCount;
   }

   /**
    *
    * @return
    * The permanentLock
    */
   public String getPermanentLock() {
      return permanentLock;
   }

   /**
    *
    * @param permanentLock
    * The permanent_lock
    */
   public void setPermanentLock(String permanentLock) {
      this.permanentLock = permanentLock;
   }

   /**
    *
    * @return
    * The studentName
    */
   public String getStudentName() {
      return studentName;
   }

   /**
    *
    * @param studentName
    * The student_name
    */
   public void setStudentName(String studentName) {
      this.studentName = studentName;
   }

   /**
    *
    * @return
    * The studentLname
    */
   public String getStudentLname() {
      return studentLname;
   }

   /**
    *
    * @param studentLname
    * The student_lname
    */
   public void setStudentLname(String studentLname) {
      this.studentLname = studentLname;
   }

   /**
    *
    * @return
    * The studentStatus
    */
   public String getStudentStatus() {
      return studentStatus;
   }

   /**
    *
    * @param studentStatus
    * The student_status
    */
   public void setStudentStatus(String studentStatus) {
      this.studentStatus = studentStatus;
   }

   /**
    *
    * @return
    * The studentId
    */
   public String getStudentId() {
      return studentId;
   }

   /**
    *
    * @param studentId
    * The student_id
    */
   public void setStudentId(String studentId) {
      this.studentId = studentId;
   }

}

