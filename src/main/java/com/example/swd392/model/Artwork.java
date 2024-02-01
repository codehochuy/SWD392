package com.example.swd392.model;

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

    @Column(name = "PostedAt")
    private LocalDateTime postedAt;

    @ManyToOne
    @JoinColumn(name = "UsersID")
    private User user;
}
