package com.example.swd392.repository;

import com.example.swd392.model.Artwork;
import com.example.swd392.model.Like;
import com.example.swd392.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    boolean existsByUserAndArtwork(User user, Artwork artwork);
    Like findByUserAndArtwork(User user, Artwork artwork);
}