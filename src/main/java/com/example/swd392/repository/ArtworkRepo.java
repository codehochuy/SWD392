package com.example.swd392.repository;

import com.example.swd392.model.Artwork;
import com.example.swd392.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtworkRepo extends JpaRepository<Artwork,Integer> {
    Optional<Artwork> findByArtworkId(int artworkID);
}
