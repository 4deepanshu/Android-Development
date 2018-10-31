package com.schoolshieldchild_ui.model.login;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("student_id")
    @Expose
    private Integer studentId;


    @SerializedName("role")
    @Expose
    private String role;


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

    /**
     * @return The studentId
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * @param studentId The student_id
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
