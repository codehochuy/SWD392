package com.example.swd392.repository;

import com.example.swd392.model.Artwork;
import com.example.swd392.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtworkRepo extends JpaRepository<Artwork,Integer> {
}
