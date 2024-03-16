package com.example.swd392.Request.LikeRequest;

import com.example.swd392.model.User;
import com.example.swd392.model.Artwork;
import lombok.Data;

@Data
public class CreateLikeRequest {
    private int userId;
    private int artworkId;

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
}