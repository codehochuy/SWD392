package com.example.swd392.Response.LikeResponse;


import com.example.swd392.model.Like;

public class FindLikeResponse {
    private String status;
    private String message;
    private Like like;

    public FindLikeResponse(String status, String message, Like like) {
        this.status = status;
        this.message = message;
        this.like = like;
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

    public Like getLike() {
        return like;
    }

    public void setLike(Like like) {
        this.like = like;
    }
}
