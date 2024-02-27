package com.example.swd392.Request.CommentRequest;

public class CreateCommentRequest {
    private int userId;
    private int artworkId;
    private String commentText;

    // Constructor, getters, and setters
    public CreateCommentRequest() {}

    public CreateCommentRequest(int userId, int artworkId, String commentText) {
        this.userId = userId;
        this.artworkId = artworkId;
        this.commentText = commentText;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(int artworkId) {
        this.artworkId = artworkId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}