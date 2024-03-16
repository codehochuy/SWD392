package com.example.swd392.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Rate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RateID")
    private int rateId;

    @Column(name = "RateStar")
    private int rateStar;

    @ManyToOne
    @JoinColumn(name = "ArtworkID")
    private Artwork artwork;

    @ManyToOne
    @JoinColumn(name = "UsersID")
    private User user;
}
