package com.example.swd392.repository;

import com.example.swd392.model.Artwork;
import com.example.swd392.model.Cart;
import com.example.swd392.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,Integer> {

    List<Cart> findByUser(User userId);

    Optional<Cart> findByUserAndArtwork(User user, Artwork artwork);
}
