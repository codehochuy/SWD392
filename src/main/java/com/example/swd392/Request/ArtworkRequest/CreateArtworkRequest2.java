package com.example.swd392.Request.ArtworkRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateArtworkRequest2 {
    private String artworkName;
    private double price;
    private int creator;
    private String url;
}
