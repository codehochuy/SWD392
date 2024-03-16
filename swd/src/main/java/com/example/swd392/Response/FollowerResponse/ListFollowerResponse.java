package com.example.swd392.Response.FollowerResponse;

public class ListFollowerResponse {
    private String status;
    private String message;
    private Object payload; // List<Follower> or message if empty

    public ListFollowerResponse(String status, String message, Object payload) {
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
