package com.example.swd392.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Artwork")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ArtworkID")
    private int artworkId;

    @Column(name = "ArtworkName", length = 30)
    private String artworkName;

    @Lob
    @Column(name = "ArtworkUrl", length = 1000)
    private byte[] artworkUrl;

    @Column(name = "PostedAt")
    private LocalDateTime postedAt;

    @Column(name = "Price")
    private double price;

    @Column(name = "LikeCount")
    private int likeCount;

    @Column(name = "CommentCount")
    private int commentCount;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "UsersID")
    private User user;
}
