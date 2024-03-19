package com.example.swd392.Request.ArtworkRequest;

import com.example.swd392.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateArtworkRequest {
    private String artworkName;
    private double price;
    private int creator;
    private String url;
}
