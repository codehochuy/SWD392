package com.example.swd392.Response.FollowerResponse;

import com.example.swd392.model.Follower;

public class UpdateFollowerResponse {
    private String status;
    private String message;
    private Follower follower;

    public UpdateFollowerResponse(String status, String message, Follower follower) {
        this.status = status;
        this.message = message;
        this.follower = follower;
    }

}
