package com.example.xiaojin20135.basemodule.retrofit.bean;

/**
 * Created by lixiaojin on 2018-07-12.
 * 功能描述：
 */

public class ActionResult {
    private String message;
    private Boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String toString(){
        return "message = " + message + ";success = " +success;
    }
}
