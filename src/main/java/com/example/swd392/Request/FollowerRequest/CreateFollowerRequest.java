package com.example.swd392.Request.FollowerRequest;

import com.example.swd392.model.User;
import lombok.Data;

@Data
public class CreateFollowerRequest {
    private User user;
    private User followerUser;
    private int userId;
    private int followerUserId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFollowerUser() {
        return followerUser;
    }

    public void setFollowerUser(User followerUser) {
        this.followerUser = followerUser;
    }

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