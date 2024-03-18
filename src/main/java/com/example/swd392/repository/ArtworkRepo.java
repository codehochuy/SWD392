package com.example.swd392.repository;

import com.example.swd392.model.Artwork;
import com.example.swd392.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ArtworkRepo extends JpaRepository<Artwork,Integer> {
    Optional<Artwork> findByArtworkId(int artworkID);
    Optional<Artwork> findArtworkByArtworkId(int id);
    @Query("SELECT a FROM Artwork a WHERE " +
            "(:artworkName IS NULL OR a.artworkName LIKE %:artworkName%) AND " +
            "(:price IS NULL OR a.price = :price)")
    List<Artwork> findArtworksByFilter(String artworkName, Double price);

    List<Artwork> findByUser(User user);
}

