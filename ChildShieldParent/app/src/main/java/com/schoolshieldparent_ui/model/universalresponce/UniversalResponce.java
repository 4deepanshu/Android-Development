package com.schoolshieldparent_ui.model.universalresponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mb gpson 18/7/16.
 */
public class UniversalResponce {

    @SerializedName("result")
    @Expose
    private Result result;

    /**
     * @return The result
     */
    public Result getResult() {
        return result;
    }

    /**
     * @param result The result
     */
    public void setResult(Result result) {
        this.result = result;
    }

}
