package com.schoolshieldparent_ui.model.allscreenlockchildren;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
* Created by root on 19/8/16.
*/
public class Result {

   @SerializedName("screen_lock")
   @Expose
   private List<ScreenLock> screenLock = new ArrayList<ScreenLock>();
   @SerializedName("status")
   @Expose
   private Integer status;

   /**
    * @return The screenLock
    */
   public List<ScreenLock> getScreenLock() {
       return screenLock;
   }

   /**
    * @param screenLock The screen_lock
    */
   public void setScreenLock(List<ScreenLock> screenLock) {
       this.screenLock = screenLock;
   }

   /**
    * @return The status
    */
   public Integer getStatus() {
       return status;
   }

   /**
    * @param status The status
    */
   public void setStatus(Integer status) {
       this.status = status;
   }

}
