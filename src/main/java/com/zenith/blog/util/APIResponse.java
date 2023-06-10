package com.zenith.blog.util;

public class APIResponse {
    private boolean success;
    private String status;
    private  String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public APIResponse(boolean success, String status, String message) {
        this.success = success;
        this.status = status;
        this.message = message;
    }

    public APIResponse() {
    }
}
