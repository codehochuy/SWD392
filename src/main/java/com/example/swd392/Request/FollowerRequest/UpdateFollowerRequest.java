package com.example.swd392.Request.FollowerRequest;

public class UpdateFollowerRequest {
    private int userId;
    private int followerUserId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFollowerUserId() {
        return followerUserId;
    }

    public void setFollowerUserId(int followerUserId) {
        this.followerUserId = followerUserId;
    }
}
