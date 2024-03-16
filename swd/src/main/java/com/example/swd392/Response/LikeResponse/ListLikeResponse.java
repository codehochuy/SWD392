package com.example.swd392.Response.LikeResponse;

public class ListLikeResponse {
    private String status;
    private String message;
    private Object payload;

    public ListLikeResponse(String status, String message, Object payload) {
        this.status = status;
        this.message = message;
        this.payload = payload;
    }

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

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
