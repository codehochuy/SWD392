package com.example.swd392.Request.CommentRequest;

public class CreateCommentRequest {
    private String commentText;
    private Integer userId;
    private Integer artworkId;



    public CreateCommentRequest() {
    }

    public CreateCommentRequest(String commentText, Integer userId, Integer artworkId) {
        this.commentText = commentText;
        this.userId = userId;
        this.artworkId = artworkId;
    }

    // Getters and setters
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(Integer artworkId) {
        this.artworkId = artworkId;
    }
}