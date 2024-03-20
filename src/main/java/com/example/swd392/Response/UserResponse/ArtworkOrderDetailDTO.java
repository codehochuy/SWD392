package com.example.swd392.Response.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtworkOrderDetailDTO {
    private int artworkId;
    private String artworkName;
    private String artworkUrl;
    private LocalDateTime postedAt;
    private double price;
    private int likeCount;
    private int commentCount;
    private int buyCount;
    private int userId;
    private int orderDetailId;
}
