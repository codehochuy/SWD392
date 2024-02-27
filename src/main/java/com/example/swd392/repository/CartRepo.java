package com.example.swd392.repository;

import com.example.swd392.model.Cart;
import com.example.swd392.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart,Integer> {

    List<Cart> findByUser(User userId);
}
