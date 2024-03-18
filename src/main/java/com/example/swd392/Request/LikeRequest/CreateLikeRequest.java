package com.example.swd392.Request.LikeRequest;

import com.example.swd392.model.User;
import com.example.swd392.model.Artwork;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLikeRequest {
    private int userId;
    private int artworkId;


}