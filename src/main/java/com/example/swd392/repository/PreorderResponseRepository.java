package com.example.swd392.repository;

import com.example.swd392.model.PreorderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreorderResponseRepository extends JpaRepository<PreorderResponse, Integer> {
    // Additional methods if needed
}