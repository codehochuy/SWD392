package com.example.swd392.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartID")
    private int cartId;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "ArtworkID")
    private Artwork artwork;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "UsersID")
    private User user;
}
